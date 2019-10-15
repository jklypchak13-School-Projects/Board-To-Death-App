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

public class User implements OnCompleteListener<AuthResult> {
    private static String TAG = "USER";

    private String mUserName;
    private String mEmail;
    private boolean mIsAdmin;
    private Activity mActiv;

    public User() {}

    public String getUserName() {
        return this.mUserName;
    }

    public String getEmail() {
        return this.mEmail;
    }

    /**
     * Login to a User
     *
     * @param email email associated with an account
     * @param password password for the account
     */
    public void login(final Activity activ, final String email, final String password) {
        this.mEmail = email;
        this.mActiv = activ;
        FirebaseAuth fbAuth = FirebaseAuth.getInstance();
        Log.d(TAG, "Attempting to Login User with email " + this.mEmail);
        // Check to see if Empty email and password
        if (!email.isEmpty() && !password.isEmpty()) {
            fbAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this);
        }
    }

    /**
     * Override onComplete, handles when a task finishes
     *
     * @param task to handle its return value
     */
    @Override
    public void onComplete(@NonNull Task<AuthResult> task) {
        if (task.isSuccessful()) {
            Log.d(TAG, "Successfully Logged in User with email " + this.mEmail);
            Intent landing_activity = new Intent(this.mActiv.getApplicationContext(), LandingActivity.class);
            mActiv.startActivity(landing_activity);
        } else {
            Toast.makeText(this.mActiv, "Invalid Username and/or Password", Toast.LENGTH_LONG).show();
            Log.d(TAG, "Failed to login in User with email " + this.mEmail);
        }
    }
}
