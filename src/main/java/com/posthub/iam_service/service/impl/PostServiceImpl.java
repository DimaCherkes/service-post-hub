package com.posthub.iam_service.service.impl;

import com.posthub.iam_service.mapper.PostMapper;
import com.posthub.iam_service.model.constants.ApiErrorMessage;
import com.posthub.iam_service.model.dto.post.PostDTO;
import com.posthub.iam_service.model.entity.Post;
import com.posthub.iam_service.model.exception.DataExistException;
import com.posthub.iam_service.model.exception.NotFoundException;
import com.posthub.iam_service.model.request.post.NewPostRequest;
import com.posthub.iam_service.model.request.post.UpdatePostRequest;
import com.posthub.iam_service.repository.PostRepository;
import com.posthub.iam_service.service.PostService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final PostMapper postMapper;

    @Override
    public PostDTO getById(@NotNull Integer id) {
        return postRepository.findByIdAndDeletedFalse(id)
                .map(postMapper::toPostDTO)
                .orElseThrow(() -> new NotFoundException(ApiErrorMessage.POST_NOT_FOUND_BY_ID.getMessage(id)));
    }

    @Override
    public PostDTO createPost(@NotNull NewPostRequest newPostRequest) {
        if (postRepository.existsByTitle(newPostRequest.getTitle()))
            throw new DataExistException(ApiErrorMessage.POST_ALREADY_EXISTS.getMessage(newPostRequest.getTitle()));

        Post post = postMapper.createPost(newPostRequest);
        Post savedPost = postRepository.save(post);
        return postMapper.toPostDTO(savedPost);
    }

    @Override
    public PostDTO updatePost(@NotNull Integer postId, UpdatePostRequest request) {
        Post post = postRepository.findByIdAndDeletedFalse(postId)
                .orElseThrow(() -> new NotFoundException(ApiErrorMessage.POST_NOT_FOUND_BY_ID.getMessage(postId)));

        postMapper.updatePost(post, request);
        post.setUpdatedAt(LocalDateTime.now());
        post = postRepository.save(post);

        return postMapper.toPostDTO(post);
    }

    @Override
    public void softDeletePost(Integer postId) {
        Post post = postRepository.findByIdAndDeletedFalse(postId)
                .orElseThrow(() -> new NotFoundException(ApiErrorMessage.POST_NOT_FOUND_BY_ID.getMessage(postId)));

        post.setDeleted(true);
        Post saved = postRepository.save(post);
    }


}
