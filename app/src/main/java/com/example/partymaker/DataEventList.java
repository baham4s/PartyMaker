package com.example.partymaker;

// Class: DataEventList
// Description: This class is used to store the data of the events
public class DataEventList {
    // Initialize variables
    private String nameEvent;
    private String date;
    private String id;

    // Constructor: DataEventList
    // Description: This is the constructor of the class.
    public DataEventList(){}

    // Constructor: DataEventList
    // Description: This is the constructor of the class.
    // Parameters: nameEvent - the name of the event
    //             date - the date of the event
    //             id - the id of the event
    public DataEventList(String nameEvent, String date, String id){
        this.nameEvent = nameEvent;
        this.date = date;
        this.id = id;
    }

    // Get the name of the event
    public String getNameEvent() {
        return nameEvent;
    }

    // Get the date of the event
    public String getDate() {
        return date;
    }

    // Get the id of the event
    public String getId() {
        return id;
    }

    // Set the id of the event
    public void setId(String id) {
        this.id = id;
    }

    // Print the data of the event
    @Override
    public String toString() {
        return "DataEventList{" +
                "nameEvent='" + nameEvent + '\'' +
                ", date='" + date + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
