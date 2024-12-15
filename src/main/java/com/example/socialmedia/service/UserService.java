package com.example.socialmedia.service;

import com.example.socialmedia.entity.User;
import com.example.socialmedia.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(String username, String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        return userRepository.save(user);
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public void followUser(User follower, User following) {
        follower.getFollowing().add(following);
        following.getFollowers().add(follower);
        userRepository.save(follower);
        userRepository.save(following);
    }

//    public void followUser(Long id, Long followId) {
//        Optional<User> follower = userRepository.findById(id);
//        Optional<User> following = userRepository.findById(followId);
//        if (follower.isPresent() && following.isPresent()) {
//            follower.get().getFollowing().add(following.get());
//            following.get().getFollowers().add(follower.get());
//            userRepository.save(follower.get());
//            userRepository.save(following.get());
//        }

//    }
}