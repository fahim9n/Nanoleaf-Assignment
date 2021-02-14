package com.fahim9n.nanoleafmobileassignment.utilites;

import android.content.Context;
import android.graphics.Color;

import com.fahim9n.nanoleafmobileassignment.R;
import com.fahim9n.nanoleafmobileassignment.model.Device;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class DataParser {

    private double average = 0;
    private int deviceListSize = 0;
    private String[] colorArray;
    private String[] deviceNameArray;

    public DataParser(Context context) {
        colorArray = context.getResources().getStringArray(R.array.color_array);
        deviceNameArray = context.getResources().getStringArray(R.array.device_names);
    }

    public List<Device> getParsedData(String data) {
        String[] dataArray = data.split(" ");

        List<Device> deviceList = new ArrayList<>();

        int totalBrightness = 0;
        for (int i = 0; i < dataArray.length; i = i + 3) {
            Device device = new Device();
            device.setDeviceId(convertStringToInt(dataArray[i]));
            device.setPower(convertStringToInt(dataArray[i + 1]) == 1);
            int brightness = convertStringToInt(dataArray[i + 2]);
            totalBrightness = totalBrightness + brightness;
            device.setBrightness(brightness);
            int color = Color.parseColor(colorArray[i / 3]);
            device.setColor(color);
            device.setDeviceName(deviceNameArray[i / 3]);
            deviceList.add(device);
            System.out.println(device.toString());
        }

        deviceListSize = deviceList.size();
        average = (double) totalBrightness / deviceListSize;

        Collections.sort(deviceList, new Comparator<Device>() {
            @Override
            public int compare(Device device1, Device device2) {
                return (device1.getDeviceId() - device2.getDeviceId());
            }
        });

        return deviceList;
    }

    public double getAverage() {
        return average;
    }

    private static int convertStringToInt(String s) {
        return Integer.parseInt(s);
    }

    public void updateAverage(int value) {
        average = average + ((double) value / deviceListSize);
    }

    public void updateAverage(double changedAverage) {
        average = average + changedAverage;
    }

    public void resetAverage(double average) {
        this.average = average;
    }
}
