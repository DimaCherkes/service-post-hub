package com.post_hub.utils_service.kafka.model;

import com.post_hub.utils_service.model.enums.ActionType;
import com.post_hub.utils_service.model.enums.PostHubService;
import com.post_hub.utils_service.model.enums.PriorityType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UtilMessage {
    private Integer userId;
    private ActionType actionType;
    private PriorityType priorityType;
    private PostHubService service;
    private String message;
}
