package com.posthub.iam_service.service;

import com.posthub.iam_service.mapper.CommentMapper;
import com.posthub.iam_service.model.dto.comment.CommentDTO;
import com.posthub.iam_service.model.entity.Comment;
import com.posthub.iam_service.model.entity.Post;
import com.posthub.iam_service.model.entity.User;
import com.posthub.iam_service.repository.CommentRepository;
import com.posthub.iam_service.repository.PostRepository;
import com.posthub.iam_service.repository.UserRepository;
import com.posthub.iam_service.service.impl.CommentServiceImpl;
import com.posthub.iam_service.utils.ApiUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class CommentServiceTest {

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PostRepository postRepository;

    @Mock
    private CommentMapper commentMapper;

    @Mock
    private ApiUtils apiUtils;

    @InjectMocks
    private CommentServiceImpl commentService;

    private Comment testComment;
    private CommentDTO testCommentDTO;
    private User testUser;
    private Post testPost;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setId(1);
        testUser.setUsername("TestUser");

        testPost = new Post();
        testPost.setId(1);
        testPost.setTitle("TestPost");

        testComment = new Comment();
        testComment.setId(1);
        testComment.setMessage("Test Comment");
        testComment.setPost(testPost);
        testComment.setUser(testUser);

        testCommentDTO = new CommentDTO();
        testCommentDTO.setId(1);
        testCommentDTO.setMessage("Test Comment");
    }

}
