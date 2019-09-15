package com.example.myapplication3;

import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import static android.hardware.Sensor.TYPE_GYROSCOPE;
import static android.hardware.Sensor.TYPE_LIGHT;

public class MainActivity extends Activity implements SensorEventListener {


    private Handler myhandler = new Handler();

    private SensorManager mSensorManager;
    private Sensor mgyroscope;
    private Sensor mlight;


    @Override
    public void onSensorChanged(SensorEvent event) {
        if(event.sensor.getType()==Sensor.TYPE_GYROSCOPE){
            GyroReading work = new GyroReading(event);
            myhandler.post(work);
        }

        if(event.sensor.getType()==Sensor.TYPE_LIGHT){
            LightReading passData = new LightReading(event);
            myhandler.post(passData);
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    private class LightReading implements Runnable{
        private SensorEvent ldata;
        public LightReading(SensorEvent l){
            ldata = l;
        }

        @Override
        public void run(){
            float disp = ldata.values[0];
            TextView val = (TextView)findViewById(R.id.textView4);
            val.setText(String.valueOf(disp));

        }

    }


    private class GyroReading implements Runnable{
        private SensorEvent data;
        public GyroReading(SensorEvent d){
            data = d;
        }

        @Override
        public void run() {
            float x = data.values[0];
            float y = data.values[1];
            float z = data.values[2];
            TextView tvx = (TextView)findViewById(R.id.textView1);
            TextView tvy = (TextView)findViewById(R.id.textView2);
            TextView tvz = (TextView)findViewById(R.id.textView3);
            tvx.setText(String.valueOf(x));
            tvy.setText(String.valueOf(y));
            tvz.setText(String.valueOf(z));
        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mgyroscope = mSensorManager.getDefaultSensor(TYPE_GYROSCOPE);
        mlight = mSensorManager.getDefaultSensor(TYPE_LIGHT);
    }

    @Override
    protected void onResume(){
        super.onResume();
        mSensorManager.registerListener(this,mgyroscope,SensorManager.SENSOR_DELAY_NORMAL);
        mSensorManager.registerListener(this,mlight,SensorManager.SENSOR_DELAY_NORMAL);
    }
    @Override
    protected void onPause(){
        super.onPause();
        mSensorManager.unregisterListener(this);
    }

    }

