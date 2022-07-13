package com.example.dtrack;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

import java.time.LocalTime;
import java.util.ArrayList;

public class DriverPickupList_Fragment extends Fragment {

    private RecyclerView mRecyclerView;
    private DropPickAdapter mExampleAdapter;
    private ArrayList<DropPick> mExampleList;
    private RequestQueue mRequestQueue;
    private String noplateNo = "NQ-4568"; //get no plate no from the database
    private String shift = "afternoon"; //get time

    LocalTime time ;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_driver_pickup_list,container,false);
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerView = view.findViewById(R.id.driverPickuplist);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mExampleList = new ArrayList<>();
        mRequestQueue = Volley.newRequestQueue(getContext());
        parseJSON();
    }
    private void parseJSON() {

        String url = "https://dtrack.live/generatepickupanddroparray.php?numberplateid="+noplateNo+"&shift=" +shift;

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
}
