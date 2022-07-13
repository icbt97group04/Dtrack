package com.example.dtrack;

public class DropPick {
    String fname;
    String lname;
    String cid;

    public DropPick(String fname, String lname, String cid) {
        this.fname = fname;
        this.lname = lname;
        this.cid = cid;
    }

    public String getFname() {
        return fname;
    }

    public String getLname() {
        return lname;
    }

    public String getCid() {
        return cid;
    }
}
