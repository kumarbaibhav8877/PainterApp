package com.example.painterapp.controller;

import com.example.painterapp.dto.OtpRequest;
import com.example.painterapp.dto.OtpVerifyRequest;
import com.example.painterapp.dto.PainterRegisterRequest;
import com.example.painterapp.dto.PainterUpdateRequest;
import com.example.painterapp.entity.Painter;
import com.example.painterapp.security.JwtUtil;
import com.example.painterapp.service.PainterService;
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

    // ================= REGISTER =================
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody PainterRegisterRequest request) {
        Painter painter = service.register(request);
        return ResponseEntity.ok(painter);
    }

    // ================= LOGIN PASSWORD =================
    @PostMapping("/login-password")
    public ResponseEntity<?> loginPassword(@RequestBody Map<String, String> body) {

        Painter painter = service.loginWithPassword(
                body.get("mobile"),
                body.get("password")
        );

        String token = JwtUtil.generateToken(painter.getPainterCode());

        return ResponseEntity.ok(Map.of(
                "token", token,
                "painter", painter
        ));
    }

    // ================= SEND OTP =================
    @PostMapping("/send-otp")
    public ResponseEntity<?> sendOtp(@RequestBody OtpRequest request) {
        service.sendOtp(request.getMobile());
        return ResponseEntity.ok(Map.of("message", "OTP sent successfully"));
    }

    // ================= VERIFY OTP =================
    @PostMapping("/verify-otp")
    public ResponseEntity<?> verifyOtp(@RequestBody OtpVerifyRequest request) {

        Painter painter = service.verifyOtp(
                request.getMobile(),
                request.getOtp()
        );

        String token = JwtUtil.generateToken(painter.getPainterCode());

        return ResponseEntity.ok(Map.of(
                "token", token,
                "painter", painter
        ));
    }

    // ================= PROFILE =================
    @GetMapping("/profile")
    public ResponseEntity<?> profile(@RequestParam String mobile) {
        return ResponseEntity.ok(service.getProfile(mobile));
    }

    // ================= UPDATE PROFILE =================
    @PutMapping("/update-profile")
    public ResponseEntity<?> updateProfile(
            @RequestParam String mobile,
            @RequestBody PainterUpdateRequest input
    ) {
        return ResponseEntity.ok(service.updateProfile(mobile, input));
    }

}
