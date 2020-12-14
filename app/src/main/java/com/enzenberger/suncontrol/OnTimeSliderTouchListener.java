package com.enzenberger.suncontrol;

import androidx.annotation.NonNull;

import com.google.android.material.slider.RangeSlider;

import java.util.List;

import static com.google.android.material.slider.RangeSlider.*;

public class OnTimeSliderTouchListener implements OnSliderTouchListener {
    private final CommunicationHandler communicationHandler;
    private final TimeLabelFormatter timeLabelFormatter;

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
        this.communicationHandler.sendStartTime(timeLabelFormatter.getFormattedValue(values.get(0)));
        this.communicationHandler.sendEndTime(timeLabelFormatter.getFormattedValue(values.get(1)));
    }
}
