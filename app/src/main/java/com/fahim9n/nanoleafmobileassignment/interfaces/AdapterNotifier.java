package com.fahim9n.nanoleafmobileassignment.interfaces;

import com.fahim9n.nanoleafmobileassignment.model.Device;

public interface AdapterNotifier {
    void notifyAdapterPowerChanged(int position, Device device);
    void notifyAdapterBrightnessChanged(int position, Device device, int oldValue);

}
