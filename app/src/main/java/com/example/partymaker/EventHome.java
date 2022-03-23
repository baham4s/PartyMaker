package com.example.partymaker;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.text.MessageFormat;
import java.util.ArrayList;

public class EventHome extends AppCompatActivity {

    private final ArrayList<java.util.Map<String, Object>> list = new ArrayList<>();

    private TextView title;
    private TextView dateEvent;
    private TextView heure;
    private TextView adresse;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_home);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        String mailUser = user != null ? user.getEmail() : null;
        this.dateEvent = findViewById(R.id.textEventDate);
        this.title = findViewById(R.id.textSettings);
        this.heure = findViewById(R.id.textHeureEvent);
        this.adresse = findViewById(R.id.affichageLieu);

        db.collection("event")
                .whereEqualTo("mail", mailUser)
                .get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (QueryDocumentSnapshot document : task.getResult()) {
                            Log.d("TAG", document.getId() + " => " + document.getData());
                            list.add(document.getData());
                        }
                    } else {
                        Log.d("TAG", "Aucun doc trouvé", task.getException());
                    }


                    String h = String.valueOf(list.get(0).get("heure"));
                    String m = String.valueOf(list.get(0).get("minute"));

                    if(Integer.parseInt(m) < 10){
                        m = "0" + m;
                    }
                    if(Integer.parseInt(h) < 10){
                        h = "0" + h;
                    }

                    this.title.setText((String) (list.get(0)).get("nameEvent"));
                    this.dateEvent.setText((String) (list.get(0)).get("date"));
                    this.heure.setText(MessageFormat.format("{0}:{1}", h, m));
                    this.adresse.setText((String) (list.get(0)).get("adresse"));
                });
    }

    public void goHome(View view) {
        Intent intent = new Intent (this, EventHome.class);
        // intent.putExtra();
        startActivity(intent);
    }

    public void goCalc(View view) {
        Intent intent = new Intent (this, EventCalc.class);
        // intent.putExtra();
        startActivity(intent);
    }

    public void goInvite(View view) {
        Intent intent = new Intent (this, EventInvite.class);
        // intent.putExtra();
        startActivity(intent);
    }

    public void goSettings(View view) {
        Intent intent = new Intent (this, EventSettings.class);
        // intent.putExtra();
        startActivity(intent);
    }



}
