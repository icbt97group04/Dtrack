package com.example.dtrack;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class ContactServiceProvider extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_service_provider);
        String DriverID; // get client if from Database

        String driverName = "pavithra Hatharasinghe";
        String driverAddress = "Hell";
        String driverNumber = "0779316741"; //get driver mobile no form the database
        String drivercallNumber = "tel:"+driverNumber; //get driver mobile no form the database
        String driverEmail= "pavithrahatsin@gmail.com"; //get driver email form the database

        // declare buttons
        Button call = findViewById(R.id.btnCall);
        Button message = findViewById(R.id.btnMessage);
        Button email = findViewById(R.id.btnEmail);

        TextView tvdrivername = findViewById(R.id.tvDrivername);
        TextView tvdriverAddress = findViewById(R.id.tvDriveraddress);
        TextView tvdrivermobile = findViewById(R.id.tvDriverMobileNo);

        tvdrivername.setText(driverName);
        tvdriverAddress.setText(driverAddress);
        tvdrivermobile.setText(driverNumber);

        // use button click actoions for open outside
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentphone = new Intent(Intent.ACTION_DIAL);
                intentphone.setData(Uri.parse(drivercallNumber));
                startActivity(intentphone);
            }
        });
        message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sendIntent = new Intent(Intent.ACTION_VIEW);
                sendIntent.setData(Uri.parse("sms:" + driverNumber));

                startActivity(sendIntent);
            }
        });

        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mailer = new Intent(Intent.ACTION_SEND);
                mailer.setType("text/plain");
                mailer.putExtra(Intent.EXTRA_EMAIL, new String[]{driverEmail});
                startActivity(Intent.createChooser(mailer, "Send email..."));
            }
        });
    }
}