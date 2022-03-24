package com.example.partymaker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

public class EventList extends AppCompatActivity {

    private ListView eList;
    private String mailUser;

    private ArrayList<DataList> dataLists;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser user = mAuth.getCurrentUser();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);

        this.eList = findViewById(R.id.ListViewEvent);
        dataLists = new ArrayList<>();

        setMailUser(user != null ? user.getEmail() : null);

        getDataDB();
    }

    private void getDataDB() {
        db.collection("event")
                .whereEqualTo("mail", mailUser)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                        for (DocumentSnapshot d : list) {
                            DataList dList = d.toObject(DataList.class);
                            dataLists.add(dList);
                        }
                        AdapterList adapter = new AdapterList(EventList.this, dataLists);
                        eList.setAdapter(adapter);
                    } else {
                        Toast.makeText(EventList.this, "Aucun évènement trouvé", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(e -> {
                    Toast.makeText(EventList.this, "Erreur réseau", Toast.LENGTH_SHORT).show();
                });
    }

    public void onBtnHomeClick(View view) {
        Intent intent = new Intent (this, Home.class);
        startActivity(intent);
    }

    public void onBtnBack(View view) {
        Intent intent = new Intent (this, Home.class);
        startActivity(intent);
    }

    public void onBtnNext(View view) {
        Intent intent = new Intent (this, EventHome.class);
        startActivity(intent);
    }

    public void setMailUser(String mailUser) {
        this.mailUser = mailUser;
    }
}