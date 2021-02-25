package com.enzenberger.suncontrol;

import androidx.annotation.NonNull;

import com.google.android.material.slider.LabelFormatter;

public class TimeLabelFormatter implements LabelFormatter {
    @NonNull
    @Override
    public String getFormattedValue(float decimalHours) {
        if (decimalHours < 0) {
            return "00:00";
        } else if (decimalHours > 24) {
            return "24:00";
        }
        int hours = (int)decimalHours;
        float minuteFraction = decimalHours%1;
        int seconds = (int)(minuteFraction * 60);
        String secondsFormatted = String.format("%02d", seconds);
        return hours + ":" + secondsFormatted;
    }
}
