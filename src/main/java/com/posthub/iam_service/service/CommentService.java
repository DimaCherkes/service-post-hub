package com.posthub.iam_service.service;

import com.posthub.iam_service.model.dto.comment.CommentDTO;
import com.posthub.iam_service.model.request.comment.CommentRequest;
import jakarta.validation.constraints.NotNull;

public interface CommentService {

    CommentDTO getCommentById(@NotNull Integer id);

    CommentDTO createComment(@NotNull CommentRequest request);

}
