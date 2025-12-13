package com.posthub.iam_service.mapper;

import com.posthub.iam_service.model.dto.comment.CommentDTO;
import com.posthub.iam_service.model.dto.comment.CommentSearchDTO;
import com.posthub.iam_service.model.entity.Comment;
import com.posthub.iam_service.model.entity.Post;
import com.posthub.iam_service.model.entity.User;
import com.posthub.iam_service.model.request.comment.NewCommentRequest;
import com.posthub.iam_service.model.request.comment.UpdateCommentRequest;
import org.hibernate.type.descriptor.DateTimeUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        imports = {DateTimeUtils.class, Object.class}
)
public interface CommentMapper {

    @Mapping(source = "user.id", target = "owner.id")
    @Mapping(source = "user.username", target = "owner.username")
    @Mapping(source = "user.email", target = "owner.email")
    @Mapping(source = "post.id", target = "postId")
    CommentDTO toDto(Comment comment);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "created", ignore = true)
    @Mapping(target = "updated", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "user", source = "user")
    @Mapping(target = "post", source = "post")
    @Mapping(target = "createdBy", source = "user.email")
    Comment createComment(NewCommentRequest newCommentRequest, User user, Post post);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "created", ignore = true)
    @Mapping(target = "updated", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    void updateComment(@MappingTarget Comment comment, UpdateCommentRequest request);

    @Mapping(target = "isDeleted", source = "deleted")
    @Mapping(target = "postId", source = "post.id")
    @Mapping(target = "owner.id", source = "user.id")
    @Mapping(target = "owner.email", source = "user.email")
    CommentSearchDTO toCommentSearchDto(Comment comment);

}
