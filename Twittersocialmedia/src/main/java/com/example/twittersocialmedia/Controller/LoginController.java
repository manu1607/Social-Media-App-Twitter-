package com.example.twittersocialmedia;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/login")
public class LoginController {

    private final UserService userService;
    @Autowired
    private UserRepository userRepository;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("")
    public ResponseEntity<Object> login(@RequestBody UserDetails user) {
        UserDetails existingUser = userRepository.findByEmail(user.getEmail());

        if (existingUser != null) {
            boolean loggedIn = userService.login(user.getEmail(), user.getPassword());
            if (loggedIn) {
                return ResponseEntity.ok("Login Successful");
            } else {

                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("{\"Error\": \"Username/Password Incorrect\"}");
            }
        } else {

            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("{\"Error\": \"User does not exist\"}");
        }
    }


}
