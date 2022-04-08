package com.example.partymaker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
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

public class AdapterArdoiseList extends ArrayAdapter<DataArdoiseList>{
    private int cpt;
    private String id;
    private sendKey sendKey;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public AdapterArdoiseList(@NonNull Context context, ArrayList<DataArdoiseList> dataModalArrayList, String id) {
        super(context, 0, dataModalArrayList);
        this.cpt = 0;
        this.id = id;
        sendKey = (sendKey) context;
    }

    public interface sendKey {
        void sendKey(Boolean message);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listitemView = convertView;
        if (listitemView == null) {
            listitemView = LayoutInflater.from(getContext()).inflate(R.layout.item_list, null);
        }

        DataArdoiseList dataArdoiseList = getItem(position);
        TextView nameE = listitemView.findViewById(R.id.nameEventTitle);
        TextView depenseE = listitemView.findViewById(R.id.dateEventTitle);

        nameE.setText(dataArdoiseList.getKey(this.cpt));
        depenseE.setText(dataArdoiseList.getValue(this.cpt));

        if(this.cpt < dataArdoiseList.getCount() - 1) {
            this.cpt += 1;
        }

        listitemView.findViewById(R.id.imageButton).setOnClickListener(v -> {
            db.collection("event").document(this.id).update("ardoise.".concat(dataArdoiseList.getKey(this.cpt)), FieldValue.delete());
            sendKey.sendKey(true);
        });

        return listitemView;
    }

    @Override
    public String toString() {
        return "AdapterArdoiseList{" +
                "cpt=" + cpt +
                '}';
    }
}
