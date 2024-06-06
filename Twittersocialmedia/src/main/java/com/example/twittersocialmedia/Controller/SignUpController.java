package com.example.twittersocialmedia;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path="/signup")
public class SignUpController {

    private final UserService userService;

    public SignUpController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<String> signup(@RequestBody UserDetails user) {
        boolean created = userService.createUser(user);
        if (created) {
            return ResponseEntity.status(HttpStatus.CREATED).body("Account Creation Successful");
        } else {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("{\"Error\": \"Forbidden, Account already exists\"}");
        }
    }
}