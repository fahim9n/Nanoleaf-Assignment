package com.fahim9n.nanoleafmobileassignment.model;

public class Device {

    private int deviceId;
    private boolean power;
    private int brightness;
    private int color;
    private String deviceName;

    public int getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }

    public boolean getPower() {
        return power;
    }

    public void setPower(boolean power) {
        this.power = power;
    }

    public int getBrightness() {
        return brightness;
    }

    public void setBrightness(int brightness) {
        this.brightness = brightness;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }


    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    @Override
    public String toString() {
        return "Device{" +
                "deviceId=" + deviceId +
                ", power=" + power +
                ", brightness=" + brightness +
                ", color=" + color +
                ", deviceName='" + deviceName + '\'' +
                '}';
    }

}
