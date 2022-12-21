package com.example.ProjektSQL.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ProjektSQL.ObslugaZamowienia.KoncoweZamowienie;
import com.example.ProjektSQL.Holder.ptrzymacz;
import com.example.ProjektSQL.R;
import com.example.ProjektSQL.Recycler.precycler;

import java.util.List;

public class padapter extends RecyclerView.Adapter<ptrzymacz> {
    Context context;
    List<precycler> list;
    public padapter(Context context, List<precycler> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override

    public ptrzymacz onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ptrzymacz(LayoutInflater.from(context).inflate(R.layout.produkty_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ptrzymacz holder, int position) {
        holder.name.setText("Zamówienie" + list.get(position).getName());
        holder.price.setText(String.valueOf(list.get(position).getPrice()));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(holder.context, KoncoweZamowienie.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("Nr", list.get(holder.getAdapterPosition()).getName());
                intent.putExtra("Login", list.get(holder.getAdapterPosition()).getLogin());
                holder.context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
