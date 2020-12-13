package com.enzenberger.suncontrol;

import androidx.annotation.NonNull;

import com.google.android.material.slider.Slider;
import com.google.android.material.slider.Slider.OnSliderTouchListener;

public class OnLightSliderTouchListener implements OnSliderTouchListener{
    private final CommunicationHandler handler;

    public OnLightSliderTouchListener(CommunicationHandler handler){
        this.handler = handler;
    }
    @Override
    public void onStartTrackingTouch(@NonNull Slider slider) {

    }

    @Override
    public void onStopTrackingTouch(@NonNull Slider slider) {
        handler.sendLevel((int) slider.getValue());
    }
}
