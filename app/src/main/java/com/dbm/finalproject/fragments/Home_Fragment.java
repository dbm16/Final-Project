package com.dbm.finalproject.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.dbm.finalproject.model.Game;
import com.dbm.finalproject.adapters.GamesAdapter;
import com.dbm.finalproject.R;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public class Home_Fragment extends Fragment {

    View fragmentview;


    private RecyclerView recyclerNewest, recyclerPopular, recyclerAction, recyclerSport;
    private GamesAdapter newestAdapter, popularAdapter, actionAdapter, sportAdapter;
    private List<Game> newestGames, popularGames, actionGames, sportGames;

    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentview = inflater.inflate(R.layout.fragment_home_, container, false);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        FloatingActionButton fab_search = fragmentview.findViewById(R.id.fab_search);

        fab_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavController navController = NavHostFragment.findNavController(Home_Fragment.this);
                navController.navigate(R.id.searchFragment3);
            }
        });

        loadusername();
        slider();
        define_ids(fragmentview);
        loadNewestGames();
        loadPopularGames();
        loadActionGames();
        loadSportGames();

        return fragmentview;

    }

    private void slider() {

        ArrayList<SlideModel> imageList = new ArrayList<>();

        imageList.add(new SlideModel("https://shared.cloudflare.steamstatic.com//store_item_assets/steam/apps/1623730/c961ac047294838bfe382bf193ade5c64b19e573/capsule_616x353.jpg?t=1737094038", ScaleTypes.CENTER_CROP));
        imageList.add(new SlideModel("https://shared.cloudflare.steamstatic.com//store_item_assets/steam/apps/578080/94ddc32dcf085fa01408102441e1a4d298b32f66/capsule_616x353.jpg?t=1736389084", ScaleTypes.CENTER_CROP));
        imageList.add(new SlideModel("https://shared.cloudflare.steamstatic.com//store_item_assets/steam/apps/1091500/capsule_616x353.jpg?t=1734434803", ScaleTypes.CENTER_CROP));

        ImageSlider imageSlider = fragmentview.findViewById(R.id.image_slider);
        imageSlider.setImageList(imageList);

    }

    private void define_ids(View view) {
        recyclerNewest = view.findViewById(R.id.recycler_newest);
        recyclerPopular = view.findViewById(R.id.recycler_popular);
        recyclerAction = view.findViewById(R.id.recycler_action);
        recyclerSport = view.findViewById(R.id.recycler_sport);

        newestGames = new ArrayList<>();
        popularGames = new ArrayList<>();
        actionGames = new ArrayList<>();
        sportGames = new ArrayList<>();

        newestAdapter = new GamesAdapter(requireContext(), newestGames);
        popularAdapter = new GamesAdapter(requireContext(), popularGames);
        actionAdapter = new GamesAdapter(requireContext(), actionGames);
        sportAdapter = new GamesAdapter(requireContext(), sportGames);

        setupRecyclerView(recyclerNewest, newestAdapter);
        setupRecyclerView(recyclerPopular, popularAdapter);
        setupRecyclerView(recyclerAction, actionAdapter);
        setupRecyclerView(recyclerSport, sportAdapter);
    }
    private void loadusername() {
        TextView username = fragmentview.findViewById(R.id.username);
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            String uid = user.getUid();

            mDatabase.child("Users").child(uid).child("username")
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.exists()) {
                                String username_st = snapshot.getValue(String.class);
                                username.setText("היי, " + username_st + "!");
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(getActivity(), "Failed to load username", Toast.LENGTH_SHORT).show();
                        }
                    });
        }

    }

    private void setupRecyclerView(RecyclerView recyclerView, GamesAdapter adapter) {
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(adapter);
    }

    private void loadNewestGames() {
        DatabaseReference gamesRef = FirebaseDatabase.getInstance().getReference("games");
        gamesRef.orderByChild("release_date").limitToLast(10).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                newestGames.clear();
                for (DataSnapshot gameSnapshot : snapshot.getChildren()) {
                    Game game = gameSnapshot.getValue(Game.class);
                    newestGames.add(game);
                }
                newestGames.sort(new Comparator<Game>() {
                    @Override
                    public int compare(Game g1, Game g2) {
                        return g2.getRelease_date().compareTo(g1.getRelease_date());
                    }
                });
                newestAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
    }

    private void loadPopularGames() {
        DatabaseReference gamesRef = FirebaseDatabase.getInstance().getReference("games");
        gamesRef.orderByChild("popularity").limitToLast(10).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                popularGames.clear();
                for (DataSnapshot gameSnapshot : snapshot.getChildren()) {
                    Game game = gameSnapshot.getValue(Game.class);
                    popularGames.add(game);
                }
                popularAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
    }

    private void loadActionGames() {
        DatabaseReference gamesRef = FirebaseDatabase.getInstance().getReference("games");
        gamesRef.orderByChild("genre").equalTo("Action").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                actionGames.clear();
                for (DataSnapshot gameSnapshot : snapshot.getChildren()) {
                    Game game = gameSnapshot.getValue(Game.class);
                    actionGames.add(game);
                }
                actionAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError error) {

            }
        });
    }

    private void loadSportGames() {
        DatabaseReference gamesRef = FirebaseDatabase.getInstance().getReference("games");
        gamesRef.orderByChild("genre").equalTo("Sport").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                sportGames.clear();
                for (DataSnapshot gameSnapshot : snapshot.getChildren()) {
                    Game game = gameSnapshot.getValue(Game.class);
                    sportGames.add(game);
                }
                sportAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError error) {
            }
        });
    }
    }