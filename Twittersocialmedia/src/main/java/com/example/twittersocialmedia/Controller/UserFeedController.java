package com.example.twittersocialmedia;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("")
public class UserFeedController {

    private final PostService postService;

    public UserFeedController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/")
    public ResponseEntity<Map<String, Object>> getAllPosts() {
        List<Post> posts = postService.getAllPosts();

        // Sort posts by date and time in reverse chronological order
        posts.sort((p1, p2) -> {
            int dateComparison = p2.getDate().compareTo(p1.getDate()); // Compare dates in reverse order
            if (dateComparison == 0) {
                // If dates are equal, compare timestamps
                //int timeComparison = p2.getTime().compareTo(p1.getTime());
                // If timestamps are equal, compare postIDs
                return Integer.compare(p2.getPostID(), p1.getPostID());
            }
            return dateComparison;
        });

        Map<String, Object> responseBody = new LinkedHashMap<>();
        List<Map<String, Object>> postList = new ArrayList<>();

        for (Post post : posts) {
            Map<String, Object> postMap = new LinkedHashMap<>();
            postMap.put("postID", post.getPostID());
            postMap.put("postBody", post.getPostBody());
            postMap.put("date", post.getDate());

            List<Map<String, Object>> commentsList = new ArrayList<>();
            for (Comment comment : post.getComments()) {

                Map<String, Object> commentMap = new LinkedHashMap<>();
                commentMap.put("commentID", comment.getCommentID());
                commentMap.put("commentBody", comment.getCommentBody());

                Map<String, Object> commentCreator = new LinkedHashMap<>();
                commentCreator.put("userID", comment.getUser().getUserID());
                commentCreator.put("name", comment.getUser().getName());

                commentMap.put("commentCreator", commentCreator);

                commentsList.add(commentMap);
            }
            postMap.put("comments", commentsList);

            postList.add(postMap);
        }

        responseBody.put("posts", postList);

        return ResponseEntity.ok(responseBody);
    }

}