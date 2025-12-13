package com.posthub.iam_service.model.request.comment;

import com.posthub.iam_service.model.enums.CommentSortField;
import lombok.Data;

@Data
public class CommentSearchRequest {

    private String message;
    private String createdBy;
    private Integer postId;

    private Boolean deleted;
    private String keyword;
    private CommentSortField sortField;

}
