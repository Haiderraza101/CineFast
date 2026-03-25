package com.example.cinefast;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.PopupMenu;
import androidx.fragment.app.Fragment;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import androidx.viewpager2.widget.ViewPager2;

public class HomeFragment extends Fragment {

    private TabLayout tabLayout;
    private ViewPager2 viewPager;
    private ViewPagerAdapter adapter;
    private ImageButton btnMenu;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        init(view);
        setupViewPager();
        return view;
    }

    private void init(View view) {
        tabLayout = view.findViewById(R.id.tabLayoutHome);
        viewPager = view.findViewById(R.id.viewPagerHome);
        btnMenu = view.findViewById(R.id.btnMenuHome);

        if (btnMenu != null) {
            btnMenu.setOnClickListener(v -> showPopupMenu(v));
        }
    }

    private void showPopupMenu(View view) {
        PopupMenu popup = new PopupMenu(requireContext(), view);
        popup.getMenu().add("View Last Booking");
        popup.setOnMenuItemClickListener(item -> {
            if (item.getTitle().equals("View Last Booking")) {
                showLastBooking();
                return true;
            }
            return false;
        });
        popup.show();
    }

    private void showLastBooking() {
        SharedPreferences sPref = requireContext().getSharedPreferences("booking_data", Context.MODE_PRIVATE);
        String movie = sPref.getString("last_movie", null);
        if (movie == null) {
            new AlertDialog.Builder(requireContext())
                    .setTitle("Last Booking")
                    .setMessage("No previous booking found.")
                    .setPositiveButton("OK", null)
                    .show();
        } else {
            int seats = sPref.getInt("last_seats", 0);
            float price = sPref.getFloat("last_price", 0.0f);
            new AlertDialog.Builder(requireContext())
                    .setTitle("Last Booking")
                    .setMessage("Movie: " + movie + "\n" +
                            "Seats: " + seats + "\n" +
                            "Total Price: $" + String.format("%.1f", price))
                    .setPositiveButton("OK", null)
                    .show();
        }
    }

    private void setupViewPager() {
        adapter = new ViewPagerAdapter(this);
        viewPager.setAdapter(adapter);

        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            if (position == 0) tab.setText("Now Showing");
            else tab.setText("Coming Soon");
        }).attach();
    }
}
