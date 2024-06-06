package com.example.twittersocialmedia;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }



//    @GetMapping("")
//    public ResponseEntity<List<Post>> getAllPosts() {
//        List<Post> posts = postService.getAllPosts();
//        return ResponseEntity.ok(posts);
//    }

    @GetMapping("/post")
    public ResponseEntity<Object> getPostByID(@RequestParam Integer postID) {
        Optional<Post> postOptional = postService.getPostById(postID);

        if (postOptional.isPresent()) {
            Post post = postOptional.get();

            Map<String, Object> responseBody = new LinkedHashMap<>();
            responseBody.put("postID", post.getPostID());
            responseBody.put("postBody", post.getPostBody());
            responseBody.put("date", post.getDate());

            List<Map<String, Object>> comments = new ArrayList<>();
            for (Comment comment : post.getComments()) {
                Map<String, Object> commentMap = new LinkedHashMap<>();
                commentMap.put("commentID", comment.getCommentID());
                commentMap.put("commentBody", comment.getCommentBody());

                Map<String, Object> commentCreator = new LinkedHashMap<>();
                commentCreator.put("userID", comment.getUser().getUserID());
                commentCreator.put("name", comment.getUser().getName());

                commentMap.put("commentCreator", commentCreator);

                comments.add(commentMap);
            }
            responseBody.put("comments", comments);

            return ResponseEntity.ok(responseBody);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"Error\": \"Post does not exist\"}");
        }
    }


    static class TakePostRequest{
        int userID;
        String postBody;

        public TakePostRequest(Integer userID, String postBody) {
            this.userID = userID;
            this.postBody = postBody;
        }

        public int getUserID() { return userID; }
        public String getPostBody() { return postBody; }

        public void setUserID(int userID) {
            this.userID = userID;
        }

        public void setPostBody(String postBody) {
            this.postBody = postBody;
        }
    }



    @PostMapping("/post")
    public ResponseEntity<Object> createPost(@RequestBody TakePostRequest takePostRequest) {
        boolean created = postService.createPost(takePostRequest);
        if (created) {
            return ResponseEntity.status(HttpStatus.CREATED).body("Post created successfully");
        } else {
            String errorResponse = "{\"Error\": \"User does not exist\"}";
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }

    static class PatchRequest{

        private String postBody;
        private int postID;

        public String getPostBody() {
            return postBody;
        }

        public int getPostID() {
            return postID;
        }

        public void setPostBody(String postBody) {
            this.postBody = postBody;
        }

        public void setPostID(int postID) {
            this.postID = postID;
        }
    }

    @PatchMapping("/post")
    public Object editPost(@RequestBody PatchRequest patchRequest) {
        boolean edited = postService.editPost(patchRequest.postID, patchRequest.postBody);
        if (edited) {
            return "Post edited successfully";
        } else {
            String errorMessage = "{\"Error\": \"Post does not exist\"}";
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
        }
    }

    @DeleteMapping("/post")
    public ResponseEntity<Object> deletePost(@RequestParam int postID) {
        boolean deleted = postService.deletePost(postID);
        if (deleted) {
            return ResponseEntity.ok("Post deleted");
        } else {
            String errorMessage = "{\"Error\": \"Post does not exist\"}";
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
        }
    }

    // Implement other endpoints for post related operations
}
