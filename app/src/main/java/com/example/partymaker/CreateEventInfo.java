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

// TODO: Implémenter l'image de l'user

public class CreateEventInfo extends AppCompatActivity {
    private String mailUser;

    private EditText nameEvent;
    private EditText description;
    private EditText adresse;

    private Button imageImport;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_event_info);

        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        FirebaseUser user = mAuth.getCurrentUser();

        this.mailUser = user != null ? user.getEmail() : null;
        this.nameEvent = findViewById(R.id.editNameEvent);
        this.description = findViewById(R.id.editDescEvent);
        this.adresse = findViewById(R.id.editLieuEvent);
        this.imageImport = findViewById(R.id.button_AjoutImageGalerie);
    }

    public void returnMain(View view) {
        Intent intent = new Intent (this, Home.class);
        startActivity(intent);
    }

    public void goCreateEvent2(View view) {
        Intent intent = new Intent (this, CreateEventTime.class);

        if(this.nameEvent.getText().toString().matches("") ||
            this.description.getText().toString().matches("") ||
            this.adresse.getText().toString().matches("")){
            Toast.makeText(this, "Vous devez renseigner tous les champs !", Toast.LENGTH_SHORT).show();
        }
        else{
            intent.putExtra("mail", this.mailUser);
            intent.putExtra("nameEvent", this.nameEvent.getText().toString());
            intent.putExtra("description", this.description.getText().toString());
            intent.putExtra("adresse", this.adresse.getText().toString());
            startActivity(intent);
        }
    }
}