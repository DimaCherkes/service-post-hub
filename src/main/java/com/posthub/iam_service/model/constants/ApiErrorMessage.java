package com.posthub.iam_service.model.constants;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ApiErrorMessage {
    POST_NOT_FOUND_BY_ID("Post with ID: %s was not found"),
    POST_ALREADY_EXISTS("Post with title: %s already exists"),
    USER_NOT_FOUND_BY_ID("User with ID: %s was not found"),
    ;

    private final String message;

    public String getMessage(Object... args) {
        return String.format(message, args);
    }

}
