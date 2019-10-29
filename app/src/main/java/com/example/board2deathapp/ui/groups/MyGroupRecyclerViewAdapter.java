package com.example.board2deathapp.ui.groups;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.board2deathapp.R;
import com.example.board2deathapp.models.Group;
import com.example.board2deathapp.ui.groups.GroupFragment.OnListFragmentInteractionListener;

import java.util.List;

public class MyGroupRecyclerViewAdapter extends RecyclerView.Adapter<MyGroupRecyclerViewAdapter.ViewHolder> {

    private final List<Group> mValues;
    private final OnListFragmentInteractionListener mListener;

    public MyGroupRecyclerViewAdapter(List<Group> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_group, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        Group current = mValues.get(position);
        holder.nameView.setText(current.getGroupName());
        holder.gameView.setText("Games: "+current.getGameString());
        holder.dateView.setText(current.getDate());
        holder.ownerView.setText(current.getOwner());
        holder.countView.setText(Integer.toString(current.maxGroupSize));

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
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
        private final TextView nameView;
        private final TextView ownerView;
        private final TextView dateView;
        private final TextView countView;
        private final TextView gameView;
        private Group mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            nameView = view.findViewById(R.id.group_name);
            ownerView = view.findViewById(R.id.owner);
            dateView = view.findViewById(R.id.group_date);
            countView = view.findViewById(R.id.group_count);
            gameView = view.findViewById(R.id.games);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + nameView.getText() + "'";
        }
    }
}
