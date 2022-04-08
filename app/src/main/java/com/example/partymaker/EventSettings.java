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

import java.text.MessageFormat;
import java.util.Map;

// Class: EventSettings
// Description: This class is set up to allow the user to change the settings of an event.
public class EventSettings extends AppCompatActivity{
    // Initialize variables
    private TextView title;
    private EditText nameEvent;
    private DatePickerDialog datePickerDialog;
    private Button dateButton;
    private TimePicker timePicker;
    private Map tmp;
    private String date;
    private String id;
    private int tmpHour;
    private int tmpMin;
    private TextView textViewTime;

    // Initialize Firebase variables
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    // Create the activity
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.event_settings);

        // Get the intent id from the previous activity
        Intent intentBase = getIntent();
        this.id = intentBase.getStringExtra("id");

        // Set the variables corresponding to the layout
        this.nameEvent = findViewById(R.id.editNameEvent);
        this.title = findViewById(R.id.textSettings);
        this.dateButton = findViewById(R.id.datePickerButton);
        this.timePicker = this.findViewById(R.id.timePicker);
        this.timePicker.setIs24HourView(true);
        this.textViewTime = this.findViewById(R.id.textView_time);

        // Get the event data from the database and set the hints for the TextViews
        DocumentReference docRef = db.collection("event").document(getId());
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

        // Init the date picker
        initDatePicker();

        // Set the onClickListener for the date button
        this.datePickerDialog.setOnDateSetListener((datePicker, i, i1, i2) -> {
            date = makeDateSring(i2, i1, i);
            getDateButton().setHint(date);
        });

        // Set the onClickListener for the time button
        this.timePicker.setOnTimeChangedListener((view, hourOfDay, minute) -> {
            getTimePicker().setHour(hourOfDay);
            getTimePicker().setMinute(minute);
            if(minute < 10){
                textViewTime.setText(MessageFormat.format("{0} : 0{1}", hourOfDay, minute));
            }else{
                textViewTime.setText(MessageFormat.format("{0} : {1}", hourOfDay, minute));
            }
        });
    }

    // Initialize the date picker
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

        int style = AlertDialog.BUTTON_NEUTRAL;

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
    }

    // Make a date string
    private String makeDateSring(int day, int month, int year) {
        if(day < 10){
            return  "0" + day + " " + getMonthFormat(month) + " " + year;
        }else{
            return  day + " " + getMonthFormat(month) + " " + year;
        }
    }

    // Transform the month number to a string
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

    // Display the date picker
    public void openDatePicker(View view){
        datePickerDialog.show();
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

    // Go to event invite page and send the id of the event to the event invite page
    public void goInvite(View view) {
        Intent intent = new Intent (this, EventInvite.class);
        intent.putExtra("id", this.id);
        startActivity(intent);
    }

    // Go back to the list of events page
    public void onBtnBackHome(View view) {
        Intent intent = new Intent (this, EventList.class);
        startActivity(intent);
    }

    // Update the settings of the event
    public void onBtnConfirmClick(View view){
        Intent intent = new Intent (this, EventHome.class);

        DocumentReference tmp = db.collection("event").document(getId());
        // Update the name of the event if he is not empty
        if(!getNameEvent().getText().toString().equals("")) {
            tmp
                    .update("nameEvent", getNameEvent().getText().toString())
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(EventSettings.this, "Mise a jour", Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                    })
                    .addOnFailureListener(e -> Toast.makeText(EventSettings.this, "Pas mise a jour", Toast.LENGTH_SHORT).show());
        }
        // Update the date of the event if he is not empty
        if(date != null){
            tmp
                    .update("date", date)
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(EventSettings.this, "Mise a jour", Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                    })
                    .addOnFailureListener(e -> Toast.makeText(EventSettings.this, "Pas mise a jour", Toast.LENGTH_SHORT).show());
        }
        // Update the hour of the event if he is not empty
        if(getTmpHour() != getTimePicker().getHour()) {
            tmp
                    .update("heure", getTimePicker().getHour())
                    .addOnSuccessListener(aVoid -> {
                        Toast.makeText(EventSettings.this, "Mise a jour", Toast.LENGTH_SHORT).show();
                        startActivity(intent);
                    })
                    .addOnFailureListener(e -> Toast.makeText(EventSettings.this, "Pas mise a jour", Toast.LENGTH_SHORT).show());
        }
        // Update the minute of the event if he is not empty
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


    // Getter and setter
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

    public String getId() {
        return id;
    }
}
