package com.posthub.iam_service.service;

import com.posthub.iam_service.model.entity.RefreshToken;
import com.posthub.iam_service.model.entity.User;

public interface RefreshTokenService {

    RefreshToken generateOrUpdateRefreshToken(User user);

    RefreshToken validateAndRefreshToken(String refreshToken);

}
