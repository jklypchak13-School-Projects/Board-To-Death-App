package com.example.board2deathapp.models;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.board2deathapp.LandingActivity;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class User {
    private static String TAG = "USER";

    private String mUserName;
    private String mEmail;
    private boolean mIsAdmin;

    public User() {}

    public String getUserName() {
        return this.mUserName;
    }

    public String getEmail() {
        return this.mEmail;
    }

    /**
     * Login as a User via Email and Password
     *
     * @param activ the current Activity
     * @param email the email address to login as
     * @param password the password for User
     */
    public void login(final Activity activ, final String email, final String password) {
        this.mEmail = email;
        FirebaseAuth fbAuth = FirebaseAuth.getInstance();
        Log.d(TAG, "Attempting to Login User with email " + this.mEmail);
        if (!email.isEmpty() && !password.isEmpty()) {
            fbAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(activ, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Log.d(TAG, "Successfully Logged in User with email " + email);
                        Intent landing_activity = new Intent(activ.getApplicationContext(), LandingActivity.class);
                        activ.startActivity(landing_activity);
                    } else {
                        Toast.makeText(activ, "Invalid Username and/or Password", Toast.LENGTH_LONG).show();
                        Log.d(TAG, "Failed to login in User with email " + email);
                    }
                }
            });
        }
    }
}
