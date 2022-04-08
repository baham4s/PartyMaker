package com.example.partymaker;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
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

// Class: InviteDialogFragment
// Description: This class is used to create a dialog that allows the user to invite a friend to a party.
//              The user can enter the name of the friend and the email of the friend.

public class InviteDialogFragment extends DialogFragment {
    // Initialize variables
    private EditText nom;
    private EditText mail;
    private String id;

    // Initialize Firebase variables
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    DocumentReference tmp;

    // Interface for communication with the activity
    private SendMessagesInvite sendMessagesInvite;

    // Interface for communication with the activity
    public interface SendMessagesInvite {
        // Send a signal for updating the list of friends
        void sendMessage(Boolean message);
    }

    // OnAttach is called when the activity is attached to an application
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        sendMessagesInvite = (SendMessagesInvite) context;
    }

    // Create the dialog
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        if (getArguments() != null) {
            if (getArguments().getBoolean("notAlertDialog")) {
                return super.onCreateDialog(savedInstanceState);
            }
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setPositiveButton("Inviter", (dialog, which) -> dismiss());
        return builder.create();
    }

    // Create the view of the dialog with the layout
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.pop_up_inviter_personne, container, false);
    }

    // When the view is created we initialize the variables and we set the listeners on the buttons of the dialog layout to perform actions when they are clicked on.
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Set the variables to the corresponding layout elements
        this.nom = view.findViewById(R.id.editTextNom);
        this.mail = view.findViewById(R.id.editTextMail);
        Button btnDone = view.findViewById(R.id.buttonDialogInvite);

        // Set name if it is not empty
        if (getArguments() != null && !TextUtils.isEmpty(getArguments().getString("nom")))
            nom.setText(getArguments().getString("nom"));
        // Set mail if it is not empty
        if (getArguments() != null && !TextUtils.isEmpty(getArguments().getString("mail")))
            mail.setText(getArguments().getString("mail"));

        // Set the listener on the button to perform actions when it is clicked on.
        btnDone.setOnClickListener(view1 -> {
            tmp = db.collection("event").document(getIdd());
            // If the name and the email are not empty
            if(!TextUtils.isEmpty(nom.getText().toString()) && !TextUtils.isEmpty(mail.getText().toString())) {
                // Add the friend to the list of friends
                tmp.update(
                        "invite.".concat(nom.getText().toString()), mail.getText().toString()
                );
            }
            dismiss();
            // Send a signal to the activity to update the list of friends
            sendMessagesInvite.sendMessage(true);
        });
    }

    // Activity on resume is called when the activity is resumed
    @Override
    public void onResume() {
        super.onResume();
    }

    // Activity on create is called when the activity is created
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    // Activity on destroy is called when the activity is destroyed
    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    // Getter and setter for the id of the event
    public String getIdd() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


}
