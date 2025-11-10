package com.posthub.iam_service.service;

import com.posthub.iam_service.model.dto.comment.CommentDTO;
import com.posthub.iam_service.model.request.comment.CommentRequest;
import com.posthub.iam_service.model.request.comment.UpdateCommentRequest;
import jakarta.validation.constraints.NotNull;

public interface CommentService {

    CommentDTO getCommentById(@NotNull Integer id);

    CommentDTO createComment(@NotNull CommentRequest request);

    CommentDTO updateComment(@NotNull Integer id, @NotNull UpdateCommentRequest request);

    void softDelete(@NotNull Integer id);

}
