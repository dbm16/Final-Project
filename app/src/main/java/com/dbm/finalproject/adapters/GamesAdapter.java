package com.dbm.finalproject.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.dbm.finalproject.Activities.GameProfileActivity;
import com.dbm.finalproject.R;
import com.dbm.finalproject.model.Game;

import java.util.List;

public class GamesAdapter extends RecyclerView.Adapter<GamesAdapter.GameViewHolder> {

    private Context context;
    private List<Game> gamesList;



    public GamesAdapter(Context context, List<Game> gamesList) {
        this.context = context;
        this.gamesList = gamesList;
    }

    @NonNull
    @Override
    public GameViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_game, parent, false);
        return new GameViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GameViewHolder holder, int position) {
        Game game = gamesList.get(position);

        holder.gameName.setText(game.getName());
        holder.gameReleaseDate.setText(game.getDeveloper());
        Glide.with(context).load(game.getImage_url()).into(holder.gameImage);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, GameProfileActivity.class);
                intent.putExtra("GAME_ID", game.getId());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return gamesList.size();
    }

    public static class GameViewHolder extends RecyclerView.ViewHolder {
        ImageView gameImage;
        TextView gameName, gameReleaseDate;

        public GameViewHolder(@NonNull View itemView) {
            super(itemView);
            gameImage = itemView.findViewById(R.id.game_image);
            gameName = itemView.findViewById(R.id.game_name);
            gameReleaseDate = itemView.findViewById(R.id.game_release_date);
        }
    }
}
