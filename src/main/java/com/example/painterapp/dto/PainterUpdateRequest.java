package com.example.painterapp.dto;

public class PainterUpdateRequest {

    private String name;
    private String tehsil;
    private String pincode;
    private String whatsapp;

    // getters & setters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getTehsil() { return tehsil; }
    public void setTehsil(String tehsil) { this.tehsil = tehsil; }

    public String getPincode() { return pincode; }
    public void setPincode(String pincode) { this.pincode = pincode; }

    public String getWhatsapp() { return whatsapp; }
    public void setWhatsapp(String whatsapp) { this.whatsapp = whatsapp; }
}
