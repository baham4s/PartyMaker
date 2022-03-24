package com.example.partymaker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.MessageFormat;
import java.util.Map;

public class EventHome extends AppCompatActivity {

    private TextView title;
    private TextView dateEvent;
    private TextView heure;
    private TextView adresse;
    private String id;

    private Map tmp;

    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_home);

        Intent intentBase = getIntent();
        this.id = intentBase.getStringExtra("id");

        db = FirebaseFirestore.getInstance();

        this.dateEvent = findViewById(R.id.textEventDate);
        this.title = findViewById(R.id.textSettings);
        this.heure = findViewById(R.id.textHeureEvent);
        this.adresse = findViewById(R.id.affichageLieu);
        setInfo();
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
        startActivity(intent);
    }

    public void goInvite(View view) {
        Intent intent = new Intent (this, EventInvite.class);
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
}
