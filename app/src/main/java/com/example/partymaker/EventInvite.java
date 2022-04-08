package com.example.partymaker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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

// Class: EventInvite
// Description: This class is used to display the event invite page.
//              It displays the list of users that are invited to the event.
//              It also allows the user to add a new user to the event.
public class EventInvite extends AppCompatActivity implements InviteDialogFragment.SendMessagesInvite, AdapterInviteList.sendKeyInvite {
    // Initialize variables
    private Map tmp;
    private TextView title;
    private String id;
    private ListView eList;
    private ArrayList<DataInviteList> dataInviteLists;
    private Context context;
    private int i = 0;

    // Initialize Firebase variables
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    // Create activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_invite);

        // Get the intent id from the previous activity
        Intent intentBase = getIntent();
        setId(intentBase.getStringExtra("id"));

        // Set the variables corresponding to the layout
        this.eList = findViewById(R.id.ListViewPeople);
        this.title = findViewById(R.id.textSettings);
        Button addBtn = findViewById(R.id.buttonInvite);

        // Initialize the dataInviteLists for the listview
        dataInviteLists = new ArrayList<>();

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
        InviteDialogFragment inviteDialogFragment = new InviteDialogFragment();
        // Set Bundle arguments
        Bundle bundle = new Bundle();
        bundle.putBoolean("notAlertDialog", true);
        inviteDialogFragment.setArguments(bundle);

        // Configure the dialog fragment
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment prev = getSupportFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);
        // Send the id of the event to the dialog fragment
        inviteDialogFragment.setId(getId());

        // Set the listener for the add button
        // When the button is clicked, the dialog fragment is opened
        addBtn.setOnClickListener(v -> inviteDialogFragment.show(ft, "dialog"));

        // Get the list of users invited to the event from the database
        getDataDB();
    }

    // Get the people that are attending the event from the database
    public void getDataDB() {
        db.collection("event")
                .whereEqualTo("id", getId())
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        // Get the data from the database
                        List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                        for (DocumentSnapshot d : list) {
                            // Get the people that are attending the event
                            DataInviteList dList = d.toObject(DataInviteList.class);
                            for(int j = 0; j < dList.getCount(); j++) {
                                // Create temporary variables for one people invited to the event
                                HashMap<String, String> value = new HashMap<>();
                                value.put(dList.getKey(i), dList.getValue(i));
                                // Create a new DataInviteList object with the temporary variables
                                DataInviteList tmp = new DataInviteList(value);

                                i+=1;
                                // Add the user to the list
                                dataInviteLists.add(tmp);
                            }
                        }
                        // Create the adapter for the list
                        AdapterInviteList adapter = new AdapterInviteList(EventInvite.this, dataInviteLists, getId());
                        eList.setAdapter(adapter);
                    } else {
                        Toast.makeText(EventInvite.this, "Aucun évènement trouvé", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(e -> Toast.makeText(EventInvite.this, "Erreur réseau", Toast.LENGTH_SHORT).show());
    }

    // Go to event home page and send the id of the event to the event home page
    public void goHome(View view) {
        Intent intent = new Intent (this, EventHome.class);
        intent.putExtra("id", this.id);
        startActivity(intent);
    }

    // Go to event calc page and send the id of the event to the event calc page
    public void goCalc(View view) {
        Intent intent = new Intent (this, EventCalc.class);
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

    // Communicate with DialogFragment for update the list if the user is invited
    @Override
    public void sendMessage(Boolean message) {
        if(message) {
            finish();
            Intent intent = new Intent (this, EventInvite.class);
            intent.putExtra("id", this.id);
            startActivity(intent);
        }
    }

    // Communicate with AdapterInviteList for update the list if one people is deleted
    @Override
    public void sendKey(Boolean message) {
        if(message) {
            finish();
            Intent intent = new Intent (this, EventInvite.class);
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
