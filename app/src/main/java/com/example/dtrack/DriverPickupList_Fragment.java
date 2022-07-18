package com.example.dtrack;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DriverPickupList_Fragment extends Fragment {

    private RecyclerView mRecyclerView;
    private DropPickAdapter mExampleAdapter;
    private ArrayList<DropPick> mExampleList;
    private RequestQueue mRequestQueue;
    private String noplateNo; //get no plate no from the database
    String shift;
    AppOps appOps;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_driver_pickup_list, container, false);

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ((DriverActiviy2) getActivity()).setActionBarTitle("Pickup List");
        noplateNo = ((DriverActiviy2)getActivity()).noplateno;
       // Toast.makeText(getContext(),noplateNo,Toast.LENGTH_LONG).show();

        mRecyclerView = view.findViewById(R.id.driverPickuplist);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mExampleList = new ArrayList<>();
        mRequestQueue = Volley.newRequestQueue(getContext());

        try {
            shift = appOps.getShift();
        } catch (ParseException e) {
            e.printStackTrace();
        }


        parseJSON(noplateNo);

    }

    private void parseJSON(String noplateN ) {

        String url = "https://dtrack.live/generatepickupanddroparray.php?action=YES&numberplateid=" + noplateN + "&shift=" + ((DriverActiviy2)getActivity()).shift;

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

                        @Override
                        public void onUpdateClick(int postion) {
                            DropPick clickItem = mExampleList.get(postion);
                            markAttendance(clickItem.getCid());
                            mExampleAdapter.notifyDataSetChanged();
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

    String server_urldetai = "https://dtrack.live/markAttendance.php";

    public void markAttendance(String cid) {
        {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, server_urldetai, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    Toast.makeText(getContext(),  "Picked", Toast.LENGTH_SHORT).show();

                }
            }
                    , new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getContext(), "ERROR....." + error, Toast.LENGTH_SHORT).show();
                    StringWriter writer = new StringWriter();
                    PrintWriter printWriter = new PrintWriter(writer);
                    error.printStackTrace(printWriter);
                    printWriter.flush();

                }
            }) {
                @Override
                public Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> Params = new HashMap<String, String>();
                    Params.put("state", "Traveling");
                    Params.put("shift", shift);
                    Params.put("cid", cid);

                    return Params;
                }
            };
            Mysingnalton.getInstance(getContext()).addTorequestque(stringRequest);



        }
    }


}