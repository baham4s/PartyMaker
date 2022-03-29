package com.example.partymaker;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class ArdoiseDialogFragment extends DialogFragment {

    private EditText editTextNomDepense;
    private EditText editTextSommeDepense;

    private String id;

    private SendMessages sendMessages;

    FirebaseFirestore db;
    DocumentReference tmp;

    public interface SendMessages {
        void iAmMSG(String msg1, String msg2);
    }

    public void receiveMsg(String msg) {
        setId(msg);
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        sendMessages = (SendMessages) context;
    }


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        if (getArguments() != null) {
            if (getArguments().getBoolean("notAlertDialog")) {
                return super.onCreateDialog(savedInstanceState);
            }
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setPositiveButton("Ajouter", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dismiss();
            }
        });

        return builder.create();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.pop_up_ajout_ardoise, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.editTextNomDepense = view.findViewById(R.id.editTextNomDepense);
        this.editTextSommeDepense = view.findViewById(R.id.editTextSommeDepense);
        Button btnDone = view.findViewById(R.id.buttonDialogInvite);

        db = FirebaseFirestore.getInstance();

        if (getArguments() != null && !TextUtils.isEmpty(getArguments().getString("nom")))
            editTextNomDepense.setText(getArguments().getString("nom"));

        if (getArguments() != null && !TextUtils.isEmpty(getArguments().getString("prix")))
            editTextSommeDepense.setText(getArguments().getString("prix"));

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tmp = db.collection("event").document(getIdd());

                tmp.update(
                        "ardoise.".concat(editTextNomDepense.getText().toString()), editTextSommeDepense.getText().toString()
                );
                dismiss();
            }
        });
    }

    public String getIdd() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    public interface DialogListener {
        void onFinishEditDialog(String inputText);
    }
}
