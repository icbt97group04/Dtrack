package com.example.dtrack;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;
import com.google.android.gms.tasks.OnSuccessListener;

public class DriverActivity extends AppCompatActivity  {
    Switch sw_locationsupdates,sw_gps;
    TextView tv_lat,tv_lon,tv_altitude,tv_accuracy,tv_speed, tv_sensor, tv_updates,tv_address;
//location request
    LocationRequest locationRequest;
    FusedLocationProviderClient fusedLocationProviderClient;
    //goolgle api serverices for location
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
        sw_locationsupdates=findViewById(R.id.sw_locationsupdates);
        sw_gps=findViewById(R.id.sw_gps);

        //setting properties for locatoinrequest
        locationRequest = LocationRequest.create();
        //location request update interval
        locationRequest.setInterval(1000*30);
        locationRequest.setFastestInterval(1000*5);
        locationRequest.setPriority(Priority.PRIORITY_BALANCED_POWER_ACCURACY);

        sw_gps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(sw_gps.isChecked()){
                    locationRequest.setPriority(Priority.PRIORITY_HIGH_ACCURACY);
                    tv_sensor.setText("using gps now");
                }
                else{
                    locationRequest.setPriority(Priority.PRIORITY_BALANCED_POWER_ACCURACY);
                    tv_sensor.setText("using towers and wifi now");
                }
            }
            void  updategps(){
                fusedLocationProviderClient= LocationServices.getFusedLocationProviderClient(DriverActivity.this);

                if(ActivityCompat.checkSelfPermission(DriverActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){
                //user give permisson
                    fusedLocationProviderClient.getLastLocation().addOnSuccessListener(DriverActivity.this, new OnSuccessListener<Location>() {
                        @Override
                        public void onSuccess(Location location) {

                        }
                    });
                }else{
                    if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){

                        requestPermissions( new String[]{Manifest.permission.ACCESS_FINE_LOCATION},99);
                    }


                }

            }
        });

    }

}