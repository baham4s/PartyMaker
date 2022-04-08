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
// Class: EventList
// Description: This class is used to display the list of events that the user has created.
//              The user can click on an event to view the details of the event.
public class EventList extends AppCompatActivity {
    // Initialize variables
    private ListView eList;
    private String mailUser;
    private ArrayList<DataEventList> dataEventLists;

    // Initialize Firebase variables
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser user = mAuth.getCurrentUser();

    // Create the activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);

        // Set the variables corresponding to the layout
        this.eList = findViewById(R.id.ListViewEvent);
        dataEventLists = new ArrayList<>();
        setMailUser(user != null ? user.getEmail() : null);
        // Get the data from the database
        getDataDB();
    }

    // Get the people that are attending the event from the database
    private void getDataDB() {
        db.collection("event")
                .whereEqualTo("mail", mailUser)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        // Get the data from the database
                        List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                        for (DocumentSnapshot d : list) {
                            // Get the people that are attending the event
                            DataEventList dList = d.toObject(DataEventList.class);
                            // Add the user to the list
                            dataEventLists.add(dList);
                        }
                        // Create the adapter for the list
                        AdapterEventList adapter = new AdapterEventList(EventList.this, dataEventLists);
                        eList.setAdapter(adapter);
                    } else {
                        Toast.makeText(EventList.this, "Aucun évènement trouvé", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(e -> Toast.makeText(EventList.this, "Erreur réseau", Toast.LENGTH_SHORT).show());
    }

    // Go back to the home page
    public void onBtnHomeClick(View view) {
        Intent intent = new Intent (this, Home.class);
        startActivity(intent);
    }

    // Delete the event from the database
    public void deleteEvent(View view) {
        // Get the position of the event on which the user clicked
        int position = eList.getPositionForView((View) view.getParent());
        // Get the event from the list at the position
        DataEventList dataEventList = dataEventLists.get(position);
        db.collection("event")
                .document(dataEventList.getId())
                .delete()
                .addOnSuccessListener(aVoid -> {
                    Toast.makeText(EventList.this, "Evènement supprimé", Toast.LENGTH_SHORT).show();
                    // Delete the event from the list
                    dataEventLists.remove(position);
                    // Update the adapter with the new list without the deleted event
                    AdapterEventList adapter = new AdapterEventList(EventList.this, dataEventLists);
                    eList.setAdapter(adapter);
                }).addOnFailureListener(e -> Toast.makeText(EventList.this, "Erreur réseau", Toast.LENGTH_SHORT).show());
    }

    // Set the mail of the user
    public void setMailUser(String mailUser) {
        this.mailUser = mailUser;
    }

}