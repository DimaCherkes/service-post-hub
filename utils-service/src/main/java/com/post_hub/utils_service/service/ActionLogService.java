package com.post_hub.utils_service.service;

import com.post_hub.utils_service.entity.ActionLog;
import com.post_hub.utils_service.kafka.model.UtilMessage;
import com.post_hub.utils_service.model.dto.ActionLogDTO;
import com.post_hub.utils_service.model.request.ActionLogIsReadRequest;
import com.post_hub.utils_service.model.response.ActionLogIsReadResponse;
import com.post_hub.utils_service.model.response.PaginationResponse;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Pageable;

public interface ActionLogService {

    ActionLogDTO getActionLog(Integer logId, Integer userId);

    PaginationResponse<ActionLogDTO> findAll(Pageable pageable);

    ActionLogIsReadResponse setIsReadEqualsTrue(@NotNull ActionLogIsReadRequest request);

    ActionLog saveLogFromKafkaMessage(UtilMessage message);

}
