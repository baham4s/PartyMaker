package com.example.partymaker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_settings);
    }

    public void onBtnValidateClick(View view) {
        Intent intent = new Intent (this, MainActivity2.class);
        // intent.putExtra();
        startActivity(intent);
    }

    public void goBack(View view) {
        Intent intent = new Intent (this, MainActivity2.class);
        // intent.putExtra();
        startActivity(intent);
    }
}