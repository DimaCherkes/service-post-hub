package com.posthub.iam_service.model.dto.user;

import com.posthub.iam_service.model.enums.RegistrationStatus;

import java.io.Serializable;
import java.time.LocalDateTime;

public record UserDTO(
        Integer id,
        String username,
        String email,
        LocalDateTime created,
        LocalDateTime lastLogin,

        RegistrationStatus registrationStatus
) implements Serializable {
}
