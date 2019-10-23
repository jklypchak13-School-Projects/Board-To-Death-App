package com.example.board2deathapp.ui.boardgame;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.board2deathapp.LandingActivity;
import com.example.board2deathapp.R;
import com.example.board2deathapp.models.BoardGame;

public class AddBoardGameFragment extends DialogFragment {
    private static String TAG = "ADDGAME";

    private DialogInterface something;

    public AddBoardGameFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d(TAG, "Creating Dialog");
        final View v = inflater.inflate(R.layout.fragment_add_board_game_dialog, container, false);

        return v;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        final View v = inflater.inflate(R.layout.fragment_add_board_game_dialog,null);
        final EditText name_field = v.findViewById(R.id.a_game_name);
        final EditText description_field= v.findViewById(R.id.a_game_desc);
        final EditText time_field= v.findViewById(R.id.a_game_time);
        final EditText player_count_field= v.findViewById(R.id.a_game_count);
        builder.setView(v);
        builder.setPositiveButton("Create Game", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        String name = name_field.getText().toString();
                        String description = description_field.getText().toString();
                        String players = player_count_field.getText().toString();
                        int player_count  = Integer.parseInt(players);
                        double play_time = Double.parseDouble(time_field.getText().toString());
                        new BoardGame(name,description,/*((LandingActivity)getActivity()).getUser().getUserName()*/"jklypchak13",player_count,play_time,getActivity());
                    }
                });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        AddBoardGameFragment.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }


}
