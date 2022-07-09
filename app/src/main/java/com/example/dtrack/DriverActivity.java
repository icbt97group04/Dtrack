package com.example.dtrack;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;
import com.google.android.gms.tasks.OnSuccessListener;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

public class DriverActivity extends AppCompatActivity {
    Switch sw_locationsupdates, sw_gps;
    String server_url = "http://139.59.118.223//darcin.php";
    TextView tv_lat, tv_lon, tv_altitude, tv_accuracy, tv_speed, tv_sensor, tv_updates, tv_address;
    //location request
    LocationCallback locationCallBack;
    LocationRequest locationRequest;
    FusedLocationProviderClient fusedLocationProviderClient;
    AlertDialog.Builder builder;

    //goolgle api serverices for location
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_driver);
        tv_lat = findViewById(R.id.tv_lat);
        tv_lon = findViewById(R.id.tv_lon);
        tv_altitude = findViewById(R.id.tv_altitude);
        tv_accuracy = findViewById(R.id.tv_accuracy);
        tv_speed = findViewById(R.id.tv_speed);
        tv_sensor = findViewById(R.id.tv_sensor);
        tv_updates = findViewById(R.id.tv_updates);
        tv_address = findViewById(R.id.tv_address);
        sw_locationsupdates = findViewById(R.id.sw_locationsupdates);
        sw_gps = findViewById(R.id.sw_gps);
        builder = new AlertDialog.Builder(DriverActivity.this);

        //setting properties for locatoinrequest
        locationRequest = LocationRequest.create();
        //location request update interval
        locationRequest.setInterval(1000 * 30);
        locationRequest.setFastestInterval(1000 * 5);
        locationCallBack = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                super.onLocationResult(locationResult);
                Location location=locationResult.getLastLocation();
                updateui(location);
            }
        };
        locationRequest.setPriority(Priority.PRIORITY_BALANCED_POWER_ACCURACY);

        sw_gps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sw_gps.isChecked()) {
                    locationRequest.setPriority(Priority.PRIORITY_HIGH_ACCURACY);
                    tv_sensor.setText("using gps now");
                } else {
                    locationRequest.setPriority(Priority.PRIORITY_BALANCED_POWER_ACCURACY);
                    tv_sensor.setText("using towers and wifi now");
                }
            }

        });
        updategps();

        //location update
        sw_locationsupdates.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (sw_locationsupdates.isChecked()) {
                    startlocationupdate();
                } else {
                    stoplocationupdate();
                }
            }
        });
    }

    private void stoplocationupdate() {
        tv_updates.setText("Tracker is offline");
        fusedLocationProviderClient.removeLocationUpdates(locationCallBack);
    }




    private void startlocationupdate() {
        tv_updates.setText("Tracker is online");
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallBack, null);
        updategps();
        insertdatabase();
    }




    private void insertdatabase() {
        fusedLocationProviderClient= LocationServices.getFusedLocationProviderClient(DriverActivity.this);

        if(ActivityCompat.checkSelfPermission(DriverActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){
            //user give permisson
            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(DriverActivity.this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    String lat = (String.valueOf(location.getLatitude()));
                    connection(lat);


                }
            });
        }else{
            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){

                requestPermissions( new String[]{Manifest.permission.ACCESS_FINE_LOCATION},99);
            }


        }

    }

    private void connection(String lat) {

        {

            StringRequest stringRequest = new StringRequest(Request.Method.POST, server_url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    builder.setTitle("Server Response");
                    builder.setMessage("Response :"+response);
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            tv_lat.setText("");

                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();

                }
            }

                    , new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(DriverActivity.this,"ERROR....."+error,Toast.LENGTH_SHORT).show();
                    StringWriter writer = new StringWriter();
                    PrintWriter printWriter = new PrintWriter( writer );
                    error.printStackTrace( printWriter );
                    printWriter.flush();

                   // String stackTrace = writer.toString();
                   // error.printStackTrace( );

                }
            }){
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map <String,String> Params = new HashMap<String, String>();
                    Params.put("name",lat);

                    return Params;

                }
            };
            Mysingnalton.getInstance(DriverActivity.this).addTorequestque(stringRequest);
        }
    }


    private void updategps() {
        fusedLocationProviderClient= LocationServices.getFusedLocationProviderClient(DriverActivity.this);

        if(ActivityCompat.checkSelfPermission(DriverActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){
            //user give permisson
            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(DriverActivity.this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    tv_lat.setText(String.valueOf(location.getLatitude()));
                    tv_accuracy.setText(String.valueOf(location.getAccuracy()));
                    tv_altitude.setText(String.valueOf(location.getAltitude()));
                    tv_speed.setText(String.valueOf(location.getSpeed()));


                }
            });
        }else{
            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){

                requestPermissions( new String[]{Manifest.permission.ACCESS_FINE_LOCATION},99);
            }


        }

    }

    private void updateui(Location location) {
        tv_lat.setText(String.valueOf(location.getLatitude()));
        String lat = (String.valueOf(location.getLatitude()));
        //connection(lat);
        tv_lon.setText(String.valueOf(location.getLongitude()));
        tv_accuracy.setText(String.valueOf(location.getAccuracy()));
        if(location.hasAltitude()){
            tv_altitude.setText(String.valueOf(location.getAltitude()));
        }else {
            tv_altitude.setText("not available");
        }
        if(location.hasAltitude()){
            tv_speed.setText(String.valueOf(location.getSpeed()));
        }else {
            tv_speed.setText("not available");
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode){
            case 99:
                if(grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    updategps();
                } else{
                    Toast.makeText(this,"Need persmisson",Toast.LENGTH_SHORT);
                    finish();
                }
                break;
        }
    }

}