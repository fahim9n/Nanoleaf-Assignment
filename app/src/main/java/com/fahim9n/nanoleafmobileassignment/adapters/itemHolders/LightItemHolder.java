package com.fahim9n.nanoleafmobileassignment.adapters.itemHolders;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Handler;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatSeekBar;
import androidx.appcompat.widget.SwitchCompat;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.fahim9n.nanoleafmobileassignment.R;
import com.fahim9n.nanoleafmobileassignment.interfaces.AdapterNotifier;
import com.fahim9n.nanoleafmobileassignment.interfaces.CommandSender;
import com.fahim9n.nanoleafmobileassignment.interfaces.UpdateDeviceData;
import com.fahim9n.nanoleafmobileassignment.model.Device;
import com.fahim9n.nanoleafmobileassignment.utilites.CommandCenter;


public class LightItemHolder extends RecyclerView.ViewHolder
        implements UpdateDeviceData {

    private AdapterNotifier adapterNotifier;
    private Device device;
    private View itemView;
    private TextView deviceNameTv, deviceIdTv;
    private SwitchCompat powerSwitch;
    private AppCompatSeekBar brightnessSeekBar;
    ConstraintLayout borderLayout, squareColorLayout;
    private CommandSender commandSender;
    public static final int ALPHA_MAX_VALUE = 255;
    public static final int ALPHA_CHANGED_VALUE = 160;

    public LightItemHolder(View itemView, Device device, CommandSender commandSender, AdapterNotifier adapterNotifier) {
        super(itemView);
        this.device = device;
        this.itemView = itemView;
        this.commandSender = commandSender;
        this.adapterNotifier = adapterNotifier;
        initViews();
        initListeners();
    }

    private void initViews() {
        deviceNameTv = itemView.findViewById(R.id.device_name);
        deviceIdTv = itemView.findViewById(R.id.device_id);
        powerSwitch = itemView.findViewById(R.id.power_switch);
        brightnessSeekBar = itemView.findViewById(R.id.seek_bar);
        borderLayout = itemView.findViewById(R.id.border_layout);
        squareColorLayout = itemView.findViewById(R.id.square_color_layout);
    }

    protected void initListeners() {

        powerSwitch.setOnCheckedChangeListener(getPowerToggleListener());
        brightnessSeekBar.setOnSeekBarChangeListener(getBrightnessSeekBarListener());
    }

    private CompoundButton.OnCheckedChangeListener getPowerToggleListener() {
        return new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isPressed()) {
                    commandSender.sendPowerCommandRequest(device.getDeviceId(), isChecked);
                    device.setPower(isChecked);
                    adapterNotifier.notifyAdapterPowerChanged(getAdapterPosition(), device);
                }
                changePowerSwitchColor(isChecked);
            }
        };
    }

    private void changePowerSwitchColor(boolean isChecked) {
        int color = Color.GRAY, trackColor = Color.LTGRAY, alpha = ALPHA_MAX_VALUE;
        if (isChecked) {
            color = device.getColor();
            trackColor = device.getColor();
            alpha = ALPHA_CHANGED_VALUE;
        }

        powerSwitch.getThumbDrawable().setColorFilter(color, PorterDuff.Mode.SRC_IN);
        powerSwitch.getTrackDrawable().setColorFilter(trackColor, PorterDuff.Mode.SRC_IN);
        powerSwitch.getTrackDrawable().setAlpha(alpha);
    }

    private SeekBar.OnSeekBarChangeListener getBrightnessSeekBarListener() {
        return new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    commandSender.sendBrightnessCommandRequest(device.getDeviceId(), progress);
                    int oldBrightness = device.getBrightness();
                    device.setBrightness(progress);
                    adapterNotifier.notifyAdapterBrightnessChanged(getAdapterPosition(), device, oldBrightness);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                System.out.println("device seekbar val "+seekBar.getProgress());
                long timeDifference =getCurrentTimeMillis()-commandSender.getCommandMap().get(device.getDeviceId());

                if(timeDifference<= CommandCenter.MIN_TIME_INTERVAL){
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            sendBrightnessCommand(seekBar, timeDifference);
                        }
                    },(CommandCenter.MIN_TIME_INTERVAL-timeDifference)+50);
                }else{
                    sendBrightnessCommand(seekBar, timeDifference);
                }

            }
        };
    }

    private void sendBrightnessCommand(SeekBar seekBar, long timeDifference) {
        System.out.println("device seekbar val " + seekBar.getProgress() + " time diff " + timeDifference);
        commandSender.sendBrightnessCommandRequest(device.getDeviceId(), seekBar.getProgress());
    }

    public long getCurrentTimeMillis() {
        return System.currentTimeMillis();
    }


    @Override
    public void syncData(Device device) {
        this.device = device;
        setDataToViews();
    }

    private void setDataToViews() {
        deviceNameTv.setText(device.getDeviceName());
        deviceIdTv.setText("ID: " + device.getDeviceId());
        powerSwitch.setChecked(device.getPower());
        brightnessSeekBar.setProgress(device.getBrightness());
        brightnessSeekBar.getProgressDrawable().setColorFilter(device.getColor(), PorterDuff.Mode.SRC_IN);
        brightnessSeekBar.getThumb().setColorFilter(device.getColor(), PorterDuff.Mode.SRC_IN);
        squareColorLayout.setBackgroundTintList(ColorStateList.valueOf(device.getColor()));
        borderLayout.setBackgroundTintList(ColorStateList.valueOf(device.getColor()));
    }


}
