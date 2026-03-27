package com.example.cinefast;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;

public class SeatSelectionFragment extends Fragment {

    private static final String ARG_MOVIE_NAME = "movieName";
    private static final String ARG_TRAILER_URL = "trailerUrl";
    private static final String ARG_IS_COMING_SOON = "isComingSoon";

    private String movieName;
    private String trailerUrl;
    private boolean isComingSoon;

    private TextView tvMovieName;
    private Button btnConfirm, btnBack, btnProceedToSnacks;
    private List<Button> allSeatButtons = new ArrayList<>();
    private ArrayList<Integer> selectedSeatIds = new ArrayList<>();
    private ArrayList<Snack> selectedSnacks = new ArrayList<>();

    private final int MAX_SEATS = 3;
    private final int TICKET_PRICE = 15;

    public SeatSelectionFragment() {

    }

    public static SeatSelectionFragment newInstance(String movieName, String trailerUrl, boolean isComingSoon) {
        SeatSelectionFragment fragment = new SeatSelectionFragment();
        Bundle args = new Bundle();
        args.putString(ARG_MOVIE_NAME, movieName);
        args.putString(ARG_TRAILER_URL, trailerUrl);
        args.putBoolean(ARG_IS_COMING_SOON, isComingSoon);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            movieName = getArguments().getString(ARG_MOVIE_NAME);
            trailerUrl = getArguments().getString(ARG_TRAILER_URL);
            isComingSoon = getArguments().getBoolean(ARG_IS_COMING_SOON);
        }

        // Listen for snack selection results
        getParentFragmentManager().setFragmentResultListener("snacks_request", this, (requestKey, bundle) -> {
            ArrayList<Snack> result = bundle.getParcelableArrayList("selected_snacks");
            if (result != null) {
                selectedSnacks = result;
                Toast.makeText(getContext(), "Snacks added to order!", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_seat_selection, container, false);
        initializeViews(view);
        setupSeatGrid(view);
        setupClickListeners();
        return view;
    }

    private void initializeViews(View view) {
        tvMovieName = view.findViewById(R.id.tvMovieName);
        btnConfirm = view.findViewById(R.id.btnConfirm);
        btnBack = view.findViewById(R.id.btnBack);
        btnProceedToSnacks = view.findViewById(R.id.btnProceedToSnacks);

        if (tvMovieName != null)
            tvMovieName.setText(movieName);

        if (isComingSoon) {
            btnConfirm.setText("Coming Soon");
            btnConfirm.setEnabled(false);
            btnConfirm.setAlpha(0.5f);

            btnProceedToSnacks.setText("Watch Trailer");
            btnProceedToSnacks.setBackgroundTintList(
                    ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.white)));
            btnProceedToSnacks.setTextColor(ContextCompat.getColor(requireContext(), R.color.black));
        } else {
            btnConfirm.setText("Book Seats");
            btnConfirm.setEnabled(false);
            btnConfirm.setAlpha(0.5f);

            btnProceedToSnacks.setText("Proceed to Snacks");
        }
    }

    private void setupSeatGrid(View view) {
        for (int i = 1; i <= 36; i++) {
            String buttonID = "btnSeat" + i;
            int resID = getResources().getIdentifier(buttonID, "id", requireContext().getPackageName());
            if (resID == 0)
                continue;

            Button btn = view.findViewById(resID);
            if (btn == null)
                continue;

            allSeatButtons.add(btn);
            final int currentSeatNum = i;

            btn.setBackgroundTintList(
                    ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.seat_available)));

            if (!isComingSoon) {
                btn.setOnClickListener(v -> toggleSeatSelection(btn, currentSeatNum));
            } else {
                btn.setEnabled(false);
                btn.setAlpha(0.7f);
            }
        }
    }

    private void toggleSeatSelection(Button btn, int seatNum) {
        if (selectedSeatIds.contains(seatNum)) {
            selectedSeatIds.remove(Integer.valueOf(seatNum));
            btn.setBackgroundTintList(
                    ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.seat_available)));
        } else {
            if (selectedSeatIds.size() >= MAX_SEATS) {
                Toast.makeText(getContext(), "Max " + MAX_SEATS + " seats allowed!", Toast.LENGTH_SHORT).show();
                return;
            }
            selectedSeatIds.add(seatNum);
            btn.setBackgroundTintList(
                    ColorStateList.valueOf(ContextCompat.getColor(requireContext(), R.color.seat_selected)));
        }
        updatePriceAndButton();
    }

    private void updatePriceAndButton() {
        if (!isComingSoon && btnConfirm != null) {
            if (selectedSeatIds.isEmpty()) {
                btnConfirm.setEnabled(false);
                btnConfirm.setAlpha(0.5f);
                btnConfirm.setText("Book Seats");
            } else {
                btnConfirm.setEnabled(true);
                btnConfirm.setAlpha(1.0f);
                btnConfirm.setText("Book Seats ($" + (selectedSeatIds.size() * TICKET_PRICE) + ")");
            }
        }
    }

    private void setupClickListeners() {
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> getParentFragmentManager().popBackStack());
        }

        if (isComingSoon) {
            btnProceedToSnacks.setOnClickListener(v -> {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(trailerUrl));
                startActivity(intent);
            });
        } else {
            btnConfirm.setOnClickListener(v -> {
                if (getActivity() instanceof MainActivity) {
                    ((MainActivity) getActivity()).loadFragment(TicketSummaryFragment.newInstance(movieName, selectedSeatIds, selectedSnacks));
                }
            });

            btnProceedToSnacks.setOnClickListener(v -> {
                if (getActivity() instanceof MainActivity) {
                    ((MainActivity) getActivity()).loadFragment(SnacksFragment.newInstance(movieName, selectedSnacks));
                }
            });
        }
    }
}
