package com.example.painterapp.repository;

import com.example.painterapp.entity.Painter;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PainterRepository extends JpaRepository<Painter, Long> {
    Optional<Painter> findByMobile(String mobile);
    boolean existsByMobile(String mobile);
    boolean existsByAadhaar(String aadhaar);
}
