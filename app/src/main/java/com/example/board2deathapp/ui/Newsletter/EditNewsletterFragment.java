package com.example.board2deathapp.ui.Newsletter;

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

import androidx.fragment.app.DialogFragment;

import com.example.board2deathapp.R;
import com.example.board2deathapp.models.Newsletter;
import com.example.board2deathapp.models.DBResponse;
import com.example.board2deathapp.models.Model;

public class EditNewsletterFragment extends DialogFragment {

    private static String TAG = "EDITNEWSLETTER";

    private DialogInterface something;
    private Newsletter newsletter;
    public EditNewsletterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d(TAG, "Creating Dialog");
        final View v = inflater.inflate(R.layout.edit_newsletter, container, false);

        return v;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        final View v = inflater.inflate(R.layout.edit_newsletter,null);
        final EditText Username = v.findViewById(R.id.eTextUsername);
        final EditText Description= v.findViewById(R.id.eTextDesc);
        final EditText Date= v.findViewById(R.id.eTextDate);

        final Button delete_button = v.findViewById(R.id.delete_game);
        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newsletter.delete(new DBResponse(getActivity()) {
                    @Override
                    public <T> void onSuccess(T t) {
                        Log.d("TOAST", "TOAST");
                        EditNewsletterFragment.this.getDialog().cancel();
                    }

                    @Override
                    public <T> void onFailure(T t) {
                        Log.d("TOAST","BURN TOAST");
                    }
                });
            }
        });
        Username.setText(newsletter.getUsername());
        Description.setText(newsletter.getDescription());
        Date.setText(newsletter.getdate());
        builder.setView(v);
        builder.setPositiveButton("Edit Newsletter", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                String username = Username.getText().toString();
                String description = Description.getText().toString();
                String date = Date.getText().toString();

                newsletter.username = username;
                newsletter.description = description;
                newsletter.date=date;

                newsletter.update(new DBResponse(getActivity()) {
                    @Override
                    public <T> void onSuccess(T t) {
                        Log.d("EDIT", "Editted the Newsletter!");
                    }

                    @Override
                    public <T> void onFailure(T t) {
                        Log.d("EDIT","Failed =(");
                    }
                });
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                com.example.board2deathapp.ui.Newsletter.EditNewsletterFragment.this.getDialog().cancel();
            }
        });
        return builder.create();
    }

    public void setNewsletter(Newsletter n){
        this.newsletter = n;
    }

}
