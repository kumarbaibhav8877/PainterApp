package com.example.painterapp.service;

import com.example.painterapp.entity.QrCode;
import com.example.painterapp.repository.QrCodeRepository;

import com.example.painterapp.dto.QrResponse;
import com.google.zxing.*;
import com.google.zxing.common.BitMatrix;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

import javax.imageio.ImageIO;

@Service
public class QrService {

    private final QrCodeRepository qrRepo;

    public QrService(QrCodeRepository qrRepo) {
        this.qrRepo = qrRepo;
    }

    // ✅ Generate SINGLE QR (UNIQUE + IMAGE)
    public QrResponse generate(int points) {

        if (points <= 0) {
            throw new IllegalArgumentException("Points must be greater than zero");
        }

        String qrValue = generateQrValue();

        // Save QR in DB
        QrCode qr = new QrCode();
        qr.setQrValue(qrValue);
        qr.setPoints(points);
        qr.setActive(true);
        qr.setUsed(false);
        qrRepo.save(qr);

        // Generate QR Image (Base64)
        String qrImage = generateQrImage(qrValue);

        return new QrResponse(qrValue, points, qrImage);
    }

    // ✅ Generate BULK QR (UNIQUE + NO IMAGE to save memory)
    @Transactional
    public List<QrCode> bulk(int count, int points) {

        if (count <= 0) {
            throw new IllegalArgumentException("QR count must be greater than zero");
        }

        if (points <= 0) {
            throw new IllegalArgumentException("Points must be greater than zero");
        }

        List<QrCode> qrCodes = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            QrCode qr = new QrCode();
            qr.setQrValue(generateQrValue());
            qr.setPoints(points);
            qr.setActive(true);
            qr.setUsed(false);
            qrCodes.add(qr);
        }

        return qrRepo.saveAll(qrCodes);
    }

    // ✅ Enable / Disable QR
    public QrCode toggle(Long id) {

        QrCode qr = qrRepo.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("QR Code not found"));

        qr.setActive(!qr.isActive());
        return qrRepo.save(qr);
    }

    // ✅ 100% UNIQUE QR VALUE (NEVER DUPLICATE)
    private String generateQrValue() {
        return "QR-" + UUID.randomUUID().toString().toUpperCase();
    }

    // ✅ QR IMAGE GENERATION (BASE64)
    private String generateQrImage(String text) {
        try {
            int size = 300;
            BitMatrix matrix = new MultiFormatWriter()
                    .encode(text, BarcodeFormat.QR_CODE, size, size);

            BufferedImage image = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);

            for (int x = 0; x < size; x++) {
                for (int y = 0; y < size; y++) {
                    image.setRGB(x, y, matrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
                }
            }

            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ImageIO.write(image, "png", out);

            return Base64.getEncoder()
                    .encodeToString(out.toByteArray())
                    .replace("\n", "")
                    .replace("\r", "")
                    .trim();


        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("QR image generation failed");
        }
    }


}
