package com.example.board2deathapp.ui.user_update;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.board2deathapp.AuthenticateUserDialog;
import com.example.board2deathapp.LandingActivity;
import com.example.board2deathapp.LoginActivity;
import com.example.board2deathapp.R;
import com.example.board2deathapp.UserUpdateViewModel;
import com.example.board2deathapp.models.DBResponse;
import com.example.board2deathapp.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UserUpdateFragment extends Fragment implements View.OnClickListener {
    private static String TAG = "USER_UPDATE_FRAGMENT";


    private UserUpdateViewModel mViewModel;

    private TextView mUpdateView;

    private EditText mUsernameEditText;
    private EditText mEmailEditText;
    private EditText mPasswordEditText;
    private EditText mPasswordEditTextConfirm;

    private boolean isValidPassword;
    private boolean isValidUsername;
    private User mUser;

    public static UserUpdateFragment newInstance() {
        return new UserUpdateFragment();
    }

    /**
     * Checks to see if newPassword == passwordConfirm, and that they both satisfy the requirements for a Password
     *
     * @param newPassword the new password
     * @param passwordConfirm the password confirmation
     */
    private void checkPassword(final String newPassword, final String passwordConfirm) {
        if (newPassword.equals(passwordConfirm)) {
            try {
                if (User.isValidPassword(newPassword)) {
                    this.isValidPassword = true;
                }
            } catch (Exception e) {
                Toast.makeText(getActivity(), e.toString(), Toast.LENGTH_LONG).show();
                this.isValidPassword = false;
            }
        } else {
            this.isValidPassword = false;
        }
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        FirebaseUser fbUser = FirebaseAuth.getInstance().getCurrentUser();
        View root = inflater.inflate(R.layout.fragment_update_user, container, false);
        if (fbUser == null) {
            getActivity().startActivity(new Intent(getActivity().getApplicationContext(), LoginActivity.class));
            return root;
        }
        this.mUpdateView = root.findViewById(R.id.updateTextView);
        setUsernameInUpdateText();
        this.mEmailEditText = root.findViewById(R.id.editTextEmail);
        this.mEmailEditText.setText(fbUser.getEmail());
        this.mPasswordEditText = root.findViewById(R.id.editTextPassword);
        this.mPasswordEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                final String newPassword = charSequence.toString();
                final String passwordConfirm = UserUpdateFragment.this.mPasswordEditTextConfirm.getText().toString();
                checkPassword(newPassword, passwordConfirm);
            }
            @Override
            public void afterTextChanged(Editable editable) {}
        });
        this.mPasswordEditTextConfirm = root.findViewById(R.id.editTextPasswordConfirm);
        this.mPasswordEditTextConfirm.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                final String passwordConfirm = charSequence.toString();
                final String newPassword = UserUpdateFragment.this.mPasswordEditText.getText().toString();
                checkPassword(newPassword, passwordConfirm);
            }
            @Override
            public void afterTextChanged(Editable editable) {}
        });
        this.mUsernameEditText = root.findViewById(R.id.editTextUsername);
        this.mUsernameEditText.setText(this.mUser.getUsername());
        this.mUsernameEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                final String newUsername = charSequence.toString();
                User.uniqueUsername(newUsername, new DBResponse(getActivity()) {
                    @Override
                    public <T> void onSuccess(T t) {
                        UserUpdateFragment.this.isValidUsername = true;
                    }
                    @Override
                    public <T> void onFailure(T t) {
                        Toast.makeText(getActivity(), "Username is not unique", Toast.LENGTH_SHORT);
                        UserUpdateFragment.this.isValidUsername = false;
                    }
                });

            }
            @Override
            public void afterTextChanged(Editable editable) {}
        });
        final Button updateButton = root.findViewById(R.id.buttonUpdate);
        updateButton.setOnClickListener(this);
        final Button deleteButton = root.findViewById(R.id.buttonDelete);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                authenticateUser(new DBResponse(getActivity()) {
                    @Override
                    public <T> void onSuccess(T t) {
                        mUser.delete(getActivity());
                    }

                    @Override
                    public <T> void onFailure(T t) {
                        Toast.makeText(getActivity(), "Failed to authenticate", Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
        return root;
    }

    /**
     * Sets the UpdateText to be "Update {username}" at the top of the UserUpdate Fragment
     */
    private void setUsernameInUpdateText() {
        Activity activity = getActivity();
        LandingActivity landingActivity = activity instanceof LandingActivity ? (LandingActivity)activity : null;
        if (landingActivity != null) {
            this.mUser = landingActivity.getUser();
            final String updateMsg = "Update " + this.mUser.getUsername();
            this.mUpdateView.setText(updateMsg);
        } else {
            if (activity != null) {
                activity.startActivity(new Intent(getActivity().getApplicationContext(), LoginActivity.class));
            }
        }
    }

    /**
     * Updates an email with Firebase
     *
     * @param newEmail new email to set the User's email to
     * @param fbUser the User
     */
    private void updateEmail(final String newEmail, final FirebaseUser fbUser) {
        final String previousEmail = fbUser.getEmail();
        Log.d(TAG, "Updating Email " + previousEmail + " -> " + newEmail);
        fbUser.updateEmail(newEmail).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Log.d(TAG, "Updated Email in Firebase " + previousEmail + " -> " + newEmail);
                   Toast.makeText(getActivity(), "Successfully Updated Email to " + newEmail, Toast.LENGTH_SHORT).show();
                } else {
                    Log.e(TAG, "Failed to update Email in Firebase " + previousEmail + " -/> " + newEmail);
                    Toast.makeText(getActivity(), "Failed to Update Email, email is already taken", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void updatePassword(final String newPassword, final FirebaseUser fbUser) {
        Log.d(TAG, "Updating Password for " + fbUser.getEmail());
        fbUser.updatePassword(newPassword).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
               if (task.isSuccessful()) {
                   Log.d(TAG, "Updated Password for " + fbUser.getEmail());
                   Toast.makeText(getActivity(), "Successfully Updated password", Toast.LENGTH_SHORT).show();
               } else {
                   Log.d(TAG, "Failed to Update Password for " + fbUser.getEmail());
                   Toast.makeText(getActivity(), "Failed to Update password", Toast.LENGTH_SHORT).show();
               }
            }
        });
    }

    private void authenticateUser(DBResponse dbResponse) {
        if (getActivity() == null) {
            return;
        }
        FragmentManager fragMan = getActivity().getSupportFragmentManager();
        DialogFragment dialogFragment = new AuthenticateUserDialog(dbResponse);
        dialogFragment.show(fragMan, "AUTHENTICATE_USER");
    }

    @Override
    public void onClick(View v) {
        authenticateUser(new DBResponse(getActivity()) {
            @Override
            public <T> void onSuccess(T t) {
                final String newEmail = mEmailEditText.getText().toString();
                final String newUsername = mUsernameEditText.getText().toString();
                final String newPassword = mPasswordEditText.getText().toString();
                final FirebaseUser fbUser = FirebaseAuth.getInstance().getCurrentUser();

                if (fbUser == null) {
                    getActivity().startActivity(new Intent(getActivity().getApplicationContext(), LoginActivity.class));
                    return;
                }
                if (!newEmail.equals(fbUser.getEmail())) {
                    updateEmail(newEmail, fbUser);
                }
                if (isValidUsername && !newUsername.equals(mUser.getUsername())) {
                    Log.d(TAG, "Updating Username " + mUser.getUsername() + " -> " + newUsername);
                    mUser.setUsername(newUsername);
                }
                if (isValidPassword) {
                    updatePassword(newPassword, fbUser);
                }
                mUser.update(new DBResponse(getActivity()) {
                    @Override
                    public <T> void onSuccess(T t) {
                        Toast.makeText(this.mActiv, "Successfully Updated " + UserUpdateFragment.this.mUser.getUsername(), Toast.LENGTH_LONG).show();
                        setUsernameInUpdateText();
                        Log.d(TAG, "Successfully updated User");
                    }
                    @Override
                    public <T> void onFailure(T t) {
                        Toast.makeText(this.mActiv, "Failed to Update, try again later", Toast.LENGTH_LONG).show();
                        this.mActiv.startActivity(new Intent(this.mActiv.getApplicationContext(), LandingActivity.class));
                    }
                });
            }
            @Override
            public <T> void onFailure(T t) {
                Toast.makeText(getActivity(), "Failed to Authenticate", Toast.LENGTH_LONG).show();
            }
        });
    }
}
