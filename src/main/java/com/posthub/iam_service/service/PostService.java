package com.posthub.iam_service.service;

import com.posthub.iam_service.model.dto.post.PostDTO;
import com.posthub.iam_service.model.dto.post.PostSearchDTO;
import com.posthub.iam_service.model.request.post.NewPostRequest;
import com.posthub.iam_service.model.request.post.PostSearchRequest;
import com.posthub.iam_service.model.request.post.UpdatePostRequest;
import com.posthub.iam_service.model.response.IamResponse;
import com.posthub.iam_service.model.response.PaginationResponse;
import jakarta.validation.constraints.NotNull;
import lombok.NonNull;
import org.springframework.data.domain.Pageable;

public interface PostService {

    PostDTO getById(@NotNull Integer postId);

    PostDTO createPost(@NotNull Integer userId, @NonNull NewPostRequest newPostRequest);

    PostDTO updatePost(@NotNull Integer postId, @NotNull UpdatePostRequest request);

    void softDeletePost(@NotNull Integer postId);

    IamResponse<PaginationResponse<PostSearchDTO>> findAllPosts(Pageable pageable);

    IamResponse<PaginationResponse<PostSearchDTO>> searchPosts(@NonNull PostSearchRequest request, Pageable pageable);

}
