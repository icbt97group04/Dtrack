package com.example.dtrack;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Client_Inform_Attendance_Fragment extends Fragment {

    private RequestQueue mRequestQueue;

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
                    //morningButton.setText("Yes");
                    correcteffect(morningButton);

                } else {
                    commingTomorrowMorning = false;
                    //morningButton.setText("No");
                    noeteffect(morningButton);
                }
                update();
            }
        });
        afternoonButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (afternoonButton.isChecked()) {
                    commingTomorrowAfternoon = true;
                    //afternoonButton.setText("Yes");
                    correcteffect(afternoonButton);
                } else {
                    commingTomorrowAfternoon = false;
                    //afternoonButton.setText("Yes");
                    noeteffect(afternoonButton);
                }
                update();

            }
        });
        TextView pickdropstatusmorning =  v.findViewById(R.id.txtViewStatusmorning);
        TextView pickdropstatusafternoon =  v.findViewById(R.id.txtViewStatusAfternoon);

        mRequestQueue = Volley.newRequestQueue(getContext());


        String cid = ((ClientActivity) getActivity()).CLIENT_ID;



        String url = "https://dtrack.live/generateChildstatusarray.php?userid=" + cid  ;
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("hits");

                    JSONObject hit = jsonArray.getJSONObject(0);

                    String morningStatus = hit.getString("morning");
                    String afternoonStatus = hit.getString("afternoon");

                    if(morningStatus.equals("Yes")){
                        pickdropstatusmorning.setText("Waiting");
                        commingTomorrowAfternoon = true;
                        correcteffect(morningButton);
                        morningButton.setChecked(true);

                    }
                    if(morningStatus.equals("No")){
                        pickdropstatusmorning.setText("Not Coming");
                        commingTomorrowMorning = false;
                    }
                    if(morningStatus.equals("Traveling")){
                        pickdropstatusmorning.setText("Traveling");
                    }
                    if(morningStatus.equals("Delivered")){
                        pickdropstatusmorning.setText("Delivered");
                    }

                    if(afternoonStatus.equals("Yes")){
                        pickdropstatusafternoon.setText("Waiting");
                        commingTomorrowAfternoon = true;
                        correcteffect(afternoonButton);
                        afternoonButton.setChecked(true);

                    }
                    if(afternoonStatus.equals("No")){
                        pickdropstatusafternoon.setText("Not Coming");
                        commingTomorrowAfternoon = false;
                    }
                    if(afternoonStatus.equals("Traveling")){
                        pickdropstatusafternoon.setText("Traveling");
                    }
                    if(afternoonStatus.equals("Delivered")){
                        pickdropstatusafternoon.setText("Delivered");
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        mRequestQueue.add(request);



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
    public void correcteffect(ToggleButton button){
        ObjectAnimator colorAnim = ObjectAnimator.ofInt(button, "textColor",
                Color.GREEN);
        colorAnim.setEvaluator(new ArgbEvaluator());
        colorAnim.start();

    }
    public void noeteffect(ToggleButton button){
        ObjectAnimator colorAnim = ObjectAnimator.ofInt(button, "textColor",
                Color.BLACK);
        colorAnim.setEvaluator(new ArgbEvaluator());
        colorAnim.start();
    }


    }


