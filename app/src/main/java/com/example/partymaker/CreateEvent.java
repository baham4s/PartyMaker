package com.example.partymaker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CreateEvent extends AppCompatActivity {

    private Button next;
    private Button cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        this.next = findViewById(R.id.btnNext);
        this.cancel = findViewById(R.id.btnCancel);
    }

    public void onBtnNextClick(View view) {
        this.next.setOnClickListener(view1 -> {
            Intent settingActivity = new Intent(getApplicationContext(), create_event_2.class);
            startActivity(settingActivity);
            finish();
        });
    }

    public void onBtnCancelClick(View view) {
        this.cancel.setOnClickListener(view1 -> {
            Intent settingActivity = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(settingActivity);
            finish();
        });
    }
}