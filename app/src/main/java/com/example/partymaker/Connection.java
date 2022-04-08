package com.example.partymaker;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
// Class: Connection
// Description: This class is used to create or log in to an account.

public class Connection extends AppCompatActivity {
    // variables for the connection activity
    private EditText mail;
    private EditText password;
    private FirebaseAuth mAuth;

    // Create the activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.connection);

        // Set the variables to the corresponding views
        this.mail = findViewById(R.id.mail);
        this.password = findViewById(R.id.password_setting);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
    }

    // Start the activity
    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        // Check if the user is already connected
        if(currentUser != null){
            currentUser.reload();
        }
    }

    // Method to check if the mail is valid
    public static boolean isValidEmail(CharSequence mail) {
        return (TextUtils.isEmpty(mail) || !Patterns.EMAIL_ADDRESS.matcher(mail).matches());
    }

    // Method to check if the password is valid
    public static boolean isValidPassword(CharSequence password) {
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
        Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
        Matcher matcher = pattern.matcher(password);

        return !matcher.matches();
    }

    // Method to check if the mail and password are valid before registering
    public void onBtnSignInClick(View view) {
        // Next activity
        Intent intent = new Intent (this, Home.class);
        String email = this.mail.getText().toString();
        String password = this.password.getText().toString();

        // Check if mail and password aren't empty
        if(password.equals("") && email.equals("")){
            Toast.makeText(this,"Champs vide", Toast.LENGTH_LONG).show();
        }
        else {
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, task -> {
                        // Mail and password are invalid
                        if (isValidEmail(email) && isValidPassword(password)) {
                            Toast.makeText(Connection.this, "Problème mail et mots de passe", Toast.LENGTH_SHORT).show();
                        }
                        // Mail is invalid
                        else if (isValidEmail(email)) {
                            Toast.makeText(Connection.this, "Problème adresse mail", Toast.LENGTH_SHORT).show();
                        }
                        // Password is invalid
                        else if (isValidPassword(password)) {
                            Toast.makeText(Connection.this, "Problème mots de passe", Toast.LENGTH_SHORT).show();
                        }
                        // Mail and password are valid
                        else if (task.isSuccessful()) {
                            startActivity(intent);
                            Toast.makeText(this, "Vous ete connecté avec l'adresse mail : " + email, Toast.LENGTH_LONG).show();
                        }
                        // Mail already exist
                        else {
                            Toast.makeText(this, "Email déja présent dans la BDD", Toast.LENGTH_LONG).show();
                        }
                    });
        }
    }

    // Method to check if the mail and password are valid before connecting
    public void onBtnConnectClick(View view) {
        // Next activity
        Intent intent = new Intent (this, Home.class);

        // Check if mail and password aren't empty
        if(this.password.getText().toString().equals("") && this.mail.getText().toString().equals("")){
            Toast.makeText(this,"Champs vide", Toast.LENGTH_LONG).show();
        }
        // Check if mail and password are valid
        else {
            mAuth.signInWithEmailAndPassword(this.mail.getText().toString(), this.password.getText().toString())
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            startActivity(intent);
                            Toast.makeText(this, "Vous ete connecté avec l'adresse mail : " + this.mail.getText().toString(), Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(this, "Identifiant incorect", Toast.LENGTH_LONG).show();
                        }
                    });
        }
    }
}