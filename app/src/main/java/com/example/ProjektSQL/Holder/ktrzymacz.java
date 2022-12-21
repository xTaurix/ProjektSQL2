package com.example.ProjektSQL.Holder;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ProjektSQL.R;

public class ktrzymacz extends RecyclerView.ViewHolder {
    public ImageView imageView;
    public TextView name;
    public TextView price;
    Context context;
    public ktrzymacz(@NonNull View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.imageview);
        name = itemView.findViewById(R.id.name);
        price = itemView.findViewById(R.id.cena12);
        context = itemView.getContext();
    }
}
