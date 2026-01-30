package com.example.painterapp.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(
        name = "qr_codes",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "qr_value")
        }
)
public class QrCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "qr_value", nullable = false, unique = true)
    private String qrValue;

    @Column(nullable = false)
    private int points;

    @Column(nullable = false)
    private boolean used = false;

    @Column(nullable = false)
    private boolean active = true;

    @JsonIgnore
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    @JsonIgnore
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public QrCode() {}

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    // ===== GETTERS & SETTERS =====
    public Long getId() { return id; }
    public String getQrValue() { return qrValue; }
    public void setQrValue(String qrValue) { this.qrValue = qrValue; }
    public int getPoints() { return points; }
    public void setPoints(int points) { this.points = points; }
    public boolean isUsed() { return used; }
    public void setUsed(boolean used) { this.used = used; }
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
}
