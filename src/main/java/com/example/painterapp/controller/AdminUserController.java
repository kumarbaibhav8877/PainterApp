package com.example.painterapp.controller;

import com.example.painterapp.entity.Painter;
import com.example.painterapp.service.AdminUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/users")
@CrossOrigin(origins = "*")
public class AdminUserController {

    private final AdminUserService service;

    @Autowired
    public AdminUserController(AdminUserService service) {
        this.service = service;
    }

    // ================= SEARCH PAINTER =================
    @GetMapping("/search")
    public ResponseEntity<?> searchPainter(
            @RequestParam(required = false) String mobile,
            @RequestParam(required = false) String aadhaar,
            @RequestParam(required = false) String code
    ) {
        try {
            Painter painter = service.search(mobile, aadhaar, code);
            return ResponseEntity.ok(painter);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

}
