package com.example.dtrack;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.Priority;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class DriverActiviy2 extends AppCompatActivity implements Driver_Current_Ride_Fragment.Driver_Current_Ride_FragmentLister{

    private Driver_Current_Ride_Fragment driver_current_ride_fragment;
    public Boolean isLocationUpdateChecked=false;

    @Override
    public void onDriverSent(Boolean input) {
        startupdate(input);
        isLocationUpdateChecked = input;
    }

    public void startupdate(Boolean chk ){
        if (chk) {
            startlocationupdate();
        } else {
            stoplocationupdate();
        }
    }
    public static String currentShift;

    String server_url = "https://dtrack.live/Dbconfig.php";

    String VEHICLE_NO;
    String DID;
    //location request
    LocationCallback locationCallBack;
    LocationRequest locationRequest;
    FusedLocationProviderClient fusedLocationProviderClient;
    AlertDialog.Builder builder;
    private Handler mhandler = new Handler();

    //goolgle api serverices for location


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_activiy2);

        Intent intent = getIntent();
        DID = intent.getStringExtra("did");

        String newid= getNoplateno(DID);


        Toast.makeText(DriverActiviy2.this, newid, Toast.LENGTH_SHORT).show();


        BottomNavigationView bottomnav = findViewById(R.id.driver_bottom_nav);
        bottomnav.setOnNavigationItemSelectedListener(navLister);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Driver_Current_Ride_Fragment()).commit();

        //----- from driver activity -----

        builder = new AlertDialog.Builder(DriverActiviy2.this);

        //setting properties for locatoinrequest
        locationRequest = LocationRequest.create();
        //location request update interval
        locationRequest.setInterval(1000 * 30);
        locationRequest.setFastestInterval(1000 * 5);
        locationCallBack = new LocationCallback() {
            @Override
            public void onLocationResult(@NonNull LocationResult locationResult) {
                super.onLocationResult(locationResult);
                Location location = locationResult.getLastLocation();

            }
        };
        locationRequest.setPriority(Priority.PRIORITY_HIGH_ACCURACY);
        updategps();


        setcurrentShift();

    }

    public void setcurrentShift(){


        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        Date MorningStartTime = null;
        try {
            MorningStartTime = dateFormat.parse("06:00");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date MorningEndTime = null;
        try {
            MorningEndTime = dateFormat.parse("08:00");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date afternoonStartTime = null;
        try {
            afternoonStartTime = dateFormat.parse("11:00");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date afternoonEndTime = null;
        try {
            afternoonEndTime = dateFormat.parse("2:30");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Date CurrentTime = null;
        try {
            CurrentTime = dateFormat.parse(dateFormat.format(new Date()));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (CurrentTime.after(MorningStartTime) && CurrentTime.before(MorningEndTime)) {
            currentShift = "morning";
        }
        if (CurrentTime.after(afternoonStartTime) && CurrentTime.before(afternoonEndTime)) {
            currentShift = "afternoon";
        }

    }


    private void updategps() {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(DriverActiviy2.this);

        if (ActivityCompat.checkSelfPermission(DriverActiviy2.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            //user give permisson
            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(DriverActiviy2.this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {

                }
            });
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 99);
            }
        }
    }

    private void startlocationupdate() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }
        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallBack, null);

        updating.run();
    }

    private void stoplocationupdate() {

        fusedLocationProviderClient.removeLocationUpdates(locationCallBack);
        mhandler.removeCallbacks(updating);
    }

    public Runnable updating = new Runnable() {
        @Override
        public void run() {
            Intent intent = getIntent();
            DID = intent.getStringExtra("did");
            getvehiclenumber(DID);
            updategps();
            //insertdatabase();
            //Toast.makeText(DriverActivity.this,"Tracking",Toast.LENGTH_SHORT).show();
            //Toast.makeText(DriverActivity.this, number+"getv", Toast.LENGTH_SHORT).show();
            mhandler.postDelayed(this, 3000);
        }
    };

    public String getvehiclenumber(String DID) {
        if (DID.isEmpty()) {
            Toast.makeText(DriverActiviy2.this, "Login Again", Toast.LENGTH_SHORT).show();
        } else {
            final ProgressDialog progressDialog = new ProgressDialog(DriverActiviy2.this);
           /* progressDialog.setTitle("Loading Data");
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setIndeterminate(false);
            progressDialog.show();*/
            String uRl = "https://dtrack.live/darcgetno.php";
            StringRequest request = new StringRequest(Request.Method.POST, uRl, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if (response.length() > 0) {


                        //progressDialog.dismiss();
                        insertdatabase(response);
                        //Toast.makeText(DriverActiviy2.this, response+"getv1", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(DriverActiviy2.this, response, Toast.LENGTH_SHORT).show();
                        // progressDialog.dismiss();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(DriverActiviy2.this, error.toString() + "getvehicleError", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    HashMap<String, String> param = new HashMap<>();
                    param.put("DID", DID);
                    return param;
                }
            };
            request.setRetryPolicy(new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            MySingleton.getmInstance(DriverActiviy2.this).addToRequestQueue(request);
            //Toast.makeText(LoginActivity.this, user+"  "+pw, Toast.LENGTH_SHORT).show();
        }
        return DID;
    }

    private void insertdatabase(String responsez) {
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(DriverActiviy2.this);

        if (ActivityCompat.checkSelfPermission(DriverActiviy2.this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            //user give permisson
            fusedLocationProviderClient.getLastLocation().addOnSuccessListener(DriverActiviy2.this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    String lat = (String.valueOf(location.getLatitude()));
                    String lon = (String.valueOf(location.getLongitude()));
                    connection(lat, lon, responsez);
                    //Toast.makeText(DriverActivity.this, responsez+"getvum", Toast.LENGTH_SHORT).show();


                }
            });
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 99);
            }
        }
    }

    public void connection(String lat, String lon, String responsez) {
        {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, server_url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Toast.makeText(DriverActiviy2.this, "Updated....." + response, Toast.LENGTH_SHORT).show();
                }
            }
                    , new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(DriverActiviy2.this, "ERROR....." + error, Toast.LENGTH_SHORT).show();
                    StringWriter writer = new StringWriter();
                    PrintWriter printWriter = new PrintWriter(writer);
                    error.printStackTrace(printWriter);
                    printWriter.flush();
                    // String stackTrace = writer.toString();
                    // error.printStackTrace( );
                }
            }) {
                @Override
                public Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> Params = new HashMap<String, String>();
                    Params.put("id", responsez);
                    Params.put("lat", lat);
                    Params.put("lon", lon);
                    //Toast.makeText(DriverActivity.this, number+"getv3", Toast.LENGTH_SHORT).show();
                    return Params;
                }
            };
            Mysingnalton.getInstance(DriverActiviy2.this).addTorequestque(stringRequest);
            //Toast.makeText(DriverActivity.this, responsez+"getvum", Toast.LENGTH_SHORT).show();


        }
    }


    private BottomNavigationView.OnNavigationItemSelectedListener navLister = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;

            switch (item.getItemId()) {
                case R.id.nav_Current_Ride:
                    selectedFragment = new Driver_Current_Ride_Fragment();
                    break;
                case R.id.nav_inform:
                    selectedFragment = new DriverPickupList_Fragment();
                    break;
                case R.id.nav_payment:
                    selectedFragment = new DriverDroplist_Fragment();
                    break;
                case R.id.nav_Account:
                    selectedFragment = new DriverAccount_Fragment();
                    break;

            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
            return true;
        }
    };

    public String getNoplateno(String DID) {

        if (DID.isEmpty()) {
            Toast.makeText(DriverActiviy2.this, "Login Again", Toast.LENGTH_SHORT).show();
        } else {
            final ProgressDialog progressDialog = new ProgressDialog(DriverActiviy2.this);

            String uRl = "https://dtrack.live/darcgetno.php";
            StringRequest request = new StringRequest(Request.Method.POST, uRl, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if (response.length() > 0) {

                       // AppOps appOps = new AppOps();
                         //appOps.setNO_PLATE(response);

                        //VEHICLE_NO =response;


                       // Toast.makeText(DriverActiviy2.this, response+"getv1", Toast.LENGTH_SHORT).show();
                    } else {
                       // Toast.makeText(DriverActiviy2.this, response, Toast.LENGTH_SHORT).show();
                        // progressDialog.dismiss();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(DriverActiviy2.this, error.toString() + "getvehicleError", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    HashMap<String, String> param = new HashMap<>();
                    param.put("DID", DID);
                    return param;
                }
            };
            request.setRetryPolicy(new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            MySingleton.getmInstance(DriverActiviy2.this).addToRequestQueue(request);
            //Toast.makeText(LoginActivity.this, user+"  "+pw, Toast.LENGTH_SHORT).show();
        }
        return DID;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.client_bottom_navigation_menu,menu);
        getMenuInflater().inflate(R.menu.drivertop_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.driver_top_notifications) {
            Intent i = new Intent(this, ViewNotifications.class);
            i.putExtra("type", "Driver");
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }


}