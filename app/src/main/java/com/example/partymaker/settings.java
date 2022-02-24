package com.example.partymaker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class settings extends AppCompatActivity {

    private Button validate;
    private Button cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        this.validate = findViewById(R.id.btnValidate);
        this.cancel = findViewById(R.id.btnCancel);
    }

    public void onBtnValidateClick(View view) {
//        TODO : Récupérer les valeurs indiquer et mettre dans la quelquechose
//        TODO : Vérifier les champs remplis
//        TODO : Vérifier la validité des champs rentré
        this.validate.setOnClickListener(view1 -> {
            Intent baseActivty = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(baseActivty);
            finish();
        });
    }

    public void onBtnCancelClick(View view) {
//        TODO : Ne rien faire avec les valeurs indiqué
        this.cancel.setOnClickListener(view1 -> {
            Intent cancelActivity = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(cancelActivity);
            finish();
        });
    }
}