package com.example.cinefast;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.net.Uri;
import java.util.ArrayList;

public class ComingSoonFragment extends Fragment {

    private RecyclerView rvMovies;
    private ArrayList<Movie> movieData;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie_list, container, false);
        init(view);
        loadData();
        setupRecyclerView();
        return view;
    }

    private void init(View view) {
        rvMovies = view.findViewById(R.id.rvMovies);
    }

    private void loadData() {
        movieData = new ArrayList<>();
        movieData.add(new Movie("Dune: Part Two", "Sci-Fi / 166 min", R.drawable.interstellar,
                "https://www.youtube.com/watch?v=Way9Dexny3w", true));
        movieData.add(new Movie("The Batman", "Action / 176 min", R.drawable.inception,
                "https://www.youtube.com/watch?v=mqqft22Sk28", true));
        movieData.add(new Movie("Tenet", "Sci-Fi / 150 min", R.drawable.inception,
                "https://www.youtube.com/watch?v=LdOM0x0XDwM", true));
    }

    private void setupRecyclerView() {
        rvMovies.setLayoutManager(new LinearLayoutManager(requireContext()));
        MovieAdapter adapter = new MovieAdapter(requireContext(), movieData, new MovieAdapter.OnMovieClickListener() {
            @Override
            public void onBookClick(Movie movie) {
                ((MainActivity) getActivity()).navigateToSeatSelection(movie);
            }

            @Override
            public void onTrailerClick(Movie movie) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(movie.getTrailerUrl()));
                startActivity(intent);
            }
        });
        rvMovies.setAdapter(adapter);
    }
}
