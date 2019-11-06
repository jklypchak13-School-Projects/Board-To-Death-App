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

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.board2deathapp.LandingActivity;
import com.example.board2deathapp.R;
import com.example.board2deathapp.models.BoardGame;
import com.example.board2deathapp.models.Chat;
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

    private static final String ARG_COLUMN_COUNT = "column-count";
    private int mColumnCount = 1;

    //Data Collections
    private ModelCollection<BoardGame> user_games;
    private ModelCollection<BoardGame> club_games;
    private ModelCollection<BoardGame> all_games;
    private ArrayList<BoardGame> current_items;

    //Recycle View Data
    private OnListFragmentInteractionListener mListener;
    private MyBoardGameRecyclerViewAdapter adpt;
    private RecyclerView recycleView;

    //Sensor Data
    private SensorManager manager;
    public boolean displaying;

    //User Data
    private String current_user;
    private static String CLUB_USER = "Board2Death";


    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public BoardGameFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Get Current User
        LandingActivity  a = (LandingActivity) getActivity();
        if(a != null){
            current_user = a.getUser().getUsername();
        }

        //Declare Model Collections
        user_games = new ModelCollection<>(BoardGame.class);
        all_games = new ModelCollection<>(BoardGame.class);
        club_games = new ModelCollection<>(BoardGame.class);

        //Set Up the Adapter
        this.adpt = new MyBoardGameRecyclerViewAdapter(this.user_games.getItems(),mListener);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_boardgame_list, container, false);


        //Initialize the Sensor Event
        Activity a = getActivity();
        if(a != null) {
            manager = (SensorManager) a.getSystemService(Context.SENSOR_SERVICE);
        }
        if(manager != null){
            Sensor s = manager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            manager.registerListener(this, s, SensorManager.SENSOR_DELAY_GAME);
        }
        displaying = false;


        View rView = view.findViewById(R.id.list);
        if (rView instanceof RecyclerView) {

            //Set Up the Recycle View
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) rView;
            if (mColumnCount <= 1) {
                recyclerView.setLayoutManager(new LinearLayoutManager(context));
            } else {
                recyclerView.setLayoutManager(new GridLayoutManager(context, mColumnCount));
            }
            recyclerView.setAdapter(this.adpt);

            //Read Initial List as the current user's games
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

            //Set up OnClickListener for Adding Games
            view.findViewById(R.id.addFab).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    DialogFragment temp = new AddBoardGameFragment();
                    temp.show(fm,"ADD_BOARDGAME");
                }
            });


            //Get Tab Buttons
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
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnListFragmentInteractionListener {
        void onListFragmentInteraction(Chat item);

        void onListFragmentInteraction(BoardGame mItem);
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
    public void onAccuracyChanged(Sensor sensor, int accuracy){}


}
