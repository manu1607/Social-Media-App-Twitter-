package com.example.twittersocialmedia;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer commentId;
    private String commentBody;

    @ManyToOne
    private UserDetails user;

    @ManyToOne
    @JsonIgnore
    private Post post;

    // Constructors, Getters and Setters

    // Constructors
    public Comment() {
    }

    public Comment(String commentBody, UserDetails user, Post post) {
        this.commentBody = commentBody;
        this.user = user;
        this.post = post;
    }

    // Getters and Setters
    public Integer getCommentID() {
        return commentId;
    }

    public void setCommentID(Integer commentId) {
        this.commentId = commentId;
    }

    public String getCommentBody() {
        return commentBody;
    }

    public void setCommentBody(String commentBody) {
        this.commentBody = commentBody;
    }

    public UserDetails getUser() {
        return user;
    }
    public Integer getUserID(){
        return user.getUserID();
    }


    public void setUser(UserDetails user) {
        this.user = user;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }


}

