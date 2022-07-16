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

    public static String getClientName() {
        return ClientName;
    }

    public static String getClientAddress() {
        return ClientAddress;
    }

    public static String getClientEmail() {
        return ClientEmail;
    }

    public static String getCbirthday() {
        return Cbirthday;
    }

    public static String getCgender() {
        return Cgender;
    }

    public static String getNumPlateNO() {
        return NumPlateNO;
    }


}
