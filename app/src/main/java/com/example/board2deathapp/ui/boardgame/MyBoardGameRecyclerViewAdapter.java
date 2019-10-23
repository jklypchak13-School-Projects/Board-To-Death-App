//package com.example.board2deathapp.ui.boardgame;
//
//import androidx.recyclerview.widget.RecyclerView;
//
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.TextView;
//
//import com.example.board2deathapp.R;
//import com.example.board2deathapp.models.BoardGame;
//import com.example.board2deathapp.ui.boardgame.BoardGameFragment.OnListFragmentInteractionListener;
//
//import java.util.List;
//
///**
// * {@link RecyclerView.Adapter} that can display a {@link BoardGame} and makes a call to the
// * specified {@link OnListFragmentInteractionListener}.
// * TODO: Replace the implementation with code for your data type.
// */
//public class MyBoardGameRecyclerViewAdapter extends RecyclerView.Adapter<MyBoardGameRecyclerViewAdapter.ViewHolder> {
//
//    private final List<BoardGame> mValues;
//    private final OnListFragmentInteractionListener mListener;
//
//    public MyBoardGameRecyclerViewAdapter(List<BoardGame> items, OnListFragmentInteractionListener listener) {
//        mValues = items;
//        mListener = listener;
//    }
//
//    @Override
//    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext())
//                .inflate(R.layout.fragment_boardgame, parent, false);
//        return new ViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(final ViewHolder holder, int position) {
//        holder.mItem = mValues.get(position);
//        holder.mIdView.setText(mValues.get(position).getOwner());
//        holder.mContentView.setText(mValues.get(position).getTitle());
//
//        holder.mView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (null != mListener) {
//                    // Notify the active callbacks interface (the activity, if the
//                    // fragment is attached to one) that an item has been selected.
//                    mListener.onListFragmentInteraction(holder.mItem);
//                }
//            }
//        });
//    }
//
//    @Override
//    public int getItemCount() {
//        return mValues.size();
//    }
//
//    public class ViewHolder extends RecyclerView.ViewHolder {
//        public final View mView;
//        public final TextView mIdView;
//        public final TextView mContentView;
//        public BoardGame mItem;
//
//        public ViewHolder(View view) {
//            super(view);
//            mView = view;
//            mIdView = view.findViewById(R.id.item_number);
//            mContentView = view.findViewById(R.id.content);
//        }
//
//        @Override
//        public String toString() {
//            return super.toString() + " '" + mContentView.getText() + "'";
//        }
//    }
//}

import androidx.appcompat.app.AppCompatActivity;
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
        holder.mIdView.setText(mValues.get(position).getOwner());
        holder.mContentView.setText(mValues.get(position).getTitle());

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

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public final View mView;
        public final TextView mIdView;
        public final TextView mContentView;
        public BoardGame mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            mIdView = view.findViewById(R.id.item_number);
            mContentView = view.findViewById(R.id.content);
            Button b = view.findViewById(R.id.edit_game);
            b.setOnClickListener(this);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + mContentView.getText() + "'";
        }

        @Override
        public void onClick(View v) {
            FragmentManager fm = ((AppCompatActivity)c).getSupportFragmentManager();
            EditBoardGameFragment temp = new EditBoardGameFragment();
            temp.setGame(this.mItem);
            temp.show(fm,"ADD_BOARDGAME");
        }

    }
}
