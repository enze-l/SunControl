package com.enzenberger.suncontrol;

import androidx.annotation.NonNull;

import com.google.android.material.slider.LabelFormatter;

public class TimeLabelFormatter implements LabelFormatter {
    @NonNull
    @Override
    public String getFormattedValue(float value) {
        int hours = (int)value;
        float minuteFraction = value%1;
        int seconds = (int)(minuteFraction * 60);
        String secondsFormatted = String.format("%02d", seconds);
        return hours + ":" + secondsFormatted;
    }
}
