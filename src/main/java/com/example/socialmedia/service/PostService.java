package com.example.socialmedia.service;

import com.example.socialmedia.entity.Post;
import com.example.socialmedia.entity.User;
import com.example.socialmedia.repository.PostRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {
    private final PostRepository postRepository;

    public PostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    public Post createPost(String title, String body, User author) {
        Post post = new Post();
        post.setTitle(title);
        post.setBody(body);
        post.setAuthor(author);
        return postRepository.save(post);
    }

    public List<Post> getPostsByAuthor(User author) {
        return postRepository.findByAuthor(author);
    }

    public Optional<Post> findPostById(Long postId) {
        return postRepository.findById(postId);
    }

    public void likePost(User user, Post post) {
        user.getLikedPosts().add(post);
        postRepository.save(post);
    }
}


