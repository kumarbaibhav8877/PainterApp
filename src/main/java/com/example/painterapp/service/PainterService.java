package com.example.painterapp.service;

import com.example.painterapp.entity.Painter;
import com.example.painterapp.repository.PainterRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
public class PainterService {

    private final PainterRepository repo;
    private final SmsService smsService;

    public PainterService(PainterRepository repo, SmsService smsService) {
        this.repo = repo;
        this.smsService = smsService;
    }

    // ================= REGISTER =================
    public Painter register(Painter painter) {

        if (repo.existsByMobile(painter.getMobile())) {
            throw new RuntimeException("Mobile already exists");
        }

        if (repo.existsByAadhaar(painter.getAadhaar())) {
            throw new RuntimeException("Aadhaar already exists");
        }

        return repo.save(painter);
    }

    // ================= LOGIN WITHOUT OTP =================
    public Painter login(String mobile) {
        return repo.findByMobile(mobile)
                .orElseThrow(() -> new RuntimeException("Painter not found"));
    }

    // ================= SEND OTP =================
    public void sendOtp(String mobile) {

        Painter painter = repo.findByMobile(mobile)
                .orElseThrow(() -> new RuntimeException("Painter not found"));

        String otp = generateOtp();

        painter.setOtp(otp);
        painter.setOtpExpiry(LocalDateTime.now().plusMinutes(5));
        repo.save(painter);

        // 🔹 SMS SEND (Twilio)
        smsService.sendOtp(mobile, otp);
    }

    // ================= VERIFY OTP =================
    public Painter verifyOtp(String mobile, String otp) {

        Painter painter = repo.findByMobile(mobile)
                .orElseThrow(() -> new RuntimeException("Painter not found"));

        if (painter.getOtp() == null) {
            throw new RuntimeException("OTP not generated");
        }

        if (!otp.equals(painter.getOtp())) {
            throw new RuntimeException("Invalid OTP");
        }

        if (painter.getOtpExpiry().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("OTP expired");
        }

        // 🔹 Clear OTP after success
        painter.setOtp(null);
        painter.setOtpExpiry(null);

        return repo.save(painter);
    }

    // ================= OTP GENERATOR =================
    private String generateOtp() {
        return String.valueOf(100000 + new Random().nextInt(900000));
    }
}
