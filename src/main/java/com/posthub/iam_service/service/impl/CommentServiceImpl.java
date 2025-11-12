package com.posthub.iam_service.service.impl;

import com.posthub.iam_service.mapper.CommentMapper;
import com.posthub.iam_service.model.constants.ApiErrorMessage;
import com.posthub.iam_service.model.dto.comment.CommentDTO;
import com.posthub.iam_service.model.dto.comment.CommentSearchDTO;
import com.posthub.iam_service.model.entity.Comment;
import com.posthub.iam_service.model.entity.Post;
import com.posthub.iam_service.model.entity.User;
import com.posthub.iam_service.model.exception.NotFoundException;
import com.posthub.iam_service.model.request.comment.CommentSearchRequest;
import com.posthub.iam_service.model.request.comment.NewCommentRequest;
import com.posthub.iam_service.model.request.comment.UpdateCommentRequest;
import com.posthub.iam_service.model.response.IamResponse;
import com.posthub.iam_service.model.response.PaginationResponse;
import com.posthub.iam_service.repository.CommentRepository;
import com.posthub.iam_service.repository.PostRepository;
import com.posthub.iam_service.repository.UserRepository;
import com.posthub.iam_service.repository.criteria.CommentSearchCriteria;
import com.posthub.iam_service.security.validation.AccessValidator;
import com.posthub.iam_service.service.CommentService;
import com.posthub.iam_service.utils.ApiUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final ApiUtils apiUtils;
    private final UserRepository userRepository;
    private final PostRepository postRepository;
    private final AccessValidator accessValidator;

    @Override
    public CommentDTO getCommentById(Integer id) {
        Comment comment = commentRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new NotFoundException(ApiErrorMessage.COMMENT_NOT_FOUND_BY_ID.getMessage(id)));

        return commentMapper.toDto(comment);
    }

    @Override
    public CommentDTO createComment(NewCommentRequest request) {
        Integer userId = apiUtils.getUserIdFromAuthentication();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(ApiErrorMessage.USER_NOT_FOUND_BY_ID.getMessage(userId)));

        Post post = postRepository.findByIdAndDeletedFalse(request.getPostId())
                .orElseThrow(() -> new NotFoundException(ApiErrorMessage.POST_NOT_FOUND_BY_ID.getMessage(request.getPostId())));

        Comment comment = commentMapper.createComment(request, user, post);
        comment.setDeleted(false);
        commentRepository.save(comment);
        postRepository.save(post);

        return commentMapper.toDto(comment);
    }

    @Override
    public CommentDTO updateComment(Integer id, UpdateCommentRequest request) {
        Comment comment = commentRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new NotFoundException(ApiErrorMessage.COMMENT_NOT_FOUND_BY_ID.getMessage(id)));

        accessValidator.validateAdminOrOwnerAccess(comment.getUser().getId());

        if (request.getPostId() != null) {
            Post post = postRepository.findByIdAndDeletedFalse(request.getPostId())
                    .orElseThrow(() -> new NotFoundException(ApiErrorMessage.POST_NOT_FOUND_BY_ID.getMessage(request.getPostId())));
            comment.setPost(post);
        }

        commentMapper.updateComment(comment, request);
        comment = commentRepository.save(comment);

        return commentMapper.toDto(comment);
    }

    @Override
    public void softDelete(Integer id) {
        Comment comment = commentRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new NotFoundException(ApiErrorMessage.COMMENT_NOT_FOUND_BY_ID.getMessage(id)));

        accessValidator.validateAdminOrOwnerAccess(comment.getUser().getId());

        comment.setDeleted(true);
        commentRepository.save(comment);
    }

    @Override
    public IamResponse<PaginationResponse<CommentSearchDTO>> findAll(Pageable pageable) {
        Page<CommentSearchDTO> comments = commentRepository.findAll(pageable)
                .map(commentMapper::toCommentSearchDto);

        PaginationResponse<CommentSearchDTO> paginationResponse = new PaginationResponse<>(
                comments.getContent(),
                new PaginationResponse.Pagination(
                        comments.getTotalElements(),
                        pageable.getPageSize(),
                        comments.getNumber() + 1,
                        comments.getTotalPages()
                )
        );

        return IamResponse.createSuccessful(paginationResponse);
    }

    @Override
    public IamResponse<PaginationResponse<CommentSearchDTO>> searchComments(CommentSearchRequest request, Pageable pageable) {
        Specification<Comment> specification = new CommentSearchCriteria(request);

        Page<CommentSearchDTO> commentsPage = commentRepository.findAll(specification, pageable)
                .map(commentMapper::toCommentSearchDto);

        PaginationResponse<CommentSearchDTO> response = PaginationResponse.<CommentSearchDTO>builder()
                .content(commentsPage.getContent())
                .pagination((PaginationResponse.Pagination.builder()
                        .total(commentsPage.getTotalElements())
                        .limit(pageable.getPageSize())
                        .page(commentsPage.getNumber() + 1)
                        .pages(commentsPage.getTotalPages())
                        .build()))
                .build();

        return IamResponse.createSuccessful(response);
    }

}
