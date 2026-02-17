package com.example.cinefast;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    // Trailer buttons
    Button btnTrailerDarkKnight, btnTrailerInception, btnTrailerInterstellar, btnTrailerShawshank;

    // Book Seats buttons
    Button btnBookDarkKnight, btnBookInception, btnBookInterstellar, btnBookShawshank;

    // Date selector buttons
    Button btnToday, btnTomorrow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize all buttons
        initializeViews();

        // Set up trailer button listeners (Implicit Intent to YouTube)
        setupTrailerButtons();

        // Set up book seats button listeners
        setupBookSeatsButtons();

        // Set up date selector buttons
        setupDateButtons();
    }

    private void initializeViews() {
        // Trailer buttons
        btnTrailerDarkKnight = findViewById(R.id.btnTrailerDarkKnight);
        btnTrailerInception = findViewById(R.id.btnTrailerInception);
        btnTrailerInterstellar = findViewById(R.id.btnTrailerInterstellar);
        btnTrailerShawshank = findViewById(R.id.btnTrailerShawshank);

        // Book Seats buttons
        btnBookDarkKnight = findViewById(R.id.btnBookDarkKnight);
        btnBookInception = findViewById(R.id.btnBookInception);
        btnBookInterstellar = findViewById(R.id.btnBookInterstellar);
        btnBookShawshank = findViewById(R.id.btnBookShawshank);

        // Date selector buttons
        btnToday = findViewById(R.id.btnToday);
        btnTomorrow = findViewById(R.id.btnTomorrow);
    }

    private void setupTrailerButtons() {
        // The Dark Knight Trailer - Implicit Intent to YouTube
        btnTrailerDarkKnight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openYouTubeTrailer("https://www.youtube.com/watch?v=EXeTwQWrcwY");
            }
        });

        // Inception Trailer - Implicit Intent to YouTube
        btnTrailerInception.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openYouTubeTrailer("https://www.youtube.com/watch?v=YoHD9XEInc0");
            }
        });

        // Interstellar Trailer - Implicit Intent to YouTube
        btnTrailerInterstellar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openYouTubeTrailer("https://www.youtube.com/watch?v=zSWdZVtXT7E");
            }
        });

        // The Shawshank Redemption Trailer - Implicit Intent to YouTube
        btnTrailerShawshank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openYouTubeTrailer("https://www.youtube.com/watch?v=6hB3S9bIaco");
            }
        });
    }

    private void setupBookSeatsButtons() {
        // The Dark Knight - Book Seats
        btnBookDarkKnight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSeatSelection("The Dark Knight", "Action / 152 min");
            }
        });

        // Inception - Book Seats
        btnBookInception.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSeatSelection("Inception", "Sci-Fi / 148 min");
            }
        });

        // Interstellar - Book Seats
        btnBookInterstellar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSeatSelection("Interstellar", "Sci-Fi / 169 min");
            }
        });

        // The Shawshank Redemption - Book Seats
        btnBookShawshank.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSeatSelection("The Shawshank Redemption", "Drama / 142 min");
            }
        });
    }

    private void setupDateButtons() {
        btnToday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Showing movies for Today", Toast.LENGTH_SHORT).show();
            }
        });

        btnTomorrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "Showing movies for Tomorrow", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Opens YouTube trailer using implicit intent
     * 
     * @param youtubeUrl The YouTube video URL
     */
    private void openYouTubeTrailer(String youtubeUrl) {
        try {
            // Create implicit intent with ACTION_VIEW
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(youtubeUrl));

            // Check if there's an app that can handle this intent
            if (intent.resolveActivity(getPackageManager()) != null) {
                startActivity(intent);
            } else {
                Toast.makeText(this, "No app found to open YouTube", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Error opening trailer: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Opens Seat Selection Activity using explicit intent
     * Passes the selected movie name and details to the next activity
     * 
     * @param movieName    The name of the selected movie
     * @param movieDetails The genre and duration of the movie
     */
    private void openSeatSelection(String movieName, String movieDetails) {
        // Create explicit intent to SeatSelectionActivity
        Intent intent = new Intent(MainActivity.this, SeatSelectionActivity.class);

        // Pass movie data to the next activity
        intent.putExtra("MOVIE_NAME", movieName);
        intent.putExtra("MOVIE_DETAILS", movieDetails);

        // Start the SeatSelectionActivity
        startActivity(intent);
    }

    private void init(){

        btnTrailerDarkKnight = findViewById(R.id.trailerDarl)
    }
}