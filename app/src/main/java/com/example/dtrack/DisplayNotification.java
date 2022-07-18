package com.example.dtrack;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class DisplayNotification extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_notification);

        Intent intent = getIntent();
        String datetime = intent.getStringExtra("DateTime");
        String notTitle = intent.getStringExtra("Topic");
        String content = intent.getStringExtra("Contetnt");

        TextView textViewdate = findViewById(R.id.txtnotDateTime);
        TextView textViewTitle = findViewById(R.id.txtnotTitle);
        TextView textViewContent = findViewById(R.id.txtnotContent);

        textViewdate.setText(datetime);
        textViewTitle.setText(notTitle);
        textViewContent.setText(content);

    }
}