package com.example.cinefast;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class TicketSummaryFragment extends Fragment {

    private String movieName;
    private ArrayList<Integer> selectedSeats;
    private ArrayList<Snack> selectedSnacks;
    private final float SEAT_PRICE = 15.0f;

    public TicketSummaryFragment() {
    }

    public static TicketSummaryFragment newInstance(String movieName, ArrayList<Integer> seats,
            ArrayList<Snack> snacks) {
        TicketSummaryFragment fragment = new TicketSummaryFragment();
        Bundle args = new Bundle();
        args.putString("movieName", movieName);
        args.putIntegerArrayList("seats", seats);
        args.putParcelableArrayList("snacks", snacks);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            movieName = getArguments().getString("movieName");
            selectedSeats = getArguments().getIntegerArrayList("seats");
            selectedSnacks = getArguments().getParcelableArrayList("snacks");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ticket_summary, container, false);

        TextView tvMovie = view.findViewById(R.id.tvFinalMovieName);
        TextView tvPrice = view.findViewById(R.id.tvFinalTotalPrice);
        LinearLayout llTicketsContainer = view.findViewById(R.id.llTicketsDynamicContainer);
        LinearLayout llSnacksContainer = view.findViewById(R.id.llSnacksDynamicContainer);
        ImageView btnBack = view.findViewById(R.id.btnSummaryBack);
        Button btnDone = view.findViewById(R.id.btnDoneSummary);
        ImageView ivPoster = view.findViewById(R.id.ivMoviePoster);

        TextView tvDate = view.findViewById(R.id.tvDateVal);
        TextView tvTime = view.findViewById(R.id.tvTimeVal);

        tvMovie.setText(movieName);

        android.content.SharedPreferences sPref = requireContext().getSharedPreferences("booking_data",
                android.content.Context.MODE_PRIVATE);
        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd.MM.yyyy", java.util.Locale.getDefault());
        String defaultDate = sdf.format(new java.util.Date());
        tvDate.setText(sPref.getString("selected_date", defaultDate));

        if (movieName != null) {
            if (movieName.equalsIgnoreCase("Inception"))
                ivPoster.setImageResource(R.drawable.inception);
            else if (movieName.equalsIgnoreCase("Interstellar"))
                ivPoster.setImageResource(R.drawable.interstellar);
            else if (movieName.equalsIgnoreCase("The Night Rider"))
                ivPoster.setImageResource(R.drawable.the_night_rider);
        }

        float seatTotal = (selectedSeats != null ? selectedSeats.size() : 0) * SEAT_PRICE;
        float snackTotal = 0;

        if (selectedSeats != null) {
            for (int seat : selectedSeats) {
                View rowView = inflater.inflate(R.layout.item_summary_row, llTicketsContainer, false);
                TextView tvLabel = rowView.findViewById(R.id.tvLabel);
                TextView tvValue = rowView.findViewById(R.id.tvValue);

                int rowNum = (seat / 8) + 1;
                int seatNum = (seat % 8) + 1;
                char rowChar = (char) ('A' + (rowNum - 1));

                tvLabel.setText("Row " + rowChar + ", Seat " + seatNum);
                tvValue.setText(String.format("%.0f USD", SEAT_PRICE));

                llTicketsContainer.addView(rowView);
            }
        }

        if (selectedSnacks != null) {
            boolean hasSnacks = false;
            for (Snack snack : selectedSnacks) {
                if (snack.getQuantity() > 0) {
                    hasSnacks = true;
                    float subTotal = (float) (snack.getPrice() * snack.getQuantity());
                    snackTotal += subTotal;

                    View rowView = inflater.inflate(R.layout.item_summary_row, llSnacksContainer, false);
                    TextView tvLabel = rowView.findViewById(R.id.tvLabel);
                    TextView tvValue = rowView.findViewById(R.id.tvValue);

                    tvLabel.setText("X" + snack.getQuantity() + " " + snack.getName());
                    tvValue.setText(String.format("%.0f USD", subTotal));

                    llSnacksContainer.addView(rowView);
                }
            }
            if (!hasSnacks) {
                TextView tvNoSnacks = new TextView(getContext());
                tvNoSnacks.setText("No snacks selected");
                tvNoSnacks.setTextColor(0xFF666666);
                tvNoSnacks.setPadding(0, 16, 0, 16);
                llSnacksContainer.addView(tvNoSnacks);
            }
        }

        float total = seatTotal + snackTotal;
        tvPrice.setText(String.format("%.2f USD", total));

        btnBack.setOnClickListener(v -> getParentFragmentManager().popBackStack());
        btnDone.setOnClickListener(v -> {
            saveToPreferences(total);

            StringBuilder shareBody = new StringBuilder();
            shareBody.append("CineFast Ticket Summary\n\n");
            shareBody.append("Movie: ").append(movieName).append("\n");
            shareBody.append("Date: ").append(tvDate.getText()).append("\n");
            shareBody.append("Time: ").append(tvTime.getText()).append("\n\n");

            shareBody.append("Seats Selected:\n");
            if (selectedSeats != null) {
                for (int seat : selectedSeats) {
                    int rowNum = (seat / 8) + 1;
                    int seatNum = (seat % 8) + 1;
                    char rowChar = (char) ('A' + (rowNum - 1));
                    shareBody.append("- Row ").append(rowChar).append(", Seat ").append(seatNum).append("\n");
                }
            }

            if (selectedSnacks != null && !selectedSnacks.isEmpty()) {
                shareBody.append("\nSnack Details:\n");
                for (Snack snack : selectedSnacks) {
                    if (snack.getQuantity() > 0) {
                        shareBody.append("- ").append(snack.getName()).append(" (x").append(snack.getQuantity())
                                .append(")\n");
                    }
                }
            }

            shareBody.append("\nTotal Amount: ").append(String.format("%.2f USD", total));

            android.content.Intent shareIntent = new android.content.Intent(android.content.Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "CineFast Ticket - " + movieName);
            shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody.toString());
            startActivity(android.content.Intent.createChooser(shareIntent, "Share Ticket via"));

            if (getActivity() instanceof MainActivity) {
                ((MainActivity) getActivity()).loadFragment(new HomeFragment());
            }
        });

        return view;
    }

    private void saveToPreferences(float totalPrice) {
        SharedPreferences sPref = requireContext().getSharedPreferences("booking_data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sPref.edit();
        editor.putString("last_movie", movieName);
        editor.putInt("last_seats", selectedSeats != null ? selectedSeats.size() : 0);
        editor.putFloat("last_price", totalPrice);
        editor.apply();
    }
}
