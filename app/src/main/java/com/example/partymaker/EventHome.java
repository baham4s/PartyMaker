package com.example.partymaker;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

public class EventHome extends AppCompatActivity {

    // Enlever ImageButton

    private ImageButton toSettings;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consult_event);

        this.toSettings = findViewById(R.id.footer_settings);

    }

    public void goSettings() {
        Intent intent = new Intent (this, EventSettings.class);
        // intent.putExtra();
        startActivity(intent);
    }

}
