package com.posthub.iam_service.model.request.user;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserRequest {

    @NotBlank(message = "Username cannot be empty.")
    private String username;
    @NotBlank(message = "Email cannot be empty.")
    private String email;

}
