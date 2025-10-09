package com.posthub.iam_service.model.dto.post;

import com.posthub.iam_service.model.entity.Post;
import lombok.Builder;

import java.io.Serializable;
import java.time.LocalDateTime;

@Builder
public record PostDTO (
        Integer id,
        String title,
        String content,
        Integer likes,
        LocalDateTime createdAt
) implements Serializable {

    public static PostDTO mapToDto(Post post) {
        return PostDTO.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .likes(post.getLikes())
                .createdAt(post.getCreatedAt())
                .build();
    }
}
