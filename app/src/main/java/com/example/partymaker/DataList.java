package com.example.partymaker;

public class DataList {

    private String nameEvent;
    private String date;
    private String id;

    public DataList(){}

    public DataList(String nameEvent, String date, String id){
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
}
