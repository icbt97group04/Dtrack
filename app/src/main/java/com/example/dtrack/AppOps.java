package com.example.dtrack;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;


public class AppOps {

    String NO_PLATE;

    public AppOps(String NO_PLATE) {
        this.NO_PLATE = NO_PLATE;
    }

    public String getNO_PLATE() {
        return NO_PLATE;
    }

    public void setNO_PLATE(String NO_PLATE) {
        this.NO_PLATE = NO_PLATE;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String getShift() throws ParseException {

        String pattern = "HH:mm";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);

        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm");
        LocalTime localTime = LocalTime.now();


        LocalTime now = LocalTime.now();
        LocalTime limit1 = LocalTime.parse("06:00");
        LocalTime limit2 = LocalTime.parse("08:00");
        LocalTime limit3 = LocalTime.parse("08:00");
        LocalTime limit4 = LocalTime.parse("14:00");
        Boolean morning = now.isAfter(limit1) && now.isBefore(limit2);
        Boolean afternoon = now.isAfter(limit3) && now.isBefore(limit4);
        if (morning) {
            return "morning";
        } else if (afternoon) {
            return "afternoon";
        } else {
            return "shift";
        }
    }

}
