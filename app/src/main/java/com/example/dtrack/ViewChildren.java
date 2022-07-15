package com.example.dtrack;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

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

public class ViewChildren extends AppCompatActivity {

    private RequestQueue mRequestQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_children);

        Intent intent = getIntent();
        String cid = intent.getStringExtra("cid");

        mRequestQueue = Volley.newRequestQueue(this);

        String url = "https://dtrack.live/generateChildrenInfoarray.php?cid=" + cid;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("hits");

                    JSONObject hit = jsonArray.getJSONObject(0);

                    String jsoncid = hit.getString("cid");
                    String jsonemail = hit.getString("email");
                    String jsonfname = hit.getString("fname");
                    String jsonlname = hit.getString("lname");
                    String jsongender = hit.getString("gender");
                    String jsonbirthday = hit.getString("birthday");
                    String jsoncnumber = hit.getString("cnumber");
                    String jsonaddress = hit.getString("address");
                    String jsoncity = hit.getString("city");
                    String jsonNumPlateNO = hit.getString("NumPlateNO");
                    String jsonFees = hit.getString("Fees");




                    String full_name = jsonfname + " " + jsonlname;

                    TextView tvCName = findViewById(R.id.tvCname);
                    TextView tvemail = findViewById(R.id.tvCEmail);
                    TextView tvage = findViewById(R.id.tvCAge);
                    TextView tvNumber = findViewById(R.id.tvCNumber);
                    TextView tvAddress = findViewById(R.id.tvCAddress);
                    TextView tvCurrentVehicle = findViewById(R.id.tvCVehicleNO);
                    TextView tvFees = findViewById(R.id.tvCFees);
                    TextView tvViewPayments = findViewById(R.id.tvCPaymentStatus);
                    ImageView ivgender = findViewById(R.id.imageviewGeneder);

                    if(jsongender.equals("Male")){
                        ivgender.setBackgroundResource(R.drawable.deliveryboy);
                    }

                    tvCName.setText(full_name);
                    tvemail.setText("Email : " + jsonemail);
                    tvage.setText("Age : " +jsonbirthday);
                    tvNumber.setText("Mobile Number : " +jsoncnumber);
                    tvAddress.setText("Address : " +jsonaddress);
                    tvCurrentVehicle.setText("Current Vehicle Number : " +jsonNumPlateNO);
                    tvFees.setText("Monthly fee : " +jsonFees);

                    tvViewPayments.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent i = new Intent(ViewChildren.this, DriverViewPayments.class);
                            i.putExtra("cid",cid );
                            startActivity(i);
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