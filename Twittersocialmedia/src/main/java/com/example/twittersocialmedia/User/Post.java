package com.example.twittersocialmedia;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;

import java.util.List;

@Entity
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer postID;
    private String postBody;
    private LocalDate date;

    @ManyToOne
    @JsonIgnore
    private UserDetails user; // Change to singular "User"

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Comment> comments = new ArrayList<>();

    // Constructors, Getters and Setters

    // Constructors
    public Post() {
    }

    public Post(String postBody, LocalDate date, UserDetails user) { // Change to singular "User"
        this.postBody = postBody;
        this.date = date;
        this.user = user;
    }

    public Integer getPostID() {
        return postID;
    }

    public void setPostID(Integer postID) { // Change method name to setPostId
        this.postID = postID;
    }

    public String getPostBody() {
        return postBody;
    }

    public void setPostBody(String postBody) {
        this.postBody = postBody;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public UserDetails getUser() { // Change to singular "User"
        return user;
    }

    public void setUser(UserDetails user) { // Change to singular "User"
        this.user = user;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }


}

