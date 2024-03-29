package com.example.dtrack;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

public class FAQ_Web_Viewer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_faq_web_viewer);

        WebView myWebView = (WebView) findViewById(R.id.faqwebview);
        myWebView.loadUrl("https://dtrack.live/faq.php");

        // Force links and redirects to open in the WebView instead of in a browser
        myWebView.setWebViewClient(new WebViewClient());
    }
}