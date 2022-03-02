package com.example.partymaker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class CreateEvent2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event2);

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