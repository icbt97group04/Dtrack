package com.example.dtrack;


import android.os.Build;

import androidx.annotation.RequiresApi;

import java.text.ParseException;
import java.time.LocalTime;

public class AppOps {


    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String getShift() throws ParseException {

        LocalTime now = LocalTime.now();
        LocalTime limit1 = LocalTime.parse("05:30");
        LocalTime limit2 = LocalTime.parse("10:00");
        LocalTime limit3 = LocalTime.parse("11:00");
        LocalTime limit4 = LocalTime.parse("15:00");
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
