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

public class AdapterList extends ArrayAdapter<DataList> {
    public AdapterList(@NonNull Context context, ArrayList<DataList> dataModalArrayList) {
        super(context, 0, dataModalArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View listitemView = convertView;
        if (listitemView == null) {
            listitemView = LayoutInflater.from(getContext()).inflate(R.layout.item_list, parent, false);
        }

        DataList dataList = getItem(position);

        TextView nameE = listitemView.findViewById(R.id.nameEventTitle);
        TextView dateE = listitemView.findViewById(R.id.dateEventTitle);

        nameE.setText(dataList.getNameEvent());
        dateE.setText(dataList.getDate());

        listitemView.setOnClickListener(v -> {
            Intent openE = new Intent(v.getContext(), EventHome.class);
            openE.putExtra("id", dataList.getId());
            v.getContext().startActivity(openE);
        });
        return listitemView;
    }
}
