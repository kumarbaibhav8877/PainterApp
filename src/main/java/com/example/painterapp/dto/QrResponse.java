package com.example.painterapp.dto;

public class QrResponse {

    private String qrValue;
    private int points;
    private String qrImage; // Base64

    public QrResponse(String qrValue, int points, String qrImage) {
        this.qrValue = qrValue;
        this.points = points;
        this.qrImage = qrImage;
    }

    public String getQrValue() {
        return qrValue;
    }

    public int getPoints() {
        return points;
    }

    public String getQrImage() {
        return qrImage;
    }
}