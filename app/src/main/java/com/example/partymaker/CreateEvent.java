package com.example.partymaker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class CreateEvent extends AppCompatActivity {
    private String mailUser;

    private EditText nameEvent;
    private EditText description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_event);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        this.mailUser = user != null ? user.getEmail() : null;
        this.nameEvent = findViewById(R.id.editNameEvent);
        this.description = findViewById(R.id.editDescEvent);
    }

    public void returnMain(View view) {
        Intent intent = new Intent (this, MainActivity2.class);
        startActivity(intent);
    }

    public void goCreateEvent2(View view) {
        Intent intent = new Intent (this, CreateEvent2.class);
        intent.putExtra("mail", this.mailUser);
        intent.putExtra("nameEvent", this.nameEvent.getText().toString());
        intent.putExtra("description", this.description.getText().toString());
        startActivity(intent);
    }
}