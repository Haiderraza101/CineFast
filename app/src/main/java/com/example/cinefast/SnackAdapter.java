package com.example.cinefast;

import android.content.Context;
import android.content.res.ColorStateList;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import com.google.android.material.button.MaterialButton;

import java.util.List;

public class SnackAdapter extends ArrayAdapter<Snack> {

    private Context context;
    private OnQuantityChangeListener listener;

    public interface OnQuantityChangeListener {
        void onQuantityChanged();
    }

    public SnackAdapter(@NonNull Context context, @NonNull List<Snack> objects, OnQuantityChangeListener listener) {
        super(context, 0, objects);
        this.context = context;
        this.listener = listener;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_snack, parent, false);
        }

        Snack snack = getItem(position);

        ImageView ivImage = convertView.findViewById(R.id.ivSnackImage);
        TextView tvName = convertView.findViewById(R.id.tvSnackName);
        TextView tvDesc = convertView.findViewById(R.id.tvSnackDesc);
        TextView tvPrice = convertView.findViewById(R.id.tvSnackPrice);
        TextView tvQty = convertView.findViewById(R.id.tvQty);
        MaterialButton btnPlus = convertView.findViewById(R.id.btnPlus);
        MaterialButton btnMinus = convertView.findViewById(R.id.btnMinus);
        CardView cardImage = convertView.findViewById(R.id.cardSnackImage);

        tvName.setText(snack.getName());
        tvDesc.setText(snack.getDescription());
        tvPrice.setText("$" + snack.getPrice());
        tvQty.setText(String.valueOf(snack.getQuantity()));
        ivImage.setImageResource(snack.getImageRes());
        cardImage.setCardBackgroundColor(ColorStateList.valueOf(snack.getBgColor()));

        btnPlus.setOnClickListener(v -> {
            snack.setQuantity(snack.getQuantity() + 1);
            tvQty.setText(String.valueOf(snack.getQuantity()));
            if (listener != null) listener.onQuantityChanged();
        });

        btnMinus.setOnClickListener(v -> {
            if (snack.getQuantity() > 0) {
                snack.setQuantity(snack.getQuantity() - 1);
                tvQty.setText(String.valueOf(snack.getQuantity()));
                if (listener != null) listener.onQuantityChanged();
            }
        });

        return convertView;
    }
}
