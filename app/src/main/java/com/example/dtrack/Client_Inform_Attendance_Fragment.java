package com.example.dtrack;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class Client_Inform_Attendance_Fragment extends Fragment {

    private TextView countDownText;
    private ToggleButton confirmButton;
    private ToggleButton morningButton;
    private ToggleButton afternoonButton;
    private CountDownTimer countDownTimer;
    boolean timerRunning;
    boolean commingTomorrow;
    boolean commingTomorrowMorning;
    boolean commingTomorrowAfternoon;
    private DBHelper Db;


    private long tiemeLeftInMilliseconds = 432000;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_client_infrom_attendance,container,false);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ToggleButton confirmButton = (ToggleButton) view.findViewById(R.id.tb_on_off_inform);
        ToggleButton morningButton = (ToggleButton) view.findViewById(R.id.tb_on_off_inform_morning);
        ToggleButton afternoonButton = (ToggleButton) view.findViewById(R.id.tb_on_off_inform_afternoon);
        morningButton.setEnabled(false);
        afternoonButton.setEnabled(false);

        confirmButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    morningButton.setEnabled(true);
                    afternoonButton.setEnabled(true);
                    Db=new DBHelper(getActivity());
       Intent intent = getActivity().getIntent();
        String CID = intent.getStringExtra("cid");
       Boolean cds = Db.checkusername(CID);

        if (cds=true){
            Toast.makeText(getActivity(), "LOGGGED IN", Toast.LENGTH_SHORT).show();
        }
                } else {
                    morningButton.setEnabled(false);
                    afternoonButton.setEnabled(false);
                }
            }
        });

    }


}
