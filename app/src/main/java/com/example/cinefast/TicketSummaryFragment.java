package com.example.cinefast;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

    public TicketSummaryFragment() {}

    public static TicketSummaryFragment newInstance(String movieName, ArrayList<Integer> seats, ArrayList<Snack> snacks) {
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
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ticket_summary, container, false);
        
        TextView tvMovie = view.findViewById(R.id.tvFinalMovieName);
        TextView tvPrice = view.findViewById(R.id.tvFinalTotalPrice);
        LinearLayout llContainer = view.findViewById(R.id.llTicketsContainer);
        Button btnBack = view.findViewById(R.id.btnSummaryBack);
        Button btnDone = view.findViewById(R.id.btnDoneSummary);

        tvMovie.setText(movieName);
        
        float seatTotal = (selectedSeats != null ? selectedSeats.size() : 0) * SEAT_PRICE;
        float snackTotal = 0;
        
        // Display Seats
        if (selectedSeats != null) {
            for (int seat : selectedSeats) {
                TextView tv = new TextView(getContext());
                tv.setText("Seat #" + seat + " ..................... $15.00");
                tv.setTextColor(0xFFAAAAAA);
                tv.setPadding(0, 8, 0, 8);
                llContainer.addView(tv);
            }
        }

        // Display Snacks
        if (selectedSnacks != null) {
            for (Snack snack : selectedSnacks) {
                if (snack.getQuantity() > 0) {
                    float subTotal = (float)(snack.getPrice() * snack.getQuantity());
                    snackTotal += subTotal;
                    
                    TextView tv = new TextView(getContext());
                    tv.setText(snack.getName() + " x" + snack.getQuantity() + " .................. $" + String.format("%.2f", subTotal));
                    tv.setTextColor(0xFFAAAAAA);
                    tv.setPadding(0, 8, 0, 8);
                    llContainer.addView(tv);
                }
            }
        }

        float total = seatTotal + snackTotal;
        tvPrice.setText(String.format("$%.2f", total));

        btnBack.setOnClickListener(v -> getParentFragmentManager().popBackStack());
        btnDone.setOnClickListener(v -> {
            saveToPreferences(total);
            Toast.makeText(requireContext(), "Booking Saved Locally!", Toast.LENGTH_SHORT).show();
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
