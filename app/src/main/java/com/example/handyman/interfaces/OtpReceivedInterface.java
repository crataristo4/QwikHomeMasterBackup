package com.example.handyman.interfaces;

public interface OtpReceivedInterface {

    void onOtpReceived(String otp);

    void onOtpTimeout();

}
