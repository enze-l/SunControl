package com.enzenberger.suncontrol;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.Bindable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.Observable;
import androidx.databinding.ObservableField;

import android.os.Bundle;
import android.view.View;

import com.enzenberger.suncontrol.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity  {

    private ActivityMainBinding binding;
    private final ObservableField<String> header = new ObservableField<>("Nothing");
    private final String espIP = "192.168.0.128";
    private final int espPort= 50000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setMain(this);
    }

    public void onClickLight(View view) {
        uniMessage("toggle");
    }

    public void onClickAutomation(View view) {
        duplMessage("automation");
    }

    private void uniMessage(String message){
        dispenseMessage(new SimpleConnection(espIP, espPort, message));
    }

    private String duplMessage(String message){
        dispenseMessage(new SimpleConnection(espIP, espPort, message, this));
        return null;
    }

    private void dispenseMessage(SimpleConnection connection){
        Thread simpleConnection = new Thread(connection);
        simpleConnection.start();
    }

    public ObservableField<String> getHeader() {
        return header;
    }
}