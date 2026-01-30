package com.example.painterapp.repository;

import com.example.painterapp.entity.Painter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PainterRepository extends JpaRepository<Painter, Long> {

    // ================= LOGIN =================
    Optional<Painter> findByMobile(String mobile);
    
    Optional<Painter> findByAadhaar(String aadhaar);
    Optional<Painter> findByPainterCode(String painterCode);
    // ================= DUPLICATE CHECKS =================
    boolean existsByMobile(String mobile);

    boolean existsByEmail(String email);

    boolean existsByAadhaar(String aadhaar);

    boolean existsByPainterCode(String painterCode);
}
