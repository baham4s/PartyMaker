package com.example.partymaker;

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

    public String getKey(int i){
        return ardoise.keySet().toArray()[i].toString();
    }

    public String getValue(int i){
        return ardoise.get(getKey(i));
    }

    public float getTotalPrix(){
        float total = 0;
        for (int i = 0; i < ardoise.size(); i++){
            total += Float.parseFloat(getValue(i));
        }
        return total;
    }
    @Override
    public String toString() {
        return "DataArdoiseList{" +
                "ardoise=" + ardoise +
                '}';
    }
}
