package com.example.board2deathapp.ui.boardgame;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.board2deathapp.LandingActivity;
import com.example.board2deathapp.R;
import com.example.board2deathapp.models.BoardGame;


public class AddBoardGameFragment extends DialogFragment {

    public AddBoardGameFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_board_game, container, false);
    }

    @Override @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        final View v = inflater.inflate(R.layout.fragment_add_board_game, null);
        builder.setView(v);

        //Get Various Text Fields
        final EditText name_field = v.findViewById(R.id.a_game_name);
        final EditText description_field = v.findViewById(R.id.a_game_desc);
        final EditText time_field = v.findViewById(R.id.a_game_time);
        final EditText player_count_field = v.findViewById(R.id.a_game_count);

        //Set Positive Action Button
        builder.setPositiveButton("Create Game", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                //Get Values from the Dialog
                String name = name_field.getText().toString();
                String description = description_field.getText().toString();
                String players = player_count_field.getText().toString();
                int player_count = Integer.parseInt(players);
                double play_time = Double.parseDouble(time_field.getText().toString());

                LandingActivity current_activity = (LandingActivity)getActivity();
                if(current_activity != null){
                    new BoardGame(name, description, current_activity.getUser().getUsername(), player_count, play_time, getActivity());
                }
                //Construct the new Object

            }
        });

        //Set Neutral Button to exit
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                AddBoardGameFragment.this.getDialog().cancel();
            }
        });
        return builder.create();
    }


}
