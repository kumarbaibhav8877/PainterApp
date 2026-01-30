package com.example.painterapp.repository;

import com.example.painterapp.entity.QrCode;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QrCodeRepository extends JpaRepository<QrCode, Long> {
	Optional<QrCode> findByQrValue(String qrValue);
}
