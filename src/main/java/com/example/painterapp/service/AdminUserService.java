package com.example.painterapp.service;

import com.example.painterapp.entity.Painter;
import com.example.painterapp.repository.PainterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AdminUserService {

    private final PainterRepository painterRepo;

    @Autowired
    public AdminUserService(PainterRepository painterRepo) {
        this.painterRepo = painterRepo;
    }

    public Painter search(String mobile, String aadhaar, String code) {

        Optional<Painter> painter = Optional.empty();

        // Priority 1: Mobile
        if (mobile != null && !mobile.trim().isEmpty()) {
            painter = painterRepo.findByMobile(mobile);
        }
        // Priority 2: Aadhaar
        else if (aadhaar != null && !aadhaar.trim().isEmpty()) {
            painter = painterRepo.findByAadhaar(aadhaar);
        }
        // Priority 3: Painter Code
        else if (code != null && !code.trim().isEmpty()) {
            painter = painterRepo.findByPainterCode(code);
        }
        else {
            throw new IllegalArgumentException("At least one search parameter is required");
        }

        return painter.orElseThrow(
                () -> new IllegalArgumentException("Painter not found")
        );
    }
}
