package com.example.dtrack;

public class Client {


    private static String ClientId;
    private static String ClientName;
    private static String ClientAddress;
    //expand address into parts ---  by pav
    private static String ClientEmail;
    private static String Cbirthday;
    private static String Cgender;
    private static String NumPlateNO;

    public Client() {
    }

    public Client(String clientId, String clientName, String clientAddress, String clientEmail, String cbirthday, String cgender, String numPlateNO) {
        ClientId = clientId;
        ClientName = clientName;
        ClientAddress = clientAddress;
        ClientEmail = clientEmail;
        Cbirthday = cbirthday;
        Cgender = cgender;
        NumPlateNO = numPlateNO;
    }

    public static String getClientId() {
        return ClientId;
    }

    public static void setClientId(String clientId) {
        ClientId = clientId;
    }

    public static String getClientName() {
        return ClientName;
    }

    public static void setClientName(String clientName) {
        ClientName = clientName;
    }

    public static String getClientAddress() {
        return ClientAddress;
    }

    public static void setClientAddress(String clientAddress) {
        ClientAddress = clientAddress;
    }

    public static String getClientEmail() {
        return ClientEmail;
    }

    public static void setClientEmail(String clientEmail) {
        ClientEmail = clientEmail;
    }

    public static String getCbirthday() {
        return Cbirthday;
    }

    public static void setCbirthday(String cbirthday) {
        Cbirthday = cbirthday;
    }

    public static String getCgender() {
        return Cgender;
    }

    public static void setCgender(String cgender) {
        Cgender = cgender;
    }

    public static String getNumPlateNO() {
        return NumPlateNO;
    }

    public static void setNumPlateNO(String numPlateNO) {
        NumPlateNO = numPlateNO;
    }
}
