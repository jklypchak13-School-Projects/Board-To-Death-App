package com.example.board2deathapp.ui.boardgame;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.example.board2deathapp.LandingActivity;
import com.example.board2deathapp.R;
import com.example.board2deathapp.models.BoardGame;
import com.example.board2deathapp.models.DBResponse;
import com.example.board2deathapp.models.Group;
import com.example.board2deathapp.models.User;
import com.example.board2deathapp.ui.groups.EditGroupFragment;


public class DetailedBoardgameFragment extends DialogFragment {

    private BoardGame game;

    private BoardGameFragment f;
    private static String TAG = "DETAILEDBOARDGAME";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detailed_boardgame, container, false);
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        final User current_user = ((LandingActivity)getActivity()).getUser();
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        final View v = inflater.inflate(R.layout.fragment_detailed_boardgame,null);
        final TextView name = v.findViewById(R.id.gameName);
        final TextView description = v.findViewById(R.id.gameDescription);
        final TextView size = v.findViewById(R.id.gameCount);
        final TextView time = v.findViewById(R.id.gameTime);
        final TextView owner = v.findViewById(R.id.gameOwner);

        builder.setView(v);
        name.setText(game.getTitle());
        description.setText(game.getDescription());
        size.setText(game.getCount()+"");
        time.setText(Double.toString(game.getTime()));
        owner.setText(game.getOwner());
        builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                DetailedBoardgameFragment.this.getDialog().cancel();
            }
        });
        return builder.create();
    }

    public void setGame(BoardGame g ){
        this.game = g;
    }
    public void setFragment(BoardGameFragment fragment){this.f = fragment;}

    public void onDismiss(DialogInterface dialogInterface)
    {

        if(f != null) {
            f.displaying = false;
        }
    }
}
