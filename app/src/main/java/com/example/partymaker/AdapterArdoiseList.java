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
import java.util.Map;

public class AdapterArdoiseList extends ArrayAdapter<DataArdoiseList> {
    private int cpt;

    public AdapterArdoiseList(@NonNull Context context, ArrayList<DataArdoiseList> dataModalArrayList) {
        super(context, 0, dataModalArrayList);
        this.cpt = 0;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listitemView = convertView;
        if (listitemView == null) {
            listitemView = LayoutInflater.from(getContext()).inflate(R.layout.item_list, parent, false);
        }


        Map map = getItem(position).getArdoise();
        DataArdoiseList dataArdoiseList = getItem(position);

//        Log.d("IAEHBOFIZFB", dataArdoiseList.toString());
        Log.d("IAEHBOFIZFB", map.toString());

        TextView nameE = listitemView.findViewById(R.id.nameEventTitle);
        TextView depenseE = listitemView.findViewById(R.id.dateEventTitle);

        nameE.setText(dataArdoiseList.getArdoiseName(this.cpt));
        depenseE.setText(dataArdoiseList.getArdoisePrix(this.cpt));
        this.cpt += 1;


//        listitemView.setOnClickListener(v -> {
//            Intent openE = new Intent(v.getContext(), EventCalc.class);
//            openE.putExtra("id", dataArdoiseList.getId());
//            v.getContext().startActivity(openE);
//        });
        return listitemView;
    }

    @Override
    public String toString() {
        return "AdapterArdoiseList{" +
                "cpt=" + cpt +
                '}';
    }
}
