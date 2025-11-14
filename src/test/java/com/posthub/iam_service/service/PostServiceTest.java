package com.posthub.iam_service.service;

import com.posthub.iam_service.mapper.PostMapper;
import com.posthub.iam_service.model.dto.post.PostDTO;
import com.posthub.iam_service.model.entity.Post;
import com.posthub.iam_service.model.entity.User;
import com.posthub.iam_service.repository.PostRepository;
import com.posthub.iam_service.repository.UserRepository;
import com.posthub.iam_service.service.impl.PostServiceImpl;
import com.posthub.iam_service.utils.ApiUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.parameters.P;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_METHOD)
public class PostServiceTest {

    @Mock
    private PostRepository postRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PostMapper postMapper;

    @Mock
    private ApiUtils apiUtils;

    @InjectMocks
    private PostServiceImpl postService;

    private Post testPost;
    private PostDTO testPostDTO;
    private User testUser;

    @BeforeEach
    void setUp() {
        testUser = new User();
        testUser.setId(1);
        testUser.setUsername("TestUser");

        testPost = new Post();
        testPost.setId(1);
        testPost.setTitle("Test Post");
        testPost.setContent("Test content");
        testPost.setUser(testUser);

        testPostDTO = new PostDTO();
        testPostDTO.setId(1);
        testPostDTO.setTitle("Test Post");
        testPostDTO.setContent("Test content");
    }

    @Test
    void getById_PostExists_ReturnsPostDTO() {
        when(postRepository.findByIdAndDeletedFalse(1)).thenReturn(Optional.of(testPost));
        when(postMapper.toPostDTO(testPost)).thenReturn(testPostDTO);

        PostDTO result = postService.getById(1);

        assertNotNull(result);

    }

}
