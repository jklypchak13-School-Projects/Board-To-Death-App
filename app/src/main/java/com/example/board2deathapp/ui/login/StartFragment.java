package com.example.board2deathapp.ui.login;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.board2deathapp.R;


public class StartFragment extends Fragment {
    private static String TAG = "START_FRAGMENT";


    public StartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_start, container, false);
        final Button signUpButton = view.findViewById(R.id.signUpButton);
        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragMan = getFragmentManager();
                if (fragMan != null) {
                    Fragment frag = new SignUpFragment();
                    FragmentTransaction fragTrans = fragMan.beginTransaction();
                    fragTrans.addToBackStack(null);
                    fragTrans.replace(R.id.login_fragment_container, frag).commit();
                }
            }
        });
        final Button loginButton = view.findViewById(R.id.loginButton);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragMan = getFragmentManager();
                if (fragMan != null) {
                    Fragment frag = new LoginFragment();
                    FragmentTransaction fragTrans = fragMan.beginTransaction();
                    fragTrans.addToBackStack(null);
                    fragTrans.replace(R.id.login_fragment_container, frag).commit();
                }
            }
        });
        return view;
    }

}
