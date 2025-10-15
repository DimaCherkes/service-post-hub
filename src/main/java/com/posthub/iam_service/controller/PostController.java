package com.posthub.iam_service.controller;

import com.posthub.iam_service.model.constants.ApiConstants;
import com.posthub.iam_service.model.constants.ApiLogMessage;
import com.posthub.iam_service.model.dto.post.PostDTO;
import com.posthub.iam_service.model.dto.post.PostSearchDTO;
import com.posthub.iam_service.model.request.post.NewPostRequest;
import com.posthub.iam_service.model.request.post.PostSearchRequest;
import com.posthub.iam_service.model.request.post.UpdatePostRequest;
import com.posthub.iam_service.model.response.IamResponse;
import com.posthub.iam_service.model.response.PaginationResponse;
import com.posthub.iam_service.service.PostService;
import com.posthub.iam_service.utils.ApiUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@Validated
@RequiredArgsConstructor
@RequestMapping("${end.point.posts}")
public class PostController {

    // is it better to return IamResponse or PostDTO from Service?
    private final PostService postService;

    @GetMapping("${end.point.id}")
    public ResponseEntity<IamResponse<PostDTO>> getPostById(
            @PathVariable(name = "id") Integer postId) {
        log.trace(ApiLogMessage.NAME_OF_CURRENT_METHOD.getValue(), ApiUtils.getMethodName());

        PostDTO postDto = postService.getById(postId);
        return ResponseEntity.ok(IamResponse.createSuccessful(postDto));
    }

    @PostMapping("${end.point.create}")
    public ResponseEntity<IamResponse<PostDTO>> createPost(
            @RequestBody @Valid NewPostRequest newPostRequest) {
        log.trace(ApiLogMessage.NAME_OF_CURRENT_METHOD.getValue(), ApiUtils.getMethodName());

        PostDTO postDto = postService.createPost(1, newPostRequest);
        return ResponseEntity.ok(IamResponse.createSuccessful(ApiConstants.CREATE_SUCCESSFUL, postDto));
    }

    @PutMapping("${end.point.id}")
    public ResponseEntity<IamResponse<PostDTO>> updatePost(
            @PathVariable(name = "id") Integer postId,
            @RequestBody @Valid UpdatePostRequest request) {
        log.trace(ApiLogMessage.NAME_OF_CURRENT_METHOD.getValue(), ApiUtils.getMethodName());

        PostDTO postDTO = postService.updatePost(postId, request);
        return ResponseEntity.ok(IamResponse.createSuccessful(postDTO));
    }

    @DeleteMapping("${end.point.id}")
    public ResponseEntity<Void> softDeleteById(
            @PathVariable(name = "id") Integer postId) {
        log.trace(ApiLogMessage.NAME_OF_CURRENT_METHOD.getValue(), ApiUtils.getMethodName());

        postService.softDeletePost(postId);
        return ResponseEntity.ok().build();
    }

    @GetMapping("${end.point.all}")
    public ResponseEntity<IamResponse<PaginationResponse<PostSearchDTO>>> getAllPosts(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "limit", defaultValue = "10") int limit) {
        log.trace(ApiLogMessage.NAME_OF_CURRENT_METHOD.getValue(), ApiUtils.getMethodName());

        Pageable pageable = PageRequest.of(page, limit);
        IamResponse<PaginationResponse<PostSearchDTO>> response = postService.findAllPosts(pageable);
        return ResponseEntity.ok(response);
    }

    @PostMapping("${end.point.search}")
    public ResponseEntity<IamResponse<PaginationResponse<PostSearchDTO>>> searchPosts(
            @RequestBody @Valid PostSearchRequest request,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "limit", defaultValue = "10") int limit) {
        log.trace(ApiLogMessage.NAME_OF_CURRENT_METHOD.getValue(), ApiUtils.getMethodName());

        Pageable pageable = PageRequest.of(page, limit);
        IamResponse<PaginationResponse<PostSearchDTO>> response = postService.searchPosts(request, pageable);
        return ResponseEntity.ok(response);
    }

}
