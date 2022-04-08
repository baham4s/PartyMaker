package com.example.partymaker;

import java.util.Map;

// Class: DataInviteList
// Description: This class is used to store the data of the invite list.
public class DataInviteList {
    // Initialize the variables.
    private Map<String, String> invite;

    // Constructor: DataInviteList
    // Description: This is the constructor of the class.
    public DataInviteList(){}

    // Constructor: DataInviteList
    // Description: This is the constructor of the class.
    // Parameters: invite - the invite list.
    public DataInviteList(Map invite){
        this.invite = invite;
    }

    // Get the size of the invite list.
    public int getCount(){
        return invite.size();
    }

    // Get the invite list.
    public Map getInvite() {
        return invite;
    }

    // Get one key at position i of the invite list.
    public String getKey(int i){
        return invite.keySet().toArray()[i].toString();
    }

    // Get one value at position i of the invite list.
    public String getValue(int i){
        return invite.get(getKey(i));
    }

    // Print content of the invite list.
    @Override
    public String toString() {
        return "DataInviteList{" +
                "invite=" + invite +
                '}';
    }
}
