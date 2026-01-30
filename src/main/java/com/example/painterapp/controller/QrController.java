package com.example.painterapp.controller;

import com.example.painterapp.entity.QrCode;
import com.example.painterapp.dto.*;
import com.example.painterapp.service.QrService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/qr")
@CrossOrigin(origins = "*")
public class QrController {

    private final QrService qrService;

    public QrController(QrService qrService) {
        this.qrService = qrService;
    }

    // ✅ Generate SINGLE QR (with Base64 image)
    @PostMapping("/generate")
    public ResponseEntity<?> generate(@RequestParam int points) {
        try {
            QrResponse response = qrService.generate(points);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity
                    .internalServerError()
                    .body("QR generation failed");
        }
    }

    // ✅ Generate BULK QR (DB only, no image)
    @PostMapping("/bulk")
    public ResponseEntity<?> generateBulk(
            @RequestParam int count,
            @RequestParam int points
    ) {
        try {
            List<QrCode> qrCodes = qrService.bulk(count, points);
            return ResponseEntity.ok(qrCodes);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity
                    .internalServerError()
                    .body("Bulk QR generation failed");
        }
    }

    // ✅ Enable / Disable QR
    @PutMapping("/toggle/{id}")
    public ResponseEntity<?> toggleQr(@PathVariable Long id) {
        try {
            QrCode qrCode = qrService.toggle(id);
            return ResponseEntity.ok(qrCode);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity
                    .internalServerError()
                    .body("Failed to toggle QR status");
        }
    }
}
