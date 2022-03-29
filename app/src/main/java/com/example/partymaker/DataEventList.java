package com.example.partymaker;

import android.util.Log;

public class DataEventList {

    private String nameEvent;
    private String date;
    private String id;

    public DataEventList(){}

    public DataEventList(String nameEvent, String date, String id){
        this.nameEvent = nameEvent;
        this.date = date;
        this.id = id;
    }

    public String getNameEvent() {
        return nameEvent;
    }

    public String getDate() {
        return date;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "DataEventList{" +
                "nameEvent='" + nameEvent + '\'' +
                ", date='" + date + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
