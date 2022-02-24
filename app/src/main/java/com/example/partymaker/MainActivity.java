package com.example.partymaker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private Button settings;
    private Button consult;
    private Button create;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.settings = findViewById(R.id.button_settings);
        this.consult = findViewById(R.id.button_consult);
        this.create = findViewById(R.id.button_create);

    }

    public void onBtnCreateClick(View view) {
        this.create.setOnClickListener(view1 -> {
            Intent createActivity = new Intent(getApplicationContext(), CreateEvent.class);
            startActivity(createActivity);
            finish();
        });
    }

    public void onBtnConsultClick(View view) {
        this.consult.setOnClickListener(view1 -> {
            Intent consultActivity = new Intent(getApplicationContext(), ManagementEvent.class);
            startActivity(consultActivity);
            finish();
        });
    }

    public void onBtnParameterClick(View view) {
        this.settings.setOnClickListener(view1 -> {
            Intent settingActivity = new Intent(getApplicationContext(), settings.class);
            startActivity(settingActivity);
            finish();
        });
    }




}