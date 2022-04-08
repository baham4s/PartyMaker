package com.example.partymaker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.firebase.ui.auth.AuthUI;
// Class: Home
// Description: This class is the main activity of the app. It is the first activity that the user sees when they are connected.
public class Home extends AppCompatActivity {
    // Creation of the activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
    }

    // Go to the create event activity
    public void onBtnCreateClick(View view) {
        Intent intent = new Intent (this, CreateEventInfo.class);
        startActivity(intent);
    }

    // Go to the consult list of event activity
    public void onBtnConsultClick(View view) {
        Intent intent = new Intent (this, EventList.class);
        startActivity(intent);
    }

    // Go to setting activity
    public void onBtnParameterClick(View view) {
        Intent intent = new Intent (this, Settings.class);
        startActivity(intent);
    }

    // Logout the user
    public void onBtnDisconnectClick(View view) {
        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener(task -> Toast.makeText(Home.this, "Deconnexion", Toast.LENGTH_SHORT).show());
        Intent intent = new Intent (this, Connection.class);
        startActivity(intent);
    }
}