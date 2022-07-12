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
import androidx.fragment.app.Fragment;

public class ClientAccount_Fragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_client_account,container,false);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        super.onViewCreated(view, savedInstanceState);

        TextView contatcDriver = (TextView) view.findViewById(R.id.tvContatcDriver);
        TextView contactSp = (TextView) view.findViewById(R.id.tvContatcSP);
        TextView payments = (TextView) view.findViewById(R.id.tvPayments);
        TextView inform = (TextView) view.findViewById(R.id.tvInform);
        TextView notifications = (TextView) view.findViewById(R.id.tvNotifications);
        TextView chageDetails = (TextView) view.findViewById(R.id.tvChangeDetails);
        TextView faq = (TextView) view.findViewById(R.id.tvFAQ);

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
                Intent intent = new Intent(getActivity(), ClientPayment_Fragment.class);
                startActivity(intent);
            }
        });
        inform.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Client_Inform_Attendance_Fragment.class);
                startActivity(intent);
            }
        });
        notifications.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ViewNotifications.class);
                startActivity(intent);
            }
        });
        chageDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ClientChangeDetails.class);
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

    }
}
