package com.enzenberger.suncontrol;

import org.junit.Test;

import static org.junit.Assert.*;

public class TimeLabelFormatterUnitTest {
    @Test
    public void FormattedValueIsRight() {
        TimeLabelFormatter timeLabelFormatter = new TimeLabelFormatter();
        float decimalHours = 22.75f;

        String formattedValue = timeLabelFormatter.getFormattedValue(decimalHours);

        assertEquals("22:45", formattedValue);
    }

    @Test
    public void ValueIsTooHigh(){
        TimeLabelFormatter timeLabelFormatter = new TimeLabelFormatter();
        float invalidDecimalHours = 45.0f;

        assertThrows(IllegalArgumentException.class, () -> {
            timeLabelFormatter.getFormattedValue(invalidDecimalHours);
        });
    }

    @Test
    public void ValueIsTooLow(){
        TimeLabelFormatter timeLabelFormatter = new TimeLabelFormatter();
        float invalidDecimalHours = -45.0f;

        assertThrows(IllegalArgumentException.class, () -> {
            timeLabelFormatter.getFormattedValue(invalidDecimalHours);
        });
    }
}
