package com.example.board2deathapp;

import android.content.Intent;
import android.os.Bundle;

import com.example.board2deathapp.models.User;
import com.example.board2deathapp.ui.boardgame.BoardGameFragment;
import com.example.board2deathapp.ui.groups.GroupFragment;
import com.example.board2deathapp.ui.user_update.UserUpdateFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;

public class LandingActivity extends AppCompatActivity {

    private User mUser;

    public User getUser() {
       return this.mUser;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);
        Intent intent = getIntent();
        if (intent.hasExtra("user")) {
            this.mUser = intent.getParcelableExtra("user");
        } else {
            this.startActivity(new Intent(this.getApplicationContext(), LoginActivity.class));
        }
        final ImageButton signOutButton = findViewById(R.id.signOutButton);
        signOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth fbAuth = FirebaseAuth.getInstance();
                fbAuth.signOut();
                LandingActivity.this.startActivity(new Intent(LandingActivity.this, LoginActivity.class));
            }
        });
        final ImageButton userUpdateButton = findViewById(R.id.updateUserButton);
        userUpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragMan = getSupportFragmentManager();
                FragmentTransaction fragTrans = fragMan.beginTransaction();
                fragTrans.replace(R.id.nav_host_fragment, new UserUpdateFragment());
                fragTrans.commit();
            }
        });
        BottomNavigationView navView = findViewById(R.id.nav_view);
        navView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                FragmentManager fragMan = getSupportFragmentManager();
                FragmentTransaction fragTrans = fragMan.beginTransaction();
                Fragment frag = null;
                switch(menuItem.getItemId()) {
                    case R.id.navigation_user:
                        frag = new UserUpdateFragment();
                        break;
                    case R.id.navigation_chat:
                        Log.d("LANDING","Pressed Chat");
                        break;
                    case R.id.navigation_boardgame:
                        frag = new BoardGameFragment();
                        break;
                    case R.id.navigation_newsletter:
                        Log.d("LANDING","Pressed Newsletter");
                        break;
                    case R.id.navigation_groups:
                        frag = new GroupFragment();
                        break;
                    case R.id.navigation_title_calendar:
                        Log.d("LANDING","Pressed Calendar");
                        break;
                }
                if (frag != null) {
                    fragTrans.replace(R.id.nav_host_fragment, frag);
                    fragTrans.commit();
                }else{
                    Log.d("LANDING", "Pressed on Supported tab.");
                }
                return true;
            }
        });
    }

    @Override
    protected void onStart(){
        super.onStart();
        Log.d("CHECKPOINT", "Successfully started the activity");
    }

    @Override
    protected void onResume(){
        super.onResume();
        Log.d("CHECKPOINT", "Successfully Resumed the activity");
    }

    @Override
    protected void onPause(){
        super.onPause();
        Log.d("CHECKPOINT", "Successfully paused the activity");
    }


    @Override
    protected void onStop(){
        super.onStop();
        Log.d("CHECKPOINT", "Successfully stopped the activity.");
    }

    @Override
    protected void onRestart(){
        super.onRestart();
        Log.d("CHECKPOINT", "Successfully restarted the activity.");
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        Log.d("CHECKPOINT", "Successfully destroyed the activity.");
    }
    
}
