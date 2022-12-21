package com.example.ProjektSQL.ObslugaZamowienia;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ProjektSQL.Adapter.kadapter;
import com.example.ProjektSQL.BazaDanych.SQLliteHelper;
import com.example.ProjektSQL.R;
import com.example.ProjektSQL.Recycler.krecycler;

import java.util.ArrayList;
import java.util.List;

public class KoncoweZamowienie extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.koncowezamowienie);
        Bundle bundle = getIntent().getExtras();
        String nr = "";
        String login = "";
        if (bundle != null){
            nr = bundle.getString("Nr");
            login = bundle.getString("Login");
        }
        TextView textView = findViewById(R.id.nazwa_produktu);
        textView.setText("Zamówienie " + nr);
        SQLliteHelper sqLliteHelper = new SQLliteHelper(this);
        SQLiteDatabase db = sqLliteHelper.getReadableDatabase();
        String[] projection = {
                SQLliteHelper.zamowienia.Zamowienie,
                SQLliteHelper.zamowienia.CenaZamowienia,
                SQLliteHelper.zamowienia.Obraz,
                SQLliteHelper.zamowienia.NrZamowienia

        };
        Log.v("TAG", login);
        String selection  = SQLliteHelper.zamowienia.COLUMN_NAME_TITLE + "=? AND " + SQLliteHelper.zamowienia.NrZamowienia + "=?";
        String[] selectionArgs = {login, nr};

        String sortOrder = SQLliteHelper.zamowienia.COLUMN_NAME_TITLE + " DESC";
        Cursor cursor = db.query(SQLliteHelper.zamowienia.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
        List<krecycler> items = new ArrayList<>();
        while (cursor.moveToNext()){
            items.add(new krecycler(cursor.getString(cursor.getColumnIndexOrThrow(SQLliteHelper.zamowienia.Zamowienie)), cursor.getString(cursor.getColumnIndexOrThrow(SQLliteHelper.zamowienia.CenaZamowienia)), cursor.getInt(cursor.getColumnIndexOrThrow(SQLliteHelper.zamowienia.Obraz))));
        }
        cursor.close();
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new kadapter(items, getApplicationContext()));

    }
}
