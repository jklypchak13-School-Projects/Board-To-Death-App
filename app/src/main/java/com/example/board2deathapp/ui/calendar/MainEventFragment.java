package com.example.board2deathapp.ui.calendar;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.board2deathapp.LandingActivity;
import com.example.board2deathapp.R;
import com.example.board2deathapp.models.DBResponse;
import com.example.board2deathapp.models.Event;
import com.example.board2deathapp.models.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Locale;

public class MainEventFragment extends Fragment implements View.OnClickListener {

    private Event mEvent;
    private FloatingActionButton mRsvpFAB;
    private TextView mRsvpTextView;
    private String mUsername;

    private boolean rsvpIsToggled = false;

    public MainEventFragment(Event event) {
        mEvent = event;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main_event, container, false);
        TextView titleTextView = view.findViewById(R.id.titleTextView);
        titleTextView.setText(mEvent.getTitle());
        TextView descriptionTextView = view.findViewById(R.id.descriptionTextView);
        descriptionTextView.setText(mEvent.getDesc());
        mRsvpFAB = view.findViewById(R.id.rsvpFAB);
        User user = ((LandingActivity) getActivity()).getUser();
        ImageButton editImgButton = view.findViewById(R.id.editImgButton);
        if (!user.isAdmin()) {
            editImgButton.setVisibility(View.INVISIBLE);
        }
        editImgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragMan = getFragmentManager();
                if (fragMan != null) {
                    FragmentTransaction fragTrans = fragMan.beginTransaction();
                    fragTrans.replace(R.id.nav_host_fragment, new AddEventFragment(mEvent));
                    fragTrans.commit();
                }
            }
        });
        mUsername = user.getUsername();
        if (mEvent.isRSVP(mUsername)) {
            mRsvpFAB.setImageResource(R.drawable.ic_favorite_white_24dp);
            rsvpIsToggled = true;
        }
        mRsvpTextView = view.findViewById(R.id.rsvpTextView);
        mRsvpTextView.setText(mEvent.rsvpToString());
        mRsvpFAB.setOnClickListener(this);
        final TextView dateTextView = view.findViewById(R.id.dateTextView);
        Locale locale = Locale.getDefault();
        dateTextView.setText(mEvent.getDateDuration(locale));
        ImageButton closeImgButton = view.findViewById(R.id.closeImgButton);
        closeImgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragMan = getFragmentManager();
                if (fragMan != null) {
                    FragmentTransaction fragTrans = fragMan.beginTransaction();
                    fragTrans.replace(R.id.nav_host_fragment, new CalendarFragment());
                    fragTrans.commit();
                }
            }
        });
        return view;
    }

    @Override
    public void onClick(View view) {
        if (!rsvpIsToggled) {
            mRsvpFAB.setImageResource(R.drawable.ic_favorite_white_24dp);
            mEvent.addRSVP(mUsername, new DBResponse(getActivity()) {
                @Override
                public <T> void onSuccess(T t) {
                    Toast.makeText(getActivity(), "Successfully RSVPed", Toast.LENGTH_SHORT).show();
                    mRsvpTextView.setText(mEvent.rsvpToString());
                }

                @Override
                public <T> void onFailure(T t) {
                    Toast.makeText(getActivity(), "Failed to RSVP", Toast.LENGTH_SHORT).show();
                    mRsvpTextView.setText(mEvent.rsvpToString());
                }
            });
            rsvpIsToggled = true;
        } else {
            mRsvpFAB.setImageResource(R.drawable.ic_favorite_border_white_24dp);
            mEvent.removeRSVP(mUsername, new DBResponse(getActivity()) {
                @Override
                public <T> void onSuccess(T t) {
                    Toast.makeText(getActivity(), "Successfully unRSVPed", Toast.LENGTH_SHORT).show();
                    mRsvpTextView.setText(mEvent.rsvpToString());
                }

                @Override
                public <T> void onFailure(T t) {
                    Toast.makeText(getActivity(), "Failed to remove RSVP", Toast.LENGTH_SHORT).show();
                    mRsvpTextView.setText(mEvent.rsvpToString());
                }
            });
            rsvpIsToggled = false;
        }
    }

}
