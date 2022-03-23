package com.example.partymaker;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Map;

public class EventSettings extends AppCompatActivity{

    private TextView title;
    private EditText nameEvent;

    private DatePickerDialog datePickerDialog;
    private Button dateButton;

    private TimePicker timePicker;

    private Map tmp;
    private String date;

    FirebaseFirestore db;

    private int tmpHour;
    private int tmpMin;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_settings);

        db = FirebaseFirestore.getInstance();

        this.nameEvent = findViewById(R.id.editNameEvent);
        this.title = findViewById(R.id.textSettings);
        this.dateButton = findViewById(R.id.datePickerButton);
        this.timePicker = this.findViewById(R.id.timePicker);

        DocumentReference docRef = db.collection("event").document("1Uk8QBlLn5nLeaXBw4zH");
        docRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                DocumentSnapshot document = task.getResult();
                if (document.exists()) {
                    setTmp(document.getData());
                    getTitlee().setText((CharSequence) tmp.get("nameEvent"));
                    getNameEvent().setHint((CharSequence) tmp.get("nameEvent"));
                    getDateButton().setHint((CharSequence) tmp.get("date"));
                    setTmpHour(Integer.parseInt(String.valueOf(tmp.get("heure"))));
                    setTmpMin(Integer.parseInt(String.valueOf(tmp.get("minute"))));
                    getTimePicker().setHour(Integer.parseInt(String.valueOf(tmp.get("heure"))));
                    getTimePicker().setMinute(Integer.parseInt(String.valueOf(tmp.get("minute"))));
                }
            }
        });

        initDatePicker();

        this.datePickerDialog.setOnDateSetListener((datePicker, i, i1, i2) -> {
            date = makeDateSring(i2, i1, i);
            getDateButton().setHint(date);
        });
        this.timePicker.setOnTimeChangedListener((view, hourOfDay, minute) -> {
            getTimePicker().setHour(hourOfDay);
            getTimePicker().setMinute(minute);
        });

    }

    private void initDatePicker(){
        DatePickerDialog.OnDateSetListener dateSetListener = (datePicker, year, month, day) -> {
            month = month + 1;
            String date = makeDateSring(day, month, year);
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
        switch(month + 1){
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
        Intent intent = new Intent (this, EventHome.class);

        DocumentReference tmp = db.collection("event").document("1Uk8QBlLn5nLeaXBw4zH");
        if(!getNameEvent().getText().toString().equals("")) {
            tmp
                    .update("nameEvent", getNameEvent().getText().toString())
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(EventSettings.this, "Mise a jour", Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                    })
                    .addOnFailureListener(e -> Toast.makeText(EventSettings.this, "Pas mise a jour", Toast.LENGTH_SHORT).show());
        }
        if(date != null){
            tmp
                    .update("date", date)
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(EventSettings.this, "Mise a jour", Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                    })
                    .addOnFailureListener(e -> Toast.makeText(EventSettings.this, "Pas mise a jour", Toast.LENGTH_SHORT).show());
        }
        if(getTmpHour() != getTimePicker().getHour()) {
            tmp
                    .update("heure", getTimePicker().getHour())
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(EventSettings.this, "Mise a jour", Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                    })
                    .addOnFailureListener(e -> Toast.makeText(EventSettings.this, "Pas mise a jour", Toast.LENGTH_SHORT).show());
        }
        if(getTmpMin() != getTimePicker().getMinute()) {
            tmp
                    .update("minute", getTimePicker().getMinute())
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(EventSettings.this, "Mise a jour", Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                    })
                    .addOnFailureListener(e -> Toast.makeText(EventSettings.this, "Pas mise a jour", Toast.LENGTH_SHORT).show());
        }
    }

    public void onBtnBackHome(View view) {
        Intent intent = new Intent (this, Home.class);
        startActivity(intent);
    }

    public void setTmp(Map tmp) {
        this.tmp = tmp;
    }

    public TextView getTitlee() {
        return title;
    }

    public EditText getNameEvent() {
        return nameEvent;
    }

    public Button getDateButton(){
        return dateButton;
    }

    public TimePicker getTimePicker() {
        return timePicker;
    }

    public int getTmpHour() {
        return tmpHour;
    }

    public void setTmpHour(int tmpHour) {
        this.tmpHour = tmpHour;
    }

    public int getTmpMin() {
        return tmpMin;
    }

    public void setTmpMin(int tmpMin) {
        this.tmpMin = tmpMin;
    }
}
