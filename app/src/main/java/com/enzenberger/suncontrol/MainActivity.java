package com.enzenberger.suncontrol;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.drawable.DrawableCompat;
import androidx.databinding.DataBindingUtil;

import android.app.Activity;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.enzenberger.suncontrol.databinding.ActivityMainBinding;
import com.google.android.material.slider.RangeSlider;
import com.google.android.material.slider.Slider;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.List;

public class MainActivity extends AppCompatActivity implements Displayable {
    private final CommunicationHandler communicationHandler =
            new CommunicationHandler(this);
    private GraphView graphView;
    private Slider lightSlider;
    private RangeSlider timeSlider;
    private EditText edittext;
    private ImageButton automationButton;
    private ImageButton onOffButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityMainBinding binding =
                DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setMain(this);

        initTimeSlider();
        initLightSlider();
        initGraph();
        initEditText();
        initButtons();
        initCommunication();
    }

    private void initButtons() {
        this.automationButton = (ImageButton) findViewById(R.id.button_automation);
        this.onOffButton = (ImageButton) findViewById(R.id.button_on_off);
    }

    private void initEditText() {
        this.edittext = (EditText) findViewById(R.id.editTextIp);
        this.edittext.setOnEditorActionListener((v, actionId, event) -> {
            if(actionId== EditorInfo.IME_ACTION_DONE){
                hideEditFocus();
                hideKeyboard();
                this.communicationHandler.setEspIP(edittext.getText().toString()
                );
                return true;
            }
            return false;
        });
    }

    private void hideEditFocus(){
        edittext.clearFocus();
    }

    private void hideKeyboard(){
        InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initCommunication();
        hideEditFocus();
    }

    @Override
    protected void onStart() {
        super.onStart();
        initCommunication();
        hideEditFocus();
    }

    private void initCommunication() {
        communicationHandler.requestData();
    }

    private void initLightSlider() {
        this.lightSlider = (Slider) findViewById(R.id.slider_light);
        lightSlider.addOnSliderTouchListener(
                new OnLightSliderTouchListener(this.communicationHandler));
    }


    private void initTimeSlider() {
        this.timeSlider = (RangeSlider) findViewById(R.id.slider_time);
        TimeLabelFormatter timeLabelFormatter = new TimeLabelFormatter();
        timeSlider.setLabelFormatter(timeLabelFormatter);
        timeSlider.addOnSliderTouchListener(
                new OnTimeSliderTouchListener(communicationHandler, timeLabelFormatter));
    }

    private void initGraph() {
        this.graphView = (GraphView) findViewById(R.id.graph_light);
        Viewport viewport = graphView.getViewport();
        viewport.setMaxX(24);
        viewport.setMaxY(1000);
        viewport.setXAxisBoundsManual(true);
        viewport.setYAxisBoundsManual(true);

        graphView.getGridLabelRenderer().setVerticalLabelsVisible(false);
        graphView.getGridLabelRenderer().setNumHorizontalLabels(8);
    }

    public void onClickLight(View view) {
        communicationHandler.sendToggle();
    }

    public void onClickAutomation(View view) {
        communicationHandler.sendAutomation();
    }

    @Override
    public void displayGraph(List<Double> list) {
        graphView.removeAllSeries();
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>();
        for(int index = 0; index < list.size(); index ++){
            series.appendData(
                    new DataPoint(index*25.0/list.size(), list.get(index)),
                    false, list.size()
            );
        }
        graphView.addSeries(series );
    }

    @Override
    public void displayLevel(int level) {
        this.lightSlider.setValue(roundToHundred(level));
    }

    @Override
    public void displayTimes(String startTime, String endTime) {
        Float[] times = new Float[2];
        times[0] = convertTime(startTime);
        times[1] = convertTime(endTime);
        this.timeSlider.setValues(times);
    }

    private int roundToHundred(int value){
        return value - (value % 100);
    }

    private Float convertTime(String time){
        String[] values = time.split(":");
        float hours = Float.parseFloat(values[0]);
        float minutes = Float.parseFloat(values[1]);
        float minutesToPercent = minutes / 60;
        return hours + minutesToPercent;
    }

    @Override
    public void setMaxLevel(int level) {
        level = roundToHundred(level) + 100;
        this.graphView.getViewport().setMaxY(level);
        this.lightSlider.setValueTo(level);
    }

    @Override
    public void displayAutomation(boolean automation) {
        if (automation){
            this.automationButton.setImageTintList(ColorStateList.valueOf(Color.CYAN));
        } else {
            this.automationButton.setImageTintList(ColorStateList.valueOf(Color.GRAY));
        }
    }

    @Override
    public void displayStatus(boolean status) {
        if (status) {
            this.onOffButton.setImageTintList(ColorStateList.valueOf(Color.GREEN));
        } else {
            this.onOffButton.setImageTintList(ColorStateList.valueOf(Color.RED));
        }
    }
}