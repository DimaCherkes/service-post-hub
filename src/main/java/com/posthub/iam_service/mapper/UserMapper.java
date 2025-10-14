package com.posthub.iam_service.mapper;

import com.posthub.iam_service.model.dto.user.UserDTO;
import com.posthub.iam_service.model.entity.User;
import com.posthub.iam_service.model.enums.RegistrationStatus;
import com.posthub.iam_service.model.request.user.NewUserRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
    componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        imports = {RegistrationStatus.class, Object.class}
)
public interface UserMapper {

    @Mapping(target = "created", source = "createdAt")
    UserDTO toDto(User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "registrationStatus", expression = "java(RegistrationStatus.ACTIVE)")
    User createUser(NewUserRequest request);

}
