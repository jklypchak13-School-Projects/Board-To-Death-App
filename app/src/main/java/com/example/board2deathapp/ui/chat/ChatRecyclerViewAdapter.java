package com.example.board2deathapp.ui.chat;

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
import com.example.board2deathapp.models.Chat;



import java.util.List;



public class ChatRecyclerViewAdapter extends RecyclerView.Adapter<ChatRecyclerViewAdapter.ViewHolder> {

    //The values the being displayed
    private final List<Chat> mValues;
    private final chatListFragment.OnListFragmentInteractionListener mListener;

    //The lists context
    private Context c;

    public ChatRecyclerViewAdapter(List<Chat> items, chatListFragment.OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_message, parent, false);
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
        holder.authorView.setText(mValues.get(position).getOwner());

        holder.dateView.setText(mValues.get(position).getDate());


        holder.chatView.setText(mValues.get(position).getChat());
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

    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {
        private final View mView;

        private final TextView chatView;
        public final TextView authorView;

        public final TextView dateView;



        private Chat mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;

            chatView = view.findViewById(R.id.postTitle);
            authorView = view.findViewById(R.id.postAuthor);

            dateView = view.findViewById(R.id.postDate);


        }
        @Override
        public String toString() {
            return super.toString() + " '" + chatView.getText() + "'";
        }


        @Override
        public boolean onLongClick(View v) {
            /*
            Navigate to the edit board game button.
             */
            FragmentManager fm = ((AppCompatActivity)c).getSupportFragmentManager();
            return true;
        }

    }
}
