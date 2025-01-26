package com.dbm.finalproject.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.dbm.finalproject.R;
import com.dbm.finalproject.model.Game;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class GameProfileActivity extends AppCompatActivity {

    private TextView gameTitle, gameDeveloper, gameReleaseDate, gamePopularity, gameFullDescription, gamePlatform;
    private FloatingActionButton fabPlay;
    private ImageView gameImage;

    private DatabaseReference gamesRef;
    private String gameId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_game__profile);

        gameId = getIntent().getStringExtra("GAME_ID");
        if (gameId == null) {
            Toast.makeText(this, "Game ID is missing!", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        define_ids();
        fetchGameDetailsFromFirebase();
        setupTrailerButton();
    }

    private void define_ids() {
        gameTitle = findViewById(R.id.game_title);
        gameDeveloper = findViewById(R.id.game_developer);
        gameReleaseDate = findViewById(R.id.game_release_date);
        gamePopularity = findViewById(R.id.game_popularity);
        gamePlatform = findViewById(R.id.game_platform);
        gameFullDescription = findViewById(R.id.game_full_description);
        fabPlay = findViewById(R.id.fab_search);
        gameImage = findViewById(R.id.game_image);

        gamesRef = FirebaseDatabase.getInstance().getReference("games");
    }

    private void fetchGameDetailsFromFirebase() {
        gamesRef.child(gameId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    Game game = snapshot.getValue(Game.class);
                    if (game != null) {
                        populateGameDetails(game);
                    }
                } else {
                    Toast.makeText(GameProfileActivity.this, "Game not found!", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(GameProfileActivity.this, "Failed to fetch game details!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void populateGameDetails(Game game) {
        gameTitle.setText(game.getName());
        gameDeveloper.setText(game.getDeveloper());
        gameReleaseDate.setText(game.getRelease_date());
        gamePopularity.setText(String.valueOf(game.getPopularity()));
        gameFullDescription.setText(game.getDescription());
        gamePlatform.setText(game.getPlatform());

        loadGameImage(game.getImage_url());

        if (game.getTrailer_url() != null && !game.getTrailer_url().isEmpty()) {
            fabPlay.setTag(game.getTrailer_url());
        } else {
            fabPlay.setEnabled(false);
        }
    }

    private void loadGameImage(String imageUrl) {
        if (imageUrl != null && !imageUrl.isEmpty()) {
            Glide.with(this)
                    .load(imageUrl)
                    .into(gameImage);
        } else {
            Toast.makeText(this, "Image not available!", Toast.LENGTH_SHORT).show();
        }
    }



    private void setupTrailerButton() {
        fabPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String trailerUrl = (String) fabPlay.getTag();
                if (trailerUrl != null && !trailerUrl.isEmpty()) {

                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(trailerUrl));
                    startActivity(intent);

                }
                else {
                    Toast.makeText(GameProfileActivity.this, "Trailer not available!", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }


}
