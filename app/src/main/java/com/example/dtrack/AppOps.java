package com.example.dtrack;

import android.app.ProgressDialog;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;


public class AppOps {


    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String getShift() throws ParseException {

        LocalTime now = LocalTime.now();
        LocalTime limit1 = LocalTime.parse("06:00");
        LocalTime limit2 = LocalTime.parse("08:00");
        LocalTime limit3 = LocalTime.parse("12:00");
        LocalTime limit4 = LocalTime.parse("23:00");
        Boolean morning = now.isAfter(limit1) && now.isBefore(limit2);
        Boolean afternoon = now.isAfter(limit3) && now.isBefore(limit4);
        if (morning) {
            return "morning";
        } else if (afternoon) {
            return "afternoon";
        } else {
            return "Noshift";
        }
    }
}
