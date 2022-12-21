package com.example.ProjektSQL.Holder;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ProjektSQL.R;

public class ptrzymacz extends RecyclerView.ViewHolder {
    public TextView name;
    public TextView price;
    public Context context;
    public ptrzymacz(@NonNull View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.Zamowienie);
        price = itemView.findViewById(R.id.cena);
        context = itemView.getContext();
    }
}
