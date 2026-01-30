package com.example.painterapp.service;

import com.example.painterapp.dto.PainterRegisterRequest;
import com.example.painterapp.dto.PainterUpdateRequest;
import com.example.painterapp.entity.Painter;
import com.example.painterapp.repository.PainterRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.Random;

@Service
public class PainterService {

    private final PainterRepository repo;
    private final SmsService smsService;
    private final PasswordEncoder encoder;

    public PainterService(PainterRepository repo,
                          SmsService smsService,
                          PasswordEncoder encoder) {
        this.repo = repo;
        this.smsService = smsService;
        this.encoder = encoder;
    }

    // ================= REGISTER =================
    public Painter register(PainterRegisterRequest req) {

        if (repo.existsByMobile(req.getMobile()))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Mobile already registered");

        if (req.getEmail() != null && repo.existsByEmail(req.getEmail()))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already registered");

        if (req.getAadhaar() != null && repo.existsByAadhaar(req.getAadhaar()))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Aadhaar already registered");

        Painter painter = new Painter();
        painter.setName(req.getName());
        painter.setDob(req.getDob());
        painter.setMobile(req.getMobile());
        painter.setEmail(req.getEmail());
        painter.setAadhaar(req.getAadhaar());
        painter.setWhatsapp(req.getWhatsapp());
        painter.setPincode(req.getPincode());
        painter.setTehsil(req.getTehsil());

        painter.setPassword(encoder.encode(req.getPassword()));
        painter.setPainterCode(generatePainterCode());
        painter.setTotalPoints(0);
        painter.setCreatedAt(LocalDateTime.now());

        return repo.save(painter);
    }

    // ================= LOGIN PASSWORD =================
    public Painter loginWithPassword(String mobile, String password) {

        Painter painter = repo.findByMobile(mobile)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Painter not found"));

        if (!encoder.matches(password, painter.getPassword()))
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "Invalid password");

        return painter;
    }

    // ================= OTP =================
    public void sendOtp(String mobile) {

        Painter painter = repo.findByMobile(mobile)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Painter not found"));

        String otp = generateOtp();
        painter.setOtp(otp);
        painter.setOtpExpiry(LocalDateTime.now().plusMinutes(5));

        repo.save(painter);
        smsService.sendOtp(mobile, otp);
    }

    public Painter verifyOtp(String mobile, String otp) {

        Painter painter = repo.findByMobile(mobile)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Painter not found"));

        if (!otp.equals(painter.getOtp()))
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Invalid OTP");

        if (painter.getOtpExpiry().isBefore(LocalDateTime.now()))
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "OTP expired");

        painter.setOtp(null);
        painter.setOtpExpiry(null);

        return repo.save(painter);
    }

    // ================= PROFILE =================
    public Painter getProfile(String mobile) {
        return repo.findByMobile(mobile)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Painter not found"));
    }

    public Painter updateProfile(String mobile, PainterUpdateRequest input) {

        Painter painter = getProfile(mobile);

        painter.setName(input.getName());
        painter.setTehsil(input.getTehsil());
        painter.setPincode(input.getPincode());
        painter.setWhatsapp(input.getWhatsapp());

        return repo.save(painter);
    }


    // ================= HELPERS =================
    private String generatePainterCode() {
        String code;
        do {
            code = "PT-" + (100000 + new Random().nextInt(900000));
        } while (repo.existsByPainterCode(code));
        return code;
    }

    private String generateOtp() {
        return String.valueOf(100000 + new Random().nextInt(900000));
    }
}
