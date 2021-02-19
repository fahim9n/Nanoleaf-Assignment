package com.fahim9n.nanoleafmobileassignment.utilites;

import com.fahim9n.nanoleafmobileassignment.interfaces.CommandSender;

import java.util.HashMap;
import java.util.List;

public class CommandCenter implements CommandSender {

    HashMap<Integer,Long> commandMap= new HashMap<>();
    public static final int MIN_TIME_INTERVAL= 500;

    public void sendPowerCommandRequest(int deviceId, boolean isPowerOn) {
        int powerValue = isPowerOn ? 1 : 0;
        String powerStatus = isPowerOn ? "on" : "off";
        System.out.println("Device " + deviceId + "- " + "Turned " + powerStatus);
    }

    public void sendBrightnessCommandRequest(int deviceId, int brightnessValue) {

        long currentTimeMillis = getCurrentTimeMillis();
        if(commandMap.containsKey(deviceId)){
            long time=currentTimeMillis-commandMap.get(deviceId);
           // System.out.println("device time diff command center "+time);

        }
        if(!(commandMap.containsKey(deviceId)) || (currentTimeMillis-commandMap.get(deviceId))>MIN_TIME_INTERVAL ){
            System.out.println("Device " + deviceId + "- " + "brightness set to " + brightnessValue);
            commandMap.put(deviceId,currentTimeMillis);
        }/* else if( commandMap.containsKey(deviceId) && ){
            System.out.println("Device " + deviceId + "- " + "brightness set to " + brightnessValue);
            commandMap.put(deviceId,currentTimeMillis);
        }*/

    }

    public long getCurrentTimeMillis() {
        return System.currentTimeMillis();
    }

    public HashMap<Integer, Long> getCommandMap() {
        return commandMap;
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
          //  sendBrightnessCommandRequest(integer, brightnessValue);
        }

    }
}
