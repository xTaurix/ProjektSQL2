package com.example.ProjektSQL.ObslugaZamowienia;

import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ProjektSQL.Adapter.zadapter;
import com.example.ProjektSQL.BazaDanych.SQLliteHelper;
import com.example.ProjektSQL.KonotUzytkownika.Login;
import com.example.ProjektSQL.KonotUzytkownika.Register;
import com.example.ProjektSQL.MainActivity;
import com.example.ProjektSQL.R;
import com.example.ProjektSQL.Recycler.zrecycler;

import java.util.ArrayList;
import java.util.List;

public class zamowienia extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        TextView nameT;
        TextView priceT;
        TextView amount;
        ImageView imageView;
        Button minus;
        Button plus;
        Button buy;
        SeekBar seekBar;
        RecyclerView recyclerView;






        String name = "";
        String price = "";
        int image = 0;
        int cash = 0;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zamowienie);

        Bundle extras = getIntent().getExtras();
        if (extras != null){
            name = extras.getString("nazwa");
            price = extras.getString("cena");
            image = extras.getInt("image");


        }
        nameT = findViewById(R.id.name);
        priceT = findViewById(R.id.cena);
        imageView = findViewById(R.id.obraz);
        seekBar = findViewById(R.id.seekbar);
        nameT.setText(name);
        priceT.setText(price);
        imageView.setImageResource(image);
        plus = findViewById(R.id.plus);
        minus = findViewById(R.id.minus);
        amount = findViewById(R.id.ilosc_liczba);
        amount.setText(String.valueOf(seekBar.getProgress()+1));
        TextView textView1 = findViewById(R.id.wynik);
        recyclerView = findViewById(R.id.recycler_view1);
        int wynik = Integer.parseInt(priceT.getText().toString()) * (seekBar.getProgress()+1);

        textView1.setText(String.valueOf(wynik));

        List<zrecycler> items = new ArrayList<zrecycler>();

        items.add(new zrecycler("Myszka Steelseries Rival 5", "200",R.drawable.mynszka, textView1, seekBar,price));
        items.add(new zrecycler("Klawiatura Razer Ornata v2", "400",R.drawable.klawiamtura, textView1, seekBar,price));
        items.add(new zrecycler("Słuchawki Steelseries Nova 3", "500",R.drawable.sluchawki, textView1, seekBar,price));

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new zadapter(getApplicationContext(),items));

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                amount.setText(String.valueOf(seekBar.getProgress()+1));

                    recyclerView.post(new Runnable() {
                        @Override
                        public void run() {
                            int pricee = Integer.parseInt(priceT.getText().toString());
                            int cash = 0;
                            TextView textView1 = findViewById(R.id.wynik);
                            for (int j = 0; j < recyclerView.getAdapter().getItemCount(); j++) {
                                View selectedview = recyclerView.getChildAt(j);
                                CheckBox checkBox = selectedview.findViewById(R.id.checkbox1);
                                TextView textView = selectedview.findViewById(R.id.cena1);
                                if (checkBox.isChecked()){
                                    cash = cash + Integer.parseInt(textView.getText().toString());

                            }
                        }
                            textView1.setText(String.valueOf((pricee + cash) * (seekBar.getProgress() + 1)));
                    }


                });



            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                amount.setText(String.valueOf(seekBar.getProgress()+1));
                recyclerView.post(new Runnable() {
                    @Override
                    public void run() {
                        int pricee = Integer.parseInt(priceT.getText().toString());
                        TextView textView1 = findViewById(R.id.wynik);
                        int cash = 0;
                        for (int j = 0; j < recyclerView.getAdapter().getItemCount(); j++) {
                            View selectedview = recyclerView.getChildAt(j);
                            CheckBox checkBox = selectedview.findViewById(R.id.checkbox1);
                            TextView textView = selectedview.findViewById(R.id.cena1);
                            if (checkBox.isChecked()){
                                cash = cash + Integer.parseInt(textView.getText().toString());

                            }
                        }
                        textView1.setText(String.valueOf((pricee + cash) * (seekBar.getProgress() + 1)));
                    }


                });



            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                amount.setText(String.valueOf(seekBar.getProgress()+1));
                recyclerView.post(new Runnable() {
                    @Override
                    public void run() {
                        int pricee = Integer.parseInt(priceT.getText().toString());
                        TextView textView1 = findViewById(R.id.wynik);
                        int cash = 0;
                        for (int j = 0; j < recyclerView.getAdapter().getItemCount(); j++) {
                            View selectedview = recyclerView.getChildAt(j);
                            CheckBox checkBox = selectedview.findViewById(R.id.checkbox1);
                            TextView textView = selectedview.findViewById(R.id.cena1);
                            if (checkBox.isChecked()){
                                cash = cash + Integer.parseInt(textView.getText().toString());

                            }
                        }
                        textView1.setText(String.valueOf((pricee + cash) * (seekBar.getProgress() + 1)));
                    }


                });



            }
        });

        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                seekBar.setProgress(seekBar.getProgress()+1);
                amount.setText(String.valueOf(seekBar.getProgress()+1));
            }
        });
        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                seekBar.setProgress(seekBar.getProgress()-1);
                amount.setText(String.valueOf(seekBar.getProgress()+1));
            }
        });
        buy = findViewById(R.id.zakup1);
        buy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String login1 = "";
                String email1 = "";
                String login = "";
                String email = "";
                String check = "";
                int image = 0;
                Bundle extras = getIntent().getExtras();
                if (extras != null){
                    check = extras.getString("Bool");
                }
                if (check != null && check.equalsIgnoreCase("True")){

                if (extras != null){
                    login = extras.getString("Login");
                    email = extras.getString("Email");
                    image = extras.getInt("image");



                }
                if (login != null && !login.equalsIgnoreCase("")) {
                    String text = "";
                    for (int j = 0; j < recyclerView.getAdapter().getItemCount(); j++) {
                        View selectedview = recyclerView.getChildAt(j);
                        CheckBox checkBox = selectedview.findViewById(R.id.checkbox1);
                        if (checkBox.isChecked()) {
                            if (text.equals("")) {
                                text = text + items.get(j).getName();
                            }else {
                                text = text + ", " + items.get(j).getName();
                            }
                        }
                    }
                    SQLliteHelper sqLliteHelper = new SQLliteHelper(zamowienia.this);
                    SQLiteDatabase db = sqLliteHelper.getWritableDatabase();
                    SQLiteDatabase db1 = sqLliteHelper.getReadableDatabase();
                    String[] projection = {
                            SQLliteHelper.SQLscheme.COLUMN_NAME_TITLE,
                            SQLliteHelper.SQLscheme.EMAIL,

                    };
                    String selection = SQLliteHelper.SQLscheme.COLUMN_NAME_TITLE + "=?";
                    String[] selectionArgs = {login};
                    String sortOrder = SQLliteHelper.SQLscheme.COLUMN_NAME_TITLE + " DESC";
                    Cursor cursor = db1.query(SQLliteHelper.SQLscheme.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                    while (cursor.moveToNext()){
                        login1 = cursor.getString(cursor.getColumnIndexOrThrow(SQLliteHelper.SQLscheme.COLUMN_NAME_TITLE));
                        email1 = cursor.getString(cursor.getColumnIndexOrThrow(SQLliteHelper.SQLscheme.EMAIL));
                    }
                    int seekbar = seekBar.getProgress() + 1;
                    ContentValues values = new ContentValues();
                    values.put(SQLliteHelper.zamowienia.Zamowienie, "Zestaw " + nameT.getText().toString() + " z " + text + " x " + seekbar);
                    values.put(SQLliteHelper.zamowienia.COLUMN_NAME_TITLE, login1);
                    values.put(SQLliteHelper.zamowienia.EMAIL, email1);
                    values.put(SQLliteHelper.zamowienia.CenaZamowienia, textView1.getText().toString());
                    values.put(SQLliteHelper.zamowienia.Obraz, image);
                    values.put(SQLliteHelper.zamowienia.NrZamowienia, 0);
                    db.insert(SQLliteHelper.zamowienia.TABLE_NAME, null, values);

                    cursor.close();
                    Intent intent = new Intent(zamowienia.this, kosz.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("Login", login);
                    intent.putExtra("Bool", check);
                    intent.putExtra("Email", email);
                    startActivity(intent);
                }

                    if (email !=null && !email.equalsIgnoreCase("")) {
                        String napis = "";
                        for (int j = 0; j < recyclerView.getAdapter().getItemCount(); j++) {
                            View selectedview = recyclerView.getChildAt(j);
                            CheckBox checkBox = selectedview.findViewById(R.id.checkbox1);
                            if (checkBox.isChecked()) {
                                if (napis.equals("")) {
                                    napis = napis + items.get(j).getName();
                                }
                                napis = napis + ", " + items.get(j).getName();
                            }
                        }
                        SQLliteHelper sqLliteHelper = new SQLliteHelper(zamowienia.this);
                        SQLiteDatabase db = sqLliteHelper.getWritableDatabase();
                        SQLiteDatabase db1 = sqLliteHelper.getReadableDatabase();
                        String[] projection = {
                                SQLliteHelper.SQLscheme.COLUMN_NAME_TITLE,
                                SQLliteHelper.SQLscheme.EMAIL,

                        };
                        String selection2 = SQLliteHelper.SQLscheme.EMAIL + "=?";
                        String[] selectionArgs1 = {email};
                        String sortOrder2 = SQLliteHelper.SQLscheme.EMAIL + " DESC";
                        Cursor cursor1 = db1.query(SQLliteHelper.SQLscheme.TABLE_NAME, projection, selection2, selectionArgs1, null, null, sortOrder2);
                        while (cursor1.moveToNext()){
                            login1 = cursor1.getString(cursor1.getColumnIndexOrThrow(SQLliteHelper.SQLscheme.COLUMN_NAME_TITLE));
                            email1 = cursor1.getString(cursor1.getColumnIndexOrThrow(SQLliteHelper.SQLscheme.EMAIL));

                        }



                        int seekbar = seekBar.getProgress() + 1;
                        ContentValues values = new ContentValues();
                        values.put(SQLliteHelper.zamowienia.Zamowienie, "Zestaw " + nameT.getText().toString() + " z " + napis + " x " + seekbar);
                        values.put(SQLliteHelper.zamowienia.COLUMN_NAME_TITLE, login1);
                        values.put(SQLliteHelper.zamowienia.EMAIL, email1);
                        values.put(SQLliteHelper.zamowienia.CenaZamowienia, textView1.getText().toString());
                        values.put(SQLliteHelper.zamowienia.Obraz, image);
                        values.put(SQLliteHelper.zamowienia.NrZamowienia, 0);
                        db.insert(SQLliteHelper.zamowienia.TABLE_NAME, null, values);
                        cursor1.close();
                        Intent intent = new Intent(zamowienia.this, kosz.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra("Login", login);
                        intent.putExtra("Bool", check);
                        intent.putExtra("Email", email);
                        startActivity(intent);
                    }
                } else {
                    AlertDialog.Builder alert = new AlertDialog.Builder(zamowienia.this);
                    alert.setMessage("Musisz być zalogowany, aby dodać produkt do koszyka");
                    alert.setCancelable(false);
                    alert.setNeutralButton("zaloguj", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent = new Intent(zamowienia.this, Login.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                    });
                    alert.create().show();

                }
            }


            });
        }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Bundle extras = getIntent().getExtras();
        String checkuno = "";
        if (extras != null){
            checkuno = extras.getString("Bool");
        }
        MenuInflater inflater = getMenuInflater();
        if (checkuno != null && checkuno.equalsIgnoreCase("True")){
            inflater.inflate(R.menu.menupologin, menu);
        }
        else {
            inflater.inflate(R.menu.menu, menu);
        }
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int i = item.getItemId();

        Intent intent12 = new Intent(this, MainActivity.class);
        Bundle extras = getIntent().getExtras();
        String email = "";
        String login = "";
        String check = "";
        if (extras != null){
            login = extras.getString("Login");
            email = extras.getString("Email");
            check = extras.getString("Bool");


        }

        switch (i){
            case R.id.register:
                Intent intent = new Intent(this, Register.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                this.startActivity(intent);
                break;
            case R.id.login:
                Intent intent1 = new Intent(this, Login.class);
                intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                this.startActivity(intent1);
                break;
            case R.id.Wyloguj:
                intent12.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent12);
                break;
            case R.id.Usun:
                SQLliteHelper sqLliteHelper = new SQLliteHelper(this);
                SQLiteDatabase db2 = sqLliteHelper.getReadableDatabase();
                if (login != null && !login.equals("")){
                    String selection = SQLliteHelper.SQLscheme.COLUMN_NAME_TITLE + " LIKE ?";
                    String selection2 = SQLliteHelper.produkty.Login + " LIKE ?";
                    String selection3 = SQLliteHelper.zamowienia.COLUMN_NAME_TITLE + " LIKE ?";
                    String [] selectionArgs = {login};
                    db2.delete(SQLliteHelper.SQLscheme.TABLE_NAME, selection, selectionArgs);
                    db2.delete(SQLliteHelper.zamowienia.TABLE_NAME, selection3, selectionArgs);
                    db2.delete(SQLliteHelper.produkty.TABLE_NAME, selection2, selectionArgs);

                }
                if (email != null && !email.equals("")){
                    String selection = SQLliteHelper.SQLscheme.EMAIL + " LIKE ?";
                    String selection2 = SQLliteHelper.produkty.EMAIL + " LIKE ?";
                    String selection3 = SQLliteHelper.zamowienia.EMAIL + " LIKE ?";
                    String [] selectionArgs = {email};
                    db2.delete(SQLliteHelper.SQLscheme.TABLE_NAME, selection, selectionArgs);
                    db2.delete(SQLliteHelper.zamowienia.TABLE_NAME, selection3, selectionArgs);
                    db2.delete(SQLliteHelper.produkty.TABLE_NAME, selection2, selectionArgs);
                }
                intent12.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent12);
                break;

            case R.id.koszyk:
                Intent intent11 = new Intent(this, kosz.class);
                intent11.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent11.putExtra("Login", login);
                intent11.putExtra("Email", email);
                intent11.putExtra("Bool", check);

                startActivity(intent11);
                break;
            case R.id.zamowienia:
                Intent intent2 = new Intent(this, produkt.class);
                intent2.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent2.putExtra("Login", login);
                intent2.putExtra("Email", email);
                intent2.putExtra("Bool", check);
                startActivity(intent2);
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
