package com.post_hub.utils_service.service;

import com.post_hub.utils_service.model.dto.ActionLogDTO;

public interface ActionLogService {

    ActionLogDTO getActionLog(Integer logId, Integer userId);

}
