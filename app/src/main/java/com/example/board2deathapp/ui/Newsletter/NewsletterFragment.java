package com.example.board2deathapp.ui.Newsletter;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.board2deathapp.R;
import com.example.board2deathapp.models.Newsletter;
import com.example.board2deathapp.models.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class NewsletterFragment extends Fragment implements View.OnClickListener{

    private static String TAG = "NEWSLETTER_FRAGMENT";


    private EditText mDescription;
    private EditText mdate;
    private EditText mUsername;




    private User mUser;

    public static com.example.board2deathapp.ui.Newsletter.NewsletterFragment newInstance() {
        return new com.example.board2deathapp.ui.Newsletter.NewsletterFragment();
    }




    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        FirebaseUser fbUser = FirebaseAuth.getInstance().getCurrentUser();
        View root = inflater.inflate(R.layout.activity_post, container, false);



        this.mDescription = root.findViewById(R.id.TextDesc);
        this.mdate = root.findViewById(R.id.TextDate);
        this.mUsername = root.findViewById(R.id.TextUsername);





        final Button POSTButton = root.findViewById(R.id.buttonPOST);
        POSTButton.setOnClickListener(this);
        return root;
    }








    @Override
    public void onClick(View v) {


                final String newDescription = mDescription.getText().toString();
                final String newdate = mdate.getText().toString();
                final String newUsername = mUsername.getText().toString();
                final FirebaseUser fbUser = FirebaseAuth.getInstance().getCurrentUser();
                new Newsletter(newDescription,newdate,newUsername, getActivity());

    }
}
