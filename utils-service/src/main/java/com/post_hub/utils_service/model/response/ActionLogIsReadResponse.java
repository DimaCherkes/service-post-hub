package com.post_hub.utils_service.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class ActionLogIsReadResponse {
    private int updatedCount;
    private List<Integer> updatedIds;
    private List<Integer> skippedIds;
}
