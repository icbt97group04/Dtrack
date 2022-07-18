package com.example.dtrack;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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
import java.util.HashMap;
import java.util.Map;

public class ClientEditAccountDetails extends AppCompatActivity {
    private RequestQueue mRequestQueue;

    String server_url = "https://dtrack.live/updatepw.php";
    String server_urldetail = "https://dtrack.live/updatedetails.php";
    String cid;
    TextView newPassword, confirmPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_edit_account_details);

        Intent i = getIntent();
        cid = i.getStringExtra("cid");

        TextView mobileno = findViewById(R.id.editTextTextMobileNo);
        TextView address = findViewById(R.id.editTextTextAddress);
        TextView city = findViewById(R.id.editTextTextCity);
        newPassword = findViewById(R.id.editTextTextNewPassword);
        confirmPassword = findViewById(R.id.editTextTextConfirmPassword);


        mRequestQueue = Volley.newRequestQueue(this);

        String url = "https://dtrack.live/generateChildrenInfoarray.php?cid=" + cid;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("hits");

                    JSONObject hit = jsonArray.getJSONObject(0);



                    String jsoncnumber = hit.getString("cnumber");
                    String jsonaddress = hit.getString("address");
                    String jsoncity = hit.getString("city");
                    mobileno.setText(jsoncnumber);
                    address.setText(jsonaddress);
                    city.setText(jsoncity);


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




        String pw = newPassword.getText().toString();
        String cpw = confirmPassword.getText().toString();

        Button updateDetails = findViewById(R.id.btn_updateDetails);

        updateDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mobileno.toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please insert mobile number", Toast.LENGTH_LONG).show();
                } else if (address.toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please insert address", Toast.LENGTH_LONG).show();
                } else if (city.toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please insert city", Toast.LENGTH_LONG).show();
                } else {
                    updateAccountDetails (mobileno.getText().toString(), address.getText().toString(),city.getText().toString(), cid);
                    mobileno.setText("");
                    address.setText("");
                    city.setText("");
                }
            }
        });


        Button updateUpdate = findViewById(R.id.btn_update_password);

        updateUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if (newPassword.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please insert New password", Toast.LENGTH_LONG).show();
                } else if (confirmPassword.getText().toString().isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Please insert Confirm", Toast.LENGTH_LONG).show();
                } else if (!confirmPassword.getText().toString().equals(newPassword.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "New passwords must be matched", Toast.LENGTH_LONG).show();
                } else {
                    updatepassword(newPassword.getText().toString(), cid);
                    newPassword.setText("");
                    confirmPassword.setText("");
                }


            }

        });


    }

    public void updatepassword(String newpw, String cid) {
        {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, server_url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    Toast.makeText(ClientEditAccountDetails.this, "Password Updated", Toast.LENGTH_SHORT).show();

                }
            }
                    , new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(ClientEditAccountDetails.this, "ERROR....." + error, Toast.LENGTH_SHORT).show();
                    StringWriter writer = new StringWriter();
                    PrintWriter printWriter = new PrintWriter(writer);
                    error.printStackTrace(printWriter);
                    printWriter.flush();
                    // String stackTrace = writer.toString();
                    // error.printStackTrace( );
                }
            }) {
                @Override
                public Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> Params = new HashMap<String, String>();
                    Params.put("newpw", newpw);
                    Params.put("cid", cid);

                    //Toast.makeText(DriverActivity.this, number+"getv3", Toast.LENGTH_SHORT).show();
                    return Params;
                }
            };
            Mysingnalton.getInstance(ClientEditAccountDetails.this).addTorequestque(stringRequest);
            //Toast.makeText(DriverActivity.this, responsez+"getvum", Toast.LENGTH_SHORT).show();


        }
    }

    public void updateAccountDetails(String mobileNo,String address,String city, String cid) {
        {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, server_urldetail, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    Toast.makeText(ClientEditAccountDetails.this, "Details Updated", Toast.LENGTH_SHORT).show();

                }
            }
                    , new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(ClientEditAccountDetails.this, "ERROR....." + error, Toast.LENGTH_SHORT).show();
                    StringWriter writer = new StringWriter();
                    PrintWriter printWriter = new PrintWriter(writer);
                    error.printStackTrace(printWriter);
                    printWriter.flush();
                    // String stackTrace = writer.toString();
                    // error.printStackTrace( );
                }
            }) {
                @Override
                public Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> Params = new HashMap<String, String>();
                    Params.put("newno", mobileNo);
                    Params.put("newaddress", address);
                    Params.put("newcity", city);
                    Params.put("cid", cid);

                    //Toast.makeText(DriverActivity.this, number+"getv3", Toast.LENGTH_SHORT).show();
                    return Params;
                }
            };
            Mysingnalton.getInstance(ClientEditAccountDetails.this).addTorequestque(stringRequest);
            //Toast.makeText(DriverActivity.this, responsez+"getvum", Toast.LENGTH_SHORT).show();


        }
    }
}