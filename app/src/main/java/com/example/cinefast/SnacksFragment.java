package com.example.cinefast;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;

public class SnacksFragment extends Fragment {

    private ListView lvSnacks;
    private TextView tvSnacksTotal;
    private Button btnConfirm;
    private ArrayList<Snack> snackList;
    private SnackAdapter adapter;
    private String movieName;

    public SnacksFragment() {
        // Required empty public constructor
    }

    public static SnacksFragment newInstance(String movieName, ArrayList<Snack> existingSnacks) {
        SnacksFragment fragment = new SnacksFragment();
        Bundle args = new Bundle();
        args.putString("movieName", movieName);
        args.putParcelableArrayList("existing_snacks", existingSnacks);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            movieName = getArguments().getString("movieName");
            snackList = getArguments().getParcelableArrayList("existing_snacks");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_snacks, container, false);
        initialize(view);
        if (snackList == null || snackList.isEmpty()) {
            loadData();
        }
        setupListView();
        updateTotal();
        return view;
    }

    private void initialize(View view) {
        lvSnacks = view.findViewById(R.id.lvSnacks);
        tvSnacksTotal = view.findViewById(R.id.tvSnacksTotal);
        btnConfirm = view.findViewById(R.id.btnConfirmSnacks);
        Button btnBack = view.findViewById(R.id.btnBackSnacks);

        if (btnBack != null) {
            btnBack.setOnClickListener(v -> getParentFragmentManager().popBackStack());
        }

        btnConfirm.setOnClickListener(v -> {
            // Prepare Result
            Bundle result = new Bundle();
            result.putParcelableArrayList("selected_snacks", snackList);

            // Send back to SeatSelectionFragment
            getParentFragmentManager().setFragmentResult("snacks_request", result);

            // Go back
            getParentFragmentManager().popBackStack();
        });
    }

    private void loadData() {
        snackList = new ArrayList<>();
        snackList.add(new Snack("Popcorn", "Large / Buttered", 8.99, R.drawable.popcorn,
                ContextCompat.getColor(requireContext(), R.color.snack_popcorn)));
        snackList.add(new Snack("Nachos", "With Cheese Dip", 7.99, R.drawable.nacho,
                ContextCompat.getColor(requireContext(), R.color.snack_nachos)));
        snackList.add(new Snack("Soft Drink", "Large / Any Flavor", 5.99, R.drawable.softdrink,
                ContextCompat.getColor(requireContext(), R.color.snack_soda)));
        snackList.add(new Snack("Hot Dog", "Spicy / Mustard", 6.99, R.drawable.nacho,
                ContextCompat.getColor(requireContext(), R.color.snack_hotdog)));
    }

    private void setupListView() {
        adapter = new SnackAdapter(requireContext(), snackList, this::updateTotal);
        lvSnacks.setAdapter(adapter);
    }

    private void updateTotal() {
        double total = 0;
        if (snackList != null) {
            for (Snack s : snackList) {
                total += s.getQuantity() * s.getPrice();
            }
        }
        tvSnacksTotal.setText(String.format("$%.2f", total));
    }
}
