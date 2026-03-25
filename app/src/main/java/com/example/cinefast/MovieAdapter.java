package com.example.cinefast;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private Context context;
    private ArrayList<Movie> movies;
    private OnMovieClickListener listener;

    public interface OnMovieClickListener {
        void onBookClick(Movie movie);
        void onTrailerClick(Movie movie);
    }

    public MovieAdapter(Context context, ArrayList<Movie> movies, OnMovieClickListener listener) {
        this.context = context;
        this.movies = movies;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Movie movie = movies.get(position);
        holder.tvName.setText(movie.getName());
        holder.tvDesc.setText(movie.getDescription());
        holder.ivPoster.setImageResource(movie.getImageRes());

        holder.btnBook.setOnClickListener(v -> listener.onBookClick(movie));
        holder.btnTrailer.setOnClickListener(v -> listener.onTrailerClick(movie));
    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        ImageView ivPoster;
        TextView tvName, tvDesc;
        Button btnBook;
        Button btnTrailer;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            ivPoster = itemView.findViewById(R.id.ivMoviePoster);
            tvName = itemView.findViewById(R.id.tvMovieName);
            tvDesc = itemView.findViewById(R.id.tvMovieDesc);
            btnBook = itemView.findViewById(R.id.btnBookSeats);
            btnTrailer = itemView.findViewById(R.id.btnTrailer);
        }
    }
}
