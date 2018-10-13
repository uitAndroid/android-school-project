package com.school.health;


import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.wifi.p2p.WifiP2pManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class step extends Fragment implements SensorEventListener {

    boolean running =  false;
    TextView count;
    TextView cal;
    TextView d;

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
        //if unregistered, hardware stop dectedting steps
        //sm.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(running)
        {
            count.setText(String.valueOf(event.values[0]));
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}
