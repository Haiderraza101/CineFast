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

public class NowShowingFragment extends Fragment {

    private RecyclerView rvMovies;
    private ArrayList<Movie> movieData;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
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
        try {
            java.io.InputStream is = requireContext().getAssets().open("movies.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            String json = new String(buffer, "UTF-8");
            org.json.JSONArray array = new org.json.JSONArray(json);
            for (int i = 0; i < array.length(); i++) {
                org.json.JSONObject obj = array.getJSONObject(i);
                if (!obj.getBoolean("isComingSoon")) {
                    String imgName = obj.getString("image");
                    int resId = getResources().getIdentifier(imgName, "drawable", requireContext().getPackageName());
                    movieData.add(new Movie(
                        obj.getString("name"),
                        obj.getString("genre"),
                        resId,
                        obj.getString("trailerUrl"),
                        false
                    ));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void setupRecyclerView() {
        rvMovies.setLayoutManager(new LinearLayoutManager(requireContext()));
        MovieAdapter adapter = new MovieAdapter(requireContext(), movieData, new MovieAdapter.OnMovieClickListener() {
            @Override
            public void onBookClick(Movie movie) {
                ((MainActivity)getActivity()).navigateToSeatSelection(movie);
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
