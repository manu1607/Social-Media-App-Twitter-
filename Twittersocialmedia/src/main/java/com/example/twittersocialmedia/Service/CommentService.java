package com.example.twittersocialmedia;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static java.io.ObjectInputFilter.merge;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;

    public List<Comment> getAllComments() {
        return commentRepository.findAll();
    }

    public Optional<Comment> getCommentById(Integer commentID) {
        return commentRepository.findById(commentID);
    }

    public boolean createComment(CommentController.TakeCommentRequest commentRequest) {
        // Check if post exists
        if (!postService.postExists(commentRequest.getPostID())) {
            return false; // Post does not exist
        }

        // Check if user exists
        if (!userService.userExists(commentRequest.getUserID())) {
            return false; // User does not exist
        }

        // Create and save the comment
        Comment comment = new Comment(commentRequest.getCommentBody(),
                userService.getUserById(commentRequest.getUserID()).get(),
                postService.getPostById(commentRequest.getPostID()).get());
        commentRepository.save(comment);
        return true; // Comment created successfully
    }

    public static void mergeSort(int[] arr) {
        if (arr.length > 1) {
            int mid = arr.length / 2;
            int[] left = Arrays.copyOfRange(arr, 0, mid);
            int[] right = Arrays.copyOfRange(arr, mid, arr.length);

            mergeSort(left);
            mergeSort(right);

            merge(arr, left, right);
        }
    }

    private static void merge(int[] arr, int[] left, int[] right) {

    }


    public boolean editComment(Integer commentID, String commentBody) {
        Optional<Comment> optionalComment = commentRepository.findById(commentID);
        if (optionalComment.isPresent()) {
            Comment comment = optionalComment.get();
            comment.setCommentBody(commentBody);
            commentRepository.save(comment);
            return true; // Comment edited successfully
        }
        return false; // Comment does not exist
    }

    public boolean deleteComment(Integer commentID) {
        Optional<Comment> optionalComment = commentRepository.findById(commentID);
        if (optionalComment.isPresent()) {
            commentRepository.delete(optionalComment.get());
            return true; // Comment deleted successfully
        }
        return false; // Comment does not exist
    }

    // Implement other comment-related business logic methods
}
