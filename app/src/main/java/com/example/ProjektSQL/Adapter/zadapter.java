package com.example.ProjektSQL.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ProjektSQL.R;
import com.example.ProjektSQL.Recycler.zrecycler;
import com.example.ProjektSQL.Holder.ztrzymacz;

import java.util.List;

public class zadapter extends RecyclerView.Adapter<ztrzymacz> {

    Context context;
    List<zrecycler> items;
    int cash;

    public zadapter(Context context, List<zrecycler> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public ztrzymacz onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ztrzymacz(LayoutInflater.from(context).inflate(R.layout.zamowienia_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ztrzymacz holder, int position) {
        holder.name.setText(items.get(position).getName());
        holder.price.setText(items.get(position).getPrice());
        holder.imageView.setImageResource(items.get(position).getImage());
        holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                int pomoc = Integer.parseInt(items.get(holder.getAdapterPosition()).price);
                if (b) {
                    cash = cash + pomoc;
                }
                if (!b){
                    cash = cash - pomoc;

                }
                int mnoznik = items.get(holder.getAdapterPosition()).getSeekBar().getProgress()+1;
                int cena = Integer.parseInt(items.get(holder.getAdapterPosition()).getOrgprice());
                int wynik = mnoznik * (cena + cash);
                items.get(holder.getAdapterPosition()).getTextView().setText(String.valueOf(wynik));


            }
        });

    }
    @Override
    public int getItemCount() {
        return items.size();
    }
}
