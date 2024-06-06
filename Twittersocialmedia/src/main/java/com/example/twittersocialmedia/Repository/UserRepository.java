package com.example.twittersocialmedia;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<UserDetails,Integer> {
    UserDetails findByEmail(String email);
}

