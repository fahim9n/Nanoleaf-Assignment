package com.fahim9n.nanoleafmobileassignment.utilites;

import com.fahim9n.nanoleafmobileassignment.interfaces.CommandSender;

import java.util.List;

public class CommandCenter implements CommandSender {

    public void sendPowerCommandRequest(int deviceId, boolean isPowerOn) {
        int powerValue = isPowerOn ? 1 : 0;
        String powerStatus = isPowerOn ? "on" : "off";
        System.out.println("Device " + deviceId + "- " + "Turned " + powerStatus);
    }

    public void sendBrightnessCommandRequest(int deviceId, int brightnessValue) {

        System.out.println("Device " + deviceId + "- " + "brightness set to " + brightnessValue);

    }

    public void sendGlobalPowerCommandRequest(List<Integer> deviceIdList, boolean isPowerOn) {
        int powerValue = isPowerOn ? 1 : 0;
        String powerStatus = isPowerOn ? "on" : "off";

        for (Integer integer : deviceIdList) {
            sendPowerCommandRequest(integer, isPowerOn);
        }

    }

    public void sendGlobalBrightnessCommandRequest(List<Integer> deviceIdList, int brightnessValue) {

        for (Integer integer : deviceIdList) {
            sendBrightnessCommandRequest(integer, brightnessValue);
        }

    }
}
