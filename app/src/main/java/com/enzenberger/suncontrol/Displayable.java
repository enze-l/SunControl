package com.enzenberger.suncontrol;

import java.util.List;

public interface Displayable {

    /**
     * Displays given light levels in an 2D-graph spreading the values evenly along the x-axis.
     * @param list the measuring point of the light
     */
    void displayGraph(List<Double> list);

    /**
     * Displays the level set at which the sun should turn off.
     * @param level the level to which to adjust
     */
    void displayLevel(int level);

    /**
     * Displays the start and end time at which the sun should be enabled.
     * @param startTime the time at which the sun can turn on by itself
     * @param endTime the time at which the sun can turn off by itself
     */
    void displayTimes(String startTime, String endTime);

    /**
     * Uses the level provided to adjust the scale of the graph and level control.
     * @param level the level to which to adjust
     */
    void setMaxLevel(int level);

    /**
     * Displays the state of automation.
     * @param automation the state of automation. False == off / True == on
     */
    void displayAutomation(boolean automation);

    /**
     * Displays the state of the sun.
     * @param status the state of the sun. False == off / True == on
     */
    void displayStatus(boolean status);

    /**
     * Displays the state of connection in an human-readable manner
     * @param message the message that should be displayed
     */
    void displayConnectionMessage(String message);

    /**
     * Clears display of all previously set states
     */
    void clearDisplay();
}
