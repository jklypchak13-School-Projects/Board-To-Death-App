package com.example.board2deathapp.ui.calendar;


import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.board2deathapp.LandingActivity;
import com.example.board2deathapp.R;
import com.example.board2deathapp.models.DBResponse;
import com.example.board2deathapp.models.Event;
import com.example.board2deathapp.models.ModelCollection;
import com.example.board2deathapp.models.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;


public class CalendarFragment extends Fragment implements View.OnClickListener {

    private static final String ARG_COLUMN_COUNT = "column-count";

    private int mColumnCount = 1;
    private OnListFragmentInteractionListener mListener;
    private MyEventRecyclerViewAdapter mEventRecyclerViewAdapter;

    private FloatingActionButton mAddFab;
    private ModelCollection<Event> mEventCollection;

    public CalendarFragment() {
    }

    @SuppressWarnings("unused")
    public static CalendarFragment newInstance(int columnCount) {
        CalendarFragment fragment = new CalendarFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
        mEventCollection = new ModelCollection<>(Event.class);
        mEventRecyclerViewAdapter = new MyEventRecyclerViewAdapter(mEventCollection.getItems(), mListener);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);
        mAddFab = view.findViewById(R.id.addFab);
        LandingActivity landingActivity = (LandingActivity) getActivity();
        if (landingActivity != null) {
            User currentUser = landingActivity.getUser();
            mAddFab.setEnabled(currentUser.isAdmin());
        }
        mAddFab.setOnClickListener(this);
        RecyclerView eventRecyclerView = view.findViewById(R.id.eventRecyclerView);
        Context context = view.getContext();
        if (mColumnCount <= 1) {
            eventRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        } else {
            eventRecyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
        }
        eventRecyclerView.setAdapter(mEventRecyclerViewAdapter);
        Query q = FirebaseFirestore.getInstance().collection("event").orderBy("start_date");
        mEventCollection.read_current(q, new DBResponse(getActivity()) {
            @Override
            public <T> void onSuccess(T t) {
                mEventRecyclerViewAdapter.notifyDataSetChanged();
            }

            @Override
            public <T> void onFailure(T t) {
            }
        });
        return view;
    }

    public void onClick(View view) {
        FragmentManager fragMan = getFragmentManager();
        if (fragMan == null) {
            return;
        }
        FragmentTransaction fragTrans = fragMan.beginTransaction();
        fragTrans.replace(R.id.nav_host_fragment, new AddEventFragment());
        fragTrans.commit();
    }

    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(Event item);
    }
}
