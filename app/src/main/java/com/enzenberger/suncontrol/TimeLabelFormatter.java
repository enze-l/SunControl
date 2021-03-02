package com.enzenberger.suncontrol;

import androidx.annotation.NonNull;

import com.google.android.material.slider.LabelFormatter;

public class TimeLabelFormatter implements LabelFormatter {
    @NonNull
    @Override
    public String getFormattedValue(float decimalHours) {
        if (decimalHours < 0 || decimalHours > 24) {
            throw new IllegalArgumentException("not a valid time");
        }
        int hours = (int)decimalHours;
        float minuteFraction = decimalHours%1;
        int seconds = (int)(minuteFraction * 60);
        String secondsFormatted = String.format("%02d", seconds);
        return hours + ":" + secondsFormatted;
    }
}
