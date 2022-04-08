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

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;

// Class: CreateEventTime
// Description: This class is used to create the time of the event.
//              and send data to the database.
public class CreateEventTime extends AppCompatActivity {
    // Initialize variables
    private DatePickerDialog datePickerDialog;
    private Button dateButton;
    private TimePicker timePicker;
    private TextView textViewTime;
    private String mailUser;
    private String nameEvent;
    private String description;
    private String date;
    private String adresse;
    private String id;

    // Create the activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_event_time);

        // Get the intent from the previous activity
        Intent intentBase = getIntent();
        this.mailUser = intentBase.getStringExtra("mail");
        this.nameEvent = intentBase.getStringExtra("nameEvent");
        this.description = intentBase.getStringExtra("description");
        this.adresse = intentBase.getStringExtra("adresse");
        this.dateButton = findViewById(R.id.datePickerButton);

        // Initialize the date picker
        this.dateButton.setHint(getTodaysDate());
        initDatePicker();

        // Set the variables to the corresponding views
        this.textViewTime = this.findViewById(R.id.textView_time);
        this.timePicker = this.findViewById(R.id.timePicker);
        this.timePicker.setIs24HourView(true);

        // Set the time picker listener
        this.timePicker.setOnTimeChangedListener((view, hourOfDay, minute) -> {
            // If minute is less than 10, add a 0 before the minute
            if(minute < 10){
                textViewTime.setText(MessageFormat.format("{0} : 0{1}", hourOfDay, minute));
            }else{
                textViewTime.setText(MessageFormat.format("{0} : {1}", hourOfDay, minute));
            }
        });

        // Set the date picker listener
        this.datePickerDialog.setOnDateSetListener((datePicker, i, i1, i2) -> {
            date = makeDateSring(i2, i1, i);
            // Set the date in the date picker button
            getDateButton().setHint(date);
        });
    }

    // Send the data to the database
    public void confirmEvent(View view) {
        // Database initialization
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Map<String, String> ardoise = new HashMap<>();
        Map<String, String> invite = new HashMap<>();

        // Date aren't set
        if(date == null){
            Toast.makeText(this, "Vous devez renseigner une date", Toast.LENGTH_SHORT).show();
        }
        // Create the event
        else{
            // Fields of the event
            Map<String, Object> user = new HashMap<>();
            user.put("mail", this.mailUser);
            user.put("nameEvent", this.nameEvent);
            user.put("description", this.description);
            user.put("adresse", this.adresse);
            user.put("date", date);
            user.put("heure", this.timePicker.getHour());
            user.put("minute", this.timePicker.getMinute());
            user.put("ardoise", ardoise);
            user.put("invite", invite);

            // Add the event to the database
            db.collection("event")
                    .add(user)
                    .addOnSuccessListener(documentReference -> {
                        Log.d("TAG", "DocumentSnapshot added with ID: " + documentReference.getId());
                        setId(documentReference.getId());
                        DocumentReference dr = db.collection("event").document(getId());
                        // Add the id of the event to the event
                        dr
                                .update("id", getId())
                                .addOnSuccessListener(aVoid -> Log.d("TAG", "DocumentSnapshot successfully updated!"))
                                .addOnFailureListener(e -> Log.w("TAG", "Error updating document", e));
                    })
                    .addOnFailureListener(e -> Log.w("TAG", "Error adding document", e));

            // Redirect to the event page
            Intent intent = new Intent(this, Home.class);
            startActivity(intent);
        }
    }

    // Get the today's date
    private String getTodaysDate() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        month = month + 1;
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        return makeDateSring(day, month, year);
    }

    // Initialize the date picker
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

        int style = AlertDialog.BUTTON_NEUTRAL;

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
    }

    // Make the date string
    private String makeDateSring(int day, int month, int year) {
        if(day < 10){
            return  "0" + day + " " + getMonthFormat(month) + " " + year;
        }else{
            return  day + " " + getMonthFormat(month) + " " + year;
        }
    }

    // Transform the month number to the month name
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

    // Go back to previous activity
    public void returnCreate1(View view) {
        Intent intent = new Intent (this, CreateEventInfo.class);
        startActivity(intent);
    }

    // Go to Home activity
    public void onBtnHomeClick(View view) {
        Intent intent = new Intent (this, Home.class);
        startActivity(intent);
    }

    // Getter and setter
    public Button getDateButton(){
        return dateButton;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}