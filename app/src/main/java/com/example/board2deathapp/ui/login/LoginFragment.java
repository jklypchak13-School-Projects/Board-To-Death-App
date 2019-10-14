package com.example.board2deathapp.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.board2deathapp.LandingActivity;
import com.example.board2deathapp.R;

public class LoginFragment extends Fragment {

    private LoginViewModel loginViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        loginViewModel =
                ViewModelProviders.of(this).get(LoginViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_login, container, false);
        final TextView textView = root.findViewById(R.id.text_login);
        loginViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        final Button sign_up = root.findViewById(R.id.btnSignup);
        sign_up.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Fragment sign_up_page = new SignUpFragment();
                Log.d("CHECKPOINT", "switching fragments");
                FragmentManager f_manager = getFragmentManager();
                if( f_manager != null) {
                    FragmentTransaction transaction = f_manager.beginTransaction();
                    transaction.replace(R.id.login_fragment_container, sign_up_page);
                    transaction.addToBackStack(null);
                    transaction.commit();
                    Log.d("CHECKPOINT", "switched fragments");
                }
            }
        });

        final Button login = root.findViewById(R.id.btnLogin);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Read the Username and Password
                String name = ((EditText)root.findViewById(R.id.etUserName)).getText().toString();
                String password = ((EditText)root.findViewById(R.id.etPassword)).getText().toString();

                //Validate If entered credentials are valid
                if(isUser(name,password)){
                    Intent land_activity = new Intent(getActivity().getApplicationContext(), LandingActivity.class);
                    startActivity(land_activity);
                }

            }
        });
        Log.d("CHECKPOINT","Login Fragment Created");
        return root;
    }

    /**
     * Determines if a given username/password matches those of a username
     * in our database
     *
     * @param user_name the username to be checked
     * @param password the password to be checked
     * @return wheter or not the given user is valid in the database
     */
    private static boolean isUser(String user_name, String password){
        if(!user_name.equals("") && !password.equals("")){
            Log.d("LOGIN", user_name);
            Log.d("LOGIN", password);
            return true;
        }
        return false;
    }
}