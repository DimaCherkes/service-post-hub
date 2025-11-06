package com.posthub.iam_service.service;

import com.posthub.iam_service.model.dto.comment.CommentDTO;
import jakarta.validation.constraints.NotNull;

public interface CommentService {

    CommentDTO getCommentById(@NotNull Integer id);

}
