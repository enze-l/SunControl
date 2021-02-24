package com.enzenberger.suncontrol;

import androidx.databinding.Observable;
import androidx.databinding.ObservableField;

import java.util.ArrayList;
import java.util.List;

public class CommunicationHandler {
    private final ObservableField<String> dataResponse;
    private final Displayable displayable;

    //192.168.0.35
    private String espIP = "0";
    private final int espPort= 50000;

    public CommunicationHandler(Displayable displayable){
        this.dataResponse = new ObservableField<>();
        this.displayable = displayable;
        initListeners();
    }

    public void setEspIP(String ip){
        this.espIP = ip;
        requestData();
    }

    private void initListeners() {
        dataResponse.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                handleDataResponse();
            }
        });
    }

    private void handleDataResponse() {
        List<Double> formattedValues = new ArrayList<>();
        String response = dataResponse.get();
        String[] data = response.split("\\s");

        int maxLevel = Integer.parseInt(data[0]);
        int triggerValue = Integer.parseInt(data[1]);
        String startTime = data[2];
        String endTime = data[3];

        for (int interval = 4; interval < data.length; interval++){
            formattedValues.add(Double.parseDouble(data[interval]));
        }

        displayable.setMaxLevel(maxLevel);
        displayable.displayLevel(triggerValue);
        displayable.displayGraph(formattedValues);
        displayable.displayTimes(startTime, endTime);
    }

    private void sendMessage(String message){
        dispenseMessage(new SimpleConnection(espIP, espPort, message));
    }

    private void request(String message){
        dispenseMessage(new SimpleConnection(espIP, espPort, message, dataResponse));
    }

    private void dispenseMessage(SimpleConnection connection){
        Thread simpleConnection = new Thread(connection);
        simpleConnection.start();
    }

    public void sendToggle() {
        sendMessage("toggle");
    }

    public void sendAutomation() {
        sendMessage("automation");
    }

    public void sendLevel(int level){
        sendMessage("level "+ level);
    }

    public void sendStartTime(String value) {
        sendMessage("startTime "+ value);
    }

    public void sendEndTime(String value) {
        sendMessage("endTime "+ value);
    }

    public void requestData(){
        request("getData");
    }
}
