package com.posthub.iam_service.model.dto.post;

import lombok.Builder;

import java.io.Serializable;
import java.time.LocalDateTime;

@Builder
public record PostDTO (
        Integer id,
        String title,
        String content,
        Integer likes,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) implements Serializable {
}
