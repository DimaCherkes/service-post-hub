package com.posthub.iam_service.service.impl;

import com.posthub.iam_service.mapper.UserMapper;
import com.posthub.iam_service.model.constants.ApiErrorMessage;
import com.posthub.iam_service.model.dto.user.UserDTO;
import com.posthub.iam_service.model.dto.user.UserSearchDTO;
import com.posthub.iam_service.model.entity.User;
import com.posthub.iam_service.model.exception.DataExistException;
import com.posthub.iam_service.model.exception.NotFoundException;
import com.posthub.iam_service.model.request.user.NewUserRequest;
import com.posthub.iam_service.model.request.user.UpdateUserRequest;
import com.posthub.iam_service.model.request.user.UserSearchRequest;
import com.posthub.iam_service.model.response.PaginationResponse;
import com.posthub.iam_service.repository.UserRepository;
import com.posthub.iam_service.service.UserService;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserDTO getById(@NonNull Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(ApiErrorMessage.USER_NOT_FOUND_BY_ID.getMessage(userId)));

        return userMapper.toDto(user);
    }

    @Override
    public UserDTO createUser(@NonNull NewUserRequest newUserRequest) {
        if (userRepository.existsByUsername(newUserRequest.getUsername()))
            throw new DataExistException(ApiErrorMessage.USERNAME_ALREADY_EXISTS.getMessage(newUserRequest.getUsername()));

        if (userRepository.existsByEmail(newUserRequest.getEmail()))
            throw new DataExistException(ApiErrorMessage.EMAIL_ALREADY_EXISTS.getMessage(newUserRequest.getEmail()));

        User user = userMapper.createUser(newUserRequest);
        User persistedUser = userRepository.save(user);
        return userMapper.toDto(persistedUser);
    }

    @Override
    @Transactional
    public UserDTO updateUser(Integer userId, UpdateUserRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(ApiErrorMessage.USER_NOT_FOUND_BY_ID.getMessage(userId)));

        userMapper.updateUser(user, request);
        User updatedUser = userRepository.save(user);
        return userMapper.toDto(updatedUser);
    }

    @Override
    @Transactional
    public void softDeleteUser(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(ApiErrorMessage.USER_NOT_FOUND_BY_ID.getMessage(userId)));

        user.setDeleted(true);
        userRepository.save(user);
    }

    @Override
    public PaginationResponse<UserSearchDTO> findAllUsers(Pageable pageable) {
        Page<UserSearchDTO> users = userRepository.findAll(pageable)
                .map(userMapper::toUserSearchDTO);

        return new PaginationResponse<>(
                users.getContent(),
                new PaginationResponse.Pagination(
                        users.getTotalElements(),
                        pageable.getPageSize(),
                        pageable.getPageNumber() + 1,
                        users.getTotalPages()
                )
        );
    }

    // TODO:
    @Override
    public PaginationResponse<UserSearchDTO> searchUsers(UserSearchRequest request, Pageable pageable) {

        return null;
    }

}
