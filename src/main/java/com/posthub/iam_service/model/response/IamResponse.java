package com.posthub.iam_service.model.response;

import com.posthub.iam_service.model.constants.ApiMessage;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;

public record IamResponse<R extends Serializable>(
        String message,
        R payload,
        boolean success
) implements Serializable {

    public static <R extends Serializable> IamResponse<R> createSuccessful(R payload) {
        return new IamResponse<>(StringUtils.EMPTY, payload, true);
    }

    public static <R extends Serializable> IamResponse<R> createSuccessful(String message, R payload) {
        return new IamResponse<>(message, payload, true);
    }
    
    public static <R extends Serializable> IamResponse<R> createWithError(String message) {
        return new IamResponse<>(message, null, false);
    }

    public static <R extends Serializable> IamResponse<R> createSuccessfulWithNewToken(R payload) {
        return new IamResponse<>(ApiMessage.TOKEN_CREATED_OR_UPDATED.getMessage(), payload, true);
    }
}
