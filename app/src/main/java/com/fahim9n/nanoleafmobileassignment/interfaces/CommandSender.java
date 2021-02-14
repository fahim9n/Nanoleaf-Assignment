package com.fahim9n.nanoleafmobileassignment.interfaces;

public interface CommandSender {
    void sendPowerCommandRequest(int deviceId, boolean isPowerOn);
    void sendBrightnessCommandRequest(int deviceId, int brightnessValue);
}
