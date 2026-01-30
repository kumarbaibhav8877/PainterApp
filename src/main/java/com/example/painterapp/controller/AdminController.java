package com.example.painterapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.painterapp.dto.AdminRegisterDto;
import com.example.painterapp.entity.Admin;
import com.example.painterapp.service.AdminService;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "*")
public class AdminController {

    private final AdminService adminService;

    @Autowired
    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    // ================= ADMIN REGISTER =================
    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody AdminRegisterDto dto) {

        String result = adminService.register(
                dto.getUsername(),
                dto.getPassword(),
                dto.getSecretKey()
        );

        return ResponseEntity.ok(result);
    }

    // ================= ADMIN LOGIN =================
    @PostMapping("/login")
    public String login(@RequestBody Admin admin) {
        return adminService.login(
                admin.getUsername(),
                admin.getPassword()
        );
    }
}