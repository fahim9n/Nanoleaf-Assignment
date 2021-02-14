package com.fahim9n.nanoleafmobileassignment.activities;

import android.content.Context;
import android.widget.CompoundButton;
import android.widget.SeekBar;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.fahim9n.nanoleafmobileassignment.R;
import com.fahim9n.nanoleafmobileassignment.adapters.DashboardRecyclerViewAdapter;
import com.fahim9n.nanoleafmobileassignment.interfaces.CommandSender;
import com.fahim9n.nanoleafmobileassignment.interfaces.GlobalControllerChanger;
import com.fahim9n.nanoleafmobileassignment.utilites.DataParser;

public class MainActivityHelper {

    public DataParser getDataParser(Context applicationContext) {
        return new DataParser(applicationContext);
    }

    public void setRecyclerView(RecyclerView deviceRecyclerView, DataParser dataParser, Context applicationContext, GlobalControllerChanger globalControllerChanger, CommandSender commandSender) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(applicationContext, RecyclerView.VERTICAL, false);
        setAdapter(deviceRecyclerView, new DashboardRecyclerViewAdapter(dataParser.getParsedData(applicationContext.getString(R.string.data)), globalControllerChanger, commandSender));
        deviceRecyclerView.setLayoutManager(layoutManager);
    }

    private void setAdapter(RecyclerView recyclerView, RecyclerView.Adapter backgroundEditRecyclerViewAdapter) {
        recyclerView.setAdapter(backgroundEditRecyclerViewAdapter);
    }


    public CompoundButton.OnCheckedChangeListener getOnCheckedChangeListener(RecyclerView deviceRecyclerView, DataParser dataParser) {
        return new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buttonView.isPressed()) {
                    (getAdapter(deviceRecyclerView)).updatePowerInAllData(isChecked);
                }

            }
        };
    }

    public SeekBar.OnSeekBarChangeListener getSeekBarChangeListener(RecyclerView deviceRecyclerView, DataParser dataParser) {
        return new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    getAdapter(deviceRecyclerView).updateBrightnessInAllData(progress);
                    dataParser.resetAverage(progress);
                }

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        };
    }

    private DashboardRecyclerViewAdapter getAdapter(RecyclerView deviceRecyclerView) {
        return (DashboardRecyclerViewAdapter) deviceRecyclerView.getAdapter();
    }

    public boolean getPowerValue(RecyclerView deviceRecyclerView) {
        return (getAdapter(deviceRecyclerView)).getPowerValue();
    }

}
