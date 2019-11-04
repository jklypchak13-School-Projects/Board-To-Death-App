package com.example.board2deathapp.ui.boardgame;


import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.board2deathapp.LandingActivity;
import com.example.board2deathapp.R;
import com.example.board2deathapp.models.BoardGame;
import com.example.board2deathapp.models.DBResponse;
import com.example.board2deathapp.models.ModelCollection;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.Random;

/**
 * A fragment representing a list of Items.
 * <p/>
 * Activities containing this fragment MUST implement the {@link OnListFragmentInteractionListener}
 * interface.
 */
public class BoardGameFragment extends Fragment implements SensorEventListener {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    private static final String TAG = "BOARDGAME";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private ModelCollection<BoardGame> user_games;
    private ModelCollection<BoardGame> club_games;
    private ModelCollection<BoardGame> all_games;
    private OnListFragmentInteractionListener mListener;
    private MyBoardGameRecyclerViewAdapter adpt;
    private RecyclerView recycleView;
    private SensorManager manager;
    private String current_user;
    private static String CLUB_USER = "Board2Death";
    private ArrayList<BoardGame> current_items;
    public boolean displaying;
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

        current_user = ((LandingActivity)getActivity()).getUser().getUsername();


        user_games = new ModelCollection<>(BoardGame.class);
        all_games = new ModelCollection<>(BoardGame.class);
        club_games = new ModelCollection<>(BoardGame.class);
        this.adpt = new MyBoardGameRecyclerViewAdapter(this.user_games.getItems(),mListener);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_boardgame_list, container, false);

        // Set the adapter
        displaying = false;
        Activity a = getActivity();
        if(a != null) {
            manager = (SensorManager) a.getSystemService(Context.SENSOR_SERVICE);
        }
        if(manager != null){
            Sensor s = manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            manager.registerListener(this, s, SensorManager.SENSOR_DELAY_GAME);
        }



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
            Query q = FirebaseFirestore.getInstance().collection("boardgame").whereEqualTo("owner", current_user);
            user_games.read_current(q, new DBResponse(getActivity()) {
                @Override
                public <T> void onSuccess(T t) {
                    adpt.notifyDataSetChanged();
                }
                @Override
                public <T> void onFailure(T t){

                }

            });
            current_items = (ArrayList<BoardGame>)user_games.getItems();
            recycleView = recyclerView;
            view.findViewById(R.id.addFab).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    DialogFragment temp = new AddBoardGameFragment();
                    temp.show(fm,"ADD_BOARDGAME");
                }
            });

            final Button my_games_b = view.findViewById(R.id.user_games);
            my_games_b.setBackgroundColor(getResources().getColor(R.color.colorAccent));
            final Button all_games_b = view.findViewById(R.id.all_games);
            final Button club_games_b = view.findViewById(R.id.club_games);

            all_games_b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    current_items = (ArrayList<BoardGame>)BoardGameFragment.this.all_games.getItems();
                    all_games_b.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                    my_games_b.setBackgroundColor(Color.TRANSPARENT);
                    club_games_b.setBackgroundColor(Color.TRANSPARENT);
                    adpt = new MyBoardGameRecyclerViewAdapter(BoardGameFragment.this.all_games.getItems(),mListener);
                    recycleView.setAdapter(BoardGameFragment.this.adpt);
                    Query q = FirebaseFirestore.getInstance().collection("boardgame").orderBy("owner");
                    all_games.read_current(q, new DBResponse(getActivity()) {
                        @Override
                        public <T> void onSuccess(T t) {
                            adpt.notifyDataSetChanged();
                        }
                        @Override
                        public <T> void onFailure(T t){

                        }

                    });
                }
            });

            my_games_b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    current_items = (ArrayList<BoardGame>)BoardGameFragment.this.user_games.getItems();
                    all_games_b.setBackgroundColor(Color.TRANSPARENT);
                    my_games_b.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                    club_games_b.setBackgroundColor(Color.TRANSPARENT);
                    adpt = new MyBoardGameRecyclerViewAdapter(BoardGameFragment.this.user_games.getItems(),mListener);
                    recycleView.setAdapter(BoardGameFragment.this.adpt);
                    Query q = FirebaseFirestore.getInstance().collection("boardgame").whereEqualTo("owner", current_user);
                    user_games.read_current(q, new DBResponse(getActivity()) {
                        @Override
                        public <T> void onSuccess(T t) {
                            adpt.notifyDataSetChanged();
                        }
                        @Override
                        public <T> void onFailure(T t){

                        }

                    });
                }
            });

            club_games_b.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    current_items = (ArrayList<BoardGame>)BoardGameFragment.this.club_games.getItems();
                    all_games_b.setBackgroundColor(Color.TRANSPARENT);
                    my_games_b.setBackgroundColor(Color.TRANSPARENT);
                    club_games_b.setBackgroundColor(getResources().getColor(R.color.colorAccent));
                    adpt = new MyBoardGameRecyclerViewAdapter(BoardGameFragment.this.club_games.getItems(),mListener);
                    recycleView.setAdapter(BoardGameFragment.this.adpt);
                    Query q = FirebaseFirestore.getInstance().collection("boardgame").whereEqualTo("owner", CLUB_USER);
                    club_games.read_current(q, new DBResponse(getActivity()) {
                        @Override
                        public <T> void onSuccess(T t) {
                            adpt.notifyDataSetChanged();
                        }
                        @Override
                        public <T> void onFailure(T t){

                        }

                    });
                }
            });
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        /*
        if (context instanceof OnListFragmentInteractionListener) {
            mListener = (OnListFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnListFragmentInteractionListener");
        }

         */
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
        void onListFragmentInteraction(BoardGame item);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if(!displaying) {

            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];

            float gX = x / SensorManager.GRAVITY_EARTH;
            float gY = y / SensorManager.GRAVITY_EARTH;
            float gZ = z / SensorManager.GRAVITY_EARTH;

            // gForce will be close to 1 when there is no movement.
            float gForce = (float) Math.sqrt(gX * gX + gY * gY + gZ * gZ);

            if (gForce > 2) {
                displaying = true;
                Random rand = new Random();
                BoardGame g = current_items.get(rand.nextInt(current_items.size()));

                Activity current_activity = getActivity();
                if(current_activity != null){
                    FragmentManager fm = ((FragmentActivity) current_activity).getSupportFragmentManager();

                    DetailedBoardgameFragment temp = new DetailedBoardgameFragment();
                    temp.setGame(g);
                    temp.setFragment(this);
                    temp.show(fm, "ADD_BOARDGAME");
                }

            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy){


    }


}
