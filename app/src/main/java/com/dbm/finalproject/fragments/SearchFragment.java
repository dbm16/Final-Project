package com.dbm.finalproject.fragments;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import com.dbm.finalproject.R;
import com.dbm.finalproject.adapters.GamesAdapter;
import com.dbm.finalproject.model.Game;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.List;

public class SearchFragment extends Fragment {

    private EditText searchBar;
    private RecyclerView searchResultsRecycler;
    private GamesAdapter gamesAdapter;
    private List<Game> gamesList;
    private DatabaseReference gamesRef;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        Elements_Define(view);
        setupRecyclerView();
        setupListeners();

        return view;
    }


    private void Elements_Define(View view) {
        searchBar = view.findViewById(R.id.search_bar);
        searchResultsRecycler = view.findViewById(R.id.search_results_recycler);
        ImageButton backButton = view.findViewById(R.id.back_button);

        backButton.setOnClickListener(v -> navigateToHomeFragment());
        gamesList = new ArrayList<>();
        gamesAdapter = new GamesAdapter(getContext(), gamesList);

        gamesRef = FirebaseDatabase.getInstance().getReference("games");
    }


    private void setupRecyclerView() {
        searchResultsRecycler.setLayoutManager(new GridLayoutManager(requireContext(), 2));
        searchResultsRecycler.setAdapter(gamesAdapter);
    }


    private void setupListeners() {
        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                searchGames(s.toString().toLowerCase());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });
    }


    private void navigateToHomeFragment() {
        NavController navController = NavHostFragment.findNavController(this);
        navController.navigate(R.id.home_Fragment);
    }


    private void searchGames(String query) {
        if (query.isEmpty()) {
            clearSearchResults();
            return;
        }

        gamesRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                updateSearchResults(snapshot, query);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(requireContext(), "Try Again", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void updateSearchResults(DataSnapshot snapshot, String query) {
        gamesList.clear();
        for (DataSnapshot gameSnapshot : snapshot.getChildren()) {
            Game game = gameSnapshot.getValue(Game.class);
            if (game != null && matchesQuery(game, query)) {
                gamesList.add(game);
            }
        }
        gamesAdapter.notifyDataSetChanged();
    }


    private boolean matchesQuery(Game game, String query) {
        return game.getName().toLowerCase().contains(query) ||
                game.getGenre().toLowerCase().contains(query) ||
                game.getDeveloper().toLowerCase().contains(query) ||
                (game.getPlatform() != null && game.getPlatform().toLowerCase().contains(query));
    }



    private void clearSearchResults() {
        gamesList.clear();
        gamesAdapter.notifyDataSetChanged();
    }
}
