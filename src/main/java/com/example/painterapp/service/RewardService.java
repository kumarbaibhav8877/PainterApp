package com.example.painterapp.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.painterapp.entity.Painter;
import com.example.painterapp.entity.PointsHistory;
import com.example.painterapp.entity.QrCode;
import com.example.painterapp.repository.PainterRepository;
import com.example.painterapp.repository.PointsHistoryRepository;
import com.example.painterapp.repository.QrCodeRepository;


@Service
public class RewardService {

    private final QrCodeRepository qrRepo;
    private final PainterRepository painterRepo;
    private final PointsHistoryRepository historyRepo;

    public RewardService(
            QrCodeRepository qrRepo,
            PainterRepository painterRepo,
            PointsHistoryRepository historyRepo) {

        this.qrRepo = qrRepo;
        this.painterRepo = painterRepo;
        this.historyRepo = historyRepo;
    }

    public Painter scanQr(String painterCode, String qrValue) {

        Painter painter = painterRepo.findByPainterCode(painterCode)
                .orElseThrow(() -> new RuntimeException("Painter not found"));

        QrCode qr = qrRepo.findByQrValue(qrValue)
                .orElseThrow(() -> new RuntimeException("Invalid QR"));

        if (!qr.isActive())
            throw new RuntimeException("QR disabled");

        if (qr.isUsed())
            throw new RuntimeException("QR already used");

        // ✅ ADD POINTS
        painter.setTotalPoints(
                painter.getTotalPoints() + qr.getPoints()
        );

        // ✅ MARK QR USED
        qr.setUsed(true);

        // ✅ SAVE HISTORY
        PointsHistory history = new PointsHistory(
                painterCode,
                qrValue,
                qr.getPoints()
        );

        painterRepo.save(painter);
        qrRepo.save(qr);
        historyRepo.save(history);

        return painter;
    }



    // ================= HISTORY =================
    public List<PointsHistory> history(String painterCode) {
        return historyRepo.findByPainterCodeOrderByDateDesc(painterCode);
    }
}
