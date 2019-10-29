package com.example.board2deathapp.ui.calendar;


import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.board2deathapp.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;


public class CalendarFragment extends Fragment implements View.OnClickListener {

    private FloatingActionButton mAddFab;

    public CalendarFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_calendar, container, false);
        mAddFab = view.findViewById(R.id.addFab);
        mAddFab.setOnClickListener(this);
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
}
