package com.enzenberger.suncontrol;

import android.widget.SeekBar;

import static android.widget.SeekBar.*;

public class OnLightSeekBarChangeListener implements OnSeekBarChangeListener {

    private final CommunicationHandler communicationHandler;
    private int value;

    OnLightSeekBarChangeListener(CommunicationHandler communicationHandler){
        this.communicationHandler = communicationHandler;
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        this.value = progress;
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        communicationHandler.sendLevel(value);
    }
}
