package com.fahim9n.nanoleafmobileassignment.interfaces;

import java.util.HashMap;

public interface CommandSender {
    void sendPowerCommandRequest(int deviceId, boolean isPowerOn);
    void sendBrightnessCommandRequest(int deviceId, int brightnessValue);
    HashMap<Integer, Long> getCommandMap();
}
