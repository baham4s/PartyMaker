package com.example.partymaker;

import android.content.Context;
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

public class AdapterInviteList extends ArrayAdapter<DataInviteList> {
    private int cpt;
    private String id;
    private sendKeyInvite sendKeyInvite;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public AdapterInviteList(@NonNull Context context, ArrayList<DataInviteList> dataModalArrayList, String id) {
        super(context, 0, dataModalArrayList);
        this.cpt = 0;
        this.id = id;
        sendKeyInvite = (sendKeyInvite) context;
    }

    public interface sendKeyInvite {
        void sendKey(Boolean message);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listitemView = convertView;
        if (listitemView == null) {
            listitemView = LayoutInflater.from(getContext()).inflate(R.layout.item_list, null);
        }

        DataInviteList dataInviteList = getItem(position);
        TextView nameP = listitemView.findViewById(R.id.nameEventTitle);
        TextView mailP = listitemView.findViewById(R.id.dateEventTitle);

        nameP.setText(dataInviteList.getKey(this.cpt));
        mailP.setText(dataInviteList.getValue(this.cpt));

        if(this.cpt < dataInviteList.getCount() - 1) {
            this.cpt += 1;
        }

        listitemView.findViewById(R.id.imageButton).setOnClickListener(v -> {
            db.collection("event").document(this.id).update("invite.".concat(dataInviteList.getKey(this.cpt)), FieldValue.delete());
            sendKeyInvite.sendKey(true);
        });

        return listitemView;
    }

    @Override
    public String toString() {
        return "AdapterInviteList{" +
                "cpt=" + cpt +
                '}';
    }
}
