package com.example.cinefast;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class BookingAdapter extends RecyclerView.Adapter<BookingAdapter.BookingViewHolder> {

    private Context context;
    private ArrayList<Booking> bookings;
    private String userId;

    public BookingAdapter(Context context, ArrayList<Booking> bookings, String userId) {
        this.context = context;
        this.bookings = bookings;
        this.userId = userId;
    }

    public void filterList(ArrayList<Booking> filteredList) {
        this.bookings = filteredList;
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public BookingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_booking, parent, false);
        return new BookingViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookingViewHolder holder, int position) {
        Booking booking = bookings.get(position);

        holder.tvMovieName.setText(booking.getMovieName());
        holder.tvDateTime.setText(booking.getDate() + " | " + booking.getTime());
        holder.tvTickets.setText(booking.getSeats() + " Tickets");

        if (booking.getMovieName() != null) {
            String posterName = booking.getMovieName().toLowerCase().replace(" ", "");
            int resId = context.getResources().getIdentifier(posterName, "drawable", context.getPackageName());
            if (resId != 0) {
                holder.ivPoster.setImageResource(resId);
            } else {
                holder.ivPoster.setImageResource(R.drawable.inception); // fallback
            }
        }


        holder.btnCancel.setOnClickListener(v -> showCancelDialog(booking, position));
    }

    private void showCancelDialog(Booking booking, int position) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault());
            String bookingDateTimeStr = booking.getDate() + " " + booking.getTime();
            Date bookingDate = sdf.parse(bookingDateTimeStr);
            Date currentDate = new Date();

            if (bookingDate != null && bookingDate.after(currentDate)) {
                new AlertDialog.Builder(context)
                        .setTitle("Cancel Booking")
                        .setMessage("Are you sure you want to cancel this booking?")
                        .setPositiveButton("Yes", (dialog, which) -> deleteBooking(booking, position))
                        .setNegativeButton("No", null)
                        .show();
            } else {
                Toast.makeText(context, "Cannot cancel past bookings", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void deleteBooking(Booking booking, int position) {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("bookings")
                .child(userId).child(booking.getBookingId());

        ref.removeValue().addOnSuccessListener(aVoid -> {
            bookings.remove(position);
            notifyItemRemoved(position);
            Toast.makeText(context, "Booking Cancelled Successfully", Toast.LENGTH_SHORT).show();
        }).addOnFailureListener(e -> {
            Toast.makeText(context, "Failed to cancel: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return bookings.size();
    }

    public static class BookingViewHolder extends RecyclerView.ViewHolder {
        ImageView ivPoster;
        TextView tvMovieName, tvDateTime, tvTickets;
        ImageButton btnCancel;

        public BookingViewHolder(@NonNull View itemView) {
            super(itemView);
            ivPoster = itemView.findViewById(R.id.ivBookingMoviePoster);
            tvMovieName = itemView.findViewById(R.id.tvBookingMovieName);
            tvDateTime = itemView.findViewById(R.id.tvBookingDateTime);
            tvTickets = itemView.findViewById(R.id.tvBookingTickets);
            btnCancel = itemView.findViewById(R.id.btnCancelBooking);
        }
    }
}
