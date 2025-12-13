package com.posthub.iam_service.model.request.post;

import com.posthub.iam_service.model.enums.PostSortField;
import lombok.Data;

@Data
public class PostSearchRequest {

    private String title;
    private String content;
    private Integer likes;

    private Boolean deleted;
    private String keyword;
    private PostSortField sortField;
}
