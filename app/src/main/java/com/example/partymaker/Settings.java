package com.example.partymaker;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

// Class: Settings
// Description: This class is used to set the user's settings.
//              The user can change their email and password.

public class Settings extends AppCompatActivity {
    // Initialize variables
    private EditText mail;
    private EditText actualPassword;
    private EditText newPassword;
    private EditText newPassword2;
    private String newPasswordString;
    @SuppressLint("UseSwitchCompatOrMaterialCode")
    private Switch switch1;

    private FirebaseAuth mAuth;
    private FirebaseUser user;

    // Create the activity
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        // Set the variables to the corresponding views
        this.mail = findViewById(R.id.editTextSettingsUserMail);
        this.actualPassword = findViewById(R.id.editTextSettingsUserCurrentMDP);
        this.newPassword = findViewById(R.id.editTextSettingsUserNewMDP);
        this.newPassword2 = findViewById(R.id.editTextSettingsUserNewMDPConfirm);
        this.switch1 = findViewById(R.id.switchTheme);

        // Initialize the FirebaseAuth instance
        mAuth = FirebaseAuth.getInstance();
        // Get the current user
        user = FirebaseAuth.getInstance().getCurrentUser();

        // Set the theme according to the switch
        switch1.setOnClickListener(v -> {
            if(switch1.isChecked()){
                // Set the theme to dark
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
            } else {
                // Set the theme to light
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
            }
        });
    }

    // Check if the new password is valid
    private boolean changePasswordVerif(String newPasswordString, String newPass2){
        // Check if the new password is valid
        if(!isValidPassword(newPasswordString)){
            Toast.makeText(Settings.this, "format incorect 8char + maj + min + bizarre", Toast.LENGTH_SHORT).show();
            return false;
        }
        // Check if the new password is the same as the confirmation
        else if(!newPasswordString.equals(newPass2)){
            Toast.makeText(Settings.this, "2 password different", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    // Go back to the home page
    public void goHome(View view) {
        Intent intent = new Intent (this, Home.class);

        // Set the new password in the database
        String mail = this.mail.getText().toString();
        String lastPass = this.actualPassword.getText().toString();
        setNewPass(this.newPassword.getText().toString());

        // Check if the new password is valid
        if(changePasswordVerif(this.newPassword.getText().toString(), this.newPassword2.getText().toString())){
            mAuth.signInWithEmailAndPassword(mail, lastPass)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            user.updatePassword(getNewPass())
                                    .addOnCompleteListener(task1 -> {
                                        if (task1.isSuccessful()) {
                                            Toast.makeText(Settings.this, "Password mis a jour!", Toast.LENGTH_SHORT).show();
                                            startActivity(intent);
                                        } else {
                                            Toast.makeText(Settings.this, "Problème dans le changement de password", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        } else {
                            Toast.makeText(Settings.this, "Identifiant pour modifier le mots de passe incorect", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    // If the user press the back button
    public void returnMain(View view) {
        Intent intent = new Intent (this, Home.class);
        startActivity(intent);
    }

    // Check if the password pattern is valid
    public static boolean isValidPassword(CharSequence password) {
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
        Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
        Matcher matcher = pattern.matcher(password);

        return matcher.matches();
    }

    // Getters and setters
    public String getNewPass() {
        return newPasswordString;
    }

    public void setNewPass(String newPasswordString) {
        this.newPasswordString = newPasswordString;
    }
}