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

public class SignUpFragment extends Fragment {

    private boolean isValidUsername;
    private boolean isValidPassword;

    private static String TAG = "SIGNUP";

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
                        isValidUsername = true;
                        Log.d(TAG, "Username is valid");
                    }

                    @Override
                    public <T> void onFailure(T t) {
                        isValidUsername = false;
                        Toast.makeText(this.mActiv, "Username is already taken", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        EditText emailField = (EditText)root.findViewById(R.id.ediEmail);

        final EditText passwordField = (EditText)root.findViewById(R.id.etPassword);
        passwordField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                try {
                    if (User.isValidPassword(charSequence.toString())) {
                        isValidPassword = true;
                    }
                } catch (Exception e) {
                    Toast.makeText(getActivity(), "Invalid Password: " + e.getMessage(), Toast.LENGTH_LONG).show();
                    isValidPassword = false;
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {}
        });
        Button signUp = root.findViewById(R.id.btnSignup);
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user_name = ((EditText)root.findViewById(R.id.etUserName)).getText().toString();

                String password = ((EditText)root.findViewById(R.id.etPassword)).getText().toString();
                String email = ((EditText)root.findViewById(R.id.ediEmail)).getText().toString();
                if (isValidPassword && isValidUsername) {
                    new User(user_name).signUp(getActivity(), email, password);
                }
            }
        });
        return root;
    }
}
