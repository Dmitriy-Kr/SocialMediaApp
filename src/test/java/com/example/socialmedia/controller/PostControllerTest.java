package com.example.socialmedia.controller;

import com.example.socialmedia.entity.Post;
import com.example.socialmedia.entity.User;
import com.example.socialmedia.service.PostService;
import com.example.socialmedia.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PostControllerTest {

    private PostService postService;
    private UserService userService;
    private PostController postController;

    @BeforeEach
    void setUp() {
        postService = mock(PostService.class);
        userService = mock(UserService.class);
        postController = new PostController(postService, userService);
    }

    @Test
    void createPost_ValidRequest_ReturnsCreatedPost() {
        // Arrange
        Long authorId = 1L;
        User author = new User();
        author.setId(authorId);

        Post newPost = new Post();
        newPost.setTitle("Sample Title");
        newPost.setBody("Sample Body");
        newPost.setAuthor(author);

        when(userService.getUserById(authorId)).thenReturn(Optional.of(author));
        when(postService.createPost("Sample Title", "Sample Body", author)).thenReturn(newPost);

        // Act
        Post response = postController.createPost("Sample Title", "Sample Body", authorId);

        // Assert
        assertNotNull(response);
        assertEquals("Sample Title", response.getTitle());
        assertEquals("Sample Body", response.getBody());
        assertEquals(author, response.getAuthor());
        verify(userService, times(1)).getUserById(authorId);
        verify(postService, times(1)).createPost("Sample Title", "Sample Body", author);
    }

    @Test
    void createPost_InvalidAuthorId_ReturnsNull() {
        // Arrange
        Long invalidAuthorId = 99L;
        when(userService.getUserById(invalidAuthorId)).thenReturn(Optional.empty());

        // Act
        Post response = postController.createPost("Sample Title", "Sample Body", invalidAuthorId);

        // Assert
        assertNull(response);
        verify(userService, times(1)).getUserById(invalidAuthorId);
        verify(postService, never()).createPost(anyString(), anyString(), any(User.class));
    }

    @Test
    void getPostsByAuthor_ValidAuthorId_ReturnsPosts() {
        // Arrange
        Long authorId = 1L;
        User author = new User();
        author.setId(authorId);

        Post post1 = new Post();
        post1.setTitle("Post 1");
        post1.setBody("Body 1");

        Post post2 = new Post();
        post2.setTitle("Post 2");
        post2.setBody("Body 2");

        List<Post> posts = Arrays.asList(post1, post2);

        when(userService.getUserById(authorId)).thenReturn(Optional.of(author));
        when(postService.getPostsByAuthor(author)).thenReturn(posts);

        // Act
        List<Post> response = postController.getPostsByAuthor(authorId);

        // Assert
        assertNotNull(response);
        assertEquals(2, response.size());
        verify(userService, times(1)).getUserById(authorId);
        verify(postService, times(1)).getPostsByAuthor(author);
    }

    @Test
    void likePost_ValidRequest_ReturnsVoid() {
        // Arrange
        Long userId = 1L;
        Long postId = 2L;

        User user = new User();
        user.setId(userId);

        Post post = new Post();
        post.setId(postId);

        when(userService.getUserById(userId)).thenReturn(Optional.of(user));
        when(postService.findPostById(postId)).thenReturn(Optional.of(post));

        // Act
        postController.likePost(userId, postId);

        // Assert
        verify(userService, times(1)).getUserById(userId);
        verify(postService, times(1)).findPostById(postId);
        verify(postService, times(1)).likePost(user, post);
    }

    @Test
    void likePost_InvalidUserOrPost_ReturnsVoid() {
        // Arrange
        Long userId = 1L;
        Long postId = 2L;

        when(userService.getUserById(userId)).thenReturn(Optional.empty());
        when(postService.findPostById(postId)).thenReturn(Optional.empty());

        // Act
        postController.likePost(userId, postId);

        // Assert
        verify(userService, times(1)).getUserById(userId);
        verify(postService, times(1)).findPostById(postId);
        verify(postService, never()).likePost(any(User.class), any(Post.class));
    }
}