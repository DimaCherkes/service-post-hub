package com.posthub.iam_service.advice;

import com.posthub.iam_service.model.exception.DataExistException;
import com.posthub.iam_service.model.response.IamResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@ControllerAdvice
public class DefaultControllerAdvice {

    @ExceptionHandler
    @ResponseBody
    protected ResponseEntity<? extends Serializable> handleNotFoundException(Exception ex) {
        log.warn(ex.getMessage());

        return new ResponseEntity<>(
                IamResponse.createWithError(ex.getMessage()),
                HttpStatusCode.valueOf(HttpStatus.NOT_FOUND.value())
        );
    }

    @ExceptionHandler(DataExistException.class)
    @ResponseBody
    protected ResponseEntity<? extends Serializable> handleDataExistsException(DataExistException ex) {
        log.warn(ex.getMessage());

        return new ResponseEntity<>(
                IamResponse.createWithError(ex.getMessage()),
                HttpStatusCode.valueOf(HttpStatus.CONFLICT.value())
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseBody
    protected ResponseEntity<Map<String, String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        log.warn(ex.getMessage());

        Map<String, String> errors = new HashMap<>();
        for (ObjectError error : ex.getBindingResult().getAllErrors()) {
            String errorMessage = error.getDefaultMessage();
            errors.put("error", errorMessage);
        }

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }

}
