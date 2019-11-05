package com.example.board2deathapp.ui.chat;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.example.board2deathapp.LandingActivity;
import com.example.board2deathapp.R;
import com.example.board2deathapp.models.BoardGame;
import com.example.board2deathapp.models.Chat;
import com.example.board2deathapp.models.DBResponse;
import com.example.board2deathapp.models.User;

public class AddChatFragment extends DialogFragment {
    private Chat ch;

    public AddChatFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.add_chat, container, false);
    }

    @Override @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        final User current_user = ((LandingActivity)getActivity()).getUser();
        final View v = inflater.inflate(R.layout.add_chat, null);
        builder.setView(v);

        //Get Various Text Fields

        final EditText chat_field = v.findViewById(R.id.postMessage1);
        builder.setView(v);
        builder.setPositiveButton("Send", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {

                String chat = chat_field.getText().toString();

                new Chat(chat, current_user.getUsername(), getActivity());
            }
        });



        //Set Positive Action Button
        builder.setPositiveButton("Create chat", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                //Get Values from the Dialog

                String chat = chat_field.getText().toString();


                LandingActivity current_activity = (LandingActivity)getActivity();
                if(current_activity != null){
                    new Chat(chat, current_activity.getUser().getUsername(), getActivity());
                }
                //Construct the new Object

            }
        });

        //Set Neutral Button to exit
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                com.example.board2deathapp.ui.chat.AddChatFragment.this.getDialog().cancel();
            }
        });
        return builder.create();
    }

    public void setChat(Chat g){
        this.ch = g;
    }


}
