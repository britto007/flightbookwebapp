package com.example.flightbook;

public class UserItem {
    private String key;
    private String username;
    private String password;
    private String email;
    private String role;
    private String status;
    private String joinDate;

    public UserItem() {
    }

    public UserItem(String username, String email, String role, String status, String joinDate) {
        this.username = username;
        this.email = email;
        this.role = role;
        this.status = status;
        this.joinDate = joinDate;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(String joinDate) {
        this.joinDate = joinDate;
    }
}
