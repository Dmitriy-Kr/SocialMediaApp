package com.example.socialmedia.service;

import com.example.socialmedia.entity.User;
import com.example.socialmedia.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    private UserRepository userRepository;
    private UserService userService;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class); // Mock the repository
        userService = new UserService(userRepository); // Inject the mock into the service
    }

    @Test
    void createUser_ValidInput_ReturnsSavedUser() {
        // Arrange
        String username = "testuser";
        String password = "password123";

        User userToSave = new User();
        userToSave.setUsername(username);
        userToSave.setPassword(password);

        User savedUser = new User();
        savedUser.setId(1L);
        savedUser.setUsername(username);

        //Deleted by human
        //when(userRepository.save(userToSave)).thenReturn(savedUser);

        //Added by human
        when(userRepository
                .save(argThat(user -> userToSave.getUsername().equals(user.getUsername()) && userToSave.getPassword().equals(user.getPassword()))))
                .thenReturn(savedUser);

        // Act
        User result = userService.createUser(username, password);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(username, result.getUsername());
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    void getUserById_UserExists_ReturnsUser() {
        // Arrange
        Long userId = 1L;
        User user = new User();
        user.setId(userId);
        user.setUsername("testuser");

        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        // Act
        Optional<User> result = userService.getUserById(userId);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(userId, result.get().getId());
        assertEquals("testuser", result.get().getUsername());
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void getUserById_UserDoesNotExist_ReturnsEmptyOptional() {
        // Arrange
        Long userId = 1L;

        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        // Act
        Optional<User> result = userService.getUserById(userId);

        // Assert
        assertFalse(result.isPresent());
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void followUser_ValidUsers_SavesBothUsers() {
        // Arrange
        User follower = new User();
        follower.setId(1L);
        follower.setUsername("follower");

        User following = new User();
        following.setId(2L);
        following.setUsername("following");

        // Act
        userService.followUser(follower, following);

        // Assert
        assertTrue(follower.getFollowing().contains(following));
        assertTrue(following.getFollowers().contains(follower));
        verify(userRepository, times(1)).save(follower);
        verify(userRepository, times(1)).save(following);
    }
}