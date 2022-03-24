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
        startActivity(intent);
    }

    public void goCalc(View view) {
        Intent intent = new Intent (this, EventCalc.class);
        startActivity(intent);
    }

    public void goSettings(View view) {
        Intent intent = new Intent (this, EventSettings.class);
        startActivity(intent);
    }

    public void onBtnHomeClick(View view) {
        Intent intent = new Intent (this, EventList.class);
        startActivity(intent);
    }
}
