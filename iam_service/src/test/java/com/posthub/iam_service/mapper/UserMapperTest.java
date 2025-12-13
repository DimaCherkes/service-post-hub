package com.posthub.iam_service.mapper;

import com.posthub.iam_service.model.dto.user.UserDTO;
import com.posthub.iam_service.model.entity.Role;
import com.posthub.iam_service.model.entity.User;
import com.posthub.iam_service.model.enums.RegistrationStatus;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class UserMapperTest {

    private final UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    @Test
    void shouldMapUserToUserDto() {
        // Given
        Role role = new Role();
        role.setId(1);
        role.setName("USER");

        User user = new User();
        user.setId(1);
        user.setUsername("testuser");
        user.setEmail("test@example.com");
        user.setCreatedAt(LocalDateTime.now());
        user.setLastLogin(LocalDateTime.now());
        user.setRegistrationStatus(RegistrationStatus.ACTIVE);
        user.setRoles(List.of(role));

        // When
        UserDTO userDTO = userMapper.toDto(user);

        // Then
        assertThat(userDTO).isNotNull();
        assertThat(userDTO.getId()).isEqualTo(user.getId());
        assertThat(userDTO.getUsername()).isEqualTo(user.getUsername());
        assertThat(userDTO.getEmail()).isEqualTo(user.getEmail());
        assertThat(userDTO.getCreated()).isEqualTo(user.getCreatedAt());
        assertThat(userDTO.getLastLogin()).isEqualTo(user.getLastLogin());
        assertThat(userDTO.getRegistrationStatus()).isEqualTo(user.getRegistrationStatus());
        assertThat(userDTO.getRoles()).hasSize(1);
        assertThat(userDTO.getRoles().get(0).getName()).isEqualTo("USER");
    }
}
