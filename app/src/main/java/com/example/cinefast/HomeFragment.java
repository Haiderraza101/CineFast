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
    private android.widget.Button btnToday, btnTomorrow;
    private String selectedDate;

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

        btnToday = view.findViewById(R.id.btnToday);
        btnTomorrow = view.findViewById(R.id.btnTomorrow);

        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd.MM.yyyy", java.util.Locale.getDefault());
        String today = sdf.format(new java.util.Date());
        
        java.util.Calendar cal = java.util.Calendar.getInstance();
        cal.add(java.util.Calendar.DAY_OF_YEAR, 1);
        String tomorrow = sdf.format(cal.getTime());

        SharedPreferences sPref = requireContext().getSharedPreferences("booking_data", Context.MODE_PRIVATE);
        selectedDate = sPref.getString("selected_date", today);

        updateDateUI(today, tomorrow);

        btnToday.setOnClickListener(v -> {
            selectedDate = today;
            sPref.edit().putString("selected_date", today).apply();
            updateDateUI(today, tomorrow);
        });

        btnTomorrow.setOnClickListener(v -> {
            selectedDate = tomorrow;
            sPref.edit().putString("selected_date", tomorrow).apply();
            updateDateUI(today, tomorrow);
        });
    }

    private void updateDateUI(String today, String tomorrow) {
        if (selectedDate.equals(today)) {
            btnToday.setBackgroundTintList(android.content.res.ColorStateList.valueOf(android.graphics.Color.parseColor("#FF0000")));
            btnToday.setTextColor(android.graphics.Color.WHITE);
            btnTomorrow.setBackgroundTintList(android.content.res.ColorStateList.valueOf(android.graphics.Color.parseColor("#1A1A2E")));
            btnTomorrow.setTextColor(android.graphics.Color.parseColor("#B0B0B0"));
        } else {
            btnToday.setBackgroundTintList(android.content.res.ColorStateList.valueOf(android.graphics.Color.parseColor("#1A1A2E")));
            btnToday.setTextColor(android.graphics.Color.parseColor("#B0B0B0"));
            btnTomorrow.setBackgroundTintList(android.content.res.ColorStateList.valueOf(android.graphics.Color.parseColor("#FF0000")));
            btnTomorrow.setTextColor(android.graphics.Color.WHITE);
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
