package com.example.board2deathapp.ui.login;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import com.example.board2deathapp.R;

public class SignUpFragment extends Fragment {

    private static String tag = "SIGNUP";
    private com.example.board2deathapp.ui.login.SignUpViewModel SignUpViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SignUpViewModel =
                ViewModelProviders.of(this).get(SignUpViewModel.class);
        final View root = inflater.inflate(R.layout.fragment_signup, container, false);
        Log.d("CHECKPOINT", "Sign up fragment created.");

        Button sign_up = root.findViewById(R.id.btnSignup);
        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user_name = ((EditText)root.findViewById(R.id.etUserName)).getText().toString();
                String password = ((EditText)root.findViewById(R.id.etPassword)).getText().toString();
                String email = ((EditText)root.findViewById(R.id.ediEmail)).getText().toString();

                registerInDatabase(user_name,password,email);
            }
        });
        return root;
    }

    private static boolean registerInDatabase(String user_name, String password, String email){
        Log.d(SignUpFragment.tag, "USER:"+user_name+" PASS:" +password+ " EMAIL:"+email);
        return true;
    }
}
