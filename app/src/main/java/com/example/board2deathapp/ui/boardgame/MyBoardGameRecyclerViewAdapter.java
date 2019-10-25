package com.example.board2deathapp.ui.boardgame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.board2deathapp.LandingActivity;
import com.example.board2deathapp.R;
import com.example.board2deathapp.models.BoardGame;
import com.example.board2deathapp.ui.boardgame.BoardGameFragment.OnListFragmentInteractionListener;

import java.util.List;

import javax.annotation.OverridingMethodsMustInvokeSuper;

/**
 * {@link RecyclerView.Adapter} that can display a {@link BoardGame} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 * TODO: Replace the implementation with code for your data type.
 */
public class MyBoardGameRecyclerViewAdapter extends RecyclerView.Adapter<MyBoardGameRecyclerViewAdapter.ViewHolder> {

    private final List<BoardGame> mValues;
    private final OnListFragmentInteractionListener mListener;
    private Context c;

    public MyBoardGameRecyclerViewAdapter(List<BoardGame> items, OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_boardgame, parent, false);
        c = view.getContext();
        ViewHolder v = new ViewHolder(view);
        return v;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.nameView.setText(mValues.get(position).getTitle());
        holder.descriptionView.setText(mValues.get(position).getDescription());
        holder.countView.setText(Integer.toString(mValues.get(position).getCount()));
        holder.timeView.setText(Double.toString(mValues.get(position).getTime()));
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
        CardView card = holder.mView.findViewById(R.id.game_card);
        if(holder.mItem.getOwner().equals(((LandingActivity)c).getUser().getUsername())){
            card.setOnLongClickListener(holder);
        }
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {
        public final View mView;
        public final TextView nameView;
        public final TextView descriptionView;
        public final TextView countView;
        public final TextView timeView;
        public BoardGame mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            nameView = view.findViewById(R.id.game_name);
            descriptionView = view.findViewById(R.id.game_description);
            countView = view.findViewById(R.id.game_count);
            timeView = view.findViewById(R.id.game_time);




        }

        @Override
        public String toString() {
            return super.toString() + " '" + nameView.getText() + "'";
        }

        @Override
        public boolean onLongClick(View v) {
            FragmentManager fm = ((AppCompatActivity)c).getSupportFragmentManager();
            EditBoardGameFragment temp = new EditBoardGameFragment();
            temp.setGame(this.mItem);
            temp.show(fm,"ADD_BOARDGAME");
            return true;
        }

    }
}
