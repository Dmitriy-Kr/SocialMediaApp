package com.example.socialmedia.controller;

import com.example.socialmedia.entity.Post;
import com.example.socialmedia.entity.User;
import com.example.socialmedia.service.PostService;
import com.example.socialmedia.service.UserService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService postService;
    private final UserService userService;

    public PostController(PostService postService, UserService userService) {
        this.postService = postService;
        this.userService = userService;
    }

    @PostMapping
    public Post createPost(@RequestParam String title, @RequestParam String body, @RequestParam Long authorId) {
        Optional<User> author = userService.getUserById(authorId);
        return author.map(user -> postService.createPost(title, body, user)).orElse(null);
    }

    @GetMapping("/user/{authorId}")
    public List<Post> getPostsByAuthor(@PathVariable Long authorId) {
        Optional<User> author = userService.getUserById(authorId);
        return author.map(postService::getPostsByAuthor).orElse(null);
    }

    @PostMapping("/{postId}/like")
    public void likePost(@RequestParam Long userId, @PathVariable Long postId) {
        Optional<User> user = userService.getUserById(userId);
        Optional<Post> post = postService.findPostById(postId); // Corrected this line
        if (user.isPresent() && post.isPresent()) {
            postService.likePost(user.get(), post.get());
        }
    }
}