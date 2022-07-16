package com.example.dtrack;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class DriverAccount_Fragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_driver_account,container,false);

    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        ((DriverActiviy2) getActivity()).setActionBarTitle("My Account");
        TextView currentride = (TextView) view.findViewById(R.id.tvCurrentRide);

        TextView pickuplist = (TextView) view.findViewById(R.id.tvPickuplist);
        TextView droplist = (TextView) view.findViewById(R.id.tvDropList);
        TextView notifications = (TextView) view.findViewById(R.id.tvDriverNotification);
        TextView ContatcAdmin = (TextView) view.findViewById(R.id.tvContatcAdmin);

        Button logout = (Button) view.findViewById(R.id.btnLogoutDriver);

        currentride.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                Fragment myFragment = new Driver_Current_Ride_Fragment();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, myFragment).addToBackStack(null).commit();
            }
        });

        pickuplist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                Fragment myFragment = new DriverPickupList_Fragment();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, myFragment).addToBackStack(null).commit();
            }
        });
        droplist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                Fragment myFragment = new DriverDroplist_Fragment();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, myFragment).addToBackStack(null).commit();
            }
        });
        notifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), ViewNotifications.class);
                i.putExtra("type", "Driver" );
                startActivity(i);
            }
        });
        ContatcAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ContactServiceProvider.class);
                startActivity(intent);
            }
        });



        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // ClientActivity  clientActivity = (ClientActivity)getActivity();
                //clientActivity.IsLoggedIn = false;


                startActivity(new Intent( getContext(),LoginActivity.class));
            }
        });

    }
}
