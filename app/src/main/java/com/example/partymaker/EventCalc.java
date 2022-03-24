package com.example.partymaker;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.Map;

public class EventCalc extends AppCompatActivity {

    private ListView lsv;
    private Button addBtn;
    private ArrayList<Map<String, Object>> list = new ArrayList<java.util.Map<String, Object>>();

    private TextView title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_ardoise);

        this.title = findViewById(R.id.textSettings);
        this.lsv = findViewById(R.id.ListViewAroidse);
        this.addBtn = findViewById(R.id.addBtnArdoise);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        String mailUser = user != null ? user.getEmail() : null;

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
                    this.title.setText((String) (list.get(0)).get("nameEvent"));
                });
    }

//    public Dialog onCreateDialog(Bundle savedInstanceState) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//        // Get the layout inflater
//        LayoutInflater inflater = requireActivity().getLayoutInflater();
//
//        // Inflate and set the layout for the dialog
//        // Pass null as the parent view because its going in the dialog layout
//        // Nom du fichier xml générantla pop up
//        builder.setView(inflater.inflate(R.layout.dialog_signin, null))
//                // Add action buttons
//                .setPositiveButton(R.string.signin, (DialogInterface.OnClickListener) (dialog, id) -> {
//
//                })
//                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        LoginDialogFragment.this.getDialog().cancel();
//                    }
//                });
//        return builder.create();
//    }

//    private Dialog requireActivity() {
//    }


    public void goHome(View view) {
        Intent intent = new Intent (this, EventHome.class);
        startActivity(intent);
    }

    public void goInvite(View view) {
        Intent intent = new Intent (this, EventInvite.class);
        startActivity(intent);
    }

    public void goSettings(View view) {
        Intent intent = new Intent (this, EventSettings.class);
        startActivity(intent);
    }

    public void onBtnHomeClick(View view) {
        Intent intent = new Intent (this, EventList.class);
        startActivity(intent);
    }

//    public void addMoney(View view){
//
//    }
}
