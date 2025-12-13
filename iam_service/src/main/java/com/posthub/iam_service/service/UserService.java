package com.posthub.iam_service.service;

import com.posthub.iam_service.model.dto.user.UserDTO;
import com.posthub.iam_service.model.dto.user.UserSearchDTO;
import com.posthub.iam_service.model.request.user.NewUserRequest;
import com.posthub.iam_service.model.request.user.UpdateUserRequest;
import com.posthub.iam_service.model.request.user.UserSearchRequest;
import com.posthub.iam_service.model.response.PaginationResponse;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    UserDTO getById(@NotNull Integer userId);

    UserDTO createUser(@NotNull NewUserRequest request);

    UserDTO updateUser(@NotNull Integer userId, @NotNull UpdateUserRequest request);

    void softDeleteUser(Integer userId);

    PaginationResponse<UserSearchDTO> findAllUsers(Pageable pageable);

    PaginationResponse<UserSearchDTO> searchUsers(UserSearchRequest request, Pageable pageable);

}
