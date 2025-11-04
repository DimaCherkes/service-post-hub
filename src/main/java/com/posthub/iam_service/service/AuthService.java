package com.posthub.iam_service.service;

import com.posthub.iam_service.model.request.user.LoginRequest;
import com.posthub.iam_service.model.dto.user.UserProfileDTO;
import com.posthub.iam_service.model.request.user.RegistrationUserRequest;

public interface AuthService {

    UserProfileDTO login(LoginRequest request);

    UserProfileDTO refreshAccessToken(String refreshToken);

    UserProfileDTO registerUser(RegistrationUserRequest request);

}
