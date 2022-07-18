package com.example.dtrack;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    private Button login;
    private TextView username, password;
    private DBHelper Db;
    AlertDialog.Builder builder;
    public static String Email;
    public TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();

        Spinner spinner = findViewById(R.id.spinner);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.userlevel, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        login = findViewById(R.id.btnLogin);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
        tv=findViewById(R.id.textView);
        builder = new AlertDialog.Builder(LoginActivity.this);
        Db = new DBHelper(this);
        Db.OpenDB();

        TextView howtoregister = findViewById(R.id.tvHowToRegister);
        howtoregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, HowToRegisterWebview.class));
            }
        });


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String text = spinner.getSelectedItem().toString();
                String user = username.getText().toString();
                Email = user;
                String pw = password.getText().toString();

                if (text.length() == 6) {
                    logind(user, pw);
                    //Toast.makeText(LoginActivity.this, "yes", Toast.LENGTH_SHORT).show();
                } else {
                    clientlogin(user, pw);

                }
            }
        });
    }

    private void clientlogin(String user, String pw) {
        //checking empty textboxes
        if (user.isEmpty() || pw.isEmpty()) {
            Toast.makeText(LoginActivity.this, "Enter Details", Toast.LENGTH_SHORT).show();
            erroreffect();
        } else {
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
                        paymentcheck(res, user);
                        progressDialog.dismiss();
                    } else {
                        erroreffect();
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
                }
            };

            request.setRetryPolicy(new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            MySingleton.getmInstance(LoginActivity.this).addToRequestQueue(request);
        }
    }

    private void logind(String user, String pw) {
        if (user.isEmpty() || pw.isEmpty()) {
            Toast.makeText(LoginActivity.this, "Enter Details", Toast.LENGTH_SHORT).show();
            erroreffect();
        } else {
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
                    String res = response;

                    if (response.length() > 0) {
                        getvehiclenumber(response);


                    } else {
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
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    HashMap<String, String> param = new HashMap<>();
                    param.put("Email", user);
                    param.put("DriverPassword", pw);
                    return param;

                }
            };
            request.setRetryPolicy(new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            MySingleton.getmInstance(LoginActivity.this).addToRequestQueue(request);


        }
    }

    private void paymentcheck(String cid, String user) {
        String uRl = "https://dtrack.live/darcpaymentcheck.php";
        StringRequest request = new StringRequest(Request.Method.POST, uRl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                if (response.length() == 1) {
                    //Toast.makeText(LoginActivity.this, "Blocked", Toast.LENGTH_SHORT).show();
                    builder.setTitle("Warning");
                    builder.setMessage("Please Pay your fees :");
                    builder.setPositiveButton("PAY", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                            correcteffect();
                            Intent i = new Intent(LoginActivity.this, ClientActivity.class);
                            i.putExtra("cid", cid);
                            i.putExtra("email",user);
                            startActivity(i);

                            Db.Insertcuser(cid, "Client", "Logged");
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();


                } else if (response.length() == 3) {
                    erroreffect();
                    builder.setTitle("Blocked");
                    builder.setMessage("Please Pay your fees :");
                    builder.setPositiveButton("PAY", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent i = new Intent(LoginActivity.this, PayWithoutLogin.class);
                            i.putExtra("cid", cid);
                            startActivity(i);
                        }
                    });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();

                } else if (response.length() == 4) {
                    correcteffect();
                    Intent i = new Intent(LoginActivity.this, ClientActivity.class);
                    i.putExtra("cid", cid);
                    startActivity(i);
                    Db.Insertcuser(cid, "Client", "Logged");

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LoginActivity.this, "paycheck " + error.toString(), Toast.LENGTH_SHORT).show();


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

    public String getvehiclenumber(String DID) {
        if (DID.isEmpty()) {
            Toast.makeText(LoginActivity.this, "Login Again", Toast.LENGTH_SHORT).show();
        } else {
            final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
            String uRl = "https://dtrack.live/darcgetno.php";
            StringRequest request = new StringRequest(Request.Method.POST, uRl, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    if (response.length() > 0) {
                        correcteffect();
                        Intent i = new Intent(LoginActivity.this, DriverActiviy2.class);
                        i.putExtra("did", DID);
                        i.putExtra("noplateno", response);
                        startActivity(i);
                        Db.Insertcuser(response, "Driver", "Logged");
                        progressDialog.dismiss();
                        finish();

                    } else {
                        Toast.makeText(LoginActivity.this, response, Toast.LENGTH_SHORT).show();
                        erroreffect();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(LoginActivity.this, error.toString() + "getvehicleError", Toast.LENGTH_SHORT).show();
                    progressDialog.dismiss();
                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    HashMap<String, String> param = new HashMap<>();
                    param.put("DID", DID);
                    return param;
                }
            };
            request.setRetryPolicy(new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            MySingleton.getmInstance(LoginActivity.this).addToRequestQueue(request);
            //Toast.makeText(LoginActivity.this, user+"  "+pw, Toast.LENGTH_SHORT).show();
        }
        return DID;
    }

    @Override
    public void onBackPressed() {
        finish();
    }
    public void erroreffect(){
        ObjectAnimator colorAnim = ObjectAnimator.ofInt(tv, "textColor",
                Color.RED, Color.BLACK);
        colorAnim.setEvaluator(new ArgbEvaluator());
        colorAnim.start();
    }
    public void correcteffect(){
        ObjectAnimator colorAnim = ObjectAnimator.ofInt(tv, "textColor",
                Color.GREEN, Color.BLACK);
        colorAnim.setEvaluator(new ArgbEvaluator());
        colorAnim.start();
    }


}



