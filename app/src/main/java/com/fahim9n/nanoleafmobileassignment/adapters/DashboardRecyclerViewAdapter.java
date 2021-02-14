package com.fahim9n.nanoleafmobileassignment.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.fahim9n.nanoleafmobileassignment.R;
import com.fahim9n.nanoleafmobileassignment.adapters.itemHolders.LightItemHolder;
import com.fahim9n.nanoleafmobileassignment.interfaces.AdapterNotifier;
import com.fahim9n.nanoleafmobileassignment.interfaces.CommandSender;
import com.fahim9n.nanoleafmobileassignment.interfaces.GlobalControllerChanger;
import com.fahim9n.nanoleafmobileassignment.interfaces.UpdateDeviceData;
import com.fahim9n.nanoleafmobileassignment.model.Device;

import java.util.List;

public class DashboardRecyclerViewAdapter extends RecyclerView.Adapter<LightItemHolder> implements AdapterNotifier {

    public int position;
    private List<Device> devices;
    public GlobalControllerChanger globalControllerChanger;
    private CommandSender commandSender;


    public DashboardRecyclerViewAdapter(List<Device> devices, GlobalControllerChanger globalControllerChanger, CommandSender commandSender) {
        this.devices = devices;
        this.globalControllerChanger = globalControllerChanger;
        this.commandSender = commandSender;
    }


    @Override
    public int getItemViewType(int position) {
        this.position = position;
        return position;
    }


    @Override
    public LightItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LightItemHolder holder;
        View view;

        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_single_device, parent, false);
        holder = new LightItemHolder(view, devices.get(position), commandSender, this);

        return holder;
    }


    @Override
    public void onBindViewHolder(LightItemHolder holder, int position) {
        try {
            int adapterPosition = holder.getAdapterPosition();
            ((UpdateDeviceData) holder).syncData(getItem(adapterPosition));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return devices.size();
    }

    public Device getItem(int position) {
        return devices.get(position);
    }


    public void updateAdapterData(Device data) {
        this.devices.add(data);
    }

    public void replaceAdapterData(List<Device> data) {
        this.devices = data;
    }

    public void clearData() {
        devices.clear();
    }

    public List<Device> getDevices() {
        return devices;
    }

    public void updatePowerInAllData(boolean isPowerOn) {
        for (Device device : devices) {
            device.setPower(isPowerOn);
            commandSender.sendPowerCommandRequest(device.getDeviceId(), isPowerOn);
        }
        notifyDataSetChanged();
    }


    public void updateBrightnessInAllData(int brightnessValue) {
        for (Device device : devices) {
            device.setBrightness(brightnessValue);
            commandSender.sendBrightnessCommandRequest(device.getDeviceId(), brightnessValue);
        }
        notifyDataSetChanged();
    }

    @Override
    public void notifyAdapterPowerChanged(int position, Device device) {
        devices.set(position, device);
        boolean isPowerOn = getPowerValue();
        globalControllerChanger.updatePower(isPowerOn);
    }

    public boolean getPowerValue() {
        boolean isPowerOn = false;
        for (Device device1 : devices) {
            if (device1.getPower()) {
                isPowerOn = true;
                break;
            }
        }
        return isPowerOn;
    }

    @Override
    public void notifyAdapterBrightnessChanged(int position, Device device, int oldValue) {
        devices.set(position, device);
        int changedValue = device.getBrightness() - oldValue;
        double changedAvg = (double) changedValue / devices.size();
        globalControllerChanger.updateBrightnessAverage(changedAvg);
        // notifyItemChanged(position);
    }
}
