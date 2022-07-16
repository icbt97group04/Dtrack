package com.example.dtrack;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Client_Current_Ride_Fragment extends Fragment {
    private String CLIENT_ID;
    private String EMAIL;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_client_current_ride,container,false);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((ClientActivity) getActivity()).setActionBarTitle("Current Ride");

        CLIENT_ID = ((ClientActivity)getActivity()).CLIENT_ID;
        //EMAIL = ((ClientActivity)getActivity()).Email;

        //Toast.makeText(getContext(), EMAIL, Toast.LENGTH_SHORT).show();

        WebView myWebView = (WebView) view.findViewById(R.id.webviewCurrentRide);
        myWebView.loadUrl("https://dtrack.live/mapmobile.php?cid="+CLIENT_ID);

        // Enable Javascript
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        // Force links and redirects to open in the WebView instead of in a browser
        myWebView.setWebViewClient(new WebViewClient());



    }

}
