package com.posthub.iam_service.model.response;

import lombok.Builder;

import java.io.Serializable;
import java.util.List;

@Builder
public record PaginationResponse<T>(
        List<T> content,
        Pagination pagination
) implements Serializable {

    @Builder
    public record Pagination(
            long total,
            int limit,
            int page,
            int pages
    ) implements Serializable {
    }
}
