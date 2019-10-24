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
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.board2deathapp.R;
import com.example.board2deathapp.models.DBResponse;
import com.example.board2deathapp.models.User;

public class SignUpFragment extends Fragment implements View.OnClickListener {

    private EditText mUsernameEditText;
    private EditText mEmailEditText;
    private EditText mPasswordEditText;
    private EditText mPasswordConfirmEditText;

    private Button mSignUpButton;

    private ImageView mPasswordImg;
    private ImageView mPasswordConfirmImg;
    private ImageView mUsernameImg;

    private boolean isValidPassword;
    private boolean isValidUsername;

    public SignUpFragment() {
        // Required empty public constructor
    }

    private void enableSignUpButton() {
        int emailLength = this.mEmailEditText.length();
        this.mSignUpButton.setEnabled(emailLength != 0 && this.isValidPassword && isValidUsername);
    }

    private void checkPassword(int CROSS, int CHECK) {
        final String password = mPasswordEditText.getText().toString();
        final String passwordConfirm = mPasswordConfirmEditText.getText().toString();
        try {
            boolean isValid = User.isValidPassword(password);
            if (isValid && password.equals(passwordConfirm)) {
                mPasswordImg.setImageResource(CHECK);
                mPasswordConfirmImg.setImageResource(CHECK);
                mPasswordConfirmImg.setTooltipText("Confirm password is valid");
            } else if (isValid) {
                mPasswordImg.setImageResource(CHECK);
            }
            mPasswordImg.setTooltipText("Password is valid");
            isValidPassword = User.isValidPassword(password) && password.equals(passwordConfirm);
        } catch (Exception e) {
            mPasswordImg.setImageResource(CROSS);
            mPasswordImg.setTooltipText(e.getMessage());
            mPasswordConfirmImg.setImageResource(CROSS);
            mPasswordConfirmImg.setTooltipText("Password Confirmation must match Password");
        }
        mPasswordImg.setVisibility(View.VISIBLE);
        mPasswordConfirmImg.setVisibility(View.VISIBLE);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        final int CHECK = R.drawable.ic_check_white_24dp;
        final int CROSS = R.drawable.ic_close_white_24dp;

        this.mPasswordImg = view.findViewById(R.id.passwordImg);
        this.mUsernameImg = view.findViewById(R.id.usernameImg);
        this.mPasswordConfirmImg = view.findViewById(R.id.passwordConfirmImg);

        this.mSignUpButton = view.findViewById(R.id.signUpButton);
        this.mSignUpButton.setOnClickListener(this);
        final ImageButton backButton = view.findViewById(R.id.backImgButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragMan = getFragmentManager();
                if (fragMan != null) {
                    Fragment frag = new StartFragment();
                    fragMan.beginTransaction().replace(R.id.login_fragment_container, frag).commit();
                }
            }
        });

        this.mEmailEditText = view.findViewById(R.id.editTextEmail);
        this.mUsernameEditText = view.findViewById(R.id.editTextUsername);
        this.mUsernameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() == 0) {
                    isValidUsername = false;
                    mUsernameImg.setImageResource(CROSS);
                    mUsernameImg.setTooltipText("Must not be empty");
                } else {
                    User.uniqueUsername(charSequence.toString(), new DBResponse(getActivity()) {
                        @Override
                        public <T> void onSuccess(T t) {
                            isValidUsername = true;
                            mUsernameImg.setImageResource(CHECK);
                            mUsernameImg.setTooltipText("Username is Valid");
                        }

                        @Override
                        public <T> void onFailure(T t) {
                            isValidUsername = false;
                            mUsernameImg.setImageResource(CROSS);
                            mUsernameImg.setTooltipText("Username is already taken");
                        }
                    });
                }
                mUsernameImg.setVisibility(View.VISIBLE);
                enableSignUpButton();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        this.mPasswordEditText = view.findViewById(R.id.editTextPassword);

        this.mPasswordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                checkPassword(CROSS, CHECK);
                enableSignUpButton();
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        this.mPasswordConfirmEditText = view.findViewById(R.id.editTextPasswordConfirm);
        this.mPasswordConfirmEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                checkPassword(CROSS, CHECK);
                enableSignUpButton();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        return view;
    }

    public void onClick(View view) {
        final String email = this.mEmailEditText.getText().toString();
        final String password = this.mPasswordEditText.getText().toString();
        final String username = this.mUsernameEditText.getText().toString();
        new User(username).signUp(getActivity(), email, password);
    }
}
