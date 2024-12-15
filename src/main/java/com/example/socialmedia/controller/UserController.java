package com.example.socialmedia.controller;

import com.example.socialmedia.entity.Post;
import com.example.socialmedia.entity.User;
import com.example.socialmedia.service.UserService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public User createUser(@RequestParam String username, @RequestParam String password) {
        return userService.createUser(username, password);
    }

    @PostMapping("/{id}/follow")
    public void followUser(@PathVariable Long id, @RequestParam Long followId) {
        Optional<User> follower = userService.getUserById(id);
        Optional<User> following = userService.getUserById(followId);
        if (follower.isPresent() && following.isPresent()) {
            userService.followUser(follower.get(), following.get());
        }
    }

    @GetMapping("/{id}/followers")
    public Set<User> getUserFollowers(@PathVariable Long id) {
        Optional<User> user = userService.getUserById(id);
        return user.map(User::getFollowers).orElse(null);
    }

    @GetMapping("/{id}/following")
    public Set<User> getUserFollowing(@PathVariable Long id) {
        Optional<User> user = userService.getUserById(id);
        return user.map(User::getFollowing).orElse(null);
    }

    @GetMapping("/{id}/liked-posts")
    public List<Post> getPostsLikedByUser(@PathVariable Long id) {
        Optional<User> user = userService.getUserById(id);
        return user.map(User::getLikedPosts).map(List::copyOf).orElse(null);
    }
}