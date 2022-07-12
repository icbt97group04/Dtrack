package com.example.dtrack;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;

public class FAQ_Web_Viewer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq_web_viewer);

        WebView myWebView = (WebView) findViewById(R.id.faqwebview);
        myWebView.loadUrl("https://dtrack.live/");
    }
}