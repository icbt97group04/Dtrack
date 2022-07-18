package com.example.dtrack;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

public class PayWithoutLogin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_without_login);

        Intent intent = getIntent();
        String cid = intent.getStringExtra("cid");

        WebView myWebView = (WebView) findViewById(R.id.wvpaywithoutlogin);
        myWebView.loadUrl("https://kkk:123321@dtrack.live/mobilePayments.php?cid="+cid);

        // Enable Javascript
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        // Force links and redirects to open in the WebView instead of in a browser
        myWebView.setWebViewClient(new WebViewClient());



    }
}