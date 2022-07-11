package com.example.dtrack;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    Button login;
    TextView username,password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Spinner spinner=findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.userlevel, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
       // spinner.setOnItemSelectedListener(this);

        login = findViewById(R.id.btnLogin);
        username=findViewById(R.id.username);
        password=findViewById(R.id.password);
        //String tef="Driver";
       // driverlogin(tef);


login.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        String text = spinner.getSelectedItem().toString();
        String user= username.getText().toString();
        String pw=password.getText().toString();
        //Toast.makeText(LoginActivity.this, text, Toast.LENGTH_SHORT).show();
        if(text=="Driver"){
            logind(user,pw);

        }
        else {
           clientlogin(user,pw);

        }
    }
});
    }

    private void clientlogin(String user, String pw) {
        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setTitle("login your account");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setIndeterminate(false);
        progressDialog.show();
        String uRl = "https://dtrack.live/darclogin5.php";
        StringRequest request = new StringRequest(Request.Method.POST, uRl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String res=response;

                if (response.length()>1){
                    Toast.makeText(LoginActivity.this, response, Toast.LENGTH_SHORT).show();


                    startActivity(new Intent(LoginActivity.this,DriverActivity.class));
                    progressDialog.dismiss();
                    finish();
                }
                else {
                    Toast.makeText(LoginActivity.this, response, Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
               /* try {

                    JSONArray array = new JSONArray(response);
                    for (int i = 0; i<array.length(); i++){

                        JSONObject object = array.getJSONObject(i);

                        String DID = object.getString("DID");

                        progressDialog.dismiss();


                    }


                }catch (Exception ex){
                    Toast.makeText(LoginActivity.this, "ex" + ex , Toast.LENGTH_SHORT).show();
                }*/
                //Toast.makeText(LoginActivity.this, response, Toast.LENGTH_SHORT).show();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LoginActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> param = new HashMap<>();
                param.put("Cname",user);
                param.put("ClientPassword",pw);
                return param;

            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(30000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getmInstance(LoginActivity.this).addToRequestQueue(request);
        Toast.makeText(LoginActivity.this, user+"  "+pw, Toast.LENGTH_SHORT).show();

    }

   /* @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String text = adapterView.getItemAtPosition(i).toString();
        //Toast.makeText(adapterView.getContext(), text, Toast.LENGTH_SHORT).show();
        driverlogin(text);
    }*/



    private void logind(String user,String pw) {
        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setTitle("login your account");
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setIndeterminate(false);
        progressDialog.show();
        String uRl = "https://dtrack.live/darclogin4.php";
        StringRequest request = new StringRequest(Request.Method.POST, uRl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String res=response;

                if (response.length()>1){
                    Toast.makeText(LoginActivity.this, response, Toast.LENGTH_SHORT).show();


                    startActivity(new Intent(LoginActivity.this,DriverActivity.class));
                    progressDialog.dismiss();
                    finish();
                }
                else {
                    Toast.makeText(LoginActivity.this, response, Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
               /* try {

                    JSONArray array = new JSONArray(response);
                    for (int i = 0; i<array.length(); i++){

                        JSONObject object = array.getJSONObject(i);

                        String DID = object.getString("DID");

                        progressDialog.dismiss();


                    }


                }catch (Exception ex){
                    Toast.makeText(LoginActivity.this, "ex" + ex , Toast.LENGTH_SHORT).show();
                }*/
                //Toast.makeText(LoginActivity.this, response, Toast.LENGTH_SHORT).show();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LoginActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> param = new HashMap<>();
                param.put("Name",user);
                param.put("DriverPassword",pw);
                return param;

            }
        };

        request.setRetryPolicy(new DefaultRetryPolicy(30000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getmInstance(LoginActivity.this).addToRequestQueue(request);
        Toast.makeText(LoginActivity.this, user+"  "+pw, Toast.LENGTH_SHORT).show();

    }

   /* @Override
    public void onNothingSelected(AdapterView<?> adapterView) {
       //Toast.makeText(adapterView.getContext(), "Select User Level", Toast.LENGTH_SHORT).show();

    }*/

}
