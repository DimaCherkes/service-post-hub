package com.posthub.iam_service.service;

import com.posthub.iam_service.mapper.CommentMapper;
import com.posthub.iam_service.model.constants.ApiErrorMessage;
import com.posthub.iam_service.model.dto.comment.CommentDTO;
import com.posthub.iam_service.model.entity.Comment;
import com.posthub.iam_service.model.exception.NotFoundException;
import com.posthub.iam_service.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    @Override
    public CommentDTO getCommentById(Integer id) {
        Comment comment = commentRepository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new NotFoundException(ApiErrorMessage.COMMENT_NOT_FOUND_BY_ID.getMessage(id)));

        return commentMapper.toDto(comment);
    }
}
