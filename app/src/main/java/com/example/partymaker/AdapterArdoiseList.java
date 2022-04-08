package com.example.partymaker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

// Class: AdapterArdoiseList
// Description: Adapter for the list of the ardoises
public class AdapterArdoiseList extends ArrayAdapter<DataArdoiseList>{
    // Initialize variables
    private int cpt;
    private String id;
    private sendKey sendKey;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    // Constructor of the class AdapterArdoiseList
    public AdapterArdoiseList(@NonNull Context context, ArrayList<DataArdoiseList> dataModalArrayList, String id) {
        super(context, 0, dataModalArrayList);
        this.cpt = 0;
        this.id = id;
        sendKey = (sendKey) context;
    }

    // Interface for send signal for update the activity
    public interface sendKey {
        void sendKey(Boolean message);
    }

    // Set the view of the list of the ardoises in the activity ArdoiseList
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listitemView = convertView;
        if (listitemView == null) {
            listitemView = LayoutInflater.from(getContext()).inflate(R.layout.item_list, null);
        }

        // Get the current dataModal in the list of the ardoises in the activity ArdoiseList
        DataArdoiseList dataArdoiseList = getItem(position);

        // Set the variables corresponding to the layout item_list
        TextView nameE = listitemView.findViewById(R.id.nameEventTitle);
        TextView depenseE = listitemView.findViewById(R.id.dateEventTitle);

        // Set the text of the variables corresponding to the layout item_list
        nameE.setText(dataArdoiseList.getKey(this.cpt));
        depenseE.setText(dataArdoiseList.getValue(this.cpt));

        // Increment the counter
        if(this.cpt < dataArdoiseList.getCount() - 1) {
            this.cpt += 1;
        }

        // Set the listener of the button delete and delete the ardoise item in the database
        listitemView.findViewById(R.id.imageButton).setOnClickListener(v -> {
            db.collection("event").document(this.id).update("ardoise.".concat(dataArdoiseList.getKey(this.cpt)), FieldValue.delete());
            sendKey.sendKey(true);
        });

        return listitemView;
    }

    // Print the content the adapter variable
    @Override
    public String toString() {
        return "AdapterArdoiseList{" +
                "cpt=" + cpt +
                '}';
    }
}
