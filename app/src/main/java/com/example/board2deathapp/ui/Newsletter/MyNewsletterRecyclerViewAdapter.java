package com.example.board2deathapp.ui.Newsletter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.board2deathapp.LandingActivity;
import com.example.board2deathapp.R;

import com.example.board2deathapp.models.BoardGame;
import com.example.board2deathapp.models.Newsletter;
import com.example.board2deathapp.ui.Newsletter.NewslettersListFragment;
import com.example.board2deathapp.ui.Newsletter.NewsletterFragment;

import java.util.List;

public class MyNewsletterRecyclerViewAdapter extends RecyclerView.Adapter<MyNewsletterRecyclerViewAdapter.ViewHolder> {


    private final List<Newsletter> mValues;
    private final NewslettersListFragment.OnListFragmentInteractionListener mListener;
    private Context c;

    public MyNewsletterRecyclerViewAdapter(List<Newsletter> items, NewslettersListFragment.OnListFragmentInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public com.example.board2deathapp.ui.Newsletter.MyNewsletterRecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_newsletter, parent, false);
        c = view.getContext();
        com.example.board2deathapp.ui.Newsletter.MyNewsletterRecyclerViewAdapter.ViewHolder v = new com.example.board2deathapp.ui.Newsletter.MyNewsletterRecyclerViewAdapter.ViewHolder(view);
        return v;
    }



    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.nameView.setText(mValues.get(position).getUsername());
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
        CardView card = holder.mView.findViewById(R.id.newsletter_card);
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
        public Newsletter mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            nameView = view.findViewById(R.id.newsletter_name);
            descriptionView = view.findViewById(R.id.newsletter_description);
            countView = view.findViewById(R.id.reader_count);
            timeView = view.findViewById(R.id.Newsletter_time);




        }

        @Override
        public String toString() {
            return super.toString() + " '" + nameView.getText() + "'";
        }

        @Override
        public boolean onLongClick(View v) {
            FragmentManager fm = ((AppCompatActivity)c).getSupportFragmentManager();
            EditNewsletterFragment temp = new EditNewsletterFragment();
            temp.setNewsletter(this.mItem);
            temp.show(fm,"ADD_NEWSLETTER");
            return true;
        }

    }
}
