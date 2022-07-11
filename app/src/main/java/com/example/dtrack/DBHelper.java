package com.example.dtrack;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Vector;

public class DBHelper {
    private Context con;
    private SQLiteDatabase db;

    public DBHelper(Context con) {
        this.con = con;
    }

    public DBHelper OpenDB() {
        DBConnector dbCon = new DBConnector(con);
        db = dbCon.getWritableDatabase();
        return this;
    }

    //Insert data to the database


    // Insert Driver Details
    public boolean InsertDriver(Driver driver) {
        try {
            ContentValues cv = new ContentValues();

            cv.put("Did", Driver.getDriverId());
            cv.put("DName", Driver.getDriverName());
            cv.put("DAddress", Driver.getDriverAddress());
            cv.put("DEmail", Driver.getDriverEmail());
            cv.put("DMobileNumber", Driver.getDriverMobileNumber());

            db.insert("DriverAssistanceLocal", null, cv);
            return true;
        } catch (Exception ex) {
            Toast.makeText(con, ex.getMessage(), Toast.LENGTH_LONG).show();
        }
        return false;

    }

    // Insert Client details

    public boolean InsertClient(Client client) {
        try {
            ContentValues cv = new ContentValues();

            cv.put("Cid", Client.getClientId());
            cv.put("Cname", Client.getClientName());
            cv.put("Caddress", Client.getClientAddress());
            cv.put("CEmail", Client.getClientEmail());
            cv.put("Cbirthday", Client.getCbirthday());
            cv.put("Cgender", Client.getCgender());
            cv.put("NumPlateNO", Client.getNumPlateNO());

            db.insert("DriverAssistanceLocal", null, cv);
            return true;
        } catch (Exception ex) {
            Toast.makeText(con, ex.getMessage(), Toast.LENGTH_LONG).show();
        }
        return false;

    }



}
