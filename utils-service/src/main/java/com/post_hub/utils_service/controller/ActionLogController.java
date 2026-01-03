package com.post_hub.utils_service.controller;

import com.post_hub.utils_service.model.constant.ApiLogMessage;
import com.post_hub.utils_service.model.dto.ActionLogDTO;
import com.post_hub.utils_service.model.request.ActionLogIsReadRequest;
import com.post_hub.utils_service.model.response.ActionLogIsReadResponse;
import com.post_hub.utils_service.model.response.PaginationResponse;
import com.post_hub.utils_service.model.response.UtilsResponse;
import com.post_hub.utils_service.service.ActionLogService;
import com.post_hub.utils_service.utils.ApiUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    @GetMapping("${end.point.all}")
    public ResponseEntity<UtilsResponse<PaginationResponse<ActionLogDTO>>> findAllActionLogs(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int limit) {
        log.info(ApiLogMessage.NAME_OF_CURRENT_METHOD.getValue(), ApiUtils.getMethodName());

        Pageable pageable = PageRequest.of(page, limit);
        PaginationResponse<ActionLogDTO> all = service.findAll(pageable);
        UtilsResponse<PaginationResponse<ActionLogDTO>> response = UtilsResponse.createSuccessful(all);
        return ResponseEntity.ok(response);
    }

    @PutMapping("${end.point.markAsRead}")
    public ResponseEntity<UtilsResponse<ActionLogIsReadResponse>> setIsReadEqualsTrue(
            @RequestBody @Valid ActionLogIsReadRequest request) {
        log.info(ApiLogMessage.NAME_OF_CURRENT_METHOD.getValue(), ApiUtils.getMethodName());

        ActionLogIsReadResponse actionLogIsReadResponse = service.setIsReadEqualsTrue(request);
        UtilsResponse<ActionLogIsReadResponse> response = UtilsResponse.createSuccessful(actionLogIsReadResponse);
        return ResponseEntity.ok(response);
    }

}
