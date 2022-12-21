package com.example.ProjektSQL.ObslugaZamowienia;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ProjektSQL.Adapter.padapter;
import com.example.ProjektSQL.BazaDanych.SQLliteHelper;
import com.example.ProjektSQL.MainActivity;
import com.example.ProjektSQL.R;
import com.example.ProjektSQL.Recycler.precycler;

import java.util.ArrayList;
import java.util.List;

public class produkt extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.produkty);
        SQLliteHelper sqLliteHelper = new SQLliteHelper(produkt.this);
        SQLiteDatabase db = sqLliteHelper.getReadableDatabase();
        Bundle bundle = getIntent().getExtras();
        String login = "";
        String email = "";
        if (bundle != null){
            login = bundle.getString("Login");
            email = bundle.getString("Email");
        }
        if (login != null && !login.equalsIgnoreCase("")){
            String[] projections = {
                    SQLliteHelper.produkty.NrZamowienia,
                    SQLliteHelper.produkty.Cena,
                    SQLliteHelper.produkty.Login
            };
            String selection = SQLliteHelper.produkty.NrZamowienia + " !=? AND " + SQLliteHelper.produkty.Login + " =?";
            String[] selectionargs = {
                    "0",
                    login
            };
            String order = SQLliteHelper.produkty.NrZamowienia + " DESC";
            Cursor cursor = db.query(SQLliteHelper.produkty.TABLE_NAME, projections,selection,selectionargs,null,null, order);

            RecyclerView recyclerView = findViewById(R.id.recycler_view);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            List<precycler> list = new ArrayList<>();
            while (cursor.moveToNext()){
                int cena = cursor.getInt(cursor.getColumnIndexOrThrow(SQLliteHelper.produkty.Cena));
                String nr = cursor.getString(cursor.getColumnIndexOrThrow(SQLliteHelper.produkty.NrZamowienia));
                String login1 = cursor.getString(cursor.getColumnIndexOrThrow(SQLliteHelper.produkty.Login));
                list.add(new precycler(nr, cena, login1));
            }
            recyclerView.setAdapter(new padapter(getApplicationContext(), list));
        }
        if (email != null && !email.equalsIgnoreCase("")){
            String[] projections = {
                    SQLliteHelper.produkty.NrZamowienia,
                    SQLliteHelper.produkty.Cena,
                    SQLliteHelper.produkty.Login
            };
            String selection = SQLliteHelper.produkty.NrZamowienia + " !=? AND " + SQLliteHelper.produkty.EMAIL + " =?";
            String[] selectionargs = {
                    "0",
                    email
            };
            String order = SQLliteHelper.produkty.NrZamowienia + " DESC";
            Cursor cursor = db.query(SQLliteHelper.produkty.TABLE_NAME, projections,selection,selectionargs,null,null, order);

            RecyclerView recyclerView = findViewById(R.id.recycler_view);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            List<precycler> list = new ArrayList<>();
            while (cursor.moveToNext()){
                int cena = cursor.getInt(cursor.getColumnIndexOrThrow(SQLliteHelper.produkty.Cena));
                String nr = cursor.getString(cursor.getColumnIndexOrThrow(SQLliteHelper.produkty.NrZamowienia));
                String login1 = cursor.getString(cursor.getColumnIndexOrThrow(SQLliteHelper.produkty.Login));
                list.add(new precycler(nr, cena, login1));
            }
            recyclerView.setAdapter(new padapter(getApplicationContext(), list));
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menupologin, menu);

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
