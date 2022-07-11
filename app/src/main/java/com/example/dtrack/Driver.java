package com.example.dtrack;

public class Driver {

    private  static String DriverId;
    private  static String DriverName;
    private  static String DriverAddress;
    //expand address into parts ---  by pav
    private  static String DriverEmail;
    private  static String DriverMobileNumber;

    public Driver() {
    }

    public Driver(String driverId,String driverName,String driverAddress,String driverEmail,String driverMobileNumber) {
        DriverId = driverId;
        DriverName = driverName;
        DriverAddress = driverAddress;
        DriverEmail = driverEmail;
        DriverMobileNumber = driverMobileNumber;
    }

    public static String getDriverId() {
        return DriverId;
    }

    public static void setDriverId(String driverId) {
        DriverId = driverId;
    }

    public static String getDriverName() {
        return DriverName;
    }

    public static void setDriverName(String driverName) {
        DriverName = driverName;
    }

    public static String getDriverAddress() {
        return DriverAddress;
    }

    public static void setDriverAddress(String driverAddress) {
        DriverAddress = driverAddress;
    }

    public static String getDriverEmail() {
        return DriverEmail;
    }

    public static void setDriverEmail(String driverEmail) {
        DriverEmail = driverEmail;
    }

    public static String getDriverMobileNumber() {
        return DriverMobileNumber;
    }

    public static void setDriverMobileNumber(String driverMobileNumber ) {
        DriverMobileNumber = driverMobileNumber;
    }
}
