package com.example.partymaker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

// Class: Contact
// Description: This class is used to display the contact information of the party maker app.
public class Contact extends AppCompatActivity {

    // Create the activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
    }

    // Go back to the main page
    public void onBtnHomeClick(View view) {
        finish();
        Intent intent = new Intent(this, Home.class);
        startActivity(intent);
    }
}