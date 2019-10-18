package com.example.board2deathapp.ui.login;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.board2deathapp.R;
import com.example.board2deathapp.models.DBResponse;
import com.example.board2deathapp.models.User;
import com.google.firebase.auth.FirebaseAuth;

public class SignUpFragment extends Fragment {

    private static String TAG = "SIGNUP";
    // TODO: better solution! This is bad, but suitable for the time being
    private boolean canSignUp = false;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_signup, container, false);
        Log.d("CHECKPOINT", "Sign up fragment created.");

        EditText usernameField = (EditText)root.findViewById(R.id.etUserName);
        usernameField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                User.uniqueUsername(editable.toString(), new DBResponse(getActivity()) {
                    @Override
                    public <T> void onSuccess(T t) {
                        Log.d(TAG, "Username is valid");
                        canSignUp = true;
                    }

                    @Override
                    public <T> void onFailure(T t) {
                        Log.d(TAG, "Username is already taken");
                        canSignUp = false;
                    }
                });
            }
        });
        EditText emailField = (EditText)root.findViewById(R.id.ediEmail);
        emailField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                User.uniqueEmail(editable.toString(), new DBResponse(getActivity()) {
                    @Override
                    public <T> void onSuccess(T t) {
                        Log.d(TAG, "Email is valid");
                        canSignUp = true;
                    }

                    @Override
                    public <T> void onFailure(T t) {
                        Log.d(TAG, "Email is already taken");
                        canSignUp = false;
                    }
                });
            }
        });

        final EditText passwordField = (EditText)root.findViewById(R.id.etPassword);
        passwordField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void afterTextChanged(Editable editable) {
                Log.d(TAG, "password is " + isValidPassword(passwordField.toString()));
                canSignUp = isValidPassword(passwordField.toString());
            }
        });
        Button signUp = root.findViewById(R.id.btnSignup);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user_name = ((EditText)root.findViewById(R.id.etUserName)).getText().toString();

                String password = ((EditText)root.findViewById(R.id.etPassword)).getText().toString();
                String email = ((EditText)root.findViewById(R.id.ediEmail)).getText().toString();
                User user = new User(email, user_name);
                if (canSignUp) {
                    user.signUp(getActivity(), password);
                }
            }
        });
        return root;
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
