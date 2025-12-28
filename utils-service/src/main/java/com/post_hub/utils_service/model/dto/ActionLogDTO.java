package com.post_hub.utils_service.model.dto;

import com.post_hub.utils_service.entity.ActionLog;
import com.post_hub.utils_service.model.enums.PostHubService;
import com.post_hub.utils_service.model.enums.PriorityType;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ActionLogDTO {
    private Integer id;
    private Integer userId;

    private ActionLog actionLog;
    private PriorityType priorityType;
    private PostHubService service;

    private LocalDateTime createdAt;
    private String message;
    private Boolean isRead;
}
