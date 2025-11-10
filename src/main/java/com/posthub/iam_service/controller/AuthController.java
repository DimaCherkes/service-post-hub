package com.posthub.iam_service.controller;

import com.posthub.iam_service.model.constants.ApiLogMessage;
import com.posthub.iam_service.model.constants.ApiMessage;
import com.posthub.iam_service.model.request.user.LoginRequest;
import com.posthub.iam_service.model.dto.user.UserProfileDTO;
import com.posthub.iam_service.model.request.user.RegistrationUserRequest;
import com.posthub.iam_service.model.response.IamResponse;
import com.posthub.iam_service.service.AuthService;
import com.posthub.iam_service.utils.ApiUtils;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("${end.point.auth}")
public class AuthController {

    private final AuthService authService;

    @PostMapping("${end.point.login}")
    public ResponseEntity<IamResponse<UserProfileDTO>> login(
            @RequestBody @Valid LoginRequest request) {
        log.trace(ApiLogMessage.NAME_OF_CURRENT_METHOD.getValue(), ApiUtils.getMethodName());

        UserProfileDTO userProfileDTO = authService.login(request);
        IamResponse<UserProfileDTO> result = IamResponse.createSuccessful(ApiMessage.USER_LOGIN_SUCCESSFUL.getMessage(), userProfileDTO);
        Cookie authorizationCookie = ApiUtils.createAuthCookie(result.payload().getToken());

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.SET_COOKIE, authorizationCookie.toString());

        return ResponseEntity.ok().headers(headers).body(result);
    }

    @GetMapping("${end.point.refresh.token}")
    public ResponseEntity<IamResponse<UserProfileDTO>> refreshToken(
            @RequestParam(name = "token") String refreshToken,
            HttpServletResponse response) {
        log.trace(ApiLogMessage.NAME_OF_CURRENT_METHOD.getValue(), ApiUtils.getMethodName());

        UserProfileDTO userProfileDTO = authService.refreshAccessToken(refreshToken);
        IamResponse<UserProfileDTO> result = IamResponse.createSuccessful(ApiMessage.USER_CREATED_OR_UPDATED.getMessage(), userProfileDTO);
        Cookie authorizationCookie = ApiUtils.createAuthCookie(userProfileDTO.getToken());
        response.addCookie(authorizationCookie);

        return ResponseEntity.ok(result);
    }

    @PostMapping("${end.point.register}")
    public ResponseEntity<?> register(
            @RequestBody @Valid RegistrationUserRequest request,
            HttpServletResponse response) {
        log.trace(ApiLogMessage.NAME_OF_CURRENT_METHOD.getValue(), ApiUtils.getMethodName());

        UserProfileDTO userProfileDTO = authService.registerUser(request);
        IamResponse<UserProfileDTO> result = IamResponse.createSuccessful(ApiMessage.USER_CREATED_OR_UPDATED.getMessage(), userProfileDTO);
        Cookie authorizationCookie = ApiUtils.createAuthCookie(userProfileDTO.getToken());
        response.addCookie(authorizationCookie);

        return ResponseEntity.ok(result);
    }

}
