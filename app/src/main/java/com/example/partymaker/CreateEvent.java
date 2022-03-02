package com.example.partymaker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CreateEvent extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);
    }

    public void returnMain(View view) {
        Intent intent = new Intent (this, MainActivity.class);
        // intent.putExtra();
        startActivity(intent);
    }

    public void goCreateEvent2(View view) {
        Intent intent = new Intent (this, CreateEvent2.class);
        // intent.putExtra();
        startActivity(intent);
    }
}