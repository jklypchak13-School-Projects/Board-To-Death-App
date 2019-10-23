package com.example.board2deathapp;

import android.content.Intent;
import android.os.Bundle;

import com.example.board2deathapp.models.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import android.util.Log;

public class LandingActivity extends AppCompatActivity {

    private User mUser;

    public User getUser() {
       return this.mUser;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        if (intent.hasExtra("user")) {
            this.mUser = intent.getParcelableExtra("user");
        } else {
            this.startActivity(new Intent(this.getApplicationContext(), LoginActivity.class));
        }
        setContentView(R.layout.activity_landing);
        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications, R.id.navigation_user)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);
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
