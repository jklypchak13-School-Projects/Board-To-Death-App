package com.example.board2deathapp.ui.login;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.board2deathapp.R;
import com.example.board2deathapp.models.User;

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

        this.mForgotPasswordButton = view.findViewById(R.id.forgotPasswordButton);
        this.mEmailEditText = view.findViewById(R.id.editTextEmail);
        this.mEmailEditText.addTextChangedListener(this);
        this.mPasswordEditText = view.findViewById(R.id.editTextPassword);
        this.mPasswordEditText.addTextChangedListener(this);
        return view;
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        boolean loginEnabled = this.mEmailEditText.getText().length() != 0 && this.mPasswordEditText.getText().length() != 0;
        this.mLoginButton.setEnabled(loginEnabled);
    }

    @Override
    public void afterTextChanged(Editable editable) {}

    @Override
    public void onClick(View view) {
        final String email = this.mEmailEditText.getText().toString();
        final String password = this.mPasswordEditText.getText().toString();
        new User().signIn(getActivity(), email, password);
    }
}
