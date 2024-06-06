package com.example.twittersocialmedia;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;
//import java.time.LocalDate;


@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;
    @Autowired
    private UserRepository userRepository;

    public PostService(UserRepository userRepository, PostRepository postRepository) {
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }




    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    public Optional<Post> getPostById(Integer postID) {
        return postRepository.findById(postID);
    }

    public boolean createPost(PostController.TakePostRequest takePostRequest) {
        // Check if user exists
        Optional<UserDetails> userOptional = userRepository.findById(takePostRequest.userID);
        if (!userOptional.isPresent()) {
            return false;
        }

        UserDetails user = userOptional.get();

        Post post = new Post();
        post.setPostBody(takePostRequest.postBody);
        post.setUser(user);
        post.setDate(LocalDate.now());
        postRepository.save(post);
        return true;
    }




    public boolean editPost(Integer postID, String postBody) {
        Optional<Post> optionalPost = postRepository.findById(postID);
        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();
            post.setPostBody(postBody);
            postRepository.save(post);
            return true;
        }
        return false;
    }

    private static void merge(int[] arr, int[] left, int[] right) {
        int i = 0, j = 0, k = 0;
        while (i < left.length && j < right.length) {
            if (left[i] <= right[j]) {
                arr[k++] = left[i++];
            } else {
                arr[k++] = right[j++];
            }
        }
        while (i < left.length) {
            arr[k++] = left[i++];
        }
        while (j < right.length) {
            arr[k++] = right[j++];
        }
    }


    public boolean deletePost(Integer postID) {
        Optional<Post> optionalPost = postRepository.findById(postID);
        if (optionalPost.isPresent()) {
            postRepository.deleteById(postID);
            return true;
        }
        return false;
    }

    public boolean postExists(Integer postID) {
        return postRepository.existsById(postID);
    }




    // Implement other post-related business logic methods
}

