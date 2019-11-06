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

public class AddGroupFragment extends DialogFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    public AddGroupFragment() {
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
        return inflater.inflate(R.layout.fragment_add_group, container, false);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        final User current_user = ((LandingActivity)getActivity()).getUser();
        final View v = inflater.inflate(R.layout.fragment_add_group,null);
        final EditText name = v.findViewById(R.id.group_name);
        final EditText description = v.findViewById(R.id.group_description);
        final EditText count = v.findViewById(R.id.group_count);
        final EditText game = v.findViewById(R.id.group_games);
        final EditText date = v.findViewById(R.id.group_date);
        final EditText location = v.findViewById(R.id.group_location);
        builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String g_name = name.getText().toString();
                String g_description = description.getText().toString();
                int g_count = Integer.parseInt(count.getText().toString());
                String g_game = game.getText().toString();
                String g_date = date.getText().toString();
                String g_location = location.getText().toString();
                Group newGroup = new Group(g_name, g_description, g_date, g_count, g_game, current_user.getUsername(),g_location);
                final Context c= getActivity();
                newGroup.create(new DBResponse(getActivity()) {
                    @Override
                    public <T> void onSuccess(T t) {
                        super.onSuccess(t);
                        Toast.makeText(c, "Succesfully created your Group", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                AddGroupFragment.this.getDialog().cancel();
            }
        });
        builder.setView(v);
        return builder.create();
    }


}
