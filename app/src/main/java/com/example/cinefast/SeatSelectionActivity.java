package com.example.cinefast;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public class SeatSelectionActivity extends AppCompatActivity {

  private TextView tvMovieName, tvAge, tvHallNumber, tvDate, tvTime;
  Button btnProceedToSnacks;
  private TextView tvTotalPrice;
  private Button btnConfirm, btnBack;

  private List<Button> allSeatButtons = new ArrayList<>();
  private ArrayList<Integer> selectedSeatIds = new ArrayList<>();

  private ArrayList<Integer> bookedSeatsList = new ArrayList<>();

  private final int MAX_SEATS = 3;
  private final int TICKET_PRICE = 15;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_seat_selection);

    initializeViews();
    getIntentData();
    setupSeatGrid();

    if (btnBack != null) {
      btnBack.setOnClickListener(v -> finish());
    }

    if (btnConfirm != null) {
      btnConfirm.setOnClickListener(v -> {
        if (selectedSeatIds.isEmpty()) {
          Toast.makeText(this, "Please select at least one seat.", Toast.LENGTH_SHORT).show();
        } else {
          Intent intent = new Intent(SeatSelectionActivity.this, TicketSummaryActivity.class);
          intent.putExtra("movieName_key", getIntent().getStringExtra("movieName_key"));
          intent.putExtra("age_key", getIntent().getIntExtra("age_key", 13));
          intent.putExtra("hallNumber_key", getIntent().getStringExtra("hallNumber_key"));
          intent.putExtra("date_key", getIntent().getStringExtra("date_key"));
          intent.putExtra("time_key", getIntent().getStringExtra("time_key"));
          intent.putExtra("selectedSeats_key", selectedSeatIds);
          intent.putExtra("popcornQty_key", 0);
          intent.putExtra("nachosQty_key", 0);
          intent.putExtra("sodaQty_key", 0);
          startActivity(intent);
        }
      });
    }

    btnProceedToSnacks.setOnClickListener((v) -> {
      Intent intent = new Intent(SeatSelectionActivity.this, SnacksActivity.class);
      intent.putExtra("movieName_key", getIntent().getStringExtra("movieName_key"));
      intent.putExtra("age_key", getIntent().getIntExtra("age_key", 13));
      intent.putExtra("hallNumber_key", getIntent().getStringExtra("hallNumber_key"));
      intent.putExtra("date_key", getIntent().getStringExtra("date_key"));
      intent.putExtra("time_key", getIntent().getStringExtra("time_key"));
      intent.putExtra("selectedSeats_key", selectedSeatIds);
      startActivity(intent);
    });
  }

  private void initializeViews() {
    tvMovieName = findViewById(R.id.tvMovieName);
    tvAge = findViewById(R.id.tvAge);
    tvHallNumber = findViewById(R.id.tvHallNumber);
    tvDate = findViewById(R.id.tvDate);
    tvTime = findViewById(R.id.tvTime);

    btnConfirm = findViewById(R.id.btnConfirm);
    btnBack = findViewById(R.id.btnBack);
    btnProceedToSnacks = findViewById(R.id.btnProceedToSnacks);
    if (btnConfirm != null) {
      btnConfirm.setEnabled(false);
      btnConfirm.setAlpha(0.5f);
    }
  }

  private void getIntentData() {
    Intent intent = getIntent();
    if (intent != null) {
      String movieName = intent.getStringExtra("movieName_key");
      String date = intent.getStringExtra("date_key");
      String time = intent.getStringExtra("time_key");
      int age = intent.getIntExtra("age_key", 0);
      int hallNumber = intent.getIntExtra("hallNumber_key", 1);

      if (tvMovieName != null)
        tvMovieName.setText(movieName != null ? movieName : "N/A");
      if (tvAge != null)
        tvAge.setText(age > 0 ? "+" + age : "PG");
      if (tvHallNumber != null)
        tvHallNumber.setText(String.format("%02d", hallNumber));
      if (tvDate != null)
        tvDate.setText(date != null ? date : "--/--");
      if (tvTime != null)
        tvTime.setText(time != null ? time : "--:--");

      ArrayList<Integer> receivedList = intent.getIntegerArrayListExtra("bookedSeats_key");

      if (receivedList != null) {
        bookedSeatsList.addAll(receivedList);
      }
    }
  }

  private void setupSeatGrid() {
    for (int i = 1; i <= 36; i++) {
      String buttonID = "btnSeat" + i;

      int resID = getResources().getIdentifier(buttonID, "id", getPackageName());

      if (resID == 0)
        continue;

      Button btn = findViewById(resID);
      if (btn == null)
        continue;

      allSeatButtons.add(btn);
      final int currentSeatNum = i;
      if (bookedSeatsList.contains(currentSeatNum)) {
        btn.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.brand_red)));
        btn.setEnabled(false);
      } else {
        // AVAILABLE = GREY & CLICKABLE
        btn.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.seat_available)));

        btn.setOnClickListener(v -> toggleSeatSelection(btn, currentSeatNum));
      }
    }
  }

  private void toggleSeatSelection(Button btn, int seatNum) {
    if (selectedSeatIds.contains(seatNum)) {
      selectedSeatIds.remove(Integer.valueOf(seatNum));
      btn.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.seat_available)));
    } else {
      if (selectedSeatIds.size() >= MAX_SEATS) {
        Toast.makeText(this, "Max " + MAX_SEATS + " seats allowed!", Toast.LENGTH_SHORT).show();
        return;
      }
      selectedSeatIds.add(seatNum);
      btn.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.seat_selected)));
    }

    updatePriceAndButton();
  }

  private void updatePriceAndButton() {
    int total = selectedSeatIds.size() * TICKET_PRICE;

    if (tvTotalPrice != null) {
      tvTotalPrice.setText("$" + total);
    }

    if (btnConfirm != null) {
      if (selectedSeatIds.isEmpty()) {
        btnConfirm.setEnabled(false);
        btnConfirm.setAlpha(0.5f);
      } else {
        btnConfirm.setEnabled(true);
        btnConfirm.setAlpha(1.0f);
      }
    }
  }
}
