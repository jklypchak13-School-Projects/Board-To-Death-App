package com.example.board2deathapp.ui.groups;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.board2deathapp.LandingActivity;
import com.example.board2deathapp.R;
import com.example.board2deathapp.models.BoardGame;
import com.example.board2deathapp.models.DBResponse;
import com.example.board2deathapp.models.Group;
import com.example.board2deathapp.models.User;
import com.example.board2deathapp.ui.boardgame.EditBoardGameFragment;


public class DetailedGroupFragment extends DialogFragment {

    private Group group;
    private static String TAG = "DETAILEDGROUP";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detailed_group, container, false);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        final User current_user = ((LandingActivity)getActivity()).getUser();
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        final View v = inflater.inflate(R.layout.fragment_detailed_group,null);
        final TextView name = v.findViewById(R.id.group_name);
        final TextView description = v.findViewById(R.id.group_description);
        final TextView size = v.findViewById(R.id.group_size);
        final TextView players = v.findViewById(R.id.group_players);
        final TextView games = v.findViewById(R.id.group_games);
        final TextView date = v.findViewById(R.id.group_date);
        final TextView owner = v.findViewById(R.id.group_owner);
        final boolean canJoin= group.canJoin(current_user);


        name.setText(group.getGroupName());
        description.setText(group.getDescription());
        size.setText(group.users.size()+"/"+group.maxGroupSize);
        players.setText(group.getPlayerString());
        games.setText(group.getGameString());
        date.setText(group.getDate());
        owner.setText(group.getOwner());
        builder.setView(v);
        String positiveMessage = "Leave";
        if(canJoin){
            positiveMessage = "Join";
        }
        builder.setPositiveButton(positiveMessage, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                if(canJoin) {
                    group.join(current_user);
                    group.update(new DBResponse(getActivity()) {
                        @Override
                        public <T> void onSuccess(T t) {
                            super.onSuccess(t);
                        }
                    });
                }else{
                    group.leave(current_user);
                    group.update(new DBResponse(getActivity()) {
                        @Override
                        public <T> void onSuccess(T t) {
                            super.onSuccess(t);
                        }
                    });
                }
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                DetailedGroupFragment.this.getDialog().cancel();
            }
        });
        return builder.create();
    }

    public void setGroup(Group g){
        this.group = g;
    }
}
