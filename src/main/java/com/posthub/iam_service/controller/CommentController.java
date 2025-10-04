package com.posthub.iam_service.controller;

import com.posthub.iam_service.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/comment")
public class CommentController {

    private CommentService commentService;

    @Autowired
    public void setCommentService(CommentService commentService) {
        this.commentService = commentService;
    }

    @RequestMapping("/create")
    public ResponseEntity<String> addComment(@RequestBody Map<String, Object> requestBody) {
        String content = requestBody.get("content").toString();
        commentService.createComment(content);

        return new ResponseEntity<>("Comment added: " + content, HttpStatus.OK);
    }
}
