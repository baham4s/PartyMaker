package com.example.partymaker;

import java.util.Map;

public class DataInviteList {
    private Map<String, String> invite;

    public DataInviteList(){}

    public DataInviteList(Map invite){
        this.invite = invite;
    }

    public int getCount(){
        return invite.size();
    }

    public Map getInvite() {
        return invite;
    }

    public String getKey(int i){
        return invite.keySet().toArray()[i].toString();
    }

    public String getValue(int i){
        return invite.get(getKey(i));
    }

    @Override
    public String toString() {
        return "DataInviteList{" +
                "invite=" + invite +
                '}';
    }
}
