package com.example.partymaker;

import java.util.Map;

// Class: DataArdoiseList
// Description: This class is used to store the data of the ardoise list.
public class DataArdoiseList {
    // Initialize the variables.
    private Map<String, String> ardoise;

    // Constructor: DataArdoiseList
    // Description: This is the constructor of the class.
    public DataArdoiseList(){}

    // Constructor: DataArdoiseList
    // Description: This is the constructor of the class.
    // Parameters: ardoise - the ardoise list.
    public DataArdoiseList(Map ardoise){
        this.ardoise = ardoise;
    }

    // Get the size of the ardoise list.
    public int getCount(){
        return ardoise.size();
    }

    // Get the ardoise list.
    public Map getArdoise() {
        return ardoise;
    }

    // Get one key at position i of the ardoise list.
    public String getKey(int i){
        return ardoise.keySet().toArray()[i].toString();
    }

    // Get one value at position i of the ardoise list.
    public String getValue(int i){
        return ardoise.get(getKey(i));
    }

    // Get the total expenses of the ardoise list.
    public float getTotalPrix(){
        float total = 0;
        for (int i = 0; i < ardoise.size(); i++){
            total += Float.parseFloat(getValue(i));
        }
        return total;
    }

    // Print content of the ardoise list.
    @Override
    public String toString() {
        return "DataArdoiseList{" +
                "ardoise=" + ardoise +
                '}';
    }
}
