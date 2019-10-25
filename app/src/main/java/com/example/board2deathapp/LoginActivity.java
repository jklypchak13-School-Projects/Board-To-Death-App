package com.example.board2deathapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.board2deathapp.models.DBResponse;
import com.example.board2deathapp.models.User;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.board2deathapp.ui.login.StartFragment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private static final String tag = "LOGIN";

    /**
     * Redirects the User to the LandingActivity, if they are already signed in
     */
    private void alreadySignedInRedirect() {
        final FirebaseAuth fbAuth = FirebaseAuth.getInstance();
        if (fbAuth.getCurrentUser() != null) {
            FirebaseUser fbUser = fbAuth.getCurrentUser();
            User user = new User(fbUser.getEmail());
            user.get(new DBResponse(LoginActivity.this) {
                @Override
                public <T> void onSuccess(T t) {
                    User user = (User) t;
                    LoginActivity.this.navigateToLanding(user);
                }

                @Override
                public <T> void onFailure(T t) {
                    fbAuth.signOut();
                }
            });
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        FragmentManager fm = getSupportFragmentManager();
        Fragment frag = fm.findFragmentById(R.id.login_fragment_container);
        if (frag == null) {
            frag = new StartFragment();
            fm.beginTransaction().add(R.id.login_fragment_container, frag).commit();
        }
    }


    /**
     * Creates an Intent, and starts the landing page activity.
     */
    public void navigateToLanding(User user) {
        Log.d(tag, "Navigating to the Landing Page");
        Intent landActivity = new Intent(getApplicationContext(), LandingActivity.class);
        landActivity.putExtra("user", user);
        startActivity(landActivity);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        alreadySignedInRedirect();
    }
}
