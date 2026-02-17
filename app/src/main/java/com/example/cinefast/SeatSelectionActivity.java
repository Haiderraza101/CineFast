package com.example.cinefast;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class SeatSelectionActivity extends AppCompatActivity {

  TextView tvMovieName, tvMovieDetails, tvSelectedSeats, tvTotalPrice;
  Button btnBack, btnBookSeats, btnProceedSnacks;
  LinearLayout seatGridContainer;

  String movieName;
  String movieDetails;

  // Seat configuration
  private static final int ROWS = 8;
  private static final int COLS = 6;
  private static final int TICKET_PRICE = 12; // $12 per seat

  private List<String> selectedSeatsList = new ArrayList<>();
  private int totalPrice = 0;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_seat_selection);

    initializeViews();
    getMovieDataFromIntent();
    generateSeatGrid();
    setupButtonListeners();
    updateUI();
  }

  private void initializeViews() {
    tvMovieName = findViewById(R.id.tvMovieName);
    tvMovieDetails = findViewById(R.id.tvMovieDetails);
    tvSelectedSeats = findViewById(R.id.tvSelectedSeats);
    tvTotalPrice = findViewById(R.id.tvTotalPrice);
    btnBack = findViewById(R.id.btnBack);
    btnBookSeats = findViewById(R.id.btnBookSeats);
    btnProceedSnacks = findViewById(R.id.btnProceedSnacks);
    seatGridContainer = findViewById(seatGridContainer != null ? R.id.seatGridContainer : 0);
    // Fallback if ID is missing (should be there based on my previous write)
    if (seatGridContainer == null) {
      seatGridContainer = findViewById(R.id.seatGridContainer);
    }
  }

  private void getMovieDataFromIntent() {
    Intent intent = getIntent();
    if (intent != null && intent.hasExtra("MOVIE_NAME")) {
      movieName = intent.getStringExtra("MOVIE_NAME");
      movieDetails = intent.getStringExtra("MOVIE_DETAILS");
      tvMovieName.setText(movieName);
      if (movieDetails != null)
        tvMovieDetails.setText(movieDetails);
    }
  }

  private void generateSeatGrid() {
    // Clear previous views if any
    seatGridContainer.removeAllViews();

    for (int i = 0; i < ROWS; i++) {
      LinearLayout rowLayout = new LinearLayout(this);
      rowLayout.setOrientation(LinearLayout.HORIZONTAL);
      rowLayout.setGravity(Gravity.CENTER);
      rowLayout.setLayoutParams(new LinearLayout.LayoutParams(
          LinearLayout.LayoutParams.MATCH_PARENT,
          LinearLayout.LayoutParams.WRAP_CONTENT));

      char rowChar = (char) ('A' + i);

      for (int j = 0; j < COLS; j++) {
        final String seatId = rowChar + String.valueOf(j + 1);

        View seatView = new View(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(40, 40); // Size in px roughly
        // Convert dp to px for better scaling
        int size = (int) (35 * getResources().getDisplayMetrics().density);
        params.setMargins(8, 8, 8, 8);
        seatView.setLayoutParams(new LinearLayout.LayoutParams(size, size));
        ((LinearLayout.LayoutParams) seatView.getLayoutParams()).setMargins(10, 10, 10, 10);

        // Randomly mark some seats as booked for demo
        boolean isBooked = (i == 2 && j == 1) || (i == 5 && j == 4) || (i == 1 && j == 0);

        if (isBooked) {
          seatView.setBackgroundResource(R.drawable.seat_booked);
          seatView.setEnabled(false);
        } else {
          seatView.setBackgroundResource(R.drawable.seat_available);
          seatView.setOnClickListener(new View.OnClickListener() {
            boolean isSelected = false;

            @Override
            public void onClick(View v) {
              if (isSelected) {
                v.setBackgroundResource(R.drawable.seat_available);
                selectedSeatsList.remove(seatId);
              } else {
                v.setBackgroundResource(R.drawable.seat_selected);
                selectedSeatsList.add(seatId);
              }
              isSelected = !isSelected;
              updateUI();
            }
          });
        }
        rowLayout.addView(seatView);

        // Add a gap in the middle to simulate aisle like the image
        if (j == 2) {
          View aisle = new View(this);
          aisle.setLayoutParams(new LinearLayout.LayoutParams(40, size));
          rowLayout.addView(aisle);
        }
      }
      seatGridContainer.addView(rowLayout);
    }
  }

  private void updateUI() {
    if (selectedSeatsList.isEmpty()) {
      tvSelectedSeats.setText("None");
      tvTotalPrice.setText("$0");
      btnProceedSnacks.setEnabled(false);
      btnProceedSnacks.setAlpha(0.5f);
    } else {
      StringBuilder seats = new StringBuilder();
      for (int i = 0; i < selectedSeatsList.size(); i++) {
        seats.append(selectedSeatsList.get(i));
        if (i < selectedSeatsList.size() - 1)
          seats.append(", ");
      }
      tvSelectedSeats.setText(seats.toString());
      totalPrice = selectedSeatsList.size() * TICKET_PRICE;
      tvTotalPrice.setText("$" + totalPrice);

      btnProceedSnacks.setEnabled(true);
      btnProceedSnacks.setAlpha(1.0f);
    }
  }

  private void setupButtonListeners() {
    btnBack.setOnClickListener(v -> finish());

    btnBookSeats.setOnClickListener(v -> {
      if (selectedSeatsList.isEmpty()) {
        Toast.makeText(this, "Please select at least one seat", Toast.LENGTH_SHORT).show();
        return;
      }
      // Navigate directly to Ticket Summary (confirm booking)
      Intent intent = new Intent(SeatSelectionActivity.this, TicketSummaryActivity.class);
      intent.putExtra("MOVIE_NAME", movieName);
      intent.putStringArrayListExtra("SELECTED_SEATS", new ArrayList<>(selectedSeatsList));
      intent.putExtra("TOTAL_PRICE", totalPrice);
      startActivity(intent);
    });

    btnProceedSnacks.setOnClickListener(v -> {
      // Navigate to Snacks Screen
      Intent intent = new Intent(SeatSelectionActivity.this, SnacksActivity.class);
      intent.putExtra("MOVIE_NAME", movieName);
      intent.putStringArrayListExtra("SELECTED_SEATS", new ArrayList<>(selectedSeatsList));
      intent.putExtra("TOTAL_PRICE", totalPrice);
      startActivity(intent);
    });
  }
}
