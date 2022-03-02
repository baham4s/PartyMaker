package com.example.partymaker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class EventInvite extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_invite);
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

}
