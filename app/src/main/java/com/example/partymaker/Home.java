package com.example.partymaker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Home extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
    }

    public void onBtnCreateClick(View view) {
        Intent intent = new Intent (this, CreateEventInfo.class);
        startActivity(intent);
    }

    public void onBtnConsultClick(View view) {
        Intent intent = new Intent (this, EventList.class);
        startActivity(intent);
    }

    public void onBtnParameterClick(View view) {
        Intent intent = new Intent (this, Settings.class);
        startActivity(intent);
    }
}