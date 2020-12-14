package com.enzenberger.suncontrol;

import androidx.annotation.NonNull;

import com.google.android.material.slider.RangeSlider;

import java.util.List;

import static com.google.android.material.slider.RangeSlider.*;

public class OnTimeSliderTouchListener implements OnSliderTouchListener {
    private final CommunicationHandler communicationHandler;
    private final TimeLabelFormatter timeLabelFormatter;
    private float startValue = 0;
    private float endValue = 0;

    public OnTimeSliderTouchListener(CommunicationHandler communicationHandler,
                                     TimeLabelFormatter timeLabelFormatter){
        this.communicationHandler = communicationHandler;
        this.timeLabelFormatter = timeLabelFormatter;
    }

    @Override
    public void onStartTrackingTouch(@NonNull RangeSlider slider) {

    }

    @Override
    public void onStopTrackingTouch(@NonNull RangeSlider slider) {
        List<Float> values = slider.getValues();
        if (startValue != values.get(0)) {
            startValue = values.get(0);
            this.communicationHandler.sendStartTime(
                    timeLabelFormatter.getFormattedValue(startValue));
        }
        if (endValue != values.get(1)) {
            endValue = values.get(1);
            this.communicationHandler.sendEndTime(
                    timeLabelFormatter.getFormattedValue(endValue));
        }
    }
}
