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

public class ClientAccount_Fragment extends Fragment {

    String CLIENT_ID;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_client_account,container,false);


    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        super.onViewCreated(view, savedInstanceState);

        ((ClientActivity) getActivity()).setActionBarTitle("Account");

        CLIENT_ID = ((ClientActivity)getActivity()).CLIENT_ID;

        TextView contatcDriver = (TextView) view.findViewById(R.id.tvContatcDriver);
        TextView contactSp = (TextView) view.findViewById(R.id.tvContatcSP);
        TextView payments = (TextView) view.findViewById(R.id.tvPayments);
        TextView inform = (TextView) view.findViewById(R.id.tvInform);
        TextView notifications = (TextView) view.findViewById(R.id.tvNotifications);
        TextView chageDetails = (TextView) view.findViewById(R.id.tvChangeDetails);
        TextView faq = (TextView) view.findViewById(R.id.tvFAQ);
        Button logout = (Button) view.findViewById(R.id.btnLogout);

        contatcDriver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ContatcDriver.class);
                startActivity(intent);
            }
        });
        contactSp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ContactServiceProvider.class);
                startActivity(intent);
            }
        });
        payments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                Fragment myFragment = new ClientPayment_Fragment();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, myFragment).addToBackStack(null).commit();



            }
        });
        inform.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AppCompatActivity activity = (AppCompatActivity) view.getContext();
                Fragment myFragment = new Client_Inform_Attendance_Fragment();
                activity.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, myFragment).addToBackStack(null).commit();
            }
        });
        notifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getContext(), ViewNotifications.class);
                i.putExtra("type", "Children" );
                startActivity(i);
            }
        });
        chageDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ClientEditAccountDetails.class);
                intent.putExtra("cid",CLIENT_ID);
                startActivity(intent);
            }
        });
        faq.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), FAQ_Web_Viewer.class);
                startActivity(intent);
            }
        });


        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               // ClientActivity  clientActivity = (ClientActivity)getActivity();
                //clientActivity.IsLoggedIn = false;


                startActivity(new Intent( getContext(),LoginActivity.class));

                getActivity().finish();
            }
        });





    }
}
