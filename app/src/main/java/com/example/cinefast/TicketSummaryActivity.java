package com.example.cinefast;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Locale;

public class TicketSummaryActivity extends AppCompatActivity {

  TextView tvFinalMovieName, tvFinalTotalPrice;
  LinearLayout llTicketsContainer, llSnacksContainer;
  Button btnSendTicket, btnSummaryBack;

  String movieName;
  ArrayList<String> selectedSeats;
  double ticketPrice;
  double snacksTotal;
  double totalCombined;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_ticket_summary);

    // Initialize Views
    tvFinalMovieName = findViewById(R.id.tvFinalMovieName);
    tvFinalTotalPrice = findViewById(R.id.tvFinalTotalPrice);
    llTicketsContainer = findViewById(R.id.llTicketsContainer);
    llSnacksContainer = findViewById(R.id.llSnacksContainer);
    btnSendTicket = findViewById(R.id.btnSendTicket);
    btnSummaryBack = findViewById(R.id.btnSummaryBack);

    // Get Data
    Intent intent = getIntent();
    if (intent != null) {
      movieName = intent.getStringExtra("MOVIE_NAME");
      selectedSeats = intent.getStringArrayListExtra("SELECTED_SEATS");
      // Handled both int and double since I used different types in previous steps
      // during implementation
      ticketPrice = intent.getDoubleExtra("TICKET_PRICE", 0.0);
      if (ticketPrice == 0.0)
        ticketPrice = intent.getIntExtra("TOTAL_PRICE", 0);

      snacksTotal = intent.getDoubleExtra("SNACKS_TOTAL", 0.0);
      totalCombined = ticketPrice + snacksTotal;

      // Update UI
      tvFinalMovieName.setText(movieName);
      tvFinalTotalPrice.setText(String.format(Locale.getDefault(), "%.2f USD", totalCombined));

      displayTickets();
      displaySnacks();
    }

    setupButtons();
  }

  private void displayTickets() {
    if (selectedSeats == null)
      return;

    double pricePerSeat = ticketPrice / selectedSeats.size();

    for (String seat : selectedSeats) {
      LinearLayout row = createSummaryRow("Seat " + seat, String.format(Locale.getDefault(), "%.2f USD", pricePerSeat));
      llTicketsContainer.addView(row);
    }
  }

  private void displaySnacks() {
    if (snacksTotal > 0) {
      LinearLayout row = createSummaryRow("Selected Snacks",
          String.format(Locale.getDefault(), "%.2f USD", snacksTotal));
      llSnacksContainer.addView(row);
    } else {
      TextView none = new TextView(this);
      none.setText("No snacks selected");
      none.setTextColor(0xFF666666);
      llSnacksContainer.addView(none);
    }
  }

  private LinearLayout createSummaryRow(String leftText, String rightText) {
    LinearLayout layout = new LinearLayout(this);
    layout.setOrientation(LinearLayout.HORIZONTAL);
    layout.setPadding(0, 8, 0, 8);

    TextView left = new TextView(this);
    left.setText(leftText);
    left.setTextColor(0xFFB0B0B0);
    left.setTextSize(14f);
    LinearLayout.LayoutParams p1 = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f);
    left.setLayoutParams(p1);

    TextView right = new TextView(this);
    right.setText(rightText);
    right.setTextColor(0xFFFFFFFF);
    right.setTextSize(14f);
    right.setGravity(Gravity.END);

    layout.addView(left);
    layout.addView(right);
    return layout;
  }

  private void setupButtons() {
    btnSummaryBack.setOnClickListener(v -> finish());

    btnSendTicket.setOnClickListener(v -> {
      shareTicketInfo();
    });
  }

  private void shareTicketInfo() {
    // Create the ticket text
    StringBuilder ticketText = new StringBuilder();
    ticketText.append("🎟️ CineFAST Ticket Confirmation\n\n");
    ticketText.append("Movie: ").append(movieName).append("\n");
    ticketText.append("Seats: ").append(selectedSeats != null ? selectedSeats.toString() : "N/A").append("\n");
    ticketText.append("Date: 13.04.2025\n");
    ticketText.append("Time: 22:15\n\n");
    ticketText.append("Total Amount: ").append(String.format(Locale.getDefault(), "%.2f USD", totalCombined))
        .append("\n\n");
    ticketText.append("Enjoy your movie with CineFAST! 🍿");

    // Implicit Intent (ACTION_SEND)
    Intent sendIntent = new Intent();
    sendIntent.setAction(Intent.ACTION_SEND);
    sendIntent.putExtra(Intent.EXTRA_TEXT, ticketText.toString());
    sendIntent.setType("text/plain");

    // Show Chooser (WhatsApp, Gmail, etc.)
    Intent shareIntent = Intent.createChooser(sendIntent, "Send Ticket via:");
    startActivity(shareIntent);
  }
}
