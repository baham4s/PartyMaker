package com.example.partymaker;

import android.util.Log;

import java.util.Map;

public class DataArdoiseList {
    private Map<String, String> ardoise;

    public DataArdoiseList(){}

    public DataArdoiseList(Map ardoise){
        this.ardoise = ardoise;
    }

    public int getCount(){
        return ardoise.size();
    }

    public Map getArdoise() {
        return ardoise;
    }

    public String getArdoiseName(int position){
        int i = 0;
        for (String key : ardoise.keySet()) {
            if(i == position)
                return key;
            i++;
        }
        return null;
    }

    public String getArdoisePrix(int position){
        int i = 0;
        for (String key : ardoise.keySet()) {
            if(i == position)
                return ardoise.get(key);
            i++;
        }
        return null;
    }

    @Override
    public String toString() {
        return "DataArdoiseList{" +
                "ardoise=" + ardoise +
                '}';
    }
}
