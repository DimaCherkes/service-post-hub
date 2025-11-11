package com.posthub.iam_service.service;

import com.posthub.iam_service.model.dto.comment.CommentDTO;
import com.posthub.iam_service.model.dto.comment.CommentSearchDTO;
import com.posthub.iam_service.model.request.comment.CommentSearchRequest;
import com.posthub.iam_service.model.request.comment.NewCommentRequest;
import com.posthub.iam_service.model.request.comment.UpdateCommentRequest;
import com.posthub.iam_service.model.response.IamResponse;
import com.posthub.iam_service.model.response.PaginationResponse;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Pageable;

public interface CommentService {

    CommentDTO getCommentById(@NotNull Integer id);

    CommentDTO createComment(@NotNull NewCommentRequest request);

    CommentDTO updateComment(@NotNull Integer id, @NotNull UpdateCommentRequest request);

    void softDelete(@NotNull Integer id);

    IamResponse<PaginationResponse<CommentSearchDTO>> findAll(Pageable pageable);

    IamResponse<PaginationResponse<CommentSearchDTO>> searchComments(@NotNull CommentSearchRequest request, Pageable pageable);

}
