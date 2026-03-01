package com.example.cinefast;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button btnBookDarkKnight, btnBookInception, btnBookInterstellar, btnBookAvengersEndgame, btnBookSpiderman,
            btnBookMadMax;
    Button btnTrailerDarkKnight, btnTrailerInception, btnTrailerInterstellar, btnTrailerSpiderman, btnTrailerAvengers,
            btnTrailerMadMax;
    View spiderManCard, avengersEndGameCard, madMaxCard, darkKnightCard, inceptionCard, interstellarCard;

    // Date Selector Views
    View llToday, llTomorrow;
    View vTodaySelector, vTomorrowSelector;
    TextView tvToday, tvTomorrow; // Added these to change text color

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeViews();
        setupBookSeatsButtons();
    }

    private void initializeViews() {
        // --- 1. FIND VIEWS ---
        btnBookDarkKnight = findViewById(R.id.btnBookDarkKnight);
        btnBookInception = findViewById(R.id.btnBookInception);
        btnBookInterstellar = findViewById(R.id.btnBookInterstellar);
        btnBookSpiderman = findViewById(R.id.btnBookSpiderman);
        btnBookAvengersEndgame = findViewById(R.id.btnBookAvengersEndgame);
        btnBookMadMax = findViewById(R.id.btnBookMadMax);

        // Trailer Buttons
        btnTrailerDarkKnight = findViewById(R.id.btnTrailerDarkKnight);
        btnTrailerInception = findViewById(R.id.btnTrailerInception);
        btnTrailerInterstellar = findViewById(R.id.btnTrailerInterstellar);
        btnTrailerSpiderman = findViewById(R.id.btnTrailerSpiderman);
        btnTrailerAvengers = findViewById(R.id.btnTrailerAvengers);
        btnTrailerMadMax = findViewById(R.id.btnTrailerMadMax);

        spiderManCard = findViewById(R.id.spidermanCard);
        avengersEndGameCard = findViewById(R.id.avengersEndGameCard);
        madMaxCard = findViewById(R.id.madMaxCard);
        darkKnightCard = findViewById(R.id.darkKnightCard);
        inceptionCard = findViewById(R.id.inceptionCard);
        interstellarCard = findViewById(R.id.interstellarCard);

        llToday = findViewById(R.id.llToday);
        llTomorrow = findViewById(R.id.llTomorrow);

        vTodaySelector = findViewById(R.id.vTodaySelector);
        vTomorrowSelector = findViewById(R.id.vTomorrowSelector); // Fixed copy-paste error here

        tvToday = findViewById(R.id.tvToday);
        tvTomorrow = findViewById(R.id.tvTomorrow);

        // --- 2. SET LISTENERS ---

        // CLICK TODAY
        llToday.setOnClickListener((v) -> {
            updateDateSelector(true); // true = Today is selected
        });

        // CLICK TOMORROW
        llTomorrow.setOnClickListener((v) -> {
            updateDateSelector(false); // false = Tomorrow is selected
        });
    }

    // Helper function to handles the toggling logic without XML drawables
    private void updateDateSelector(boolean isTodaySelected) {
        int colorRed = ContextCompat.getColor(this, R.color.brand_red);
        int colorGrey = ContextCompat.getColor(this, R.color.card_dark_bg); // Keeping this dark shade for inactive state

        if (isTodaySelected) {
            // --- UI: Make TODAY Red & Active ---
            setRoundedBackground(llToday, colorRed, 24);
            tvToday.setTextColor(ContextCompat.getColor(this, R.color.text_white));
            vTodaySelector.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.text_white)));

            // --- UI: Make TOMORROW Grey & Inactive ---
            setRoundedBackground(llTomorrow, colorGrey, 24);
            tvTomorrow.setTextColor(ContextCompat.getColor(this, R.color.text_muted));
            vTomorrowSelector.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.inactive_selector)));

            // --- LOGIC: Show Today's Movies ---
            darkKnightCard.setVisibility(View.VISIBLE);
            inceptionCard.setVisibility(View.VISIBLE);
            interstellarCard.setVisibility(View.VISIBLE);

            spiderManCard.setVisibility(View.GONE);
            avengersEndGameCard.setVisibility(View.GONE);
            madMaxCard.setVisibility(View.GONE);

        } else {
            // --- UI: Make TODAY Grey & Inactive ---
            setRoundedBackground(llToday, colorGrey, 24);
            tvToday.setTextColor(ContextCompat.getColor(this, R.color.text_muted));
            vTodaySelector.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.inactive_selector)));

            // --- UI: Make TOMORROW Red & Active ---
            setRoundedBackground(llTomorrow, colorRed, 24);
            tvTomorrow.setTextColor(ContextCompat.getColor(this, R.color.text_white));
            vTomorrowSelector.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.text_white)));

            // --- LOGIC: Show Tomorrow's Movies ---
            darkKnightCard.setVisibility(View.GONE);
            inceptionCard.setVisibility(View.GONE);
            interstellarCard.setVisibility(View.GONE);

            spiderManCard.setVisibility(View.VISIBLE);
            avengersEndGameCard.setVisibility(View.VISIBLE);
            madMaxCard.setVisibility(View.VISIBLE);
        }
    }

    // New helper to set rounded background without XML files
    private void setRoundedBackground(View view, int color, int radiusDp) {
        android.graphics.drawable.GradientDrawable shape = new android.graphics.drawable.GradientDrawable();
        shape.setShape(android.graphics.drawable.GradientDrawable.RECTANGLE);
        shape.setCornerRadius(radiusDp * getResources().getDisplayMetrics().density);
        shape.setColor(color);
        view.setBackground(shape);
    }

    private void setupBookSeatsButtons() {
        // ... (Your existing button logic remains exactly the same) ...
        btnBookDarkKnight.setOnClickListener(v -> {
            ArrayList<Integer> bookedSeats = new ArrayList<>();
            bookedSeats.add(1);
            bookedSeats.add(5);
            bookedSeats.add(7);
            bookedSeats.add(9);
            openSeatSelection("The Dark Knight", "Action / 152 min", 17, 2, "3/10/2026", "10:00", bookedSeats);
        });

        btnBookInception.setOnClickListener(v -> {
            ArrayList<Integer> bookedSeats = new ArrayList<>();
            bookedSeats.add(10);
            bookedSeats.add(11);
            bookedSeats.add(20);
            bookedSeats.add(18);
            openSeatSelection("Inception", "Sci-Fi / 148 min", 18, 1, "3/10/2026", "09:00", bookedSeats);
        });

        btnBookInterstellar.setOnClickListener(v -> {
            ArrayList<Integer> bookedSeats = new ArrayList<>();
            bookedSeats.add(19);
            bookedSeats.add(20);
            bookedSeats.add(18);
            bookedSeats.add(21);
            openSeatSelection("Interstellar", "Sci-Fi / 169 min", 17, 1, "3/10/2026", "12:00", bookedSeats);
        });

        btnBookSpiderman.setOnClickListener(v -> {
            ArrayList<Integer> bookedSeats = new ArrayList<>();
            bookedSeats.add(19);
            bookedSeats.add(17);
            bookedSeats.add(6);
            bookedSeats.add(7);
            openSeatSelection("Spiderman", "Action / 180 min", 15, 2, "3/11/2026", "10:00", bookedSeats);
        });

        btnBookAvengersEndgame.setOnClickListener(v -> {
            ArrayList<Integer> bookedSeats = new ArrayList<>();
            bookedSeats.add(20);
            bookedSeats.add(17);
            bookedSeats.add(9);
            bookedSeats.add(8);
            openSeatSelection("Avengers: EndGame", "Action / 184 min", 15, 2, "3/11/2026", "01:00", bookedSeats);
        });

        btnBookMadMax.setOnClickListener(v -> {
            ArrayList<Integer> bookedSeats = new ArrayList<>();
            bookedSeats.add(20);
            bookedSeats.add(21);
            bookedSeats.add(25);
            bookedSeats.add(19);
            openSeatSelection("The Shawshank Redemption", "Drama / 142 min", 18, 2, "3/11/2026", "02:00", bookedSeats);
        });

        // Trailer Clicks
        btnTrailerDarkKnight.setOnClickListener(v -> openTrailer("https://www.youtube.com/watch?v=EXeTwQWrcwY"));
        btnTrailerInception.setOnClickListener(v -> openTrailer("https://www.youtube.com/watch?v=YoHD9XEInc0"));
        btnTrailerInterstellar.setOnClickListener(v -> openTrailer("https://www.youtube.com/watch?v=zSWdZVtXT7E"));
        btnTrailerSpiderman.setOnClickListener(v -> openTrailer("https://www.youtube.com/watch?v=JfVOs4VSpmA"));
        btnTrailerAvengers.setOnClickListener(v -> openTrailer("https://www.youtube.com/watch?v=TcMBFSGZo1c"));
        btnTrailerMadMax.setOnClickListener(v -> openTrailer("https://www.youtube.com/watch?v=6hB3S9bIaco"));
    }

    private void openTrailer(String url) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        } else {
            // Fallback if no YouTube app or browser
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
        }
    }

    private void openSeatSelection(String movieName, String movieDetails, int age, int hallNumber, String date,
            String time, ArrayList<Integer> bookedSeats) {
        Intent intent = new Intent(MainActivity.this, SeatSelectionActivity.class);
        intent.putExtra("movieName_key", movieName);
        intent.putExtra("movieDetails_key", movieDetails);
        intent.putExtra("age_key", age);
        intent.putExtra("hallNumber_key", hallNumber);
        intent.putExtra("date_key", date);
        intent.putExtra("time_key", time);
        intent.putIntegerArrayListExtra("bookedSeats_key", bookedSeats); // Changed to putIntegerArrayListExtra for
                                                                         // safety
        startActivity(intent);
    }
}
