package com.example.board2deathapp.ui.home;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import com.example.board2deathapp.R;

public class SignUpFragment extends Fragment {

    private SignUpViewModel SignUpViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SignUpViewModel =
                ViewModelProviders.of(this).get(SignUpViewModel.class);
        View root = inflater.inflate(R.layout.fragment_signup, container, false);
        Log.d("CHECKPOINT", "Sign up fragment created.");
        return root;
    }
}
