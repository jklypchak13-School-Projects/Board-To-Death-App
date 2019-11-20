package com.example.board2deathapp.ui.Newsletter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.board2deathapp.LandingActivity;
import com.example.board2deathapp.R;

import com.example.board2deathapp.models.Newsletter;

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
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_newsletter, parent, false);
        c = view.getContext();
        ViewHolder v = new ViewHolder(view);
        return v;
    }



    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.authorView.setText(mValues.get(position).getUsername());
        holder.descriptionView.setText(mValues.get(position).getDescription());
        holder.dateView.setText(mValues.get(position).getDate());
        holder.titleView.setText(mValues.get(position).getTitle());

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

        if(((LandingActivity)c).getUser().isAdmin()){
            CardView card = holder.mView.findViewById(R.id.newsletter_card);
            card.setOnLongClickListener(holder);
        }
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnLongClickListener {
        public final View mView;
        public final TextView authorView;
        public final TextView titleView;
        public final TextView descriptionView;
        public final TextView dateView;

        public Newsletter mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            authorView = view.findViewById(R.id.postAuthor);
            titleView = view.findViewById(R.id.postTitle);
            descriptionView = view.findViewById(R.id.postContent);
            dateView = view.findViewById(R.id.postDate);





        }

        @Override
        public String toString() {
            return super.toString() + " '" + titleView.getText() + "'";
        }

        @Override
        public boolean onLongClick(View v) {
            FragmentManager fm = ((AppCompatActivity)c).getSupportFragmentManager();
            EditNewsletterFragment temp = new EditNewsletterFragment();
            temp.setNewsletter(this.mItem);
            temp.show(fm,"EDIT_NEWSLETTER");
            return true;
        }

    }
}
