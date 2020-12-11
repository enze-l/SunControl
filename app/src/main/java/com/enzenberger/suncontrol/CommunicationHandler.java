package com.enzenberger.suncontrol;

import androidx.databinding.Observable;
import androidx.databinding.ObservableField;

import java.util.ArrayList;
import java.util.List;

public class CommunicationHandler {
    private ObservableField<String> toggleResponse;
    private ObservableField<String> dataResponse;
    private Displayable displayable;

    private final String espIP = "192.168.0.128";
    private final int espPort= 50000;

    public CommunicationHandler(Displayable displayable){
        this.toggleResponse = new ObservableField<>();
        this.dataResponse = new ObservableField<>();
        this.displayable = displayable;
        initListeners();
    }

    private void initListeners() {
        toggleResponse.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
            @Override
            public void onPropertyChanged(Observable sender, int propertyId) {
                handleToggleResponse();
            }
        });

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
        String[] strings = response.split("\\s");
        for (String string:strings){
            formattedValues.add(Double.parseDouble(string));
        }

        displayable.displayData(formattedValues);
    }

    //todo
    private void handleToggleResponse() {

    }

    public void sendMessage(String message){
        dispenseMessage(new SimpleConnection(espIP, espPort, message));
    }

    private void sendMessage(String message, ObservableField<String> result){
        dispenseMessage(new SimpleConnection(espIP, espPort, message, result));
    }

    private void dispenseMessage(SimpleConnection connection){
        Thread simpleConnection = new Thread(connection);
        simpleConnection.start();
    }

    public void clickToggle() {
        sendMessage("toggle");
    }

    public void clickAutomation() {
        sendMessage("automation", dataResponse);
    }
}
