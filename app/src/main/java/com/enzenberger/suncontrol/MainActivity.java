package com.enzenberger.suncontrol;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;
import android.view.View;
import android.widget.SeekBar;

import com.enzenberger.suncontrol.databinding.ActivityMainBinding;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.List;

public class MainActivity extends AppCompatActivity implements Displayable {
    private final CommunicationHandler communicationHandler =
            new CommunicationHandler(this);
    private GraphView graphView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding =
                DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setMain(this);
        SeekBar seekBarLight = (SeekBar)findViewById(R.id.seekBarLightLevel);
        seekBarLight.setOnSeekBarChangeListener(new LightSeekBar(this.communicationHandler));

        initGraph();
    }

    private void initGraph() {
        this.graphView = (GraphView) findViewById(R.id.graph_light);
        Viewport viewport = graphView.getViewport();
        viewport.setMaxX(24);
        viewport.setMaxY(55000);
        viewport.setXAxisBoundsManual(true);
        viewport.setYAxisBoundsManual(true);

        graphView.getGridLabelRenderer().setVerticalLabelsVisible(false);
        graphView.getGridLabelRenderer().setNumHorizontalLabels(8);

        communicationHandler.sendAutomation();
    }

    public void onClickLight(View view) {
        communicationHandler.sendToggle();
    }

    public void onClickAutomation(View view) {
        communicationHandler.sendAutomation();
    }

    @Override
    public void displayData(List<Double> list) {
        graphView.removeAllSeries();
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>();
        for(int index = 0; index < list.size(); index ++){
            series.appendData(new DataPoint(index, list.get(index)), false, list.size());
        }
        graphView.addSeries(series );
    }
}