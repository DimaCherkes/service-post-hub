package com.posthub.iam_service.service.impl;

import com.posthub.iam_service.mapper.PostMapper;
import com.posthub.iam_service.model.constants.ApiErrorMessage;
import com.posthub.iam_service.model.dto.post.PostDTO;
import com.posthub.iam_service.model.dto.post.PostSearchDTO;
import com.posthub.iam_service.model.entity.Post;
import com.posthub.iam_service.model.entity.User;
import com.posthub.iam_service.model.exception.DataExistException;
import com.posthub.iam_service.model.exception.NotFoundException;
import com.posthub.iam_service.model.request.post.NewPostRequest;
import com.posthub.iam_service.model.request.post.PostSearchRequest;
import com.posthub.iam_service.model.request.post.UpdatePostRequest;
import com.posthub.iam_service.model.response.IamResponse;
import com.posthub.iam_service.model.response.PaginationResponse;
import com.posthub.iam_service.repository.PostRepository;
import com.posthub.iam_service.repository.UserRepository;
import com.posthub.iam_service.repository.criteria.PostSearchCriteria;
import com.posthub.iam_service.security.validation.AccessValidator;
import com.posthub.iam_service.service.PostService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final PostMapper postMapper;
    private final AccessValidator accessValidator;

    @Override
    public PostDTO getById(@NotNull Integer id) {
        Post post = postRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new NotFoundException(ApiErrorMessage.POST_NOT_FOUND_BY_ID.getMessage(id)));

        return postMapper.toPostDTO(post);
    }

    @Override
    public PostDTO createPost(@NotNull String username, @NotNull NewPostRequest newPostRequest) {
        if (postRepository.existsByTitle(newPostRequest.getTitle()))
            throw new DataExistException(ApiErrorMessage.POST_ALREADY_EXISTS.getMessage(newPostRequest.getTitle()));

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new DataExistException(ApiErrorMessage.USERNAME_NOT_FOUND.getMessage(username)));

        Post post = postMapper.createPost(newPostRequest);
        post.setUser(user);
        post.setCreatedBy(username);

        Post savedPost = postRepository.save(post);
        return postMapper.toPostDTO(savedPost);
    }

    @Override
    public PostDTO updatePost(@NotNull Integer postId, UpdatePostRequest request) {
        Post post = postRepository.findByIdAndDeletedFalse(postId)
                .orElseThrow(() -> new NotFoundException(ApiErrorMessage.POST_NOT_FOUND_BY_ID.getMessage(postId)));

        accessValidator.validateAdminOrOwnerAccess(post.getUser().getUsername(), post.getCreatedBy());

        if (postRepository.existsByTitle(request.getTitle()))
            throw new DataExistException(ApiErrorMessage.POST_ALREADY_EXISTS.getMessage(request.getTitle()));

        postMapper.updatePost(post, request);
        post.setUpdatedAt(LocalDateTime.now());
        post = postRepository.save(post);

        return postMapper.toPostDTO(post);
    }

    @Override
    public void softDeletePost(Integer postId) {
        Post post = postRepository.findByIdAndDeletedFalse(postId)
                .orElseThrow(() -> new NotFoundException(ApiErrorMessage.POST_NOT_FOUND_BY_ID.getMessage(postId)));

        accessValidator.validateAdminOrOwnerAccess(post.getUser().getUsername(), post.getCreatedBy());

        post.setDeleted(true);
        postRepository.save(post);
    }

    @Override
    public IamResponse<PaginationResponse<PostSearchDTO>> findAllPosts(Pageable pageable) {
        Page<PostSearchDTO> posts = postRepository.findAll(pageable)
                .map(postMapper::toPostSearchDTO);

        PaginationResponse<PostSearchDTO> paginationResponse = new PaginationResponse<>(
                posts.getContent(),
                new PaginationResponse.Pagination(
                        posts.getTotalElements(),
                        pageable.getPageSize(),
                        posts.getNumber() + 1,
                        posts.getTotalPages()
                )
        );

        return IamResponse.createSuccessful(paginationResponse);
    }

    @Override
    public IamResponse<PaginationResponse<PostSearchDTO>> searchPosts(@NotNull PostSearchRequest request, Pageable pageable) {
        Specification<Post> specification = new PostSearchCriteria(request);
        Page<PostSearchDTO> posts = postRepository.findAll(specification, pageable)
                .map(postMapper::toPostSearchDTO);

        PaginationResponse<PostSearchDTO> response = PaginationResponse.<PostSearchDTO>builder()
                .content(posts.getContent())
                .pagination(PaginationResponse.Pagination.builder()
                        .total(posts.getTotalElements())
                        .limit(pageable.getPageSize())
                        .page(posts.getNumber() + 1)
                        .pages(posts.getTotalPages())
                        .build())
                .build();

        return IamResponse.createSuccessful(response);
    }


}
