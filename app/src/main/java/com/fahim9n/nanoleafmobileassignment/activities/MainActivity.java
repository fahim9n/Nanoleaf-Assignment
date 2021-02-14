package com.fahim9n.nanoleafmobileassignment.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatSeekBar;
import androidx.appcompat.widget.SwitchCompat;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.fahim9n.nanoleafmobileassignment.utilites.CommandCenter;
import com.fahim9n.nanoleafmobileassignment.utilites.DataParser;
import com.fahim9n.nanoleafmobileassignment.R;
import com.fahim9n.nanoleafmobileassignment.interfaces.CommandSender;
import com.fahim9n.nanoleafmobileassignment.interfaces.GlobalControllerChanger;

public class MainActivity extends AppCompatActivity implements GlobalControllerChanger {

    private MainActivityHelper mainActivityHelper = new MainActivityHelper();
    private RecyclerView deviceRecyclerView;
    private AppCompatSeekBar globalBrightnessSeekBar;
    private SwitchCompat globalPowerSwitch;
    private CommandSender commandSender = new CommandCenter();
    private DataParser dataParser;
    public static final int INITIAL_VALUE=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_main);

        deviceRecyclerView = findViewById(R.id.device_recyclerView);
        globalPowerSwitch = findViewById(R.id.power_switch);
        globalBrightnessSeekBar = findViewById(R.id.seek_bar);

        dataParser = mainActivityHelper.getDataParser(getApplicationContext());

        mainActivityHelper.setRecyclerView(deviceRecyclerView, dataParser, getApplicationContext(), this, commandSender);

        globalPowerSwitch.setOnCheckedChangeListener(mainActivityHelper.getOnCheckedChangeListener(deviceRecyclerView, dataParser));
        globalBrightnessSeekBar.setOnSeekBarChangeListener(mainActivityHelper.getSeekBarChangeListener(deviceRecyclerView, dataParser));

        updatePower(mainActivityHelper.getPowerValue(deviceRecyclerView));
        updateBrightnessAverage(INITIAL_VALUE);
    }

    private int getAverageBrightnessData() {
        return (int) dataParser.getAverage();
    }

    @Override
    public void updatePower(boolean isPowerOn) {
        globalPowerSwitch.setChecked(isPowerOn);
    }

    @Override
    public void updateBrightnessAverage(double changedAverage) {
        dataParser.updateAverage(changedAverage);
        globalBrightnessSeekBar.setProgress(getAverageBrightnessData());
    }
}