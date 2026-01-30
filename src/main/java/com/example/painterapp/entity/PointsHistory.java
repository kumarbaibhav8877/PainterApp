package com.example.painterapp.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "points_history")
public class PointsHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String painterCode;

    private String qrCode;

    private Integer points;

    private LocalDateTime date = LocalDateTime.now();

    // ================= CONSTRUCTORS =================

    public PointsHistory() {
    }

    public PointsHistory(String painterCode, String qrCode, Integer points) {
        this.painterCode = painterCode;
        this.qrCode = qrCode;
        this.points = points;
        this.date = LocalDateTime.now();
    }

    // ================= GETTERS & SETTERS =================

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPainterCode() {
        return painterCode;
    }

    public void setPainterCode(String painterCode) {
        this.painterCode = painterCode;
    }

    public String getQrCode() {
        return qrCode;
    }

    public void setQrCode(String qrCode) {
        this.qrCode = qrCode;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }
}