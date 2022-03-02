package com.example.partymaker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class settings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_settings);

    }

    public void onBtnValidateClick() {
        Intent intent = new Intent (this, MainActivity.class);
        // intent.putExtra();
        startActivity(intent);
    }

    public void goBack() {
        Intent intent = new Intent (this, MainActivity.class);
        // intent.putExtra();
        startActivity(intent);
    }

}