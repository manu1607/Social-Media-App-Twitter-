package com.example.twittersocialmedia;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.*;

@RestController
@RequestMapping("") // Updated base mapping for UserController
public class UserController {

    private final UserService userService;
    private final PostService postService;

    public UserController(UserService userService,PostService postService) {
        this.userService = userService;
        this.postService = postService;
    }

    @GetMapping("/users")
    public ResponseEntity<Object> getAllUsers() {
        List<UserDetails> users = userService.getAllUsers();

        List<Map<String, Object>> responseBody = new ArrayList<>();
        for (UserDetails user : users) {
            Map<String, Object> userMap = new LinkedHashMap<>();
            userMap.put("name", user.getName());
            userMap.put("userID", user.getUserID());
            userMap.put("email", user.getEmail());
            responseBody.add(userMap);
        }

        return ResponseEntity.ok(responseBody);
    }

    @GetMapping("/user")
    public ResponseEntity<Object> getUserById(@RequestParam Integer userID) {
        Optional<UserDetails> userOptional = userService.getUserById(userID);
        if (userOptional.isPresent()) {
            UserDetails user = userOptional.get();
            Map<String, Object> responseBody = new HashMap<>();
            responseBody.put("name", user.getName());
            responseBody.put("userID", user.getUserID());
            responseBody.put("email", user.getEmail());
            return ResponseEntity.ok(responseBody);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"Error\": \"User does not exist\"}");
        }
    }


}
