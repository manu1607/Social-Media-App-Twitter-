package com.example.twittersocialmedia;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/comment")
public class CommentController {

    private final CommentService commentService;
    private final UserService userService;
    private final PostService postService;

    public CommentController(CommentService commentService, UserService userService, PostService postService) {
        this.commentService = commentService;
        this.userService = userService;
        this.postService = postService;
    }



    static class TakeCommentRequest {
        String commentBody;
        int commentID;
        int postID;
        int userID;

        public String getCommentBody() {
            return commentBody;
        }

        public int getCommentID() {
            return commentID;
        }

        public void setCommentId(int commentId) {
            this.commentID = commentId;
        }

        public void setCommentBody(String commentBody) {
            this.commentBody = commentBody;
        }

        public int getPostID() {
            return postID;
        }

        public void setPostID(int postID) {
            this.postID = postID;
        }

        public int getUserID() {
            return userID;
        }

        public void setUserID(int userID) {
            this.userID = userID;
        }

        public TakeCommentRequest(String commentBody, int postID, int userID) {
            this.commentBody = commentBody;
            this.postID = postID;
            this.userID = userID;
        }
    }

    @PostMapping()
    public ResponseEntity<String> createComment(@RequestBody TakeCommentRequest commentRequest) {
        boolean created = commentService.createComment(commentRequest);
        if (created) {
            return ResponseEntity.status(HttpStatus.CREATED).body("Comment created successfully");
        } else if (!userService.userExists(commentRequest.getUserID())) {
            String errorMessage = "{\"Error\": \"User does not exist\"}";
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
        } else if (!postService.postExists(commentRequest.getPostID())) {
            String errorMessage = "{\"Error\": \"Post does not exist\"}";
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
        } else {
            String errorMessage = "{\"Error\": \"An unexpected error occurred\"}";
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessage);
        }
    }

    @GetMapping()
    public ResponseEntity<?> getCommentByID(@RequestParam Integer commentID) {
        Optional<Comment> comment = commentService.getCommentById(commentID);
        if (comment.isPresent()) {
            Comment commentObj = comment.get();
            Map<String, Object> responseBody = new LinkedHashMap<>();
            responseBody.put("commentID", commentObj.getCommentID());
            responseBody.put("commentBody", commentObj.getCommentBody());

            Map<String, Object> commentCreator = new LinkedHashMap<>();
            commentCreator.put("userID", commentObj.getUser().getUserID());
            commentCreator.put("name", commentObj.getUser().getName());

            responseBody.put("commentCreator", commentCreator);

            return ResponseEntity.ok(responseBody);
        } else {
            String errorMessage = "{\"Error\": \"Comment does not exist\"}";
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
        }
    }


    @PatchMapping()
    public ResponseEntity<String> editComment(@RequestBody TakeCommentRequest takeCommentRequest) {
        Integer commentId = takeCommentRequest.getCommentID();
        String commentBody = takeCommentRequest.getCommentBody();

        boolean edited = commentService.editComment(commentId, commentBody);
        if (edited) {
            return ResponseEntity.ok("Comment edited successfully");
        } else {
            String errorMessage = "{\"Error\": \"Comment does not exist\"}";
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
        }
    }

    @DeleteMapping()
    public ResponseEntity<String> deleteComment(@RequestParam int commentID) {
        boolean deleted = commentService.deleteComment(commentID);
        if (deleted) {
            return ResponseEntity.ok("Comment deleted");
        } else {
            String errorMessage = "{\"Error\": \"Comment does not exist\"}";
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
        }
    }

}
