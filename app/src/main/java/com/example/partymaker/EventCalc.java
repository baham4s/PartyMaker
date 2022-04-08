package com.example.partymaker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Class: EventCalc
// Description: This class is used to calculate the event cost and display it to the user.
//              It also allows the user to add an expense.

public class EventCalc extends AppCompatActivity implements ArdoiseDialogFragment.SendMessages, AdapterArdoiseList.sendKey {
    // Initialize variables
    private Map tmp;
    private TextView title;
    private TextView totalPrix;
    private String id;
    private ListView eList;
    private ArrayList<DataArdoiseList> dataArdoiseList;
    private Context context;
    private int i = 0;

    // Initialize Firebase variables
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    // Create activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_ardoise);

        // Get the intent id from the previous activity
        Intent intentBase = getIntent();
        setId(intentBase.getStringExtra("id"));

        // Set the variables corresponding to the layout
        this.eList = findViewById(R.id.ListViewAroidse);
        this.title = findViewById(R.id.textSettings);
        Button addBtn = findViewById(R.id.addBtnArdoise);
        this.totalPrix = findViewById(R.id.totalArdoise);

        // Initialize the dataInviteLists for the listview
        dataArdoiseList = new ArrayList<>();

        // Set the title of the page
        DocumentReference docRef = db.collection("event").document(getId());
        docRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    setTmp(document.getData());
                    getTitlee().setText((CharSequence) tmp.get("nameEvent"));
                }
            }
        });

        // Create the dialog fragment
        ArdoiseDialogFragment ardoiseDialogFragment = new ArdoiseDialogFragment();
        // Set Bundle arguments
        Bundle bundle = new Bundle();
        bundle.putBoolean("notAlertDialog", true);
        ardoiseDialogFragment.setArguments(bundle);

        // Configure the dialog fragment
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment prev = getSupportFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        // Send the id of the event to the dialog fragment
        ardoiseDialogFragment.setId(getId());

        // Set the listener for the add button
        // When the button is clicked, the dialog fragment is opened
        addBtn.setOnClickListener(v -> ardoiseDialogFragment.show(ft, "dialog"));

        // Get the expense of the event from the database
        getDataDB();
    }

    // Get the expense of the event from the database
    public void getDataDB() {
        db.collection("event")
                .whereEqualTo("id", getId())
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        // Get the data from the database
                        List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                        Log.d("TAG", "getDataDB: " + list.size());
                        for (DocumentSnapshot d : list) {
                            // Get the expense of the event
                            DataArdoiseList dList = d.toObject(DataArdoiseList.class);
                            for(int j = 0; j < dList.getCount(); j++) {
                                // Create temporary variables for one people invited to the event
                                HashMap<String, String> value = new HashMap<>();
                                value.put(dList.getKey(i), dList.getValue(i));
                                // Create a new DataArdoiseList object with the temporary variables
                                DataArdoiseList tmp = new DataArdoiseList(value);

                                i+=1;
                                // Add the user to the list
                                dataArdoiseList.add(tmp);
                                // Update the total price and set it to the textview
                                this.totalPrix.setText(String.valueOf(dList.getTotalPrix()).concat("€"));
                            }
                        }
                        // Create the adapter for the list
                        AdapterArdoiseList adapter = new AdapterArdoiseList(EventCalc.this, dataArdoiseList, getId());
                        eList.setAdapter(adapter);
                    } else {
                        Toast.makeText(EventCalc.this, "Aucun évènement trouvé", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(e -> Toast.makeText(EventCalc.this, "Erreur réseau", Toast.LENGTH_SHORT).show());
    }

    // Go to event home page and send the id of the event to the event home page
    public void goHome(View view) {
        Intent intent = new Intent (this, EventHome.class);
        intent.putExtra("id", this.id);
        startActivity(intent);
    }

    // Go to event invite page and send the id of the event to the event invite page
    public void goInvite(View view) {
        Intent intent = new Intent (this, EventInvite.class);
        intent.putExtra("id", this.id);
        startActivity(intent);
    }

    // Go to event settings page and send the id of the event to the event settings page
    public void goSettings(View view) {
        Intent intent = new Intent (this, EventSettings.class);
        intent.putExtra("id", this.id);
        startActivity(intent);
    }

    // Go back to the list of events page
    public void onBtnHomeClick(View view) {
        Intent intent = new Intent (this, EventList.class);
        startActivity(intent);
    }

    // Communicate with DialogFragment for update the list if the expense is added
    @Override
    public void sendMessage(Boolean message) {
        if(message) {
            finish();
            Intent intent = new Intent (this, EventCalc.class);
            intent.putExtra("id", this.id);
            startActivity(intent);
        }
    }

    // Communicate with AdapterInviteList for update the list if one exepense is deleted
    @Override
    public void sendKey(Boolean message) {
        if(message) {
            finish();
            Intent intent = new Intent (this, EventCalc.class);
            intent.putExtra("id", this.id);
            startActivity(intent);
        }
    }

    // Delete android back button because he isn't useful and provide bugs
    @Override
    public void onBackPressed() {
        // Do nothing
    }

    // Getter and setter
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public TextView getTitlee() {
        return title;
    }

    public void setTmp(Map tmp) {
        this.tmp = tmp;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }
}
