package com.enzenberger.suncontrol;

import android.content.Context;
import android.preference.PreferenceManager;

import androidx.databinding.Observable;
import androidx.databinding.ObservableField;

import java.util.ArrayList;
import java.util.List;

public class CommunicationHandler {
    private final static String IP = "SAVED_IP";
    private final ObservableField<String> dataResponse;
    private final Displayable displayable;
    private final Context context;
    private String espIP;
    private final int espPort= 50000;

    /**
     * Class constructor.
     * @param displayable the instance displaying incoming results
     * @param context the context in which the CommunicationHandler gets created
     */
    public CommunicationHandler(Displayable displayable, Context context){
        this.context = context;
        this.dataResponse = new ObservableField<>();
        this.displayable = displayable;
        espIP = getEspIp();
        initListeners();
    }

    /**
     * Setter for IP-address of the sunNode.
     * @param ip the ip of the device
     */
    public void setEspIP(String ip){
        PreferenceManager.getDefaultSharedPreferences(context).edit()
                .putString(IP, ip).apply();
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
        if (response.equals(SimpleConnection.FAIL_MESSAGE)){
            displayable.displayConnectionMessage("Connection failed. Try other ip or refresh");
        } else {
            String[] data = response.split("\\s");

            int maxLevel = Integer.parseInt(data[0]);
            int triggerValue = Integer.parseInt(data[1]);
            String startTime = data[2];
            String endTime = data[3];
            boolean automation = Boolean.parseBoolean(data[4]);
            boolean status = !data[5].equals("0");

            for (int interval = 6; interval < data.length; interval++) {
                formattedValues.add(Double.parseDouble(data[interval]));
            }

            displayable.setMaxLevel(maxLevel);
            displayable.displayLevel(triggerValue);
            displayable.displayGraph(formattedValues);
            displayable.displayTimes(startTime, endTime);
            displayable.displayAutomation(automation);
            displayable.displayStatus(status);
            displayable.displayConnectionMessage("");
        }
    }

    private void request(String message){
        dispenseMessage(new SimpleConnection(espIP, espPort, message, dataResponse));
    }

    private void dispenseMessage(SimpleConnection connection){
        Thread simpleConnection = new Thread(connection);
        simpleConnection.start();
    }

    /**
     * Sends request to sun to toggle between on and off.
     */
    public void sendToggle() {
        request("toggle");
    }

    /**
     * Sends request to sun to go into auto mode.
     */
    public void sendAutomation() {
        request("automation");
    }

    /**
     * Sends trigger light level to sun.
     * @param level the level till which the sun should be on
     */
    public void sendLevel(int level){
        request("level "+ level);
    }

    /**
     * Sends the time at which the sun should be able to turn on in auto mode.
     * @param value
     */
    public void sendStartTime(String value) {
        request("startTime "+ value);
    }

    /**
     * Sends the time at which the sun should be definitely of in auto mode.
     * @param value
     */
    public void sendEndTime(String value) {
        request("endTime "+ value);
    }

    /**
     * Sends request to sun for information about its state.
     */
    public void requestData(){
        request("getData");
    }

    /**
     * Returns the ip-address of the sun.
     * @return the IP-address of the sun
     */
    public String getEspIp() {
        if (espIP == null){
            return getSavedIp();
        }
        return espIP;
    }

    private String getSavedIp() {
        return PreferenceManager.getDefaultSharedPreferences(context)
                .getString(IP, null);
    }
}
