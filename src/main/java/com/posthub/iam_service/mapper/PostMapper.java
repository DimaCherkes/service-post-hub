package com.posthub.iam_service.mapper;

import com.posthub.iam_service.model.dto.post.PostDTO;
import com.posthub.iam_service.model.entity.Post;
import org.hibernate.type.descriptor.DateTimeUtils;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.Objects;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        imports = {DateTimeUtils.class, Objects.class}
)
public interface PostMapper {

    PostDTO toPostDTO(Post post);

}
