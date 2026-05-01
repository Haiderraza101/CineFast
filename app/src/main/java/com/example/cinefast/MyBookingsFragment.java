package com.example.cinefast;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MyBookingsFragment extends Fragment {

    private RecyclerView rvBookings;
    private BookingAdapter adapter;
    private ArrayList<Booking> bookingList;
    private DatabaseReference mDatabase;
    private String userId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_bookings, container, false);

        rvBookings = view.findViewById(R.id.rvMyBookings);
        rvBookings.setLayoutManager(new LinearLayoutManager(getContext()));
        android.widget.ProgressBar pbBookings = view.findViewById(R.id.pbBookings);
        pbBookings.setVisibility(android.view.View.VISIBLE);

        bookingList = new ArrayList<>();


        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        mDatabase = FirebaseDatabase.getInstance().getReference("bookings").child(userId);

        adapter = new BookingAdapter(getContext(), bookingList, userId);
        rvBookings.setAdapter(adapter);

        view.findViewById(R.id.btnBack).setOnClickListener(v -> getParentFragmentManager().popBackStack());
        
        view.findViewById(R.id.btnMenu).setOnClickListener(v -> 
            Toast.makeText(getContext(), "Sorting features coming soon!", Toast.LENGTH_SHORT).show());

        android.widget.EditText etSearch = view.findViewById(R.id.etSearchBookings);

        etSearch.addTextChangedListener(new android.text.TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filter(s.toString());
            }

            @Override
            public void afterTextChanged(android.text.Editable s) {}
        });

        fetchBookings();

        return view;
    }

    private void filter(String text) {
        ArrayList<Booking> filteredList = new ArrayList<>();
        for (Booking item : bookingList) {
            if (item.getMovieName().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        adapter.filterList(filteredList);
    }

    private void fetchBookings() {
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (getView() != null) {
                    android.view.View pb = getView().findViewById(R.id.pbBookings);
                    if (pb != null) pb.setVisibility(android.view.View.GONE);
                }
                bookingList.clear();

                for (DataSnapshot data : snapshot.getChildren()) {
                    Booking booking = data.getValue(Booking.class);
                    if (booking != null) {
                        bookingList.add(booking);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

}
