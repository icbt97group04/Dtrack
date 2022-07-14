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

import java.util.ArrayList;

public class ClientPayment_Fragment extends Fragment {
    private RecyclerView mRecyclerView;
    private paymentAdapter mExampleAdapter;
    private ArrayList<Payment> mExampleList;
    private RequestQueue mRequestQueue;
    private String userid = "4";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_client_payment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerView = view.findViewById(R.id.recycler_view_payment);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        mExampleList = new ArrayList<>();

        mRequestQueue = Volley.newRequestQueue(getContext());
        parseJSON();

    }

    private void parseJSON() {

        String url = "https://dtrack.live/generatepaymentarray.php?userid=" + userid;

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

                    mExampleAdapter = new paymentAdapter(getContext(), mExampleList);
                    mRecyclerView.setAdapter(mExampleAdapter);

                    mExampleAdapter.setOnItemClickListner(new paymentAdapter.onItemClickListner() {
                        @Override
                        public void onItemClick(int postion) {
                            Intent detailIntent = new Intent(getContext(), PaymentDetails.class);
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
