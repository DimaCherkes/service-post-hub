package com.posthub.iam_service.service;

import com.posthub.iam_service.model.dto.post.PostDTO;
import com.posthub.iam_service.model.request.post.PostRequest;
import jakarta.validation.constraints.NotNull;

public interface PostService {

    PostDTO getById(@NotNull Integer id);

    PostDTO createPost(@NotNull PostRequest postRequest);
}
