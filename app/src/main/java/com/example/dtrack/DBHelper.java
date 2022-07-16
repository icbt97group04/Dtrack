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


    public boolean Insertcuser(String cuserid,String UserType,String loginStatus){
        //SQLiteDatabase MyDB=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("userId",cuserid);
        contentValues.put("UserType",UserType);
        contentValues.put("loginStatus",loginStatus);
        long result=db.insert("CurrentUser",null,contentValues);
        if(result==-1) return false;
        else return true;
    }
    public boolean checkusername(String userId){

        Cursor cursor = db.rawQuery("Select loginStatus from CurrentUser where userId='"+userId+"'",null);
        if(cursor.getCount()>0)
            return true;
        else
            return false;
    }



}
