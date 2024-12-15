package com.example.socialmedia.controller;

import com.example.socialmedia.entity.Post;
import com.example.socialmedia.entity.User;
import com.example.socialmedia.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserControllerTest {

    private UserService userService;
    private UserController userController;

    @BeforeEach
    void setUp() {
        userService = mock(UserService.class); // Mock UserService
        userController = new UserController(userService); // Inject mocked service
    }

    @Test
    void createUser_ValidRequest_ReturnsUser() {
        // Arrange
        String username = "testuser";
        String password = "password123";

        User createdUser = new User();
        createdUser.setId(1L);
        createdUser.setUsername(username);

        when(userService.createUser(username, password)).thenReturn(createdUser);

        // Act
        User response = userController.createUser(username, password);

        // Assert
        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals(username, response.getUsername());
        verify(userService, times(1)).createUser(username, password);
    }

    @Test
    void followUser_ValidRequest_InvokesService() {
        // Arrange
        Long followerId = 1L;
        Long followingId = 2L;

        User follower = new User();
        follower.setId(followerId);

        User following = new User();
        following.setId(followingId);

        when(userService.getUserById(followerId)).thenReturn(Optional.of(follower));
        when(userService.getUserById(followingId)).thenReturn(Optional.of(following));

        // Act
        userController.followUser(followerId, followingId);

        // Assert
        verify(userService, times(1)).getUserById(followerId);
        verify(userService, times(1)).getUserById(followingId);
        verify(userService, times(1)).followUser(follower, following);
    }

    @Test
    void followUser_InvalidRequest_DoesNotInvokeFollowService() {
        // Arrange
        Long followerId = 1L;
        Long followeeId = 2L;

        when(userService.getUserById(followerId)).thenReturn(Optional.empty());
        when(userService.getUserById(followeeId)).thenReturn(Optional.empty());

        // Act
        userController.followUser(followerId, followeeId);

        // Assert
        verify(userService, times(1)).getUserById(followerId);
        verify(userService, times(1)).getUserById(followeeId);
        verify(userService, never()).followUser(any(User.class), any(User.class));
    }

    @Test
    void getUserFollowers_ValidUserId_ReturnsFollowers() {
        // Arrange
        Long userId = 1L;
        User user = new User();
        user.setId(userId);

        User follower1 = new User();
        follower1.setId(2L);

        User follower2 = new User();
        follower2.setId(3L);

        Set<User> followers = new HashSet<>(Arrays.asList(follower1, follower2));
        //Added by human
        user.setFollowers(followers);

        when(userService.getUserById(userId)).thenReturn(Optional.of(user));
        //Deleted by human
//        when(user.getFollowers()).thenReturn(followers);

        // Act
        Set<User> response = userController.getUserFollowers(userId);

        // Assert
        assertNotNull(response);
        assertEquals(2, response.size());
        verify(userService, times(1)).getUserById(userId);
    }

    @Test
    void getUserFollowers_InvalidUserId_ReturnsNull() {
        // Arrange
        Long userId = 1L;

        when(userService.getUserById(userId)).thenReturn(Optional.empty());

        // Act
        Set<User> response = userController.getUserFollowers(userId);

        // Assert
        assertNull(response);
        verify(userService, times(1)).getUserById(userId);
    }

    @Test
    void getUserFollowing_ValidUserId_ReturnsFollowing() {
        // Arrange
        Long userId = 1L;
        User user = new User();
        user.setId(userId);

        User following1 = new User();
        following1.setId(2L);

        User following2 = new User();
        following2.setId(3L);

        Set<User> following = new HashSet<>(Arrays.asList(following1, following2));
        //Added by human
        user.setFollowing(following);

        when(userService.getUserById(userId)).thenReturn(Optional.of(user));
        //Deleted by human
        //when(user.getFollowing()).thenReturn(following);

        // Act
        Set<User> response = userController.getUserFollowing(userId);

        // Assert
        assertNotNull(response);
        assertEquals(2, response.size());
        verify(userService, times(1)).getUserById(userId);
    }

    @Test
    void getUserFollowing_InvalidUserId_ReturnsNull() {
        // Arrange
        Long userId = 1L;

        when(userService.getUserById(userId)).thenReturn(Optional.empty());

        // Act
        Set<User> response = userController.getUserFollowing(userId);

        // Assert
        assertNull(response);
        verify(userService, times(1)).getUserById(userId);
    }

    @Test
    void getPostsLikedByUser_ValidUserId_ReturnsPosts() {
        // Arrange
        Long userId = 1L;
        User user = new User();
        user.setId(userId);

        Post post1 = new Post();
        post1.setId(101L);

        Post post2 = new Post();
        post2.setId(102L);

        Set<Post> likedPosts = new HashSet<>(Arrays.asList(post1, post2));
        //Added by human
        user.setLikedPosts(likedPosts);

        when(userService.getUserById(userId)).thenReturn(Optional.of(user));
        //Deleted by human
        //when(user.getLikedPosts()).thenReturn(likedPosts);

        // Act
        List<Post> response = userController.getPostsLikedByUser(userId);

        // Assert
        assertNotNull(response);
        assertEquals(2, response.size());
        verify(userService, times(1)).getUserById(userId);
    }

    @Test
    void getPostsLikedByUser_InvalidUserId_ReturnsNull() {
        // Arrange
        Long userId = 1L;

        when(userService.getUserById(userId)).thenReturn(Optional.empty());

        // Act
        List<Post> response = userController.getPostsLikedByUser(userId);

        // Assert
        assertNull(response);
        verify(userService, times(1)).getUserById(userId);
    }
}