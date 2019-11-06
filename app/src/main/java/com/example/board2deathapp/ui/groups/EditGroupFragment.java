package com.example.board2deathapp.ui.groups;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.example.board2deathapp.LandingActivity;
import com.example.board2deathapp.R;
import com.example.board2deathapp.models.DBResponse;
import com.example.board2deathapp.models.Group;
import com.example.board2deathapp.models.User;

public class EditGroupFragment extends DialogFragment {

    private Group group;
    public EditGroupFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_edit_group, container, false);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        final User current_user = ((LandingActivity)getActivity()).getUser();
        final View v = inflater.inflate(R.layout.fragment_edit_group,null);

        final EditText name = v.findViewById(R.id.group_name);
        final EditText description = v.findViewById(R.id.group_description);
        final EditText count = v.findViewById(R.id.group_count);
        final EditText game = v.findViewById(R.id.group_games);
        final EditText date = v.findViewById(R.id.group_date);
        final EditText location = v.findViewById(R.id.group_location);

        name.setText(group.getGroupName());
        description.setText(group.getDescription());
        count.setText(group.getMaxSize());
        game.setText(group.getGameString());
        date.setText(group.getDate());
        location.setText(group.getLocation());
        builder.setPositiveButton("Edit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                group.setGroupName(name.getText().toString());
                group.setDescription(description.getText().toString());
                group.setCount(Integer.parseInt(count.getText().toString()));
                group.setGame(game.getText().toString());
                group.setDate(date.getText().toString());
                group.setLocation(location.getText().toString());
                final Context c= getActivity();
                group.update(new DBResponse(getActivity()) {
                    @Override
                    public <T> void onSuccess(T t) {
                        super.onSuccess(t);
                        Toast.makeText(c, "Succesfully updated your Group", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        builder.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                group.delete(new DBResponse(getActivity()) {
                    @Override
                    public <T> void onSuccess(T t) {
                        super.onSuccess(t);
                    }
                });
            }
        });
        builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                EditGroupFragment.this.getDialog().cancel();
            }
        });
        builder.setView(v);

        return builder.create();
    }
    public void setGroup(Group g){
        this.group = g;
    }

}
