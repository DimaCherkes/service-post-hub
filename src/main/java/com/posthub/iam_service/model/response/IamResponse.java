package com.posthub.iam_service.model.response;

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

    public static <R extends Serializable> IamResponse<R> createWithError(String message) {
        return new IamResponse<>(message, null, false);
    }
}
