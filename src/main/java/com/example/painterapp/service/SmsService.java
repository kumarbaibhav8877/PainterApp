package com.example.painterapp.service;

import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SmsService {

    @Value("${twilio.phone.number}")
    private String fromNumber;

    public void sendOtp(String toMobile, String otp) {

        String message = "Your OTP for Painter App login is " + otp +
                ". Do not share it with anyone.";

        Message.creator(
                new PhoneNumber("+91" + toMobile), // India
                new PhoneNumber(fromNumber),
                message
        ).create();
    }
}
