package com.posthub.iam_service.service;

import com.posthub.iam_service.model.dto.user.LoginRequest;
import com.posthub.iam_service.model.dto.user.UserProfileDTO;

public interface AuthService {

    UserProfileDTO login(LoginRequest request);

    UserProfileDTO refreshAccessToken(String refreshToken);

}
