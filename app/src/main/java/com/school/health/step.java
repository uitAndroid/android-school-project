package com.school.health;


import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import android.widget.Toast;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Cartesian;
import com.anychart.charts.Pie;
import com.anychart.core.ui.table.Column;

import java.util.List;
import java.util.Vector;


/**
 * A simple {@link Fragment} subclass.
 */
public class step extends Fragment implements SensorEventListener {

    boolean running =  false;
    private TextView count;
    private TextView cal;
    private TextView d;
    public static final  String FILE_RUN_DATA = "RunData.txt";
    private int stepCount = 0;
    public static int numAppear = 0;
    public static String pointOfTime = "";
    public static Vector<Pair<String,Integer>> graphData = new Vector<>();
    String weight = "";
    String pace = "";

    public step() {
        // Required empty public constructor
    }
    SensorManager sm;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_step, container, false);
    }
    @Override
    public void onViewCreated(View view,Bundle savedInstanceState) {
        cal = (TextView)getView().findViewById(R.id.calories);
        d = (TextView)getView().findViewById(R.id.distance);
        count = (TextView)getView().findViewById(R.id.count);
        sm = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        user.GetData(weight,pace);
        numAppear += 1;
        String dataUser = user.load(getActivity(),user.FILE_USER);
        String dateUser = "";
        if(!dataUser.equals(""))
        {
            String[] splitedData = dataUser.split(",");
            dateUser = splitedData[2];
        }
        if(java.time.LocalDate.now().toString().equals(dateUser)){
            dataUser =  dataUser.replaceFirst("dateUser",java.time.LocalDate.now().toString());
            user.save(getActivity(),user.FILE_USER,dataUser);
            stepCount = 0;
        }
        String runData = "";
        runData = user.load(getActivity(),FILE_RUN_DATA);
        List<DataEntry> data = new ArrayList<>();
        if(!runData.equals("")) {
            String [] dataSplited = runData.split(",");
            for(int i = 0; i < dataSplited.length-1;i++) {
                data.add(new ValueDataEntry(dataSplited[i],Integer.parseInt(dataSplited[i+1])));
            }
        }
        else {
            // demo data
            data.add(new ValueDataEntry("2018-01-06", 10000));
            data.add(new ValueDataEntry("2018-01-07", 12000));
            data.add(new ValueDataEntry("2018-01-08", 18000));
        }
        Pie pie = AnyChart.pie();
        pie.data(data);

        AnyChartView anyChartView = (AnyChartView) getActivity().findViewById(R.id.any_chart_view);
        anyChartView.setChart(pie);
    }

    @Override
    public void onResume() {
        super.onResume();
        running = true ;
        Sensor countSensor = sm.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        if(countSensor != null)
        {
            sm.registerListener(this,countSensor,sm.SENSOR_DELAY_UI);
        }
        else{

        }
    }

    @Override
    public void onPause() {
        super.onPause();
        running = false;
        pointOfTime = java.time.LocalDate.now().toString();
        Pair<String,Integer> pair = new Pair<>(pointOfTime,Integer.parseInt(count.getText().toString()));
        if(graphData.size() != 0) {
            Pair<String,Integer> oldDate = graphData.elementAt(graphData.size()-1);
            if(oldDate.first == pointOfTime) {
                int newValue = oldDate.second + pair.second;
                pair = new Pair<>(pointOfTime,newValue);
                graphData.remove(graphData.size()-1);
                graphData.add(pair);
            }
        } else {
            graphData.add(pair);
        }
        if(graphData.size() != 0){
            String runInput = "";
            for(int i = 0;i < graphData.size();i++) {
                runInput += "," + graphData.elementAt(i).first + "," + graphData.elementAt(i).second;
            }
            user.save(getActivity(),FILE_RUN_DATA,runInput);
        }
        if(graphData.size() > 4) graphData.remove(0);
        //if unregistered, hardware stop dectedting steps
        //sm.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(running) {
            stepCount++;
            int p = 0;
            int w = 0;
            count.setText(""+ stepCount);
            stepCount = Integer.parseInt(count.getText().toString().trim());
            p = Integer.parseInt(pace) / 100000;
            w = Integer.parseInt(weight);
            double numCal = stepCount * p * w * 1.036;
            float distance = stepCount * p;
            cal.setText("" + numCal);
            d.setText("" + distance);
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
