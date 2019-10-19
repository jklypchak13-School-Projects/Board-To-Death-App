package com.example.board2deathapp.ui.boardgame;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.board2deathapp.R;
import com.example.board2deathapp.models.BoardGame;
import com.example.board2deathapp.models.DBResponse;
import com.example.board2deathapp.models.Model;
import com.example.board2deathapp.models.ModelCollection;
import com.example.board2deathapp.ui.login.LoginFragment;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class BoardGameFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    private static final String TAG = "BOARDGAME";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private ModelCollection<BoardGame> ITEMS;
    private OnListFragmentInteractionListener mListener;
    private MyBoardGameRecyclerViewAdapter adpt;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public BoardGameFragment() {
    }

    public static BoardGameFragment newInstance(int columnCount) {


        BoardGameFragment fragment = new BoardGameFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COLUMN_COUNT, columnCount);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        BoardGame temp = new BoardGame("Catan", "A game", "jklypchak13", 5, 3, getActivity());
        Query q = FirebaseFirestore.getInstance().collection("boardgame").whereEqualTo("name", "Flatline");


        ITEMS = new ModelCollection<BoardGame>(BoardGame.class);
        this.adpt = new MyBoardGameRecyclerViewAdapter(this.ITEMS.getItems(),mListener);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_boardgame_list, container, false);

        // Set the adapter
        View rview = view.findViewById(R.id.list);
        if (rview instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) rview;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            recyclerView.setAdapter(this.adpt);
            Query q = FirebaseFirestore.getInstance().collection("boardgame").whereEqualTo("owner", "jklypchak13");
            this.ITEMS.read(q, new DBResponse(getActivity()) {
                @Override
                public <T> void onSuccess(T t, Model m) {
                    adpt.notifyDataSetChanged();
                    Log.d(TAG, "Successfully read Board Game list");
                }

                @Override
                public <T> void onFailure(T t) {
                    Log.d(TAG, "Failed to read Board Game List");
                }
            });
            view.findViewById(R.id.add_game).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    Fragment frag = fm.findFragmentById(R.id.nav_host_fragment);
                    if(frag == null){
                        frag = new LoginFragment();
                        fm.beginTransaction().add(R.id.nav_host_fragment, frag).commit();
                    }
                }
            });
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
/*        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }*/
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        void onListFragmentInteraction(BoardGame item);
    }

}
