package com.example.board2deathapp;

import android.content.Intent;
import android.os.Bundle;

import com.example.board2deathapp.models.User;
import com.example.board2deathapp.ui.Newsletter.NewslettersListFragment;
import com.example.board2deathapp.ui.boardgame.BoardGameFragment;
import com.example.board2deathapp.ui.calendar.CalendarFragment;

import com.example.board2deathapp.ui.chat.AddChatFragment;
import com.example.board2deathapp.ui.chat.chatListFragment;
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

@SuppressWarnings("ALL")
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
                        frag = new chatListFragment();
                        break;
                    case R.id.navigation_boardgame:
                        frag = new BoardGameFragment();
                        break;
                    case R.id.navigation_newsletter:
                        frag = new NewslettersListFragment();
                        break;
                    case R.id.navigation_groups:
                        frag = new GroupFragment();
                        break;
                    case R.id.navigation_calendar:
                        frag = new CalendarFragment();
                        break;
                }
                if (frag != null) {
                    fragTrans.replace(R.id.nav_host_fragment, frag);
                    fragTrans.commit();
                }else{
                    Log.d("LANDING", "Pressed unsupported tab.");
                }
                return true;
            }
        });
        FragmentManager fragMan = getSupportFragmentManager();
        FragmentTransaction fragTrans = fragMan.beginTransaction();
        Fragment frag = new NewslettersListFragment();
        fragTrans.replace(R.id.nav_host_fragment, frag);
        fragTrans.commit();
    }
}
