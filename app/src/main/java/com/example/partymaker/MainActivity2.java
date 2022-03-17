package com.example.partymaker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.metrics.Event;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity2 extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
    }

    public void onBtnCreateClick(View view) {
        Intent intent = new Intent (this, CreateEvent.class);
        startActivity(intent);
    }

    public void onBtnConsultClick(View view) {
        Intent intent = new Intent (this, EventHome.class);
        startActivity(intent);
    }

    public void onBtnParameterClick(View view) {
        Intent intent = new Intent (this, settings.class);
        startActivity(intent);
    }
}