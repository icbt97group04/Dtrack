package com.example.dtrack;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Switch;
import android.widget.TextView;

public class loctrack extends AppCompatActivity {
    TextView tv_lat,tv_lon,tv_altitude,tv_accuracy,tv_sensor,tv_updates,tv_address;
    Switch sw_locationsupdates,sw_gps;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loctrack);
        tv_lat=findViewById(R.id.tv_lat);
        tv_lon=findViewById(R.id.tv_lon);
        tv_altitude=findViewById(R.id.tv_altitude);
        tv_accuracy=findViewById(R.id.tv_accuracy);
        tv_sensor=findViewById(R.id.tv_sensor);
        tv_updates=findViewById(R.id.tv_updates);
        tv_address=findViewById(R.id.tv_address);
        sw_locationsupdates=findViewById(R.id.sw_locationsupdates);
        sw_gps=findViewById(R.id.sw_gps);

    }
}