package com.example.partymaker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class EventCalc extends AppCompatActivity implements ArdoiseDialogFragment.SendMessages, AdapterArdoiseList.sendKey {
    private Map tmp;
    private TextView title;
    private TextView totalPrix;
    private String id;
    private String key;

    private ListView eList;
    private String mailUser;

    private ArrayList<DataArdoiseList> dataArdoiseList;

    private Context context;

    private int i = 0;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    FirebaseUser user = mAuth.getCurrentUser();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_ardoise);

        Intent intentBase = getIntent();
        setId(intentBase.getStringExtra("id"));

        this.eList = findViewById(R.id.ListViewAroidse);
        dataArdoiseList = new ArrayList<>();

        this.title = findViewById(R.id.textSettings);
        Button addBtn = findViewById(R.id.addBtnArdoise);
        this.totalPrix = findViewById(R.id.totalArdoise);
        setMailUser(user != null ? user.getEmail() : null);

        // Récupération du titre
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

        ArdoiseDialogFragment ardoiseDialogFragment = new ArdoiseDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putBoolean("notAlertDialog", true);

        ardoiseDialogFragment.setArguments(bundle);

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment prev = getSupportFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        ardoiseDialogFragment.setId(getId());

        // click sur btnAjouter
        addBtn.setOnClickListener(v -> ardoiseDialogFragment.show(ft, "dialog"));

        getDataDB();
    }

    public void getDataDB() {
        db.collection("event")
                .whereEqualTo("id", getId())
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                        Log.d("TAG", "getDataDB: " + list.size());
                        for (DocumentSnapshot d : list) {
                            DataArdoiseList dList = d.toObject(DataArdoiseList.class);
                            Log.d("TAG", "getDataDB: " + dList.toString());
                            for(int j = 0; j < dList.getCount(); j++) {
                                HashMap<String, String> value = new HashMap<>();
                                value.put(dList.getKey(i), dList.getValue(i));

                                DataArdoiseList tmp = new DataArdoiseList(value);

                                i+=1;
                                dataArdoiseList.add(tmp);
                                this.totalPrix.setText(String.valueOf(dList.getTotalPrix()).concat("€"));
                            }
                        }
                        AdapterArdoiseList adapter = new AdapterArdoiseList(EventCalc.this, dataArdoiseList, getId());
                        eList.setAdapter(adapter);
                    } else {
                        Toast.makeText(EventCalc.this, "Aucun évènement trouvé", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(e -> Toast.makeText(EventCalc.this, "Erreur réseau", Toast.LENGTH_SHORT).show());
    }

    public void goHome(View view) {
        Intent intent = new Intent (this, EventHome.class);
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

    public void onBtnHomeClick(View view) {
        Intent intent = new Intent (this, EventList.class);
        startActivity(intent);
    }




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

    public void setMailUser(String mailUser) {
        this.mailUser = mailUser;
    }

    @Override
    public void sendMessage(Boolean message) {
        if(message) {
            finish();
            Intent intent = new Intent (this, EventCalc.class);
            intent.putExtra("id", this.id);
            startActivity(intent);
        }
    }

    @Override
    public void sendKey(Boolean message) {
        if(message) {
            finish();
            Intent intent = new Intent (this, EventCalc.class);
            intent.putExtra("id", this.id);
            startActivity(intent);
        }
    }

    @Override
    public void onBackPressed() {
    }
}
