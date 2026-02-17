package com.example.cinefast;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Locale;

public class SnacksActivity extends AppCompatActivity {

  // Views
  TextView tvSnacksTotal;
  Button btnConfirmSnacks;

  // Popcorn
  TextView tvQtyPopcorn;
  Button btnPlusPopcorn, btnMinusPopcorn;
  int qtyPopcorn = 0;
  double pricePopcorn = 8.99;

  // Nachos
  TextView tvQtyNachos;
  Button btnPlusNachos, btnMinusNachos;
  int qtyNachos = 0;
  double priceNachos = 7.99;

  // Soda
  TextView tvQtySoda;
  Button btnPlusSoda, btnMinusSoda;
  int qtySoda = 0;
  double priceSoda = 5.99;

  // Candy
  TextView tvQtyCandy;
  Button btnPlusCandy, btnMinusCandy;
  int qtyCandy = 0;
  double priceCandy = 6.99;

  // Data from previous activities
  String movieName;
  ArrayList<String> selectedSeats;
  int ticketTotalPrice;
  double snacksTotalPrice = 0.0;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_snacks);

    // Initialize Intent data
    Intent intent = getIntent();
    if (intent != null) {
      movieName = intent.getStringExtra("MOVIE_NAME");
      selectedSeats = intent.getStringArrayListExtra("SELECTED_SEATS");
      ticketTotalPrice = intent.getIntExtra("TOTAL_PRICE", 0);
    }

    initializeViews();
    setupClickListeners();
    updateTotal();
  }

  private void initializeViews() {
    tvSnacksTotal = findViewById(R.id.tvSnacksTotal);
    btnConfirmSnacks = findViewById(R.id.btnConfirmSnacks);

    // Popcorn
    tvQtyPopcorn = findViewById(R.id.tvQtyPopcorn);
    btnPlusPopcorn = findViewById(R.id.btnPlusPopcorn);
    btnMinusPopcorn = findViewById(R.id.btnMinusPopcorn);

    // Nachos
    tvQtyNachos = findViewById(R.id.tvQtyNachos);
    btnPlusNachos = findViewById(R.id.btnPlusNachos);
    btnMinusNachos = findViewById(R.id.btnMinusNachos);

    // Soda
    tvQtySoda = findViewById(R.id.tvQtySoda);
    btnPlusSoda = findViewById(R.id.btnPlusSoda);
    btnMinusSoda = findViewById(R.id.btnMinusSoda);

    // Candy
    tvQtyCandy = findViewById(R.id.tvQtyCandy);
    btnPlusCandy = findViewById(R.id.btnPlusCandy);
    btnMinusCandy = findViewById(R.id.btnMinusCandy);
  }

  private void setupClickListeners() {
    // Popcorn
    btnPlusPopcorn.setOnClickListener(v -> {
      qtyPopcorn++;
      tvQtyPopcorn.setText(String.valueOf(qtyPopcorn));
      updateTotal();
    });
    btnMinusPopcorn.setOnClickListener(v -> {
      if (qtyPopcorn > 0) {
        qtyPopcorn--;
        tvQtyPopcorn.setText(String.valueOf(qtyPopcorn));
        updateTotal();
      }
    });

    // Nachos
    btnPlusNachos.setOnClickListener(v -> {
      qtyNachos++;
      tvQtyNachos.setText(String.valueOf(qtyNachos));
      updateTotal();
    });
    btnMinusNachos.setOnClickListener(v -> {
      if (qtyNachos > 0) {
        qtyNachos--;
        tvQtyNachos.setText(String.valueOf(qtyNachos));
        updateTotal();
      }
    });

    // Soda
    btnPlusSoda.setOnClickListener(v -> {
      qtySoda++;
      tvQtySoda.setText(String.valueOf(qtySoda));
      updateTotal();
    });
    btnMinusSoda.setOnClickListener(v -> {
      if (qtySoda > 0) {
        qtySoda--;
        tvQtySoda.setText(String.valueOf(qtySoda));
        updateTotal();
      }
    });

    // Candy
    btnPlusCandy.setOnClickListener(v -> {
      qtyCandy++;
      tvQtyCandy.setText(String.valueOf(qtyCandy));
      updateTotal();
    });
    btnMinusCandy.setOnClickListener(v -> {
      if (qtyCandy > 0) {
        qtyCandy--;
        tvQtyCandy.setText(String.valueOf(qtyCandy));
        updateTotal();
      }
    });

    // Confirm
    btnConfirmSnacks.setOnClickListener(v -> {
      Intent nextIntent = new Intent(SnacksActivity.this, TicketSummaryActivity.class);
      nextIntent.putExtra("MOVIE_NAME", movieName);
      nextIntent.putStringArrayListExtra("SELECTED_SEATS", selectedSeats);
      nextIntent.putExtra("TICKET_PRICE", (double) ticketTotalPrice);
      nextIntent.putExtra("SNACKS_TOTAL", snacksTotalPrice);
      nextIntent.putExtra("TOTAL_PRICE", (double) ticketTotalPrice + snacksTotalPrice);
      startActivity(nextIntent);
    });
  }

  private void updateTotal() {
    snacksTotalPrice = (qtyPopcorn * pricePopcorn) +
        (qtyNachos * priceNachos) +
        (qtySoda * priceSoda) +
        (qtyCandy * priceCandy);

    tvSnacksTotal.setText(String.format(Locale.getDefault(), "$%.2f", snacksTotalPrice));
  }
}
