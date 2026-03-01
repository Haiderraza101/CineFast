package com.example.cinefast;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Locale;

public class TicketSummaryActivity extends AppCompatActivity {

  private TextView tvFinalMovieName, tvAge, tvHallNumber, tvDate, tvTime, tvFinalTotalPrice;
  private LinearLayout llTicketsContainer, llSnacksContainer;
  private Button btnSendTicket, btnSummaryBack;

  private String movieName, date, time, age, hallNumber;
  private ArrayList<Integer> selectedSeats;
  private int qtyPopcorn, qtyNachos, qtySoda;
  private final double TICKET_PRICE_PER_UNIT = 15.00;
  private final double PRICE_POPCORN = 8.99;
  private final double PRICE_NACHOS = 7.99;
  private final double PRICE_SODA = 5.99;

  private double totalAmount = 0.0;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_ticket_summary);

    initializeViews();
    getIntentData();
    calculateAndDisplayDetails();
    setupButtons();
  }

  private void initializeViews() {
    tvFinalMovieName = findViewById(R.id.tvFinalMovieName);
    tvAge = findViewById(R.id.tvage);
    tvHallNumber = findViewById(R.id.tvhallNumber);
    tvDate = findViewById(R.id.tvdate);
    tvTime = findViewById(R.id.tvtime);
    tvFinalTotalPrice = findViewById(R.id.tvFinalTotalPrice);

    llTicketsContainer = findViewById(R.id.llTicketsContainer);
    llSnacksContainer = findViewById(R.id.llSnacksContainer);

    btnSendTicket = findViewById(R.id.btnSendTicket);
    btnSummaryBack = findViewById(R.id.btnSummaryBack);
  }

  private void getIntentData() {
    Intent intent = getIntent();
    if (intent != null) {
      movieName = intent.getStringExtra("movieName_key");
      date = intent.getStringExtra("date_key");
      time = intent.getStringExtra("time_key");
      hallNumber = String.valueOf(intent.getIntExtra("hallNumber_key", 1));
      age = String.valueOf(intent.getIntExtra("age_key", 13));

      selectedSeats = intent.getIntegerArrayListExtra("selectedSeats_key");
      qtyPopcorn = intent.getIntExtra("popcornQty_key", 0);
      qtyNachos = intent.getIntExtra("nachosQty_key", 0);
      qtySoda = intent.getIntExtra("sodaQty_key", 0);
      tvFinalMovieName.setText(movieName != null ? movieName : "Unknown Movie");
      tvAge.setText("+" + age);
      tvHallNumber.setText(hallNumber);
      tvDate.setText(date);
      tvTime.setText(time);
    }
  }

  private void calculateAndDisplayDetails() {
    totalAmount = 0.0;

    if (selectedSeats != null && !selectedSeats.isEmpty()) {
      for (Integer seatNum : selectedSeats) {
        totalAmount += TICKET_PRICE_PER_UNIT;

        addSummaryRow(llTicketsContainer, "Seat #" + seatNum, TICKET_PRICE_PER_UNIT);
      }
    } else {
      addTextRow(llTicketsContainer, "No seats selected");
    }

    boolean hasSnacks = false;

    if (qtyPopcorn > 0) {
      double cost = qtyPopcorn * PRICE_POPCORN;
      totalAmount += cost;
      addSummaryRow(llSnacksContainer, "Popcorn x" + qtyPopcorn, cost);
      hasSnacks = true;
    }

    if (qtyNachos > 0) {
      double cost = qtyNachos * PRICE_NACHOS;
      totalAmount += cost;
      addSummaryRow(llSnacksContainer, "Nachos x" + qtyNachos, cost);
      hasSnacks = true;
    }

    if (qtySoda > 0) {
      double cost = qtySoda * PRICE_SODA;
      totalAmount += cost;
      addSummaryRow(llSnacksContainer, "Soda x" + qtySoda, cost);
      hasSnacks = true;
    }

    if (!hasSnacks) {
      addTextRow(llSnacksContainer, "No snacks selected");
    }

    // --- 3. FINAL TOTAL ---
    tvFinalTotalPrice.setText(String.format(Locale.US, "$%.2f", totalAmount));
  }

  // Helper to create the "Item ....... $Price" row dynamically
  private void addSummaryRow(LinearLayout container, String label, double price) {
    LinearLayout row = new LinearLayout(this);
    row.setOrientation(LinearLayout.HORIZONTAL);
    row.setPadding(0, 8, 0, 8);
    row.setWeightSum(1.0f);

    TextView tvLabel = new TextView(this);
    tvLabel.setText(label);
    tvLabel.setTextColor(0xFFAAAAAA);
    tvLabel.setTextSize(14f);
    LinearLayout.LayoutParams paramsLabel = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT,
        0.7f);
    tvLabel.setLayoutParams(paramsLabel);

    TextView tvPrice = new TextView(this);
    tvPrice.setText(String.format(Locale.US, "$%.2f", price));
    tvPrice.setTextColor(0xFFFFFFFF);
    tvPrice.setTextSize(14f);
    tvPrice.setGravity(Gravity.END);
    LinearLayout.LayoutParams paramsPrice = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT,
        0.3f);
    tvPrice.setLayoutParams(paramsPrice);

    row.addView(tvLabel);
    row.addView(tvPrice);
    container.addView(row);
  }

  private void addTextRow(LinearLayout container, String text) {
    TextView tv = new TextView(this);
    tv.setText(text);
    tv.setTextColor(0xFF666666);
    tv.setPadding(0, 8, 0, 8);
    container.addView(tv);
  }

  private void setupButtons() {
    btnSummaryBack.setOnClickListener(v -> finish());

    btnSendTicket.setOnClickListener(v -> shareTicketInfo());
  }

  private void shareTicketInfo() {
    StringBuilder sb = new StringBuilder();
    sb.append("*CineFAST Ticket Confirmation*\n\n");
    sb.append("Movie: ").append(movieName).append("\n");
    sb.append("Date: ").append(date).append("\n");
    sb.append("Time: ").append(time).append("\n");
    sb.append("Hall: ").append(hallNumber).append("\n");
    sb.append("Seats: ").append(selectedSeats != null ? selectedSeats.toString() : "None").append("\n\n");

    if (qtyPopcorn > 0 || qtyNachos > 0 || qtySoda > 0) {
      sb.append("Snacks:\n");
      if (qtyPopcorn > 0)
        sb.append("- Popcorn x").append(qtyPopcorn).append("\n");
      if (qtyNachos > 0)
        sb.append("- Nachos x").append(qtyNachos).append("\n");
      if (qtySoda > 0)
        sb.append("- Soda x").append(qtySoda).append("\n");
      sb.append("\n");
    }

    sb.append("Total Paid: ").append(String.format(Locale.US, "$%.2f", totalAmount)).append("\n\n");
    sb.append("See you at the movies!");

    Intent sendIntent = new Intent();
    sendIntent.setAction(Intent.ACTION_SEND);
    sendIntent.putExtra(Intent.EXTRA_TEXT, sb.toString());
    sendIntent.setType("text/plain");

    Intent shareIntent = Intent.createChooser(sendIntent, "Share Ticket via");
    startActivity(shareIntent);
  }
}