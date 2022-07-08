package com.example.dtrack;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;

public class HowToRegisterWebview extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_how_to_register_webview);

        WebView wv = (WebView) findViewById(R.id.wvHowtoReg);
        wv.loadUrl("https://dtrack.live/about.php");
    }
}