package com.example.partymaker;

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

import java.util.ArrayList;

public class AdapterEventList extends ArrayAdapter<DataEventList> {
    public AdapterEventList(@NonNull Context context, ArrayList<DataEventList> dataModalArrayList) {
        super(context, 0, dataModalArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listitemView = convertView;
        if (listitemView == null) {
            listitemView = LayoutInflater.from(getContext()).inflate(R.layout.item_list, parent, false);
        }

        DataEventList dataEventList = getItem(position);
//        Log.d("AdapterEventList", "getView: " + dataEventList.toString());

        TextView nameE = listitemView.findViewById(R.id.nameEventTitle);
        TextView dateE = listitemView.findViewById(R.id.dateEventTitle);

        nameE.setText(dataEventList.getNameEvent());
//        Log.d("nameEvent", dataEventList.getNameEvent());
        dateE.setText(dataEventList.getDate());
//        Log.d("dateEvent", dataEventList.getDate());

        listitemView.setOnClickListener(v -> {
            Intent openE = new Intent(v.getContext(), EventHome.class);
            openE.putExtra("id", dataEventList.getId());
            v.getContext().startActivity(openE);
        });

//        Log.d("AdapterEventList", "return");
        return listitemView;
    }
}
