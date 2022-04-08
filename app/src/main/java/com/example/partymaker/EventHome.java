package com.example.partymaker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Class: EventHome
// Description: This class is the home page of the event. It displays the event name, event date event time, the event description, and the list of users that are attending the event.
public class EventHome extends AppCompatActivity implements InviteDialogFragment.SendMessagesInvite, AdapterInviteList.sendKeyInvite{
    // Initialize variables
    private TextView title;
    private TextView dateEvent;
    private TextView heure;
    private TextView adresse;
    private String id;
    private Map tmp;
    private ListView eList;
    private ArrayList<DataInviteList> dataInviteLists;
    private int i = 0;

    // Initialize FirebaseFirestore instance
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_home);

        Intent intentBase = getIntent();
        this.id = intentBase.getStringExtra("id");

        this.eList = findViewById(R.id.ListViewParticipant);
        dataInviteLists = new ArrayList<>();

        this.dateEvent = findViewById(R.id.textEventDate);
        this.title = findViewById(R.id.textSettings);
        this.heure = findViewById(R.id.textHeureEvent);
        this.adresse = findViewById(R.id.affichageLieu);

        setInfo();
        getDataDB();
    }

    public void getDataDB() {
        db.collection("event")
                .whereEqualTo("id", getId())
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                        for (DocumentSnapshot d : list) {
                            DataInviteList dList = d.toObject(DataInviteList.class);
                            for(int j = 0; j < dList.getCount(); j++) {
                                HashMap<String, String> value = new HashMap<>();
                                value.put(dList.getKey(i), dList.getValue(i));

                                DataInviteList tmp = new DataInviteList(value);

                                i+=1;
                                dataInviteLists.add(tmp);
                            }
                        }
                        AdapterInviteList adapter = new AdapterInviteList(EventHome.this, dataInviteLists, getId());
                        eList.setAdapter(adapter);
                    } else {
                        Toast.makeText(EventHome.this, "Aucun évènement trouvé", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(e -> Toast.makeText(EventHome.this, "Erreur réseau", Toast.LENGTH_SHORT).show());
    }


    public void setInfo(){
        DocumentReference docRef = db.collection("event").document(this.id);
        docRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    setTmp(document.getData());

                    String h = String.valueOf(tmp.get("heure"));
                    String m = String.valueOf(tmp.get("minute"));

                    if(Integer.parseInt(m) < 10){
                        m = "0" + m;
                    }
                    if(Integer.parseInt(h) < 10){
                        h = "0" + h;
                    }

                    getTitlee().setText((CharSequence) tmp.get("nameEvent"));
                    getDateEvent().setText((CharSequence) tmp.get("date"));
                    getHeure().setText(MessageFormat.format("{0}:{1}", h, m));
                    getAdresse().setText((String) (tmp.get("adresse")));
                }
            }
        });
    }

    public void goCalc(View view) {
        Intent intent = new Intent (this, EventCalc.class);
        intent.putExtra("id", this.id);
        startActivity(intent);
    }

    public void goInvite(View view) {
        Intent intent = new Intent (this, EventInvite.class);
        intent.putExtra("id", this.id);
        startActivity(intent);
    }

    public void goSettings(View view) {
        Intent intent = new Intent (this, EventSettings.class);
        intent.putExtra("id", this.id);
        startActivity(intent);
    }

    public void onBtnList(View view){
        Intent intent = new Intent (this, EventList.class);
        startActivity(intent);
    }

    public void setTmp(Map tmp) {
        this.tmp = tmp;
    }

    public TextView getTitlee() {
        return title;
    }

    public TextView getDateEvent() {
        return dateEvent;
    }

    public TextView getHeure() {
        return heure;
    }

    public TextView getAdresse() {
        return adresse;
    }

    public String getId(){
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public void sendMessage(Boolean message) {
        if(message) {
            finish();
            Intent intent = new Intent (this, EventInvite.class);
            intent.putExtra("id", this.id);
            startActivity(intent);
        }
    }

    @Override
    public void sendKey(Boolean message) {
        if(message) {
            finish();
            Intent intent = new Intent (this, EventInvite.class);
            intent.putExtra("id", this.id);
            startActivity(intent);
        }
    }

    @Override
    public void onBackPressed() {
    }
}
