package com.posthub.iam_service.service.impl;

import com.posthub.iam_service.service.PostService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    private final List<String> posts = new ArrayList<>();

    @Override
    public void createPost(String postContent) {
        posts.add(postContent);
    }
}
