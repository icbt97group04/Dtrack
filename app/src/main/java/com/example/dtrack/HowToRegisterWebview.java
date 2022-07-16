package com.example.dtrack;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class HowToRegisterWebview extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_how_to_register_webview);

        ActionBar actionBar  = getSupportActionBar();
        actionBar.hide();

        WebView wv = (WebView) findViewById(R.id.wvHowtoReg);
        wv.loadUrl("https://dtrack.live/faq.php");

        // Force links and redirects to open in the WebView instead of in a browser
        wv.setWebViewClient(new WebViewClient());
    }
}