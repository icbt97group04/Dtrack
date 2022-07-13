package com.example.dtrack;

public class Payment {

    String PaymentID;
    String PaymentAmount;
    String DueDate;
    String PaidDate;
    String Method;
    String PaymentStatus;
    String cid;
    String order_id;

    public Payment(String paymentID, String paymentAmount, String dueDate, String paidDate, String method, String paymentStatus, String cid, String order_id) {
        PaymentID = paymentID;
        PaymentAmount = paymentAmount;
        DueDate = dueDate;
        PaidDate = paidDate;
        Method = method;
        PaymentStatus = paymentStatus;
        this.cid = cid;
        this.order_id = order_id;
    }

    public String getPaymentID() {
        return PaymentID;
    }

    public void setPaymentID(String paymentID) {
        PaymentID = paymentID;
    }

    public String getPaymentAmount() {
        return PaymentAmount;
    }

    public void setPaymentAmount(String paymentAmount) {
        PaymentAmount = paymentAmount;
    }

    public String getDueDate() {
        return DueDate;
    }

    public void setDueDate(String dueDate) {
        DueDate = dueDate;
    }

    public String getPaidDate() {
        return PaidDate;
    }

    public void setPaidDate(String paidDate) {
        PaidDate = paidDate;
    }

    public String getMethod() {
        return Method;
    }

    public void setMethod(String method) {
        Method = method;
    }

    public String getPaymentStatus() {
        return PaymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        PaymentStatus = paymentStatus;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }
}
