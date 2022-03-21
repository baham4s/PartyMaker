package com.example.partymaker;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.firestore.FirebaseFirestore;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

public class CreateEventTime extends AppCompatActivity {

    private DatePickerDialog datePickerDialog;
    private Button dateButton;

    private TimePicker timePicker;
    private TextView textViewTime;

    private String mailUser;
    private String nameEvent;
    private String description;
    private String date;
    private String adresse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_event_time);

        Intent intentBase = getIntent();
        this.mailUser = intentBase.getStringExtra("mail");
        this.nameEvent = intentBase.getStringExtra("nameEvent");
        this.description = intentBase.getStringExtra("description");
        this.adresse = intentBase.getStringExtra("adresse");
        this.dateButton = findViewById(R.id.datePickerButton);

        this.dateButton.setHint(getTodaysDate());
        initDatePicker();

        this.textViewTime = this.findViewById(R.id.textView_time);
        this.timePicker = this.findViewById(R.id.timePicker);
        // Change this value and run the application again.
        boolean is24HView = true;
        this.timePicker.setIs24HourView(true);

        this.timePicker.setOnTimeChangedListener((view, hourOfDay, minute) -> {
            if(minute < 10){
                textViewTime.setText(MessageFormat.format("{0} : 0{1}", hourOfDay, minute));
            }else{
                textViewTime.setText(MessageFormat.format("{0} : {1}", hourOfDay, minute));
            }
        });

        this.datePickerDialog.setOnDateSetListener((datePicker, i, i1, i2) -> date = makeDateSring(i2, i1, i));
    }

    public void confirmEvent(View view) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        if(date == null){
            Toast.makeText(this, "Vous devez renseigner une date", Toast.LENGTH_SHORT).show();
        }
        else{
            Map<String, Object> user = new HashMap<>();
            user.put("mail", this.mailUser);
            user.put("nameEvent", this.nameEvent);
            user.put("description", this.description);
            user.put("adresse", this.adresse);
            user.put("date", date);
            user.put("heure", this.timePicker.getHour());
            user.put("minute", this.timePicker.getMinute());

            db.collection("event")
                    .add(user)
                    .addOnSuccessListener(documentReference -> Log.d("TAG", "DocumentSnapshot added with ID: " + documentReference.getId()))
                    .addOnFailureListener(e -> Log.w("TAG", "Error adding document", e));
            Intent intent = new Intent(this, Home.class);
            startActivity(intent);
        }
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

    public void returnCreate1(View view) {
        Intent intent = new Intent (this, CreateEventInfo.class);
        startActivity(intent);
    }

}