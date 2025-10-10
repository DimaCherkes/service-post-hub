package com.posthub.iam_service.model.dto.post;

import java.io.Serializable;
import java.time.LocalDateTime;

public record PostSearchDTO(
        Integer id,
        String title,
        String content,
        Integer likes,
        LocalDateTime createdAt,
        Boolean isDeleted
) implements Serializable {
}
