package com.posthub.iam_service.controller;

import com.posthub.iam_service.model.constants.ApiLogMessage;
import com.posthub.iam_service.model.dto.user.UserDTO;
import com.posthub.iam_service.model.dto.user.UserSearchDTO;
import com.posthub.iam_service.model.request.user.NewUserRequest;
import com.posthub.iam_service.model.request.user.UpdateUserRequest;
import com.posthub.iam_service.model.request.user.UserSearchRequest;
import com.posthub.iam_service.model.response.IamResponse;
import com.posthub.iam_service.model.response.PaginationResponse;
import com.posthub.iam_service.service.UserService;
import com.posthub.iam_service.utils.ApiUtils;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("${end.point.users}")
@Tag(name = "User Controller", description = "Endpoints for user management")
public class UserController {

    private final UserService userService;

    @GetMapping("${end.point.id}")
    public ResponseEntity<IamResponse<UserDTO>> getUserById(
            @PathVariable(name = "id") Integer userId) {
        log.trace(ApiLogMessage.NAME_OF_CURRENT_METHOD.getValue(), ApiUtils.getMethodName());

        UserDTO user = userService.getById(userId);
        return ResponseEntity.ok(IamResponse.createSuccessful(user));
    }

    @PostMapping("${end.point.create}")
    public ResponseEntity<IamResponse<UserDTO>> createUser(
            @RequestBody @Valid NewUserRequest request) {
        log.trace(ApiLogMessage.NAME_OF_CURRENT_METHOD.getValue(), ApiUtils.getMethodName());

        UserDTO user = userService.createUser(request);
        return ResponseEntity.ok(IamResponse.createSuccessful(user));
    }

    @PutMapping("${end.point.id}")
    public ResponseEntity<IamResponse<UserDTO>> updatePost(
            @PathVariable(name = "id") Integer userId,
            @RequestBody @Valid UpdateUserRequest request) {
        log.trace(ApiLogMessage.NAME_OF_CURRENT_METHOD.getValue(), ApiUtils.getMethodName());

        UserDTO userDTO = userService.updateUser(userId, request);
        return ResponseEntity.ok(IamResponse.createSuccessful(userDTO));
    }

    @DeleteMapping("${end.point.id}")
    public ResponseEntity<Void> softDeleteById(
            @PathVariable(name = "id") Integer userId) {
        log.trace(ApiLogMessage.NAME_OF_CURRENT_METHOD.getValue(), ApiUtils.getMethodName());

        userService.softDeleteUser(userId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("${end.point.all}")
    public ResponseEntity<IamResponse<PaginationResponse<UserSearchDTO>>> getAllUsers(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "limit", defaultValue = "10") int limit) {
        log.trace(ApiLogMessage.NAME_OF_CURRENT_METHOD.getValue(), ApiUtils.getMethodName());

        Pageable pageable = PageRequest.of(page, limit);
        PaginationResponse<UserSearchDTO> allUsers = userService.findAllUsers(pageable);
        IamResponse<PaginationResponse<UserSearchDTO>> response = IamResponse.createSuccessful(allUsers);
        return ResponseEntity.ok(response);
    }

    @PostMapping("${end.point.search}")
    public ResponseEntity<IamResponse<PaginationResponse<UserSearchDTO>>> searchUsers(
            @RequestBody @Valid UserSearchRequest request,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "limit", defaultValue = "10") int limit) {
        log.trace(ApiLogMessage.NAME_OF_CURRENT_METHOD.getValue(), ApiUtils.getMethodName());

        Pageable pageable = PageRequest.of(page, limit);
        PaginationResponse<UserSearchDTO> userSearchDTOPaginationResponse = userService.searchUsers(request, pageable);
        IamResponse<PaginationResponse<UserSearchDTO>> response = IamResponse.createSuccessful(userSearchDTOPaginationResponse);
        return ResponseEntity.ok(response);
    }

}

