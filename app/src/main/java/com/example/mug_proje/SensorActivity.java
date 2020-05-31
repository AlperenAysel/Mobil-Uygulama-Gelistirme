package com.example.mug_proje;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

public class SensorActivity extends AppCompatActivity implements SensorEventListener {

    private static final String TAG = "SensorActivity";

    private SensorManager sensorManager;
    Sensor acceerometer;

    TextView valX, valY, valZ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor);

        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        assert sensorManager != null;
        acceerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(SensorActivity.this, acceerometer, SensorManager.SENSOR_DELAY_NORMAL);

        valX = findViewById(R.id.first);
        valY = findViewById(R.id.second);
        valZ = findViewById(R.id.third);

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        valX.setText("X: " + event.values[0]);
        valY.setText("Y: " + event.values[1]);
        valZ.setText("Z: " + event.values[2]);

    }
}
