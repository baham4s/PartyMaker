package com.example.partymaker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.metrics.Event;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    //private Button settings;
    //private Button consult;
    //private Button create;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //this.settings = findViewById(R.id.button_settings);
        //this.consult = findViewById(R.id.button_consult);
        //this.create = findViewById(R.id.button_create);

    }

    public void onBtnCreateClick(View view) {
        Intent intent = new Intent (this, CreateEvent.class);
        // intent.putExtra();
        startActivity(intent);
    }

    public void onBtnConsultClick(View view) {
        Intent intent = new Intent (this, EventHome.class);
        // intent.putExtra();
        startActivity(intent);
    }

    public void onBtnParameterClick(View view) {
        Intent intent = new Intent (this, settings.class);
        // intent.putExtra();
        startActivity(intent);
    }




}