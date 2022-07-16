package com.example.dtrack;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

public class DriverMakePayments extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_make_payments);

        Intent intent = getIntent();
        String payemntID = intent.getStringExtra("paymentID");
        String amount = intent.getStringExtra("amount");
        String dueDate = intent.getStringExtra("dueDate");
        String paidDate = intent.getStringExtra("paidDate");
        String method = intent.getStringExtra("method");
        String status = intent.getStringExtra("PaymentStatus");
        String cid = intent.getStringExtra("cid");
        String order_id = intent.getStringExtra("order_id");


        ImageView imagestatus = findViewById(R.id.statusimageDtiver);
        TextView textViewdueDate = findViewById(R.id.tvDriverDueDate);
        TextView textViewstatus = findViewById(R.id.tvDriverStatus);
        TextView textViewpaidDate = findViewById(R.id.tvDriverPaidDate);
        TextView textViewmethod = findViewById(R.id.tvDriverMethod);
        TextView textViewamount = findViewById(R.id.tvDriverAmount);
        Button btn_pay = findViewById(R.id.btn_Collect_Payment);

        textViewdueDate.setText("Due Date : " +dueDate);
        textViewamount.setText("Total : LKR " + amount );


        if (status.equals("YES") ) {
            Drawable placeholder = imagestatus.getContext().getResources().getDrawable(R.drawable.paid);
            imagestatus.setImageDrawable(placeholder);

            textViewmethod.setText("Payment Method : "+method);
            textViewpaidDate.setText("Paid Date : "+paidDate);
            textViewstatus.setText("Status : Paid");

            btn_pay.setVisibility(View.GONE);

        }
        else{
            Drawable placeholder = imagestatus.getContext().getResources().getDrawable(R.drawable.warning);
            imagestatus.setImageDrawable(placeholder);
            textViewmethod.setVisibility(View.GONE);
            textViewpaidDate.setVisibility(View.GONE);

            textViewstatus.setText("Not Paid");
            //btn_pay.setVisibility(View.GONE);
            btn_pay.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    updateAccountDetails(payemntID);

                }
            });
        }



    }
    String server_url = "https://dtrack.live/updatecashonhandpayments.php";

    public void updateAccountDetails(String payemntID) {
        {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, server_url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    Toast.makeText(DriverMakePayments.this, "Details Updated", Toast.LENGTH_SHORT).show();
                    finish();
                    startActivity(getIntent());
                }
            }
                    , new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(DriverMakePayments.this, "ERROR....." + error, Toast.LENGTH_SHORT).show();
                    StringWriter writer = new StringWriter();
                    PrintWriter printWriter = new PrintWriter(writer);
                    error.printStackTrace(printWriter);
                    printWriter.flush();

                }
            }) {
                @Override
                public Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> Params = new HashMap<String, String>();
                    Params.put("payemntID", payemntID);


                    //Toast.makeText(DriverActivity.this, number+"getv3", Toast.LENGTH_SHORT).show();
                    return Params;
                }
            };
            Mysingnalton.getInstance(DriverMakePayments.this).addTorequestque(stringRequest);
            //Toast.makeText(DriverActivity.this, responsez+"getvum", Toast.LENGTH_SHORT).show();


        }
    }
}