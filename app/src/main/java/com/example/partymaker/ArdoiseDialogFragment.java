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

// Class: ArdoiseDialogFragment
// Description: This class is used to create a dialog to add a new ardoise to the database. It is used by the ArdoiseActivity.
public class ArdoiseDialogFragment extends DialogFragment {
    // Initialize variables
    private EditText editTextNomDepense;
    private EditText editTextSommeDepense;
    private String id;

    // Initialize Firebase variables
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    DocumentReference tmp;

    // Interface for communication with the activity
    private SendMessages sendMessages;

    // Interface for communication with the activity
    public interface SendMessages {
        // Send a signal for updating the list of friends
        void sendMessage(Boolean message);
    }

    // OnAttach is called when the activity is attached to an application
    public void onAttach(Context context) {
        super.onAttach(context);
        sendMessages = (SendMessages) context;
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
        builder.setPositiveButton("Ajouter", (dialog, which) -> dismiss());
        return builder.create();
    }

    // Create the view of the dialog with the layout
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.pop_up_ajout_ardoise, container, false);
    }

    // When the view is created we initialize the variables and we set the listeners on the buttons of the dialog layout to perform actions when they are clicked on.
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Set the variables to the corresponding layout elements
        this.editTextNomDepense = view.findViewById(R.id.editTextNomDepense);
        this.editTextSommeDepense = view.findViewById(R.id.editTextSommeDepense);
        Button btnDone = view.findViewById(R.id.buttonDialogInvite);

        // Set name expense if it is not empty
        if (getArguments() != null && !TextUtils.isEmpty(getArguments().getString("nom")))
            editTextNomDepense.setText(getArguments().getString("nom"));

        // Set price of expense if it is not empty
        if (getArguments() != null && !TextUtils.isEmpty(getArguments().getString("prix")))
            editTextSommeDepense.setText(getArguments().getString("prix"));

        // Set the listener on the button to perform actions when it is clicked on.
        btnDone.setOnClickListener(view1 -> {
            tmp = db.collection("event").document(getIdd());
            // If the name and the price are not empty
            if(!TextUtils.isEmpty(editTextNomDepense.getText().toString()) && !TextUtils.isEmpty(editTextSommeDepense.getText().toString())) {
                // Add the expense to the database
                tmp.update(
                        "ardoise.".concat(editTextNomDepense.getText().toString()), editTextSommeDepense.getText().toString()
                );
            }
            dismiss();
            // Send a signal to the activity to update the list of expenses
            sendMessages.sendMessage(true);
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
