package com.example.partymaker;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

// Class: AdapterEventList
// Description: Adapter for the event list
public class AdapterEventList extends ArrayAdapter<DataEventList> {
    // Constructor for the adapter event list class
    public AdapterEventList(@NonNull Context context, ArrayList<DataEventList> dataModalArrayList) {
        super(context, 0, dataModalArrayList);
    }

    // Set the view for the event list
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listitemView = convertView;
        if (listitemView == null) {
            listitemView = LayoutInflater.from(getContext()).inflate(R.layout.item_list, parent, false);
        }
        // Get the current dataModal in the list of event in the activity EventList
        DataEventList dataEventList = getItem(position);

        // Set the variables corresponding to the layout item_list
        TextView nameE = listitemView.findViewById(R.id.nameEventTitle);
        TextView dateE = listitemView.findViewById(R.id.dateEventTitle);

        // Set the text of the variables corresponding to the layout item_list
        nameE.setText(dataEventList.getNameEvent());
        dateE.setText(dataEventList.getDate());

        // Set the listener of the button delete and delete the event item in the database
        listitemView.setOnClickListener(v -> {
            Intent openE = new Intent(v.getContext(), EventHome.class);
            openE.putExtra("id", dataEventList.getId());
            v.getContext().startActivity(openE);
        });

        return listitemView;
    }
}
