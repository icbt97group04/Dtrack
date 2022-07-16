package com.example.dtrack;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Client_Inform_Attendance_Fragment extends Fragment {

    private Client_Inform_Attendance_FragmentLister listner;

    public interface Client_Inform_Attendance_FragmentLister {
        void onClientUpdatedAttendance(Boolean morning, Boolean afternoon);
    }

    boolean commingTomorrow = false;
    boolean commingTomorrowMorning = false;
    boolean commingTomorrowAfternoon = false;
    private String CLIENT_ID;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_client_infrom_attendance, container, false);

        ((ClientActivity) getActivity()).setActionBarTitle("Inform attendance");

        TextView warningtext =  (TextView) v.findViewById(R.id.warningtext);
        ToggleButton morningButton = (ToggleButton) v.findViewById(R.id.tb_on_off_inform_morning);
        ToggleButton afternoonButton = (ToggleButton) v.findViewById(R.id.tb_on_off_inform_afternoon);

        if(!((ClientActivity)getActivity()).shift.equals("Noshift")){
            warningtext.setText("Attendance update is disabled !!! ");
            morningButton.setEnabled(false);
            afternoonButton.setEnabled(false);

        }

        if(commingTomorrowMorning){
            morningButton.setEnabled(true);
        }
        if(commingTomorrowAfternoon){
            morningButton.setEnabled(true);
        }

        morningButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (morningButton.isChecked()) {
                    commingTomorrowMorning = true;
                    morningButton.setText("Yes");

                } else {
                    commingTomorrowMorning = false;
                    morningButton.setText("No");
                }
                update();
            }
        });
        afternoonButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (afternoonButton.isChecked()) {
                    commingTomorrowAfternoon = true;
                    afternoonButton.setText("Yes");
                } else {
                    commingTomorrowAfternoon = false;
                    afternoonButton.setText("Yes");
                }
                update();
            }
        });
        return v;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof Client_Inform_Attendance_Fragment.Client_Inform_Attendance_FragmentLister) {
            listner = (Client_Inform_Attendance_Fragment.Client_Inform_Attendance_FragmentLister) context;
        } else {
            throw new RuntimeException(context.toString() + "Must implement Fragment lister ");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listner = null;
    }


    public void update() {
        listner.onClientUpdatedAttendance(commingTomorrowMorning, commingTomorrowAfternoon);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        CLIENT_ID = ((ClientActivity) getActivity()).CLIENT_ID;

        Intent intent = getActivity().getIntent();
        String CID = intent.getStringExtra("cid");


    }


}
