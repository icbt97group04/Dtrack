package com.example.dtrack;

import org.w3c.dom.Text;

import java.time.format.DateTimeFormatter;

public class Notification {

    String ID;
    String Topic;
    String Content;
    String UserType;
    String date;

    public Notification(String ID, String topic, String content, String userType, String date) {
        this.ID = ID;
        Topic = topic;
        Content = content;
        UserType = userType;
        this.date = date;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getTopic() {
        return Topic;
    }

    public void setTopic(String topic) {
        Topic = topic;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getUserType() {
        return UserType;
    }

    public void setUserType(String userType) {
        UserType = userType;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
