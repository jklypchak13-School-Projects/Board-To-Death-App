package com.example.board2deathapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.board2deathapp.models.User;
import com.example.board2deathapp.ui.login.LoginFragment;

public class LoginActivity extends AppCompatActivity {


    private static final String tag = "LOGIN";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        FragmentManager fm = getSupportFragmentManager();
        Fragment frag = fm.findFragmentById(R.id.login_fragment_container);
        if(frag == null){
            frag = new LoginFragment();
            fm.beginTransaction().add(R.id.login_fragment_container, frag).commit();
        }
    }


    /**
     * Creates an Intent, and starts the landing page activity.
     */
    public void navigateToLanding(User user){
        Log.d(tag, "Navigating to the Landing Page");
        Intent landActivity = new Intent(getApplicationContext(), LandingActivity.class);
        landActivity.putExtra("user", user);
        startActivity(landActivity);
    }

    @Override
    protected void onResume(){
        super.onResume();
    }
}
