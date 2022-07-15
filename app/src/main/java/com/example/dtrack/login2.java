package com.example.dtrack;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class login2 extends AppCompatActivity {
    public static final String EMAIL_KEY = "12";

    Button driver,parent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);

        driver = findViewById(R.id.buttonDriver);
        parent = findViewById(R.id.buttonParent);

        driver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(login2.this,DriverActiviy2.class);
                intent.putExtra("id","1" );
                startActivity(intent);

            }
        });

        // batman
        parent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(login2.this,ClientActivity.class);
                intent.putExtra("id","1" );
                startActivity(intent);
            }
        });
    }


}