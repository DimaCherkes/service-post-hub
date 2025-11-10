package com.posthub.iam_service.controller;

import com.posthub.iam_service.model.constants.ApiLogMessage;
import com.posthub.iam_service.model.constants.ApiMessage;
import com.posthub.iam_service.model.dto.comment.CommentDTO;
import com.posthub.iam_service.model.request.comment.CommentRequest;
import com.posthub.iam_service.model.request.comment.UpdateCommentRequest;
import com.posthub.iam_service.model.response.IamResponse;
import com.posthub.iam_service.service.CommentService;
import com.posthub.iam_service.utils.ApiUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/comments")
public class CommentController {
    private final CommentService commentService;

    @GetMapping("${end.point.id}")
    public ResponseEntity<IamResponse<CommentDTO>> getCommentById(
            @PathVariable(name = "id") Integer id) {
        log.trace(ApiLogMessage.NAME_OF_CURRENT_METHOD.getValue(), ApiUtils.getMethodName());
        CommentDTO commentDto = commentService.getCommentById(id);

        return ResponseEntity.ok(IamResponse.createSuccessful(commentDto));
    }

    @PostMapping("${end.point.create}")
    public ResponseEntity<IamResponse<CommentDTO>> createComment(
            @RequestBody @Valid CommentRequest request) {
        log.trace(ApiLogMessage.NAME_OF_CURRENT_METHOD.getValue(), ApiUtils.getMethodName());
        CommentDTO commentDto = commentService.createComment(request);

        return ResponseEntity.ok(IamResponse.createSuccessful(commentDto));
    }

    @PutMapping("${end.point.id}")
    public ResponseEntity<IamResponse<CommentDTO>> updateComment(
            @PathVariable(name = "id") Integer id,
            @RequestBody @Valid UpdateCommentRequest request) {
        log.trace(ApiLogMessage.NAME_OF_CURRENT_METHOD.getValue(), ApiUtils.getMethodName());
        CommentDTO commentDto = commentService.updateComment(id, request);

        return ResponseEntity.ok(IamResponse.createSuccessful(ApiMessage.COMMENT_UPDATED.getMessage(), commentDto));
    }

    @DeleteMapping("${end.point.id}")
    public ResponseEntity<Void> softDeleteById(
            @PathVariable(name = "id") Integer commentId) {
        log.trace(ApiLogMessage.NAME_OF_CURRENT_METHOD.getValue(), ApiUtils.getMethodName());

        commentService.softDelete(commentId);
        return ResponseEntity.ok().build();
    }

}
