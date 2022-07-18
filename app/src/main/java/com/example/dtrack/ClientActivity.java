package com.example.dtrack;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

public class ClientActivity extends AppCompatActivity implements  Client_Inform_Attendance_Fragment.Client_Inform_Attendance_FragmentLister {



    String server_url = "https://dtrack.live/updateAttendance.php";

        public Boolean IsLoggedIn = true;
        public static String CLIENT_ID ;
        public static String Email ;
        String shift;
        AppOps appOps;
        public static Bundle mMyAppsBundle = new Bundle();



    public void setActionBarTitle(String title){
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(title);
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);


        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("");

        try {
            shift = appOps.getShift();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Intent intent = getIntent();
        CLIENT_ID = intent.getStringExtra("cid");





        //Toast.makeText(ClientActivity.this, email2 , Toast.LENGTH_SHORT).show();



        BottomNavigationView bottomnav = findViewById(R.id.client_bottom_nav);
        bottomnav.setOnNavigationItemSelectedListener(navLister);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new Client_Current_Ride_Fragment()).commit();
    }


    private BottomNavigationView.OnNavigationItemSelectedListener navLister = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;

            switch (item.getItemId()) {
                case R.id.nav_Current_Ride:
                    selectedFragment = new Client_Current_Ride_Fragment();

                    break;
                case R.id.nav_inform:
                    selectedFragment = new Client_Inform_Attendance_Fragment();
                    break;
                case R.id.nav_payment:
                    selectedFragment = new ClientPayment_Fragment();
                    break;
                case R.id.nav_Account:
                    selectedFragment = new ClientAccount_Fragment();
                    break;

            }
            //getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
            getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.fade_in,R.anim.fade_out).replace(R.id.fragment_container, selectedFragment).commit();
            return true;
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //getMenuInflater().inflate(R.menu.client_bottom_navigation_menu,menu);
        getMenuInflater().inflate(R.menu.customertop_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == R.id.top_notifications) {
            Intent i = new Intent(this, ViewNotifications.class);
            i.putExtra("type", "Children");
            startActivity(i);
        }
        return super.onOptionsItemSelected(item);
    }

    public void updateAttendacne(String cid, Boolean morning,Boolean afternoon) {

        String morningStatus ;
        String afternoonStatus ;
        if(morning){
            morningStatus = "Yes";
        }
        else{
            morningStatus = "No";
        }

        if(afternoon){
            afternoonStatus = "Yes";
        }
        else{
            afternoonStatus = "No";
        }

        {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, server_url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    Toast.makeText(ClientActivity.this, "Attendance Updated Successfully", Toast.LENGTH_SHORT).show();

                }
            }
                    , new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(ClientActivity.this, "ERROR....." + error, Toast.LENGTH_SHORT).show();
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
                    Params.put("morning", morningStatus);
                    Params.put("afternoon", afternoonStatus);
                    Params.put("cid", cid);

                    //Toast.makeText(DriverActivity.this, number+"getv3", Toast.LENGTH_SHORT).show();
                    return Params;
                }
            };
            Mysingnalton.getInstance(ClientActivity.this).addTorequestque(stringRequest);
            //Toast.makeText(DriverActivity.this, responsez+"getvum", Toast.LENGTH_SHORT).show();


        }
    }

    @Override
    public void onClientUpdatedAttendance(Boolean morning, Boolean afternoon) {
        updateAttendacne(CLIENT_ID, morning, afternoon);
    }



}