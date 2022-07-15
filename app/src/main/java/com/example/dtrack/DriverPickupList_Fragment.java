package com.example.dtrack;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class DriverPickupList_Fragment extends Fragment {

    private RecyclerView mRecyclerView;
    private DropPickAdapter mExampleAdapter;
    private ArrayList<DropPick> mExampleList;
    private RequestQueue mRequestQueue;
    private String noplateNo = "NA-4598"; //get no plate no from the database
    private String shift = "morning";


    LocalTime time ;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_driver_pickup_list,container,false);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

       // shift  = ((DriverActiviy2)getActivity()).currentShift;

        //Toast.makeText(getContext(),shift , Toast.LENGTH_SHORT).show();

        mRecyclerView = view.findViewById(R.id.driverPickuplist);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mExampleList = new ArrayList<>();
        mRequestQueue = Volley.newRequestQueue(getContext());
        setcurrentShift();

    }
    private void parseJSON(String shift) {

        String url = "https://dtrack.live/generatepickupanddroparray.php?action=YES&numberplateid="+noplateNo+"&shift=" +shift;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("hits");

                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject hit = jsonArray.getJSONObject(i);

                        String fname = hit.getString("fname");
                        String lname = hit.getString("lname");
                        String id = hit.getString("cid");
                        mExampleList.add(new DropPick(fname, lname, id));
                    }

                    mExampleAdapter = new DropPickAdapter(getContext(), mExampleList);
                    mRecyclerView.setAdapter(mExampleAdapter);




                    mExampleAdapter.setOnItemClickListner(new DropPickAdapter.onItemClickListner() {
                        @Override
                        public void onItemClick(int postion) {
                            Intent detailIntent = new Intent(getContext(), ViewChildren.class);
                            DropPick clickItem = mExampleList.get(postion);
                            detailIntent.putExtra("cid", clickItem.getCid());
                            startActivity(detailIntent);
                        }
                    });
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
    }


    public void setcurrentShift(){

        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        Date MorningStartTime = null;
        try {
            MorningStartTime = dateFormat.parse("06:00");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date MorningEndTime = null;
        try {
            MorningEndTime = dateFormat.parse("08:00");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date afternoonStartTime = null;
        try {
            afternoonStartTime = dateFormat.parse("11:00");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date afternoonEndTime = null;
        try {
            afternoonEndTime = dateFormat.parse("2:30");
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Date CurrentTime = null;
        try {
            Date currentTime = Calendar.getInstance().getTime();
            CurrentTime = dateFormat.parse(dateFormat.format(new Date()));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (CurrentTime.after(MorningStartTime) && CurrentTime.before(MorningEndTime)) {
            String shift = "morning";
            Toast.makeText(getContext(),shift,Toast.LENGTH_SHORT).show();
            parseJSON(shift);
        }
        if (CurrentTime.after(afternoonStartTime) && CurrentTime.before(afternoonEndTime)) {
            String shift1 = "afternoon";
            Toast.makeText(getContext(),shift1,Toast.LENGTH_SHORT).show();
            parseJSON(shift1);
        }




    }
}
