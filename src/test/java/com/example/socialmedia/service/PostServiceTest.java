package com.example.socialmedia.service;

import com.example.socialmedia.entity.Post;
import com.example.socialmedia.entity.User;
import com.example.socialmedia.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PostServiceTest {

    private PostRepository postRepository;
    private PostService postService;

    @BeforeEach
    void setUp() {
        postRepository = mock(PostRepository.class); // Mock the repository
        postService = new PostService(postRepository); // Inject the mock into the service
    }

    @Test
    void createPost_ValidInput_ReturnsSavedPost() {
        // Arrange
        String title = "Post Title";
        String body = "This is the body of the post.";
        User author = new User();
        author.setId(1L);

        Post postToSave = new Post();
        postToSave.setTitle(title);
        postToSave.setBody(body);
        postToSave.setAuthor(author);

        Post savedPost = new Post();
        savedPost.setId(1L);
        savedPost.setTitle(title);
        savedPost.setBody(body);
        savedPost.setAuthor(author);

        when(postRepository
                .save(argThat(post -> postToSave.getTitle().equals(post.getTitle())
                        && postToSave.getBody().equals(post.getBody()))))
                .thenReturn(savedPost);

        // Act
        Post result = postService.createPost(title, body, author);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(title, result.getTitle());
        assertEquals(body, result.getBody());
        assertEquals(author, result.getAuthor());
        verify(postRepository, times(1)).save(any(Post.class));
    }

    @Test
    void getPostsByAuthor_ValidAuthor_ReturnsPostList() {
        // Arrange
        User author = new User();
        author.setId(1L);

        Post post1 = new Post();
        post1.setId(1L);
        post1.setTitle("Post 1");

        Post post2 = new Post();
        post2.setId(2L);
        post2.setTitle("Post 2");

        List<Post> posts = Arrays.asList(post1, post2);

        when(postRepository.findByAuthor(author)).thenReturn(posts);

        // Act
        List<Post> result = postService.getPostsByAuthor(author);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Post 1", result.get(0).getTitle());
        assertEquals("Post 2", result.get(1).getTitle());
        verify(postRepository, times(1)).findByAuthor(author);
    }

    @Test
    void getPostsByAuthor_NoPosts_ReturnsEmptyList() {
        // Arrange
        User author = new User();
        author.setId(1L);

        when(postRepository.findByAuthor(author)).thenReturn(List.of());

        // Act
        List<Post> result = postService.getPostsByAuthor(author);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(postRepository, times(1)).findByAuthor(author);
    }

    @Test
    void findPostById_ValidId_ReturnsPost() {
        // Arrange
        Long postId = 1L;
        Post post = new Post();
        post.setId(postId);
        post.setTitle("Sample Post");

        when(postRepository.findById(postId)).thenReturn(Optional.of(post));

        // Act
        Optional<Post> result = postService.findPostById(postId);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(postId, result.get().getId());
        assertEquals("Sample Post", result.get().getTitle());
        verify(postRepository, times(1)).findById(postId);
    }

    @Test
    void findPostById_InvalidId_ReturnsEmptyOptional() {
        // Arrange
        Long postId = 1L;

        when(postRepository.findById(postId)).thenReturn(Optional.empty());

        // Act
        Optional<Post> result = postService.findPostById(postId);

        // Assert
        assertFalse(result.isPresent());
        verify(postRepository, times(1)).findById(postId);
    }

    @Test
    void likePost_ValidUserAndPost_SavesPost() {
        // Arrange
        User user = new User();
        user.setId(1L);

        Post post = new Post();
        post.setId(1L);

        // Act
        postService.likePost(user, post);

        // Assert
        assertTrue(user.getLikedPosts().contains(post));
        verify(postRepository, times(1)).save(post);
    }
}