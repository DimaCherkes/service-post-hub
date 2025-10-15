package com.posthub.iam_service.model.dto.user;

import com.posthub.iam_service.model.enums.RegistrationStatus;

import java.io.Serializable;
import java.time.LocalDateTime;

public record UserSearchDTO(
        String username,
        String email,
        LocalDateTime createdAt,
        RegistrationStatus registrationStatus,
        Boolean isDeleted
) implements Serializable {
}
