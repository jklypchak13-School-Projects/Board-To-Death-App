package com.example.board2deathapp.ui.Newsletter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import com.example.board2deathapp.LandingActivity;
import com.example.board2deathapp.R;
import com.example.board2deathapp.models.DBResponse;
import com.example.board2deathapp.models.Newsletter;
import com.example.board2deathapp.models.User;

public class AddNewsletterFragment extends DialogFragment {

    private static String TAG = "ADDNEWSLETTER";

    private DialogInterface something;

    public AddNewsletterFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d(TAG, "Creating Dialog");
        final View v = inflater.inflate(R.layout.fragment_newsletter_dialog, container, false);

        return v;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        final User current_user = ((LandingActivity) getActivity()).getUser();
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        final View v = inflater.inflate(R.layout.fragment_newsletter_dialog, null);
        final EditText name_field = v.findViewById(R.id.postTitle);
        final EditText description_field = v.findViewById(R.id.postContent
        );

        builder.setView(v);
        builder.setPositiveButton("Create Newsletter", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                String title = name_field.getText().toString();
                String description = description_field.getText().toString();

                Newsletter new_post = new Newsletter(title, description, current_user.getUsername(), getActivity());
                new_post.create(new DBResponse(getActivity()) {
                    @Override
                    public <T> void onSuccess(T t) {
                        Log.d(TAG, t.toString());
                        if (getActivity() != null) {
                            Toast.makeText(getActivity().getApplicationContext(), "Successfully Created a Newsletter", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public <T> void onFailure(T t) {
                        if (getActivity() != null) {
                            Toast.makeText(getActivity().getApplicationContext(), "There was an issue while creating your Newsletter.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
        builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                AddNewsletterFragment.this.getDialog().cancel();
            }
        });
        return builder.create();
    }


}
