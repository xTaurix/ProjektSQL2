package com.example.ProjektSQL.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ProjektSQL.Holder.ktrzymacz;
import com.example.ProjektSQL.R;
import com.example.ProjektSQL.Recycler.krecycler;

import java.util.List;

public class kadapter extends RecyclerView.Adapter<ktrzymacz> {

    List<krecycler> items;
    Context context;

    public kadapter(List<krecycler> items, Context context) {
        this.context = context;
        this.items = items;
    }

    @Override
    public ktrzymacz onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ktrzymacz(LayoutInflater.from(context).inflate(R.layout.koszyk_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ktrzymacz holder, int position) {
        holder.name.setText(items.get(position).getName());
        holder.price.setText(items.get(position).getPrice());
        holder.imageView.setImageResource(items.get(position).getImage());

    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
