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

public class Connection extends AppCompatActivity {

    private EditText mail;
    private EditText password;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.connection);

        this.mail = findViewById(R.id.mail);
        this.password = findViewById(R.id.password_setting);

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            currentUser.reload();
        }
    }

    public static boolean isValidEmail(CharSequence mail) {
        return (TextUtils.isEmpty(mail) || !Patterns.EMAIL_ADDRESS.matcher(mail).matches());
    }

    public static boolean isValidPassword(CharSequence password) {
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
        Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
        Matcher matcher = pattern.matcher(password);

        return !matcher.matches();
    }

    public void onBtnSignInClick(View view) {
        Intent intent = new Intent (this, Home.class);
        String email = this.mail.getText().toString();
        String password = this.password.getText().toString();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if(isValidEmail(email) && isValidPassword(password)){
                        Toast.makeText(Connection.this, "Problème mail et mots de passe", Toast.LENGTH_SHORT).show();
                    }
                    else if(isValidEmail(email)){
                        Toast.makeText(Connection.this, "Problème adresse mail", Toast.LENGTH_SHORT).show();
                    }
                    else if(isValidPassword(password)){
                        Toast.makeText(Connection.this, "Problème mots de passe", Toast.LENGTH_SHORT).show();
                    }
                    else if(task.isSuccessful()){
                        startActivity(intent);
                        Toast.makeText(this,"Vous ete connecté avec l'adresse mail : " + email, Toast.LENGTH_LONG).show();
                    }
                    else{
                        Toast.makeText(this,"Email déja présent dans la BDD", Toast.LENGTH_LONG).show();
                    }
                });
    }

    public void onBtnConnectClick(View view) {
        Intent intent = new Intent (this, Home.class);

        mAuth.signInWithEmailAndPassword(this.mail.getText().toString(), this.password.getText().toString())
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        startActivity(intent);
                        Toast.makeText(this,"Vous ete connecté avec l'adresse mail : " + this.mail.getText().toString(), Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(this,"Identifiant incorect", Toast.LENGTH_LONG).show();
                    }
                });
    }
}