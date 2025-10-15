package com.posthub.iam_service.service;

import com.posthub.iam_service.model.dto.user.UserDTO;
import com.posthub.iam_service.model.request.user.NewUserRequest;
import lombok.NonNull;

public interface UserService {

    UserDTO getById(@NonNull Integer userId);

    UserDTO createUser(@NonNull NewUserRequest request);

}
