package com.example.twittersocialmedia;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<UserDetails> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<UserDetails> getUserById(Integer userID) {
        return userRepository.findById(userID);
    }



    public boolean login(String email, String password) {
        UserDetails user = userRepository.findByEmail(email);
        return user != null && user.getPassword().equals(password);
    }
    public static int binarySearch(int[] arr, int target) {
        int left = 0;
        int right = arr.length - 1;
        while (left <= right) {
            int mid = left + (right - left) / 2;
            if (arr[mid] == target) {
                return mid;
            } else if (arr[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return -1;
    }
    public boolean createUser(UserDetails user) {
        if (userRepository.findByEmail(user.getEmail()) != null) {
            return false; // User with this email already exists
        }
        userRepository.save(user);
        return true;
    }

    public boolean userExists(Integer userID) {
        return userRepository.existsById(userID);
    }

}

