package com.example.painterapp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.painterapp.entity.Admin;
import com.example.painterapp.repository.AdminRepository;

import java.util.Optional;

@Service
public class AdminService {

    private static final String ADMIN_SECRET = "PAINTER@2026";

    private final AdminRepository adminRepo;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public AdminService(AdminRepository adminRepo) {
        this.adminRepo = adminRepo;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    // ================= ADMIN REGISTER =================
    public String register(String username, String password, String secret) {

        if (username == null || password == null || secret == null) {
            return "INVALID_INPUT";
        }

        if (!ADMIN_SECRET.equals(secret)) {
            return "INVALID_SECRET_KEY";
        }

        Optional<Admin> existingAdmin = adminRepo.findByUsername(username);
        if (existingAdmin.isPresent()) {
            return "ADMIN_ALREADY_EXISTS";
        }

        Admin admin = new Admin();
        admin.setUsername(username);
        admin.setPassword(passwordEncoder.encode(password)); // 🔐 encrypted
        admin.setRole("ADMIN");

        adminRepo.save(admin);

        return "ADMIN_REGISTER_SUCCESS";
    }

    // ================= ADMIN LOGIN =================
    public String login(String username, String password) {

        if (username == null || password == null) {
            return "INVALID_INPUT";
        }

        Optional<Admin> adminOpt = adminRepo.findByUsername(username);

        if (adminOpt.isEmpty()) {
            return "ADMIN_NOT_FOUND";
        }

        Admin admin = adminOpt.get();

        if (!passwordEncoder.matches(password, admin.getPassword())) {
            return "WRONG_PASSWORD";
        }

        return "ADMIN_LOGIN_SUCCESS";
    }
}