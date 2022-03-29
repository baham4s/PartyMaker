package com.example.partymaker;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

//TODO : Implémenter le thème

public class Settings extends AppCompatActivity {

    private EditText mail;
    private EditText actualPassword;
    private EditText newPassword;
    private EditText newPassword2;

    private String newPasswordString;

    private FirebaseAuth mAuth;
    private FirebaseUser user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        this.mail = findViewById(R.id.editTextSettingsUserMail);
        this.actualPassword = findViewById(R.id.editTextSettingsUserCurrentMDP);
        this.newPassword = findViewById(R.id.editTextSettingsUserNewMDP);
        this.newPassword2 = findViewById(R.id.editTextSettingsUserNewMDPConfirm);

        mAuth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
    }

    private boolean changePasswordVerif(String newPasswordString, String newPass2){
        if(!isValidPassword(newPasswordString)){
            Toast.makeText(Settings.this, "format incorect 8char + maj + min + bizarre", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if(!newPasswordString.equals(newPass2)){
            Toast.makeText(Settings.this, "2 password different", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    public void goCreateEvent2(View view) {
        Intent intent = new Intent (this, Home.class);


        String mail = this.mail.getText().toString();
        String lastPass = this.actualPassword.getText().toString();
        setNewPass(this.newPassword.getText().toString());


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

    public void returnMain(View view) {
        Intent intent = new Intent (this, Home.class);
        startActivity(intent);
    }

    public static boolean isValidPassword(CharSequence password) {
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
        Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
        Matcher matcher = pattern.matcher(password);

        return matcher.matches();
    }

    public String getNewPass() {
        return newPasswordString;
    }

    public void setNewPass(String newPasswordString) {
        this.newPasswordString = newPasswordString;
    }
}