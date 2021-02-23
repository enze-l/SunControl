package com.enzenberger.suncontrol;

import java.util.List;

public interface Displayable {

    void displayGraph(List<Double> list);

    void displayLevel(int level);

    void displayTimes(String startTime, String endTime);

    void setMaxLevel(int level);
}
