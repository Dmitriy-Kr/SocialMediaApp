package com.example.socialmedia.repository;

import com.example.socialmedia.entity.Post;
import com.example.socialmedia.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByAuthor(User author);
}

