package com.example.partymaker;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.Source;

import java.text.MessageFormat;
import java.util.Map;
import java.util.Objects;

// TODO: mettre en surbrillance des champs les informations de la BDD
// TODO: implémenter l'heure
// TODO: Mettre a jour la date selon le choix user

public class EventSettings extends AppCompatActivity{

    private TextView title;
    private EditText setNameEvent;

    private DatePickerDialog datePickerDialog;
    private Button dateButton;

    private TimePicker timePicker;
    private TextView textViewTime;

    private Map tmp;
    private String date;

    FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_settings);

        db = FirebaseFirestore.getInstance();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        this.setNameEvent = findViewById(R.id.editNameEvent);
        this.title = findViewById(R.id.textSettings);
        this.dateButton = findViewById(R.id.datePickerButton);

        DocumentReference docRef = db.collection("event").document("1Uk8QBlLn5nLeaXBw4zH");
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                Log.d("CICIICICI", "LALLALALLALALALAL");
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        setTmp(document.getData());
                        getTitlee().setText((CharSequence) tmp.get("nameEvent"));
                    }
                }
            }
        });

        this.dateButton.setHint(getTodaysDate());
        initDatePicker();

        this.datePickerDialog.setOnDateSetListener((datePicker, i, i1, i2) -> date = makeDateSring(i2, i1, i));

//        this.textViewTime = this.findViewById(R.id.textView_time);
//        this.timePicker = this.findViewById(R.id.timePicker);


//        this.timePicker.setOnTimeChangedListener((view, hourOfDay, minute) -> {
//            if(minute < 10){
//                textViewTime.setText(MessageFormat.format("{0} : 0{1}", hourOfDay, minute));
//            }else{
//                textViewTime.setText(MessageFormat.format("{0} : {1}", hourOfDay, minute));
//            }
//        });

    }


    private String getTodaysDate() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        month = month + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return makeDateSring(day, month, year);
    }

    private void initDatePicker(){
        DatePickerDialog.OnDateSetListener dateSetListener = (datePicker, year, month, day) -> {
            month = month + 1;
            String date = makeDateSring(day, month, year);
            System.out.print("Ecriture de la date ...\n");
            dateButton.setHint(date);
        };

        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
    }

    private String makeDateSring(int day, int month, int year) {
        if(day < 10){
            return  "0" + day + " " + getMonthFormat(month) + " " + year;
        }else{
            return  day + " " + getMonthFormat(month) + " " + year;
        }
    }

    private String getMonthFormat(int month) {
        switch(month){
            case 1:
                return "Janvier";
            case 2:
                return "Fevrier";
            case 3:
                return "Mars";
            case 4:
                return "Avril";
            case 5:
                return "Mai";
            case 6:
                return "Juin";
            case 7:
                return "Juillet";
            case 8:
                return "Aout";
            case 9:
                return "Septembre";
            case 10:
                return "Octobre";
            case 11:
                return "Novembre";
            case 12:
                return "Décembre";
            default:
                return "JAN";
        }

    }

    public void openDatePicker(View view){
        datePickerDialog.show();
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

    public void onBtnConfirmClick(View view){
        Intent intent = new Intent (this, EventSettings.class);

        DocumentReference tmp = db.collection("event").document("1Uk8QBlLn5nLeaXBw4zH");
        if(!this.setNameEvent.getText().toString().equals("")) {
            tmp
                    .update("nameEvent", this.setNameEvent.getText().toString())
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(EventSettings.this, "Mise a jour", Toast.LENGTH_SHORT).show();
                            startActivity(intent);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(EventSettings.this, "Pas mise a jour", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
        if(date != null){
            tmp
                    .update("date", date)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(EventSettings.this, "Mise a jour", Toast.LENGTH_SHORT).show();
                            startActivity(intent);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(EventSettings.this, "Pas mise a jour", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    public void onBtnBackHome(View view) {
        Intent intent = new Intent (this, Home.class);
        startActivity(intent);
    }

    public Map getTmp() {
        return tmp;
    }

    public void setTmp(Map tmp) {
        this.tmp = tmp;
    }

    public void setTitle(TextView title) {
        this.title = title;
    }

    public TextView getTitlee() {
        return title;
    }
}


//        if(this.timePicker.getHour()) {
//                tmp
//                .update("nameEvent", this.setNameEvent.getText().toString())
//                .addOnSuccessListener(new OnSuccessListener<Void>() {
//@Override
//public void onSuccess(Void aVoid) {
//        Toast.makeText(EventSettings.this, "Mise a jour", Toast.LENGTH_SHORT).show();
//        startActivity(intent);
//        }
//        })
//        .addOnFailureListener(new OnFailureListener() {
//@Override
//public void onFailure(@NonNull Exception e) {
//        Toast.makeText(EventSettings.this, "Pas mise a jour", Toast.LENGTH_SHORT).show();
//        }
//        });
//        }