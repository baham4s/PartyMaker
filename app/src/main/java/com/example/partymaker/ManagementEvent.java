package com.example.partymaker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class ManagementEvent extends AppCompatActivity {

    private Button back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_management_event);

        this.back = findViewById(R.id.btnBack);
    }

    public void onBtnBackClick(View view) {
        this.back.setOnClickListener(view1 -> {
            Intent backActivity = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(backActivity);
            finish();
        });
    }
}