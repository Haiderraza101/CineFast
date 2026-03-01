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

  String movieName;
  ArrayList<String> selectedSeats;
  int ticketTotalPrice;
  double snacksTotalPrice = 0.0;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_snacks);

    Intent intent = getIntent();
    if (intent != null) {
      // movieName = intent.getStringExtra("MOVIE_NAME");
      // selectedSeats = intent.getStringArrayListExtra("SELECTED_SEATS");
      // ticketTotalPrice = intent.getIntExtra("TOTAL_PRICE", 0);
    }

    initializeViews();
    setupClickListeners();
    // updateTotal();
  }

  private void initializeViews() {
    tvSnacksTotal = findViewById(R.id.tvSnacksTotal);
    btnConfirmSnacks = findViewById(R.id.btnConfirmSnacks);

    tvQtyPopcorn = findViewById(R.id.tvQtyPopcorn);
    btnPlusPopcorn = findViewById(R.id.btnPlusPopcorn);
    btnMinusPopcorn = findViewById(R.id.btnMinusPopcorn);
    tvQtyNachos = findViewById(R.id.tvQtyNachos);
    btnPlusNachos = findViewById(R.id.btnPlusNachos);
    btnMinusNachos = findViewById(R.id.btnMinusNachos);
    tvQtySoda = findViewById(R.id.tvQtySoda);
    btnPlusSoda = findViewById(R.id.btnPlusSoda);
    btnMinusSoda = findViewById(R.id.btnMinusSoda);

  }

  private void setupClickListeners() {
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

    btnConfirmSnacks.setOnClickListener(v -> {
      Intent intent = new Intent(SnacksActivity.this, TicketSummaryActivity.class);
      intent.putExtra("movieName_key", getIntent().getStringExtra("movieName_key"));
      intent.putExtra("age_key", getIntent().getIntExtra("age_key", 13));
      intent.putExtra("hallNumber_key", getIntent().getStringExtra("hallNumber_key"));
      intent.putExtra("date_key", getIntent().getStringExtra("date_key"));
      intent.putExtra("time_key", getIntent().getStringExtra("time_key"));
      intent.putExtra("selectedSeats_key", getIntent().getIntegerArrayListExtra("selectedSeats_key"));
      intent.putExtra("popcornQty_key", qtyPopcorn);
      intent.putExtra("nachosQty_key", qtyNachos);
      intent.putExtra("sodaQty_key", qtySoda);
      startActivity(intent);
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
