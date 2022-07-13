package com.example.dtrack;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class ClientActivity extends AppCompatActivity {

    public Boolean IsLoggedIn = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);

        if (!IsLoggedIn) {
            startActivity(new Intent(this, login2.class));

        }

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
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).commit();
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


}