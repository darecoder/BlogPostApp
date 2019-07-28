package com.ekta.BlogPostApp.payload;

import java.time.Instant;

public class UserProfile {
    private Long id;
    private String username;
    private Instant joinedAt;
    private Long postCount;

    public UserProfile(Long id, String username, Instant joinedAt, Long postCount) {
        this.id = id;
        this.username = username;
        this.joinedAt = joinedAt;
        this.postCount = postCount;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Instant getJoinedAt() {
        return joinedAt;
    }

    public void setJoinedAt(Instant joinedAt) {
        this.joinedAt = joinedAt;
    }

    public Long getPostCount() {
        return postCount;
    }

    public void setPostCount(Long postCount) {
        this.postCount = postCount;
    }
}
