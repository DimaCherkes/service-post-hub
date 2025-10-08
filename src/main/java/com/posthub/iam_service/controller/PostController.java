package com.posthub.iam_service.controller;

import com.posthub.iam_service.service.PostService;
import com.posthub.iam_service.service.impl.PostServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;

    @Autowired
    public PostController(PostServiceImpl postService) {
        this.postService = postService;
    }

    @PostMapping("/create")
    public ResponseEntity<String> createPost(@RequestBody Map<String, Object> requestBody) {
        String title = requestBody.get("title").toString();
        String content = requestBody.get("content").toString();

        String postContent = "Title" + title + "\nContent: " + content + "\n";

        postService.createPost(postContent);

        return new ResponseEntity<>("Post created with title: " + title, HttpStatus.OK);
    }
}
