package com.example.partymaker;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainActivity extends AppCompatActivity {

    private EditText mail;
    private EditText password;

    private Button signIn;
    private Button connect;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.mail = findViewById(R.id.mail);
        this.password = findViewById(R.id.password_setting);

        this.signIn = findViewById(R.id.btnSignIn);
        this.connect = findViewById(R.id.btnConnect);

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

    public void updateUICreate(FirebaseUser account, String mail){
        if(account != null){
            startActivity(new Intent(this,MainActivity2.class));
            Toast.makeText(this,"Vous ete connecté avec l'adresse mail : " + mail, Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(this,"Email déja présent dans la BDD", Toast.LENGTH_LONG).show();
        }
    }

    public void updateUISignIn(FirebaseUser account, String mail){
        if(account != null){
            startActivity(new Intent(this,MainActivity2.class));
            Toast.makeText(this,"Vous ete connecté avec l'adresse mail : " + mail, Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(this,"Identifiant incorect", Toast.LENGTH_LONG).show();
        }
    }

    public void createAccount(String email, String password){
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful() && isValidEmail(email) && isValidPassword(password)) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        updateUICreate(user, email);
                    } else {
                        if(!isValidEmail(email) && !isValidPassword(password)){
                            Toast.makeText(MainActivity.this, "Problème mail et mots de passe", Toast.LENGTH_SHORT).show();
                        }
                        else if(!isValidEmail(email)){
                            Toast.makeText(MainActivity.this, "Problème adresse mail", Toast.LENGTH_SHORT).show();
                        }
                        else if(!isValidPassword(password)){
                            Toast.makeText(MainActivity.this, "Problème mots de passe", Toast.LENGTH_SHORT).show();
                        }
                        updateUICreate(null, "");
                    }
                });
    }

    public void connectAccount(String email, String password){
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        updateUISignIn(user, email);
                    } else {
                        updateUISignIn(null, "");
                    }
                });
    }

    public static boolean isValidEmail(CharSequence mail) {
        return (!TextUtils.isEmpty(mail) && Patterns.EMAIL_ADDRESS.matcher(mail).matches());
    }

    public static boolean isValidPassword(CharSequence password) {
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
        Pattern pattern = Pattern.compile(PASSWORD_PATTERN);
        Matcher matcher = pattern.matcher(password);

        return matcher.matches();
    }

    public void onBtnSignInClick(View view) {
        this.signIn.setOnClickListener(view1 -> createAccount(this.mail.getText().toString(), this.password.getText().toString()));
    }

    public void onBtnConnectClick(View view) {
        this.connect.setOnClickListener(view1 -> connectAccount(this.mail.getText().toString(), this.password.getText().toString()));
    }
}