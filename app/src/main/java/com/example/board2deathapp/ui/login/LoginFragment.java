package com.example.board2deathapp.ui.login;

import android.app.Activity;
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
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.board2deathapp.LoginActivity;
import com.example.board2deathapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginFragment extends Fragment {


    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
        final View root = inflater.inflate(R.layout.fragment_login, container, false);
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

                // Validate If entered credentials are valid
                if (isUser(name,password)) {
                    FirebaseAuth fba = FirebaseAuth.getInstance();
                    Activity current_activity = (Activity) getContext();
                    if( current_activity != null) {
                        fba.signInWithEmailAndPassword(name, password)
                                .addOnCompleteListener((Activity) getContext(), new OnCompleteListener<AuthResult>() {
                                    @Override
                                    public void onComplete(@NonNull Task<AuthResult> task) {
                                        if (task.isSuccessful()) {
                                            // Sign in success, update UI with the signed-in user's information
                                            Log.d("LOGIN", "signInWithEmail:success");
                                            LoginActivity current_activity = (LoginActivity) getActivity();
                                            if (current_activity != null) {
                                                current_activity.navigateToLanding();
                                            }

                                        } else {
                                            // If sign in fails, display a message to the user.
                                            Log.w("LOGIN ERROR", "signInWithEmail:failure", task.getException());
                                            Toast.makeText(getActivity(), "Authentication failed.",
                                                    Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    }
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
     * @return whether or not the given user is valid in the database
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