package com.example.dtrack;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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

public class ContatcDriver extends AppCompatActivity {

    private RequestQueue mRequestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contatc_driver);


        Intent intent = getIntent();
        String cid = intent.getStringExtra("cid");


        mRequestQueue = Volley.newRequestQueue(this);

        String url = "https://dtrack.live/generateDriverInfoarray.php?cid=" + cid;



        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("hits");

                    JSONObject hit = jsonArray.getJSONObject(0);


                    String driverName = hit.getString("Name");
                    String driverAddress = hit.getString("Adress");
                    String driverNumber =  hit.getString("MobileNumber"); //get driver mobile no form the database
                    String drivercallNumber = "tel:"+driverNumber; //get driver mobile no form the database
                    String driverEmail= hit.getString("Email"); //get driver email form the database


                    // declare buttons
                    Button call = findViewById(R.id.btnCall);
                    Button message = findViewById(R.id.btnMessage);
                    Button email = findViewById(R.id.btnEmail);

                    TextView tvdrivername = findViewById(R.id.tvDrivername);
                    TextView tvdriverAddress = findViewById(R.id.tvDriveraddress);
                    TextView tvdrivermobile = findViewById(R.id.tvDriverMobileNo);

                    tvdrivername.setText(driverName);
                    tvdriverAddress.setText(driverAddress);
                    tvdrivermobile.setText(driverNumber);

                    // use button click actoions for open outside
                    call.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intentphone = new Intent(Intent.ACTION_DIAL);
                            intentphone.setData(Uri.parse(drivercallNumber));
                            startActivity(intentphone);
                        }
                    });
                    message.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent sendIntent = new Intent(Intent.ACTION_VIEW);
                            sendIntent.setData(Uri.parse("sms:" + driverNumber));

                            startActivity(sendIntent);
                        }
                    });

                    email.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent mailer = new Intent(Intent.ACTION_SEND);
                            mailer.setType("text/plain");
                            mailer.putExtra(Intent.EXTRA_EMAIL, new String[]{driverEmail});
                            startActivity(Intent.createChooser(mailer, "Send email..."));
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