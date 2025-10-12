package com.posthub.iam_service.controller;

import com.posthub.iam_service.model.constants.ApiLogMessage;
import com.posthub.iam_service.model.dto.user.UserDTO;
import com.posthub.iam_service.model.response.IamResponse;
import com.posthub.iam_service.service.UserService;
import com.posthub.iam_service.utils.ApiUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("${end.point.users}")
public class UserController {

    private final UserService userService;

    @GetMapping("${end.point.id}")
    public ResponseEntity<IamResponse<UserDTO>> getUserById(
            @PathVariable(name = "id") Integer userId) {
        log.trace(ApiLogMessage.NAME_OF_CURRENT_METHOD.getValue(), ApiUtils.getMethodName());

        UserDTO userDto = userService.getById(userId);
        return ResponseEntity.ok(IamResponse.createSuccessful(userDto));
    }

}
