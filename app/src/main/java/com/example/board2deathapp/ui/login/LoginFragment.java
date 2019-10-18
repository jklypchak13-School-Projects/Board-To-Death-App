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
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.board2deathapp.R;
import com.example.board2deathapp.models.User;

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
                if (f_manager != null) {
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
                String name = ((EditText) root.findViewById(R.id.etUserName)).getText().toString();
                String password = ((EditText) root.findViewById(R.id.etPassword)).getText().toString();
                new User(name).signIn(getActivity(), password);
            }
        });
        Log.d("CHECKPOINT","Login Fragment Created");
        return root;
    }
}