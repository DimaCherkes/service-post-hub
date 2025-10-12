package com.posthub.iam_service.service;

import com.posthub.iam_service.model.dto.user.UserDTO;
import lombok.NonNull;

public interface UserService {
    UserDTO getById(@NonNull Integer userId);
}
