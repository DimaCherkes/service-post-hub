package com.posthub.iam_service.model.constants;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum ApiKafkaMessage {

    USER_CREATED("User '%s' was successfully created with ID: %s"),
    USER_UPDATED("User '%s' was successfully updated"),
    USER_DELETED("User '%s' was successfully deleted"),
    POST_CREATED("Post has been created by usedId: %s, postId: %s"),
    POST_UPDATED("Post with ID: '%s' was successfully updated"),
    POST_DELETED("Post with ID: '%s' was successfully deleted"),
    COMMENT_CREATED("Comment has been created by userId: %s, commentId: %s"),
    COMMENT_UPDATED("Comment with ID: '%s' has been updated by userId: %s"),
    COMMENT_DELETED("Comment with ID: '%s' was deleted by userId: %s"),
    ;

    private final String value;

    public String getMessage(Object... args) {
        return String.format(value, args);
    }
}
