package com.example.board2deathapp.ui.chat;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.board2deathapp.LandingActivity;
import com.example.board2deathapp.R;
import com.example.board2deathapp.models.Chat;
import com.example.board2deathapp.models.Newsletter;
import com.example.board2deathapp.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class chat_fragment extends Fragment implements View.OnClickListener {


    private static String TAG = "CHAT_FRAGMENT";


    private EditText mChat;
    private String current_user;






    public static com.example.board2deathapp.ui.chat.chat_fragment newInstance() {
        return new com.example.board2deathapp.ui.chat.chat_fragment();
    }




    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        FirebaseUser fbUser = FirebaseAuth.getInstance().getCurrentUser();
        View root = inflater.inflate(R.layout.activity_chat, container, false);

        LandingActivity a = (LandingActivity) getActivity();
        if(a != null){
            current_user = a.getUser().getUsername();
        }


        this.mChat = root.findViewById(R.id.TextChat);






        final Button CHATButton = root.findViewById(R.id.buttonCHAT);
        CHATButton.setOnClickListener(this);
        return root;
    }








    @Override
    public void onClick(View v) {


        final String newChat = mChat.getText().toString();

        final FirebaseUser fbUser = FirebaseAuth.getInstance().getCurrentUser();
        new Chat(newChat,current_user, getActivity());

    }
}
