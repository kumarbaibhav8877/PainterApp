package com.example.painterapp.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "painters",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "mobile"),
                @UniqueConstraint(columnNames = "email"),
                @UniqueConstraint(columnNames = "aadhaar"),
                @UniqueConstraint(columnNames = "painter_code")
        }
)
public class Painter {

    // ================= PRIMARY KEY =================
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ================= AUTO GENERATED CODE =================
    @Column(name = "painter_code", nullable = false, unique = true)
    private String painterCode;

    // ================= BASIC DETAILS =================
    @Column(nullable = false)
    private String name;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate dob;

    // ================= LOGIN =================
    @Column(nullable = false, length = 10)
    private String mobile;

    @JsonIgnore
    private String password;

    @Column(unique = true)
    private String email;

    // ================= IDENTITY =================
    @Column(unique = true, length = 12)
    private String aadhaar;

    // ================= ADDRESS =================
    @Column(length = 10)
    private String whatsapp;

    @Column(length = 6)
    private String pincode;

    private String tehsil;

    // ================= REWARD =================
    private Integer totalPoints = 0;

    // ================= PROFILE =================
    private String profilePhoto;

    // ================= OTP LOGIN =================
    @JsonIgnore
    private String otp;

    @JsonIgnore
    private LocalDateTime otpExpiry;

    // ================= AUDIT =================
    private LocalDateTime createdAt;

    // ================= CONSTRUCTOR =================
    public Painter() {
        this.createdAt = LocalDateTime.now();
    }

    // ================= GETTERS & SETTERS =================

    public Long getId() {
        return id;
    }

    public String getPainterCode() {
        return painterCode;
    }

    public void setPainterCode(String painterCode) {
        this.painterCode = painterCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
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

    public String getAadhaar() {
        return aadhaar;
    }

    public void setAadhaar(String aadhaar) {
        this.aadhaar = aadhaar;
    }

    public String getWhatsapp() {
        return whatsapp;
    }

    public void setWhatsapp(String whatsapp) {
        this.whatsapp = whatsapp;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getTehsil() {
        return tehsil;
    }

    public void setTehsil(String tehsil) {
        this.tehsil = tehsil;
    }

    public Integer getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(Integer totalPoints) {
        this.totalPoints = totalPoints;
    }

    public String getProfilePhoto() {
        return profilePhoto;
    }

    public void setProfilePhoto(String profilePhoto) {
        this.profilePhoto = profilePhoto;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public LocalDateTime getOtpExpiry() {
        return otpExpiry;
    }

    public void setOtpExpiry(LocalDateTime otpExpiry) {
        this.otpExpiry = otpExpiry;
    }

    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
}
