package com.posthub.iam_service.service;

import com.posthub.iam_service.model.dto.post.PostDTO;
import com.posthub.iam_service.model.request.post.NewPostRequest;
import com.posthub.iam_service.model.request.post.UpdatePostRequest;
import jakarta.validation.constraints.NotNull;

public interface PostService {

    PostDTO getById(@NotNull Integer postId);

    PostDTO createPost(@NotNull NewPostRequest newPostRequest);

    PostDTO updatePost(@NotNull Integer postId, @NotNull UpdatePostRequest request);

    void softDeletePost(@NotNull Integer postId);

}
