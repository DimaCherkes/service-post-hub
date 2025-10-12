package com.posthub.iam_service.model.response;

import java.io.Serializable;
import java.util.List;

public record PaginationResponse<T>(
        List<T> content,
        Pagination pagination
) implements Serializable {

    public record Pagination(
            long total,
            int limit,
            int page,
            int pages
    ) implements Serializable {
    }
}
