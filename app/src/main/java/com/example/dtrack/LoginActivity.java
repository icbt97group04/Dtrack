package com.example.dtrack;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.ProgressDialog;
import android.content.DialogInterface;
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
import java.nio.channels.Channel;
import java.util.HashMap;
import java.util.Map;
import android.app.NotificationChannel;

public class LoginActivity extends AppCompatActivity {

    Button login;
    TextView username,password;
    private DBHelper Db;
    AlertDialog.Builder builder;

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
        builder = new AlertDialog.Builder(LoginActivity.this);
        Db=new DBHelper(this);
        Db.OpenDB();



login.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        String text = spinner.getSelectedItem().toString();
        String user= username.getText().toString();
        String pw=password.getText().toString();

        if(text.length()==6){
                logind(user,pw);
            //Toast.makeText(LoginActivity.this, "yes", Toast.LENGTH_SHORT).show();
        }
        else{
           clientlogin(user,pw);

        }
    }
});
    }

    private void clientlogin(String user, String pw) {
        //checking empty textboxes
        if(user.isEmpty()||pw.isEmpty()){
            Toast.makeText(LoginActivity.this, "Enter Details", Toast.LENGTH_SHORT).show();
        }else {
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
                    String res = response;

                    if (response.length() > 0) {

                        /*Intent i = new Intent(LoginActivity.this, ClientActivity.class);
                        i.putExtra("cid", res);
                        startActivity(i);
                        Db.Insertcuser(response,"Client","Logged");*/
                        paymentcheck(res);
                        //Toast.makeText(LoginActivity.this, "yes", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();

                    } else {
                        Toast.makeText(LoginActivity.this, "Wrong Details,Try Again ", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(LoginActivity.this, error.toString(), Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();

                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    HashMap<String, String> param = new HashMap<>();
                    param.put("email", user);
                    param.put("cpassword", pw);
                    return param;
//585
                }
            };

            request.setRetryPolicy(new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            MySingleton.getmInstance(LoginActivity.this).addToRequestQueue(request);
            //Toast.makeText(LoginActivity.this, user + "  " + pw, Toast.LENGTH_SHORT).show();
        }
    }

    private void logind(String user,String pw) {
        if(user.isEmpty()||pw.isEmpty()){
            Toast.makeText(LoginActivity.this, "Enter Details", Toast.LENGTH_SHORT).show();
        }else {
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

                if (response.length()>0){
                    Toast.makeText(LoginActivity.this, response, Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(LoginActivity.this, DriverActivity.class);
                    i.putExtra("did", res);
                    startActivity(i);
                    Db.Insertcuser(response,"Driver","Logged");
                    progressDialog.dismiss();
                    finish();
                }
                else {
                    Toast.makeText(LoginActivity.this, "Wrong Password", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }//59
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
                param.put("Email",user);
                param.put("DriverPassword",pw);
                return param;

            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(30000,DefaultRetryPolicy.DEFAULT_MAX_RETRIES,DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getmInstance(LoginActivity.this).addToRequestQueue(request);
        //Toast.makeText(LoginActivity.this, user+"  "+pw, Toast.LENGTH_SHORT).show();

    }}

    private  void paymentcheck(String cid){
        String uRl = "https://dtrack.live/darcpaymentcheck.php";
        StringRequest request = new StringRequest(Request.Method.POST, uRl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Toast.makeText(LoginActivity.this, response, Toast.LENGTH_SHORT).show();


                if(response.length()==1){
                        Toast.makeText(LoginActivity.this, "Blocked", Toast.LENGTH_SHORT).show();
                        builder.setTitle("Warning");
                        builder.setMessage("Please Pay your fees :");
                        builder.setPositiveButton("PAY", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        });
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                       /* Intent i = new Intent(LoginActivity.this, ClientActivity.class);
                        i.putExtra("cid", response);
                        startActivity(i);
                        Db.Insertcuser(cid,"Client","Logged");*/

                } else if(response.length()==2) {
                        //Toast.makeText(LoginActivity.this, "Blocked", Toast.LENGTH_SHORT).show();
                        builder.setTitle("Blocked");
                        builder.setMessage("Please Pay your fees :");
                        builder.setPositiveButton("PAY", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent i = new Intent(LoginActivity.this, Payment.class);
                                i.putExtra("cid", response);
                                startActivity(i);

                            }
                        });
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();

                }else {
                       /* Intent i = new Intent(LoginActivity.this, ClientActivity.class);
                        i.putExtra("cid", response);
                        startActivity(i);
                        Db.Insertcuser(cid,"Client","Logged");*/

                    }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LoginActivity.this, "paycheck "+error.toString(), Toast.LENGTH_SHORT).show();


            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> param = new HashMap<>();
                param.put("cid", cid);
                return param;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getmInstance(LoginActivity.this).addToRequestQueue(request);

    }


    }

