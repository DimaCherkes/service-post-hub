package com.posthub.iam_service.mapper;

import com.posthub.iam_service.model.dto.user.UserDTO;
import com.posthub.iam_service.model.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
    componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface UserMapper {

    @Mapping(target = "created", source = "createdAt")
    UserDTO toDto(User user);
}
