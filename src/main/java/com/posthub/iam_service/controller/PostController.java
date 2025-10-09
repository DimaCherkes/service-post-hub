package com.posthub.iam_service.controller;

import com.posthub.iam_service.model.constants.ApiConstants;
import com.posthub.iam_service.model.constants.ApiLogMessage;
import com.posthub.iam_service.model.dto.post.PostDTO;
import com.posthub.iam_service.model.request.post.PostRequest;
import com.posthub.iam_service.model.response.IamResponse;
import com.posthub.iam_service.service.PostService;
import com.posthub.iam_service.utils.ApiUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("${end.point.posts}")
public class PostController {

    private final PostService postService;

    @GetMapping("${end.point.id}")
    public ResponseEntity<IamResponse<PostDTO>> getPostById(
            @PathVariable(name = "id") Integer postId) {
        log.trace(ApiLogMessage.NAME_OF_CURRENT_METHOD.getValue(), ApiUtils.getMethodName());

        PostDTO postDto = postService.getById(postId);
        return ResponseEntity.ok(IamResponse.createSuccessful(postDto)); // is it better to return IamResponse in Service?
    }

    @PostMapping("${end.point.create}")
    public ResponseEntity<IamResponse<PostDTO>> createPost(
            @RequestBody PostRequest postRequest) {
        log.trace(ApiLogMessage.NAME_OF_CURRENT_METHOD.getValue(), ApiUtils.getMethodName());

        PostDTO postDto = postService.createPost(postRequest);
        return ResponseEntity.ok(IamResponse.createSuccessful(ApiConstants.CREATE_SUCCESSFUL, postDto)); // is it better to return IamResponse in Service?
    }


}
