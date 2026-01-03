package com.post_hub.utils_service.service;

import com.post_hub.utils_service.model.dto.ActionLogDTO;
import com.post_hub.utils_service.model.response.PaginationResponse;
import org.springframework.data.domain.Pageable;

public interface ActionLogService {

    ActionLogDTO getActionLog(Integer logId, Integer userId);

    PaginationResponse<ActionLogDTO> findAll(Pageable pageable);

}
