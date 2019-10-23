package com.example.board2deathapp.ui.user_update;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.board2deathapp.R;
import com.example.board2deathapp.models.DBResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class AuthenticateUserDialog extends DialogFragment {

    private EditText mPasswordEditText;
    private DBResponse mDbResponse;

    public AuthenticateUserDialog(DBResponse dbResponse) { this.mDbResponse = dbResponse; }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_authenticate_user_dialog, container, false);
        return root;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        final View view = inflater.inflate(R.layout.fragment_authenticate_user_dialog,null);
        final FirebaseAuth fbAuth = FirebaseAuth.getInstance();
        final FirebaseUser fbUser = fbAuth.getCurrentUser();
        this.mPasswordEditText = view.findViewById(R.id.editTextPassword);
        if (fbUser == null) {
            return builder.create();
        }
        builder.setView(view);
        builder.setPositiveButton("Authenticate", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                final String password = mPasswordEditText.getText().toString();
                if (fbUser.getEmail() == null) {
                    AuthenticateUserDialog.this.getDialog().cancel();
                    return;
                }
                AuthCredential authCredential = EmailAuthProvider.getCredential(fbUser.getEmail(), password);
                fbUser.reauthenticate(authCredential).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d("RE-AUTHENTICATE_USER", "Successfully authenticated User");
                            mDbResponse.onSuccess(null);
                        } else {
                            Toast.makeText(getActivity(), "Failed to Authenticate", Toast.LENGTH_LONG).show();
                            AuthenticateUserDialog.this.getDialog().cancel();
                            mDbResponse.onFailure(null);
                        }
                    }
                });
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                AuthenticateUserDialog.this.getDialog().cancel();
            }
        });
        return builder.create();
    }
}
