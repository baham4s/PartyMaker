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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class EventCalc extends AppCompatActivity implements ArdoiseDialogFragment.SendMessages{
    private Map tmp;
    private TextView title;
    private Button addBtn;
    private String id;

    private ListView eList;
    private String mailUser;

    private ArrayList<DataArdoiseList> dataArdoiseList;

    private String msgLibelle = "";
    private String msgPrix = "";

    private Context context;

    FirebaseFirestore db = FirebaseFirestore.getInstance();;
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
        this.addBtn = findViewById(R.id.addBtnArdoise);
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

        // click sur btnAjouter
        addBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
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
                ardoiseDialogFragment.show(ft, "dialog");
            }
        });

        getDataDB();
    }

    private void getDataDB() {
        db.collection("event")
                .whereEqualTo("id", getId())
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                        for (DocumentSnapshot d : list) {
                            DataArdoiseList dList = d.toObject(DataArdoiseList.class);
                            dataArdoiseList.add(dList);
                        }
                        AdapterArdoiseList adapter = new AdapterArdoiseList(EventCalc.this, dataArdoiseList);
                        eList.setAdapter(adapter);
                    } else {
                        Toast.makeText(EventCalc.this, "Aucun évènement trouvé", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(e -> {
            Toast.makeText(EventCalc.this, "Erreur réseau", Toast.LENGTH_SHORT).show();
        });

//        db.collection("event")
//                .whereEqualTo("id", getId())
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//                            for (QueryDocumentSnapshot document : task.getResult()) {
//                                HashMap dList = (HashMap) document.get("ardoise");
//                                Log.d("TEsteeee", dList.toString());
//                            }
//                            AdapterArdoiseList adapter = new AdapterArdoiseList(EventCalc.this, dataArdoiseList);
//                            eList.setAdapter(adapter);
//                        } else {
//                            Log.d("TAG", "Error getting documents: ", task.getException());
//                        }
//                    }
//                });
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

    public void setTitle(TextView title) {
        this.title = title;
    }

    public Map getTmp() {
        return tmp;
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

    public String getMsgLibelle() {
        return msgLibelle;
    }

    public void setMsgLibelle(String msgLibelle) {
        this.msgLibelle = msgLibelle;
    }

    public String getMsgPrix() {
        return msgPrix;
    }

    public void setMsgPrix(String msgPrix) {
        this.msgPrix = msgPrix;
    }

    @Override
    public void iAmMSG(String msg1, String msg2) {
        setMsgLibelle(msg1);
        setMsgPrix(msg2);
    }

    public String getMailUser() {
        return mailUser;
    }

    public void setMailUser(String mailUser) {
        this.mailUser = mailUser;
    }
}
