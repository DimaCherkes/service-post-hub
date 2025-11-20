package com.posthub.iam_service.service;

import com.posthub.iam_service.mapper.UserMapper;
import com.posthub.iam_service.model.dto.user.UserProfileDTO;
import com.posthub.iam_service.model.entity.RefreshToken;
import com.posthub.iam_service.model.entity.Role;
import com.posthub.iam_service.model.entity.User;
import com.posthub.iam_service.model.enums.RegistrationStatus;
import com.posthub.iam_service.model.exception.InvalidDataException;
import com.posthub.iam_service.model.request.user.LoginRequest;
import com.posthub.iam_service.model.request.user.RegistrationUserRequest;
import com.posthub.iam_service.repository.RoleRepository;
import com.posthub.iam_service.repository.UserRepository;
import com.posthub.iam_service.security.JwtTokenProvider;
import com.posthub.iam_service.security.validation.AccessValidator;
import com.posthub.iam_service.service.impl.AuthServiceImpl;
import com.posthub.iam_service.service.model.IamServiceUserRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class AuthServiceTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private UserMapper userMapper;
    @Mock
    private JwtTokenProvider jwtTokenProvider;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private RefreshTokenService refreshTokenService;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private BCryptPasswordEncoder passwordEncoder;
    @Mock
    private AccessValidator accessValidator;

    @InjectMocks
    private AuthServiceImpl authService;

    private User testUser;
    private UserProfileDTO testUserProfileDTO;
    private RefreshToken testRefreshToken;
    private Role userRole;

    private String refreshToken;
    private String accessToken;

    @BeforeEach
    void setUp() {
        refreshToken = "refresh_token_123";
        accessToken = "access_token_123";

        testUser = new User();
        testUser.setId(1);
        testUser.setUsername("TestUser");
        testUser.setEmail("test@gmail.com");
        testUser.setPassword("hashedPassword");
        testUser.setRegistrationStatus(RegistrationStatus.ACTIVE);
        testUser.setLastLogin(LocalDateTime.now());

        userRole = new Role();
        userRole.setName("USER");
        testUser.setRoles(Collections.singleton(userRole));

        testRefreshToken = new RefreshToken();
        testRefreshToken.setToken(refreshToken);
        testRefreshToken.setUser(testUser);

        testUserProfileDTO = new UserProfileDTO(
                testUser.getId(),
                testUser.getUsername(),
                testUser.getEmail(),
                testUser.getRegistrationStatus(),
                testUser.getLastLogin(),
                accessToken,
                testRefreshToken.getToken(),
                Collections.emptyList()
        );
    }

    @Test
    void login_ValidCredentials_ReturnsUserProfile() {
        LoginRequest request = new LoginRequest("test@gmail.com", "password123");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(null);
        when(userRepository.findUserByEmailAndDeletedFalse(request.getEmail())).thenReturn(Optional.of(testUser));
        when(refreshTokenService.generateOrUpdateRefreshToken(testUser)).thenReturn(testRefreshToken);
        when(jwtTokenProvider.generateToken(testUser)).thenReturn(accessToken);
        when(userMapper.toUserProfileDTO(testUser, accessToken, testRefreshToken.getToken()))
                .thenReturn(testUserProfileDTO);

        UserProfileDTO result = authService.login(request);

        assertNotNull(result);
        assertEquals(accessToken, result.getToken());
        assertEquals(refreshToken, result.getRefreshToken());

        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(userRepository, times(1)).findUserByEmailAndDeletedFalse(request.getEmail());
        verify(refreshTokenService, times(1)).generateOrUpdateRefreshToken(testUser);
        verify(jwtTokenProvider, times(1)).generateToken(testUser);
        verify(userMapper, times(1)).toUserProfileDTO(testUser, "access_token_123", testRefreshToken.getToken());
    }

    @Test
    void login_InvalidCredentials_ThrowsException() {
        LoginRequest request = new LoginRequest("test@gmail.com", "wrongPassword");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new BadCredentialsException("Invalid credentials"));

        InvalidDataException exception = assertThrows(InvalidDataException.class, () -> authService.login(request));

        assertTrue(exception.getMessage().contains("Invalid"));

        verify(userRepository, never()).findUserByEmailAndDeletedFalse(request.getEmail());
        verify(refreshTokenService, never()).generateOrUpdateRefreshToken(any(User.class));
        verify(jwtTokenProvider, never()).generateToken(any(User.class));
    }

    @Test
    void registerUser_ValidRequest_CreatesUserSuccessfully() {
        RegistrationUserRequest request = new RegistrationUserRequest(
                "newUser",
                "newUser@gmail.com",
                "password123!",
                "password123!"
        );

        doNothing().when(accessValidator).validateNewUser(
                request.getUsername(),
                request.getEmail(),
                request.getPassword(),
                request.getConfirmPassword()
        );

        when(roleRepository.findByName(IamServiceUserRole.USER.getRole())).thenReturn(Optional.of(userRole));
        when(userMapper.fromDto(request)).thenReturn(testUser);
        when(passwordEncoder.encode(request.getPassword())).thenReturn("hashedPassword");
        when(userRepository.save(any(User.class))).thenReturn(testUser);
        when(refreshTokenService.generateOrUpdateRefreshToken(testUser)).thenReturn(testRefreshToken);
        when(jwtTokenProvider.generateToken(testUser)).thenReturn(accessToken);
        when(userMapper.toUserProfileDTO(testUser, accessToken, testRefreshToken.getToken()))
                .thenReturn(testUserProfileDTO);

        UserProfileDTO result = authService.registerUser(request);

        assertNotNull(result);
        assertEquals(accessToken, result.getToken());
        assertEquals(refreshToken, result.getRefreshToken());

        verify(accessValidator, times(1)).validateNewUser(
                request.getUsername(),
                request.getEmail(),
                request.getPassword(),
                request.getConfirmPassword()
        );
        verify(roleRepository, times(1)).findByName(IamServiceUserRole.USER.getRole());
        verify(userRepository, times(1)).save(any(User.class));
        verify(refreshTokenService, times(1)).generateOrUpdateRefreshToken(testUser);
        verify(jwtTokenProvider, times(1)).generateToken(testUser);
        verify(userMapper, times(1)).toUserProfileDTO(testUser, accessToken, testRefreshToken.getToken());
    }

}
