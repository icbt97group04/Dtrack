package com.example.dtrack;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Driver_Current_Ride_Fragment extends Fragment {

    private Driver_Current_Ride_FragmentLister listner;

    public interface Driver_Current_Ride_FragmentLister {
        void onDriverSent(Boolean input);
    }


    String DRIVER_ID;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_driver_current_ride, container, false);
        Switch setlocationUpdates = v.findViewById(R.id.stwithchStartLocationUpdates);

        ((DriverActiviy2) getActivity()).setActionBarTitle("Current Ride");

        TextView textviewRide = v.findViewById(R.id.textviewRide);

        if(((DriverActiviy2)getActivity()).shift.equals("Noshift")){
            textviewRide.setText("No shift at this moment ");
            setlocationUpdates.setClickable(false);
        }
        else{
            textviewRide.setText("Start Ride " + ((DriverActiviy2)getActivity()).shift);
            setlocationUpdates.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (setlocationUpdates.isChecked()) {
                        Boolean input = true;
                        Toast.makeText(getContext(), "The location update will start", Toast.LENGTH_SHORT).show();
                        listner.onDriverSent(input);


                    } else {
                        Boolean input = false;
                        Toast.makeText(getContext(), "Location will stop updating ", Toast.LENGTH_SHORT).show();
                        listner.onDriverSent(input);
                    }
                }
            });

            if( ((DriverActiviy2)getActivity()).isLocationUpdateChecked){
                setlocationUpdates.setChecked(true);
            }
        }
        return v;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof Driver_Current_Ride_FragmentLister){
            listner = (Driver_Current_Ride_FragmentLister) context;
        }else {
            throw new RuntimeException(context.toString() + "Must implement Fragment lister ");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listner = null;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        DRIVER_ID = ((DriverActiviy2)getActivity()).DID;

        //Toast.makeText(getContext(), DRIVER_ID, Toast.LENGTH_SHORT).show();

        WebView myWebView = (WebView) view.findViewById(R.id.drivercurrentrideweview);
        myWebView.loadUrl("https://dtrack.live/drivermap.php?did="+DRIVER_ID);

        // Enable Javascript
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        // Force links and redirects to open in the WebView instead of in a browser
        myWebView.setWebViewClient(new WebViewClient());


    }

}

