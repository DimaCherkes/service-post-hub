package com.posthub.iam_service.advice;

import com.posthub.iam_service.model.response.IamResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.Serializable;

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

}
