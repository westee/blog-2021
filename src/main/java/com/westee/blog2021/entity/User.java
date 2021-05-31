package com.westee.blog2021.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.Instant;

public class User {
    Integer id;
    String username;
    String avatar;
    @JsonIgnore
    String encryptedPassword;
    Instant createdAt;
    Instant updatedAt;

    public User(Integer id, String username, String encrypted_password) {
        this.id = id;
        this.username = username;
        this.avatar = "avatar";
        this.encryptedPassword = encrypted_password;
        this.createdAt = Instant.now();
        this.updatedAt = Instant.now();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatar() {
        return "avatar";
    }

    public void setAvatar(String avatar) {
        this.avatar = "avatar";
    }

    public String getEncryptedPassword() {
        return encryptedPassword;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }
}
