package com.posthub.iam_service.service.impl;

import com.posthub.iam_service.mapper.PostMapper;
import com.posthub.iam_service.model.constants.ApiErrorMessage;
import com.posthub.iam_service.model.dto.post.PostDTO;
import com.posthub.iam_service.model.exception.NotFoundException;
import com.posthub.iam_service.repository.PostRepository;
import com.posthub.iam_service.service.PostService;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final PostMapper postMapper;

    @Override
    public PostDTO getById(@NotNull Integer id) {
        return postRepository.findById(id)
                .map(postMapper::toPostDTO)
                .orElseThrow(() -> new NotFoundException(ApiErrorMessage.POST_NOT_FOUND_BY_ID.getMessage(id)));
    }

}
