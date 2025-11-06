package com.posthub.iam_service.service.impl;

import com.posthub.iam_service.mapper.CommentMapper;
import com.posthub.iam_service.model.constants.ApiErrorMessage;
import com.posthub.iam_service.model.dto.comment.CommentDTO;
import com.posthub.iam_service.model.entity.Comment;
import com.posthub.iam_service.model.entity.Post;
import com.posthub.iam_service.model.entity.User;
import com.posthub.iam_service.model.exception.NotFoundException;
import com.posthub.iam_service.model.request.comment.CommentRequest;
import com.posthub.iam_service.repository.CommentRepository;
import com.posthub.iam_service.repository.PostRepository;
import com.posthub.iam_service.repository.UserRepository;
import com.posthub.iam_service.service.CommentService;
import com.posthub.iam_service.utils.ApiUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    @Override
    public CommentDTO getCommentById(Integer id) {
        Comment comment = commentRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new NotFoundException(ApiErrorMessage.COMMENT_NOT_FOUND_BY_ID.getMessage(id)));

        return commentMapper.toDto(comment);
    }

    @Override
    public CommentDTO createComment(CommentRequest request) {
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

}
