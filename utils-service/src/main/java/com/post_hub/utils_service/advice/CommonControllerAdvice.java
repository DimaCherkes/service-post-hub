package com.post_hub.utils_service.advice;

import com.post_hub.utils_service.model.constant.ApiConstants;
import com.post_hub.utils_service.model.constant.ApiErrorMessage;
import com.post_hub.utils_service.model.exception.AuthException;
import com.post_hub.utils_service.model.exception.BadRequestException;
import com.post_hub.utils_service.model.exception.InternalException;
import com.post_hub.utils_service.model.exception.NotFoundException;
import com.post_hub.utils_service.model.response.UtilsResponse;
import com.post_hub.utils_service.utils.ApiUtils;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.AccessDeniedException;
import java.time.zone.ZoneRulesException;
import java.util.Arrays;
import java.util.Objects;

@Slf4j
@ControllerAdvice(annotations = RestController.class)
@RequiredArgsConstructor
public class CommonControllerAdvice {

    @ExceptionHandler(NotFoundException.class)
    @ResponseBody
    protected ResponseEntity<String> handleNotFoundException(NotFoundException exception) {
        logStackTrace(exception);

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(exception.getMessage());
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseBody
    public ResponseEntity<UtilsResponse<Void>> handleAccessDeniedException(AccessDeniedException exception) {
        logStackTrace(exception);
        return ResponseEntity.status(HttpStatus.FORBIDDEN)
                .body(UtilsResponse.createFailed(exception.getMessage()));
    }

    @ExceptionHandler(ZoneRulesException.class)
    @ResponseBody
    protected ResponseEntity<UtilsResponse<String>> handleZoneRulesException(ZoneRulesException exception) {
        logStackTrace(exception);
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new UtilsResponse<>(exception.getMessage(), null, false));
    }

    @ExceptionHandler(EntityNotFoundException.class)
    @ResponseBody
    protected ResponseEntity<UtilsResponse<String>> handleEntityNotFoundException(EntityNotFoundException exception) {
        logStackTrace(exception);

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new UtilsResponse<>(exception.getMessage(), null, false));
    }

    @ExceptionHandler(BadRequestException.class)
    @ResponseBody
    public ResponseEntity<UtilsResponse<String>> handleBadRequestException(BadRequestException exception) {
        logStackTrace(exception);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new UtilsResponse<>(exception.getMessage(), null, false));
    }

    @ExceptionHandler(AuthException.class)
    @ResponseBody
    protected ResponseEntity<UtilsResponse<String>> handleAuthException(AuthException exception) {
        logStackTrace(exception);
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(new UtilsResponse<>(exception.getMessage(), null, false));
    }

    @ExceptionHandler(InternalException.class)
    @ResponseBody
    protected ResponseEntity<String> handleUndefinedException(InternalException exception) {
        logStackTrace(exception);
        return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body(ApiErrorMessage.UNEXPECTED_ERROR.getMessage());
    }

    private void logStackTrace(Exception exception) {
        StringBuilder stackTrace = new StringBuilder();

        stackTrace.append(ApiConstants.ANSI_RED);
        stackTrace.append(exception.getMessage()).append(ApiConstants.BREAK_LINE);

        if (Objects.nonNull(exception.getCause())) {
            stackTrace.append(exception.getCause().getMessage()).append(ApiConstants.BREAK_LINE);
        }

        Arrays.stream(exception.getStackTrace())
                .filter(st -> st.getClassName().startsWith(ApiUtils.getTodayDateString()))
                .forEach(st -> stackTrace
                        .append(st.getClassName())
                        .append(".")
                        .append(st.getMethodName())
                        .append(" (")
                        .append(st.getLineNumber())
                        .append(") ")
                );

        log.error(stackTrace.append(ApiConstants.ANSI_WHITE).toString());
    }

}
