package com.example.painterapp.controller;

import com.example.painterapp.entity.Painter;
import com.example.painterapp.security.JwtUtil;
import com.example.painterapp.service.PainterService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/painter")
@CrossOrigin("*")
public class PainterController {

    private final PainterService service;

    public PainterController(PainterService service) {
        this.service = service;
    }

    // ================= REGISTER PAINTER =================
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Painter painter) {
        try {
            Painter savedPainter = service.register(painter);
            return ResponseEntity.ok(savedPainter);
        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    // ================= LOGIN WITHOUT OTP (OPTIONAL) =================
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestParam String mobile) {
        try {
            Painter painter = service.login(mobile);
            return ResponseEntity.ok(painter);
        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    // ================= SEND OTP =================
    @PostMapping("/send-otp")
    public ResponseEntity<?> sendOtp(@RequestParam String mobile) {
        try {
            service.sendOtp(mobile);
            return ResponseEntity.ok(
                    Map.of("message", "OTP sent successfully")
            );
        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    // ================= VERIFY OTP + JWT TOKEN =================
    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOtp(
            @RequestParam String mobile,
            @RequestParam String otp) {

        try {
            Painter painter = service.verifyOtp(mobile, otp);

            String token = JwtUtil.generateToken(mobile);

            return ResponseEntity.ok(
                    Map.of(
                            "token", token,
                            "painter", painter
                    )
            );
        } catch (RuntimeException e) {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", e.getMessage()));
        }
    }
}
