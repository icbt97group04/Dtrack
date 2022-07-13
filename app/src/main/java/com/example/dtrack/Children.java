package com.example.dtrack;

public class Children {
    String cid;
    String email;
    String fname;
    String lname;
    String gender;
    String birthday;
    String cnumber;
    String address;
    String city;
    String cpassword;
    String NumPlateNO;
    String Fees;

    public Children() {
    }

    public Children(String cid, String email, String fname, String lname, String gender, String birthday, String cnumber, String address, String city, String cpassword, String numPlateNO, String fees) {
        this.cid = cid;
        this.email = email;
        this.fname = fname;
        this.lname = lname;
        this.gender = gender;
        this.birthday = birthday;
        this.cnumber = cnumber;
        this.address = address;
        this.city = city;
        this.cpassword = cpassword;
        NumPlateNO = numPlateNO;
        Fees = fees;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public String getLname() {
        return lname;
    }

    public void setLname(String lname) {
        this.lname = lname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getCnumber() {
        return cnumber;
    }

    public void setCnumber(String cnumber) {
        this.cnumber = cnumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCpassword() {
        return cpassword;
    }

    public void setCpassword(String cpassword) {
        this.cpassword = cpassword;
    }

    public String getNumPlateNO() {
        return NumPlateNO;
    }

    public void setNumPlateNO(String numPlateNO) {
        NumPlateNO = numPlateNO;
    }

    public String getFees() {
        return Fees;
    }

    public void setFees(String fees) {
        Fees = fees;
    }


}
