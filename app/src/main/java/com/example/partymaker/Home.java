package com.example.partymaker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class Home extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);
    }

    public void onBtnCreateClick(View view) {
        Intent intent = new Intent (this, CreateEventInfo.class);
        startActivity(intent);
    }

    public void onBtnConsultClick(View view) {
        Intent intent = new Intent (this, EventList.class);
        startActivity(intent);
    }

    public void onBtnParameterClick(View view) {
        Intent intent = new Intent (this, Settings.class);
        startActivity(intent);
    }

    public void onBtnDisconnectClick(View view) {
        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(Home.this, "Deconnexion", Toast.LENGTH_SHORT).show();
                    }
                });
        Intent intent = new Intent (this, Connection.class);
        startActivity(intent);
    }
}