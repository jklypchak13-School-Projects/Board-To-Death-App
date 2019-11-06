package com.example.board2deathapp.ui.calendar;


import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;

import com.example.board2deathapp.LandingActivity;
import com.example.board2deathapp.R;
import com.example.board2deathapp.models.DBResponse;
import com.example.board2deathapp.models.Event;
import com.example.board2deathapp.models.ModelCollection;
import com.example.board2deathapp.models.User;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.Calendar;
import java.util.Date;


public class CalendarFragment extends Fragment implements View.OnClickListener {

    private static final String ARG_COLUMN_COUNT = "column-count";

    private int mColumnCount = 1;
    private MyEventRecyclerViewAdapter mEventRecyclerViewAdapter;

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
        OnListFragmentInteractionListener listener = new OnListFragmentInteractionListener();
        mEventRecyclerViewAdapter = new MyEventRecyclerViewAdapter(mEventCollection.getItems(), listener);
    }

    private void updateWithDatesOnDay(Date day) {
        Calendar calendarStartDay = Calendar.getInstance();
        calendarStartDay.setTime(day);
        calendarStartDay.set(Calendar.HOUR_OF_DAY, 0);
        calendarStartDay.set(Calendar.MINUTE, 0);
        Calendar calendarEndOfDay = Calendar.getInstance();
        calendarEndOfDay.setTime(calendarStartDay.getTime());
        calendarEndOfDay.add(Calendar.DAY_OF_MONTH, 1);
        Query q = FirebaseFirestore.getInstance()
                .collection("event")
                .whereGreaterThanOrEqualTo("start_date", calendarStartDay.getTime())
                .whereLessThanOrEqualTo("start_date", calendarEndOfDay.getTime()).orderBy("start_date", Query.Direction.DESCENDING);
        mEventCollection.read_current(q, new DBResponse(getActivity()) {
            @Override
            public <T> void onSuccess(T t) {
                mEventRecyclerViewAdapter.notifyDataSetChanged();
            }

            @Override
            public <T> void onFailure(T t) {
            }
        });
    }

    @SuppressLint("RestrictedApi")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);
        final FloatingActionButton addFab = view.findViewById(R.id.addFab);
        final CalendarView calendarView = view.findViewById(R.id.calendarView);
        LandingActivity landingActivity = (LandingActivity) getActivity();
        if (landingActivity != null) {
            User currentUser = landingActivity.getUser();
            if (!currentUser.isAdmin()) {
                addFab.setVisibility(View.INVISIBLE);
            }
        }
        addFab.setOnClickListener(this);
        RecyclerView eventRecyclerView = view.findViewById(R.id.eventRecyclerView);
        Context context = view.getContext();
        if (mColumnCount <= 1) {
            eventRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        } else {
            eventRecyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
        }
        eventRecyclerView.setAdapter(mEventRecyclerViewAdapter);
        Date today = Calendar.getInstance().getTime();
        updateWithDatesOnDay(today);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int day) {
               Calendar calendar = Calendar.getInstance();
               calendar.set(year, month, day);
               updateWithDatesOnDay(calendar.getTime());
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
        fragTrans.replace(R.id.nav_host_fragment, new AddEventFragment(null));
        fragTrans.commit();
    }

    public class OnListFragmentInteractionListener {
        void onListFragmentInteraction(Event item) {
            FragmentManager fragMan = getFragmentManager();
            if (fragMan != null) {
                FragmentTransaction fragTrans = fragMan.beginTransaction();
                fragTrans.replace(R.id.nav_host_fragment, new MainEventFragment(item));
                fragTrans.commit();
            }
        }
    }
}
