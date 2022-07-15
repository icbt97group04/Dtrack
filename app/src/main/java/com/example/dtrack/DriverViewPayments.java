package com.example.dtrack;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class DriverViewPayments extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private paymentAdapter mExampleAdapter;
    private ArrayList<Payment> mExampleList;
    private RequestQueue mRequestQueue;
    private String cid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_view_payments);

        Intent i = getIntent();
        cid = i.getStringExtra("cid");


        mRecyclerView = findViewById(R.id.rv_diver_child_payment_list);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mExampleList = new ArrayList<>();

        mRequestQueue = Volley.newRequestQueue(this);
        parseJSON();

    }
    private void parseJSON() {


        String url = "https://dtrack.live/generatepaymentarray.php?userid="+cid;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("hits");

                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject hit = jsonArray.getJSONObject(i);

                        String payemntID = hit.getString("PaymentID");
                        String amount = hit.getString("PaymentAmount");
                        String dueDate = hit.getString("DueDate");
                        String paidDate = hit.getString("PaidDate");
                        String method = hit.getString("Method");
                        String status = hit.getString("PaymentStatus");
                        String cid = hit.getString("cid");
                        String order_id = hit.getString("order_id");

                        mExampleList.add(new Payment(payemntID, amount, dueDate, paidDate, method, status, cid, order_id));
                    }

                    mExampleAdapter = new paymentAdapter(DriverViewPayments.this, mExampleList);
                    mRecyclerView.setAdapter(mExampleAdapter);

                    mExampleAdapter.setOnItemClickListner(DriverViewPayments.this::onItemClick);
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


    public void onItemClick(int postion) {
        Intent detailIntent = new Intent(this, DriverMakePayments.class);
        Payment clickItem = mExampleList.get(postion);
        detailIntent.putExtra("paymentID", clickItem.getPaymentID());
        detailIntent.putExtra("amount", clickItem.getPaymentAmount());
        detailIntent.putExtra("dueDate", clickItem.getDueDate());
        detailIntent.putExtra("paidDate", clickItem.getPaidDate());
        detailIntent.putExtra("method", clickItem.getMethod());
        detailIntent.putExtra("PaymentStatus", clickItem.getPaymentStatus());
        detailIntent.putExtra("cid", clickItem.getCid());
        detailIntent.putExtra("order_id", clickItem.getOrder_id());
        startActivity(detailIntent);
    }



}