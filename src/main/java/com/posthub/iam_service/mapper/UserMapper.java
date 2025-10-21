package com.posthub.iam_service.mapper;

import com.posthub.iam_service.model.dto.role.RoleDTO;
import com.posthub.iam_service.model.dto.user.UserDTO;
import com.posthub.iam_service.model.dto.user.UserSearchDTO;
import com.posthub.iam_service.model.entity.Role;
import com.posthub.iam_service.model.entity.User;
import com.posthub.iam_service.model.enums.RegistrationStatus;
import com.posthub.iam_service.model.request.user.NewUserRequest;
import com.posthub.iam_service.model.request.user.UpdateUserRequest;
import org.hibernate.type.descriptor.DateTimeUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.Collection;
import java.util.List;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        imports = {DateTimeUtils.class, RegistrationStatus.class, Object.class}
)
public interface UserMapper {

    @Mapping(target = "created", source = "createdAt")
    @Mapping(target = "roles", expression = "java(mapRoles(user.getRoles()))")
    UserDTO toDto(User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "registrationStatus", expression = "java(RegistrationStatus.ACTIVE)")
    User createUser(NewUserRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    void updateUser(@MappingTarget User user, UpdateUserRequest request);

    @Mapping(target = "isDeleted", source = "deleted")
    @Mapping(target = "roles", expression = "java(mapRoles(user.getRoles()))")
    UserSearchDTO toUserSearchDTO(User user);

    default List<RoleDTO> mapRoles(Collection<Role> roles) {
        return roles.stream()
                .map(role -> new RoleDTO(role.getId(), role.getName()))
                .toList();
    }

}
