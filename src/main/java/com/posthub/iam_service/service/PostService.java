package com.posthub.iam_service.service;

import com.posthub.iam_service.model.dto.post.PostDTO;
import jakarta.validation.constraints.NotNull;

public interface PostService {

    PostDTO getById(@NotNull Integer id);
}
