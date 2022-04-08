package com.example.partymaker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

// Class: CreateEventInfo
// Description: This class is used to create an event.
//              It is used to create an event with the information
//              entered by the user.

public class CreateEventInfo extends AppCompatActivity {
    // Initialize variables
    private String mailUser;
    private EditText nameEvent;
    private EditText description;
    private EditText adresse;
    private Button imageImport;

    // Create the activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_event_info);

        // Initialize Firebase Auth
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        // Get the current user
        FirebaseUser user = mAuth.getCurrentUser();

        // Set the variables to the corresponding views
        this.mailUser = user != null ? user.getEmail() : null;
        this.nameEvent = findViewById(R.id.editNameEvent);
        this.description = findViewById(R.id.editDescEvent);
        this.adresse = findViewById(R.id.editLieuEvent);
        this.imageImport = findViewById(R.id.button_AjoutImageGalerie);
    }

    // Go to Home
    public void returnMain(View view) {
        Intent intent = new Intent (this, Home.class);
        startActivity(intent);
    }

    // Go to next step to create an event
    public void goCreateTime(View view) {
        Intent intent = new Intent (this, CreateEventTime.class);

        // Check if the user has entered all the information
        if(this.nameEvent.getText().toString().matches("") ||
            this.description.getText().toString().matches("") ||
            this.adresse.getText().toString().matches("")){
            Toast.makeText(this, "Vous devez renseigner tous les champs !", Toast.LENGTH_SHORT).show();
        }
        // If the user has entered all the information, go to the next step
        else{
            // Put the information in the intent
            intent.putExtra("mail", this.mailUser);
            intent.putExtra("nameEvent", this.nameEvent.getText().toString());
            intent.putExtra("description", this.description.getText().toString());
            intent.putExtra("adresse", this.adresse.getText().toString());
            startActivity(intent);
        }
    }
}