package com.example.painterapp.controller;

import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.painterapp.entity.Painter;
import com.example.painterapp.service.RewardService;

@RestController
@RequestMapping("/api/reward")
@CrossOrigin("*")
public class RewardController {

    private final RewardService service;

    public RewardController(RewardService service) {
        this.service = service;
    }

    // ================= SCAN QR =================
    @PostMapping("/scan")
    public ResponseEntity<?> scan(@RequestBody Map<String, String> body) {
        try {
            Painter painter = service.scanQr(
                    body.get("painterCode"),
                    body.get("qrCode")
            );

            return ResponseEntity.ok(Map.of(
                    "message", "Points added successfully",
                    "totalPoints", painter.getTotalPoints()
            ));

        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of(
                    "message", e.getMessage()
            ));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of(
                    "message", "Internal server error"
            ));
        }
    }


    // ================= HISTORY =================
    @GetMapping("/history")
    public ResponseEntity<?> history(@RequestParam String painterCode) {
        return ResponseEntity.ok(service.history(painterCode));
    }
}
