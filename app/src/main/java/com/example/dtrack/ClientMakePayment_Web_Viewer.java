package com.example.dtrack;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

public class ClientMakePayment_Web_Viewer extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_make_payment_web_viewer);

        Intent intent = getIntent();
        String paymentID = intent.getStringExtra("paymentID");

        WebView myWebView = (WebView) findViewById(R.id.webCiewClineApyments);
        myWebView.loadUrl("https://kkk:123321@dtrack.live/mobilePayments.php?PaymentID="+paymentID);

        // Enable Javascript
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        // Force links and redirects to open in the WebView instead of in a browser
        myWebView.setWebViewClient(new WebViewClient());
    }
}