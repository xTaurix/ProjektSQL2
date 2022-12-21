package com.example.ProjektSQL.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ProjektSQL.Holder.MyViewtrzymacz;
import com.example.ProjektSQL.R;
import com.example.ProjektSQL.Recycler.irecycler;
import com.example.ProjektSQL.ObslugaZamowienia.zamowienia;

import java.util.List;


public class adapter extends RecyclerView.Adapter<MyViewtrzymacz> {

    Context context;
    List<irecycler> items;
    String Login;
    String password;
    String Email;
    Boolean check;




    public adapter(Context context, List<irecycler> items, @Nullable String Login, @Nullable String password, @Nullable String Email, @Nullable Boolean check) {
        this.context = context;
        this.items = items;
        this.password = password;
        this.Login = Login;
        this.Email = Email;
        this.check = check;
    }

    @Override
    public MyViewtrzymacz onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewtrzymacz(LayoutInflater.from(context).inflate(R.layout.item_view, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewtrzymacz holder, int position) {
        holder.name.setText(items.get(position).getName());
        holder.price.setText(items.get(position).getPrice());
        holder.imageView.setImageResource(items.get(position).getImage());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Intent intent;
                switch (holder.getAdapterPosition()){
                    default:
                        if (check == false) {
                            intent = new Intent(holder.context, zamowienia.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            String nazwa = items.get(holder.getAdapterPosition()).getName();
                            String cena = items.get(holder.getAdapterPosition()).getPrice();
                            int image = items.get(holder.getAdapterPosition()).getImage();
                            intent.putExtra("nazwa", nazwa);
                            intent.putExtra("cena", cena);
                            intent.putExtra("image", image);
                            intent.putExtra("Bool", "False");
                            holder.context.startActivity(intent);
                        } else {
                            intent = new Intent(holder.context, zamowienia.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            String nazwa = items.get(holder.getAdapterPosition()).getName();
                            String cena = items.get(holder.getAdapterPosition()).getPrice();
                            int image = items.get(holder.getAdapterPosition()).getImage();
                            intent.putExtra("nazwa", nazwa);
                            intent.putExtra("cena", cena);
                            intent.putExtra("image", image);
                            intent.putExtra("Login", Login);
                            intent.putExtra("Haslo", password);
                            intent.putExtra("Email", Email);
                            String bool = "True";
                            intent.putExtra("Bool", bool);
                            holder.context.startActivity(intent);
                        }
                        break;
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return items.size();
    }


}
