package com.posthub.iam_service.security.validation;

import com.posthub.iam_service.model.constants.ApiErrorMessage;
import com.posthub.iam_service.model.entity.User;
import com.posthub.iam_service.model.exception.CustomAccessDeniedException;
import com.posthub.iam_service.model.exception.DataExistException;
import com.posthub.iam_service.model.exception.InvalidDataException;
import com.posthub.iam_service.model.exception.InvalidPasswordException;
import com.posthub.iam_service.model.exception.NotFoundException;
import com.posthub.iam_service.repository.UserRepository;
import com.posthub.iam_service.service.model.IamServiceUserRole;
import com.posthub.iam_service.utils.ApiUtils;
import com.posthub.iam_service.utils.PasswordUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AccessValidator {
    private final UserRepository userRepository;
    private final ApiUtils apiUtils;

    public void validateNewUser(String username, String email, String password, String confirmPassword) {
        userRepository.findByUsername(username).ifPresent(existingUser -> {
            throw new DataExistException(ApiErrorMessage.USERNAME_ALREADY_EXISTS.getMessage(username));
        });

        userRepository.findUserByEmail(email).ifPresent(existingUser -> {
            throw new DataExistException(ApiErrorMessage.EMAIL_ALREADY_EXISTS.getMessage(email));
        });

        if (!password.equals(confirmPassword)) {
            throw new InvalidDataException(ApiErrorMessage.MISMATCH_PASSWORDS.getMessage());
        }

        if (PasswordUtils.isNotValidPassword(password)) {
            throw new InvalidPasswordException(ApiErrorMessage.INVALID_PASSWORD.getMessage());
        }
    }

    public boolean isAdminOrSuperAdmin(Integer userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(ApiErrorMessage.USER_NOT_FOUND_BY_ID.getMessage(userId)));

        return user.getRoles().stream()
                .map(role -> IamServiceUserRole.fromName(role.getName()))
                .anyMatch(role -> role == IamServiceUserRole.ADMIN || role == IamServiceUserRole.SUPER_ADMIN);
    }

    public void validateAdminOrOwnerAccess(Integer ownerId) {
        Integer currentUserId = apiUtils.getUserIdFromAuthentication();

        if (!currentUserId.equals(ownerId) && !isAdminOrSuperAdmin(currentUserId)) {
            throw new CustomAccessDeniedException(ApiErrorMessage.HAVE_NO_ACCESS.getMessage());
        }
    }

}
