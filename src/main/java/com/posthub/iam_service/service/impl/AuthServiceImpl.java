package com.posthub.iam_service.service.impl;

import com.posthub.iam_service.mapper.UserMapper;
import com.posthub.iam_service.model.constants.ApiErrorMessage;
import com.posthub.iam_service.model.dto.user.LoginRequest;
import com.posthub.iam_service.model.dto.user.UserProfileDTO;
import com.posthub.iam_service.model.entity.RefreshToken;
import com.posthub.iam_service.model.entity.User;
import com.posthub.iam_service.model.exception.InvalidDataException;
import com.posthub.iam_service.repository.UserRepository;
import com.posthub.iam_service.security.JwtTokenProvider;
import com.posthub.iam_service.service.AuthService;
import com.posthub.iam_service.service.RefreshTokenService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;
    private final RefreshTokenService refreshTokenService;

    @Override
    public UserProfileDTO login(LoginRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
        } catch (BadCredentialsException e) {
            throw new InvalidDataException(ApiErrorMessage.INVALID_USER_OR_PASSWORD.getMessage());
        }

        User user = userRepository.findUserByEmailAndDeletedFalse(request.getEmail())
                .orElseThrow(() -> new InvalidDataException(ApiErrorMessage.INVALID_USER_OR_PASSWORD.getMessage()));

        RefreshToken refreshToken = refreshTokenService.generateOrUpdateRefreshToken(user);
        String token = jwtTokenProvider.generateToken(user);
        UserProfileDTO userProfileDTO = userMapper.toUserProfileDTO(user, token, refreshToken.getToken());
        userProfileDTO.setToken(token);

        return userProfileDTO;
    }

    @Override
    public UserProfileDTO refreshAccessToken(String refreshTokenValue) {
        RefreshToken refreshToken = refreshTokenService.validateAndRefreshToken(refreshTokenValue);
        User user = refreshToken.getUser();
        String accessToken = jwtTokenProvider.generateToken(user);

        return userMapper.toUserProfileDTO(user, accessToken, refreshToken.getToken());
    }

}
