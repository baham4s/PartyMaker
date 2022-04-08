package com.example.partymaker;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class InviteDialogFragment extends DialogFragment {

    private EditText nom;
    private EditText mail;

    private String id;

    private SendMessagesInvite sendMessagesInvite;

    FirebaseFirestore db;
    DocumentReference tmp;

    public interface SendMessagesInvite {
        void sendMessage(Boolean message);
    }

    public void onAttach(Context context) {
        super.onAttach(context);
        sendMessagesInvite = (SendMessagesInvite) context;
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

        builder.setPositiveButton("Inviter", new DialogInterface.OnClickListener() {
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
        return inflater.inflate(R.layout.pop_up_inviter_personne, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        this.nom = view.findViewById(R.id.editTextNom);
        this.mail = view.findViewById(R.id.editTextMail);
        Button btnDone = view.findViewById(R.id.buttonDialogInvite);

        db = FirebaseFirestore.getInstance();

        if (getArguments() != null && !TextUtils.isEmpty(getArguments().getString("nom")))
            nom.setText(getArguments().getString("nom"));

        if (getArguments() != null && !TextUtils.isEmpty(getArguments().getString("mail")))
            mail.setText(getArguments().getString("mail"));

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tmp = db.collection("event").document(getIdd());
                if(!TextUtils.isEmpty(nom.getText().toString()) && !TextUtils.isEmpty(mail.getText().toString())) {
                    tmp.update(
                            "invite.".concat(nom.getText().toString()), mail.getText().toString()
                    );
                }
                dismiss();
                sendMessagesInvite.sendMessage(true);
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
