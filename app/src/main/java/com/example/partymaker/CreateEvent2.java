package com.example.partymaker;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.icu.util.Calendar;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;

public class CreateEvent2 extends AppCompatActivity {

    private DatePickerDialog datePickerDialog;
    private Button dateButton;

    private TimePicker timePicker;
    private TextView textViewTime;

    // Change this value and run the application again.
    private boolean is24HView = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event2);
        dateButton = findViewById(R.id.datePickerButton);

        dateButton.setHint(getTodaysDate());
        initDatePicker();

        this.textViewTime = (TextView) this.findViewById(R.id.textView_time);
        this.timePicker = (TimePicker) this.findViewById(R.id.timePicker);
        this.timePicker.setIs24HourView(this.is24HView);

        this.timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                if(minute < 10){
                    textViewTime.setText(hourOfDay + " : 0" + minute);
                }else{
                    textViewTime.setText(hourOfDay + " : " + minute);
                }

            }
        });

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
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                String date = makeDateSring(day, month, year);
                System.out.print("Ecriture de la date ...\n");
                dateButton.setHint(date);
            }
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
        Intent intent = new Intent (this, CreateEvent.class);
        // intent.putExtra();
        startActivity(intent);
    }

    public void confirmEvent(View view) {
        Intent intent = new Intent (this, MainActivity.class);
        // intent.putExtra();
        startActivity(intent);
    }

}