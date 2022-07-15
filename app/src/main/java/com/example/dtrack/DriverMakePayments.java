package com.example.dtrack;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

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
                    //Uri uri = Uri.parse("https://dtrack.live/payhere.php?PaymentID=" + payemntID);
                    //Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    //startActivity(intent);
                }
            });
        }



    }
}