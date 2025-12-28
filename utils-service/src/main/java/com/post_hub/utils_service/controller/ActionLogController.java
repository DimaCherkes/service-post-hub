package com.post_hub.utils_service.controller;

import com.post_hub.utils_service.model.constant.ApiLogMessage;
import com.post_hub.utils_service.model.dto.ActionLogDTO;
import com.post_hub.utils_service.model.response.UtilsResponse;
import com.post_hub.utils_service.service.ActionLogService;
import com.post_hub.utils_service.utils.ApiUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("${end.point.actionLogs}")
@RequiredArgsConstructor
public class ActionLogController {

    private final ActionLogService service;

    @GetMapping("${end.point.id}")
    public ResponseEntity<UtilsResponse<ActionLogDTO>> getActionLogById(
            @PathVariable("id") Integer logId,
            @RequestParam(name = "userId", required = false) Integer userId) {

        log.info(ApiLogMessage.NAME_OF_CURRENT_METHOD.getValue(), ApiUtils.getMethodName());

        ActionLogDTO actionLog = service.getActionLog(logId, userId);
        UtilsResponse<ActionLogDTO> response = UtilsResponse.createSuccessful(actionLog);
        return ResponseEntity.ok(response);
    }


}
