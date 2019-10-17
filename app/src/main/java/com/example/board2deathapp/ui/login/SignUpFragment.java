package com.example.board2deathapp.ui.login;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.board2deathapp.LoginActivity;
import com.example.board2deathapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUpFragment extends Fragment {

    private static String tag = "SIGNUP";

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_signup, container, false);
        Log.d("CHECKPOINT", "Sign up fragment created.");

        Button sign_up = root.findViewById(R.id.btnSignup);
        sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user_name = ((EditText)root.findViewById(R.id.etUserName)).getText().toString();

                if (user_name.isEmpty()) {
                    return;
                }

                String password = ((EditText)root.findViewById(R.id.etPassword)).getText().toString();

                if (!isValidPassword(password)) {
                    return;
                }
                String email = ((EditText)root.findViewById(R.id.ediEmail)).getText().toString();

                if(registerInDatabase(user_name,password,email)){

                    //TODO Add code to get user instance
                    LoginActivity current_activity = (LoginActivity) getActivity();
                    if(current_activity != null){
                        current_activity.navigateToLanding();
                    }
                }
            }
        });
        return root;
    }

    private boolean registerInDatabase(String user_name, final String password, String email){
        Log.d(SignUpFragment.tag, "USER:"+user_name+" PASS:" +password+ " EMAIL:"+email);
        final FirebaseAuth fba = FirebaseAuth.getInstance();
        LoginActivity current_activity = (LoginActivity)getActivity();
        if(current_activity != null) {
            fba.createUserWithEmailAndPassword(user_name, password)
                    .addOnCompleteListener(current_activity, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser user = fba.getCurrentUser();
                                if(user != null) {
                                    Log.d(tag, user.toString());
                                }
                                Toast.makeText(getActivity(), "Creation of account successful",
                                        Toast.LENGTH_SHORT).show();
                            } else {
                                Exception error = task.getException();
                                if(error != null){
                                    Log.d("Sign up Error", error.toString());
                                    Toast.makeText(getActivity(), "Create Account Failed, try again later",
                                            Toast.LENGTH_SHORT).show();
                                }

                            }
                        }
                    });
        }
        return true;
    }

    /**
     * Determines if a password is valid
     * Requirements:
     *  password.length >= 8
     *  password has at least one number
     *  password has at least one upper and lowercase letter
     *
     * @param password the password to be checked
     * @return Whether or not the password is valid
     */
    private boolean isValidPassword(String password) {
        String err_msg = "";
        if (password.length() < 8) {
            err_msg = "Password length must be at least 8 characters, but was " + password.length();
        } else if (!password.matches(".*\\d.*")) {
            err_msg = "Password must contain at least one digit";
        } else if (!password.matches(".*[a-z].*")) {
            err_msg = "Password must contain at least one lowercase character";
        } else if (!password.matches(".*[A-Z].*")) {
            err_msg = "Password must contain at least one uppercase character";
        }
        if (!err_msg.isEmpty()) {
            Log.w("LOGIN ERROR", err_msg);
            Toast.makeText(getActivity(), err_msg, Toast.LENGTH_SHORT).show();
        }
        return err_msg.isEmpty();
    }
}