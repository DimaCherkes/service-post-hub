package com.posthub.iam_service.model.dto.post;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostSearchDTO implements Serializable {
    private Integer id;
    private String title;
    private String content;
    private Integer likes;
    private LocalDateTime createdAt;
    private Boolean isDeleted;
    private String createdBy;
}