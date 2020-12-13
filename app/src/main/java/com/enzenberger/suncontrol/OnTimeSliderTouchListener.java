package com.enzenberger.suncontrol;

import androidx.annotation.NonNull;

import com.google.android.material.slider.RangeSlider;

import java.util.List;

import static com.google.android.material.slider.RangeSlider.*;

public class OnTimeSliderTouchListener implements OnSliderTouchListener {
    private final CommunicationHandler communicationHandler;

    public OnTimeSliderTouchListener(CommunicationHandler communicationHandler){
        this.communicationHandler = communicationHandler;
    }

    @Override
    public void onStartTrackingTouch(@NonNull RangeSlider slider) {

    }

    @Override
    public void onStopTrackingTouch(@NonNull RangeSlider slider) {
        List<Float> values = slider.getValues();
        this.communicationHandler.sendStartTime(values.get(0));
        this.communicationHandler.sendEndTime(values.get(1));
    }
}
