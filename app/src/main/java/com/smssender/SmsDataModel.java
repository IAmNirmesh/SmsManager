package com.smssender;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class SmsDataModel extends RealmObject {

    @PrimaryKey
    private long id;
    private String number;
    private String text;
    private String dateTime;
    private boolean isRead;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setIsRead(boolean isRead) {
        this.isRead = isRead;
    }
}
