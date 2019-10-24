package com.example.board2deathapp.ui.login;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.board2deathapp.R;
import com.example.board2deathapp.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class LoginFragment extends Fragment implements View.OnClickListener, TextWatcher {

    private Button mLoginButton;
    private Button mForgotPasswordButton;
    private ImageButton mBackButton;

    private EditText mEmailEditText;
    private EditText mPasswordEditText;

    public LoginFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);
        this.mLoginButton = view.findViewById(R.id.loginButton);
        this.mLoginButton.setOnClickListener(this);
        this.mBackButton = view.findViewById(R.id.backImgButton);
        this.mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragMan = getFragmentManager();
                if (fragMan != null) {
                    Fragment frag = new StartFragment();
                    fragMan.beginTransaction().replace(R.id.login_fragment_container, frag).commit();
                }
            }
        });

        this.mForgotPasswordButton = view.findViewById(R.id.forgotPasswordButton);
        this.mForgotPasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = mEmailEditText.getText().toString();
                if (email.isEmpty()) {
                    Toast.makeText(getActivity(), "A valid email is required", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!isWellFormedEmail(email)) {
                    Toast.makeText(getActivity(), "Email is not well formed", Toast.LENGTH_SHORT).show();
                    return;
                }
                FirebaseAuth fbAuth = FirebaseAuth.getInstance();
                fbAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getActivity(), "Email sent to " + email, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getActivity(), "Failed to send Email to " + email, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        this.mEmailEditText = view.findViewById(R.id.editTextEmail);
        this.mEmailEditText.addTextChangedListener(this);
        this.mPasswordEditText = view.findViewById(R.id.editTextPassword);
        this.mPasswordEditText.addTextChangedListener(this);
        return view;
    }

    private static boolean isWellFormedEmail(final String email) {
       return email.matches("[\\w\\d]+@[\\w\\d]+\\.[a-zA-Z]+");
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        boolean loginEnabled = this.mEmailEditText.getText().length() != 0 && this.mPasswordEditText.getText().length() != 0;
        this.mLoginButton.setEnabled(loginEnabled);
    }

    @Override
    public void afterTextChanged(Editable editable) {
    }

    @Override
    public void onClick(View view) {
        final String email = this.mEmailEditText.getText().toString();
        final String password = this.mPasswordEditText.getText().toString();
        new User().signIn(getActivity(), email, password);
    }
}
