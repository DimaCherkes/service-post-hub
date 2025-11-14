package com.posthub.iam_service.controller;

import com.posthub.iam_service.model.constants.ApiLogMessage;
import com.posthub.iam_service.utils.ApiUtils;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/iamServiceInner")
@RequiredArgsConstructor
@Hidden
public class InnerController {

    @GetMapping("/healthCheck")
    public ResponseEntity<Void> healthCheck() {
        log.trace(ApiLogMessage.NAME_OF_CURRENT_METHOD.getValue(), ApiUtils.getMethodName());

        return ResponseEntity.ok().build();
    }
}
