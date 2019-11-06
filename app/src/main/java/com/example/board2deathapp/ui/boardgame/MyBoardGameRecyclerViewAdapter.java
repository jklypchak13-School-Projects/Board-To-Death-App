package com.example.board2deathapp.ui.boardgame;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.board2deathapp.LandingActivity;
import com.example.board2deathapp.R;
import com.example.board2deathapp.models.BoardGame;
import com.example.board2deathapp.models.User;
import com.example.board2deathapp.ui.boardgame.BoardGameFragment.OnListFragmentInteractionListener;

import java.util.List;


/**
 * {@link RecyclerView.Adapter} that can display a {@link BoardGame} and makes a call to the
 * specified {@link OnListFragmentInteractionListener}.
 */
public class MyBoardGameRecyclerViewAdapter extends RecyclerView.Adapter<MyBoardGameRecyclerViewAdapter.ViewHolder> {

    //The values the being displayed
    private final List<BoardGame> mValues;
    private final OnListFragmentInteractionListener mListener;

    //The lists context
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
        /*
         * Sets Up the values being displayed in the individual card
         */
        holder.mItem = mValues.get(position);
        holder.nameView.setText(mValues.get(position).getTitle());
        holder.descriptionView.setText(mValues.get(position).getDescriptionPreview());
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

        /*
         * Set the edit game page only accessible in the current user.
         */
        User current_user = ((LandingActivity)c).getUser();

        if(holder.mItem.getOwner().equals(current_user.getUsername())){
            CardView card = holder.mView.findViewById(R.id.game_card);
            card.setOnLongClickListener(holder);
        }else if(current_user.isAdmin() && holder.mItem.getOwner().equals("Board2Death")) {
            CardView card = holder.mView.findViewById(R.id.game_card);
            card.setOnLongClickListener(holder);
        }else{
            CardView card = holder.mView.findViewById(R.id.game_card);
            card.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    FragmentManager fm = ((AppCompatActivity)c).getSupportFragmentManager();
                    DetailedBoardgameFragment temp = new DetailedBoardgameFragment();
                    temp.setGame(holder.mItem);
                    temp.show(fm,"DetailedGame");
                    return true;
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {
        private final View mView;
        private final TextView nameView;
        private final TextView descriptionView;
        private final TextView countView;
        private final TextView timeView;
        private BoardGame mItem;

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
            /*
            Navigate to the edit board game button.
             */
            FragmentManager fm = ((AppCompatActivity)c).getSupportFragmentManager();
            EditBoardGameFragment temp = new EditBoardGameFragment();
            temp.setGame(this.mItem);
            temp.show(fm,"Edit Boardgame");
            return true;
        }

    }
}
