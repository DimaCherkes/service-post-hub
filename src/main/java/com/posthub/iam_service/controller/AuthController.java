package com.posthub.iam_service.controller;

import com.posthub.iam_service.model.constants.ApiLogMessage;
import com.posthub.iam_service.model.constants.ApiMessage;
import com.posthub.iam_service.model.dto.user.LoginRequest;
import com.posthub.iam_service.model.dto.user.UserProfileDTO;
import com.posthub.iam_service.model.response.IamResponse;
import com.posthub.iam_service.service.AuthService;
import com.posthub.iam_service.utils.ApiUtils;
import jakarta.servlet.http.Cookie;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        IamResponse<UserProfileDTO> result = IamResponse.createSuccessful(ApiMessage.USER_CREATED_OR_UPDATED.getMessage(), userProfileDTO);
        Cookie authorizationCookie = ApiUtils.createAuthCookie(result.payload().getToken());

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.SET_COOKIE, authorizationCookie.toString());

        return ResponseEntity.ok().headers(headers).body(result);
    }

}
