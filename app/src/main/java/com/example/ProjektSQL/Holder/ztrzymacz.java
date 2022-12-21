package com.example.ProjektSQL.Holder;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ProjektSQL.R;

public class ztrzymacz extends RecyclerView.ViewHolder{

    public TextView price;
    public TextView name;
    TextView priceuno;
    Context context;
    public ImageView imageView;
    public CheckBox checkBox;
    TextView textView;
    SeekBar seekBar;
    public ztrzymacz(@NonNull View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.imageview1);
        name = itemView.findViewById(R.id.nazwa1);
        price = itemView.findViewById(R.id.cena1);
        checkBox = itemView.findViewById(R.id.checkbox1);
        context = itemView.getContext();
        textView = itemView.findViewById(R.id.wynik);
        seekBar = itemView.findViewById(R.id.seekbar);
        priceuno = itemView.findViewById(R.id.cena);

    }
}
