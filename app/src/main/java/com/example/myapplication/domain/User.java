package com.example.myapplication.domain;

public class User {
    private int userId;
    private int adminpriority;
    private String username;
    private String email;
    private String password;

    public User(int userId, String username, String email, String password, int adminpriority) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.password = password;
        this.adminpriority = adminpriority;
    }

    public int getUserId() {
        return userId;
    }
    public void setUserId(int userID) {
        this.userId = userId;
    }
    public int getAdminpriority() {
        return adminpriority;
    }
    public void setAdminpriority(int adminpriority) {
        this.adminpriority = adminpriority;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}