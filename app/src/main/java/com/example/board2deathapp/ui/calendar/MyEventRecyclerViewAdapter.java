package com.example.board2deathapp.ui.calendar;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.board2deathapp.R;
import com.example.board2deathapp.models.Event;
import com.example.board2deathapp.ui.calendar.CalendarFragment.OnListFragmentInteractionListener;

import java.util.List;
import java.util.Locale;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Event} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyEventRecyclerViewAdapter extends RecyclerView.Adapter<MyEventRecyclerViewAdapter.ViewHolder> {

    private final List<Event> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyEventRecyclerViewAdapter(List<Event> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_event_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Locale locale = Locale.getDefault();
        holder.mInner = mValues.get(position);
        holder.mDescriptionTextView.setText(holder.mInner.getDescOneSentence());
        holder.mTitleTextView.setText(holder.mInner.getTitle());
        holder.mDateTextView.setText(holder.mInner.getDateDuration(locale));

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onListFragmentInteraction(holder.mInner);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final View mView;
        private final TextView mTitleTextView;
        private final TextView mDateTextView;
        private final TextView mDescriptionTextView;

        private Event mInner;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mTitleTextView = view.findViewById(R.id.eventTitleTextView);
            mDateTextView = view.findViewById(R.id.dateTextView);
            mDescriptionTextView = view.findViewById(R.id.descriptionTextView);
        }
    }
}
