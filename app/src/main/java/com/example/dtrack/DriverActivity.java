package com.example.dtrack;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class DriverActivity extends AppCompatActivity {
    TextView tv_lat,tv_lon,tv_altitude,tv_accuracy,tv_speed, tv_sensor, tv_updates,tv_address;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver);


        tv_lat= findViewById(R.id.tv_lat);
        tv_lon= findViewById(R.id.tv_lon);
        tv_altitude= findViewById(R.id.tv_altitude);
        tv_accuracy= findViewById(R.id.tv_accuracy);
        tv_speed= findViewById(R.id.tv_speed);
        tv_sensor= findViewById(R.id.tv_sensor);
        tv_updates= findViewById(R.id.tv_updates);
        tv_address= findViewById(R.id.tv_address);
    }
}