package com.example.painterapp.dto;

public class AdminRegisterDto {

    private String username;
    private String password;
    private String secretKey;

    // ===== DEFAULT CONSTRUCTOR =====
    public AdminRegisterDto() {
    }

    // ===== PARAMETERIZED CONSTRUCTOR =====
    public AdminRegisterDto(String username, String password, String secretKey) {
        this.username = username;
        this.password = password;
        this.secretKey = secretKey;
    }

    // ===== GETTERS & SETTERS =====
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

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }
}