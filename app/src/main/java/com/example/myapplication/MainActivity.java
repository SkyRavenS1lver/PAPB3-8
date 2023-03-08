package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity  implements SensorEventListener {
    private SensorManager sensorManager;
    private Sensor sensorProx,sensorLight,sensorAmbient, sensorPressure, sensorHumidity;
    private TextView TVProx, TVLight, TVAmbient, TVPressure,TVHumidity;
    ConstraintLayout bg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        bg = findViewById(R.id.BG);
        List<Sensor> sensorList = sensorManager.getSensorList(Sensor.TYPE_ALL);
        StringBuilder sensorText = new StringBuilder();
        for (Sensor sensor:sensorList){sensorText.append(sensor.getName()).append(System.getProperty("line.separator"));}
        TextView placeholder = findViewById(R.id.sensor_list);
        placeholder.setText(sensorText);
        TVLight = findViewById(R.id.sensor_light);
        TVProx = findViewById(R.id.sensor_prox);
        TVAmbient = findViewById(R.id.sensor_ambient);
        TVPressure = findViewById(R.id.sensor_pressure);
        TVHumidity = findViewById(R.id.sensor_humidity);
        sensorLight = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        sensorProx = sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        sensorAmbient = sensorManager.getDefaultSensor(Sensor.TYPE_AMBIENT_TEMPERATURE);
        sensorPressure = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE);
        sensorHumidity = sensorManager.getDefaultSensor(Sensor.TYPE_RELATIVE_HUMIDITY);



    }

    @Override
    protected void onStart() {
        super.onStart();
        if (sensorProx != null){
            sensorManager.registerListener(this,sensorProx,SensorManager.SENSOR_DELAY_NORMAL);
        }
        if (sensorLight != null){
            sensorManager.registerListener(this,sensorLight,SensorManager.SENSOR_DELAY_NORMAL);
        }
        if (sensorPressure != null){
            sensorManager.registerListener(this,sensorPressure,SensorManager.SENSOR_DELAY_NORMAL);
        }
        if (sensorHumidity != null){
            sensorManager.registerListener(this,sensorHumidity,SensorManager.SENSOR_DELAY_NORMAL);
        }
        if (sensorAmbient != null){
            sensorManager.registerListener(this,sensorAmbient,SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        int sensorType = sensorEvent.sensor.getType();
        float currentValue = sensorEvent.values[0];
        switch (sensorType){
            case Sensor.TYPE_LIGHT:
                TVLight.setText(String.format("Light sensor : %1$.2f", currentValue));
                if (currentValue>20000){bg.setBackgroundColor(Color.RED);}
                else {bg.setBackgroundColor(Color.BLUE);}
                break;
            case Sensor.TYPE_PRESSURE:
                TVPressure.setText(String.format("Pressure sensor : %1$.2f", currentValue));
                break;
            case Sensor.TYPE_AMBIENT_TEMPERATURE:
                TVAmbient.setText(String.format("AmBient sensor : %1$.2f", currentValue));
                break;
            case Sensor.TYPE_RELATIVE_HUMIDITY:
                TVHumidity.setText(String.format("Relative Humidity sensor : %1$.2f", currentValue));
                break;
            case Sensor.TYPE_PROXIMITY:
                TVProx.setText(String.format("Poximity sensor : %1$.2f", currentValue));
                break;
            default:
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}