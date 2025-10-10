package com.posthub.iam_service.mapper;

import com.posthub.iam_service.model.dto.post.PostDTO;
import com.posthub.iam_service.model.dto.post.PostSearchDTO;
import com.posthub.iam_service.model.entity.Post;
import com.posthub.iam_service.model.request.post.NewPostRequest;
import com.posthub.iam_service.model.request.post.UpdatePostRequest;
import org.hibernate.type.descriptor.DateTimeUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.Objects;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        imports = {DateTimeUtils.class, Objects.class}
)
public interface PostMapper {

    PostDTO toPostDTO(Post post);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Post createPost(NewPostRequest newPostRequest);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    void updatePost(@MappingTarget Post post, UpdatePostRequest updatePostRequest);

    @Mapping(target = "isDeleted", source = "deleted")
    PostSearchDTO toPostSearchDTO(Post post);

}
