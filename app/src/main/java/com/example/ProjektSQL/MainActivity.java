package com.example.ProjektSQL;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;


import com.example.ProjektSQL.Adapter.adapter;
import com.example.ProjektSQL.BazaDanych.SQLliteHelper;
import com.example.ProjektSQL.KonotUzytkownika.Login;
import com.example.ProjektSQL.KonotUzytkownika.Register;
import com.example.ProjektSQL.ObslugaZamowienia.kosz;
import com.example.ProjektSQL.ObslugaZamowienia.produkt;
import com.example.ProjektSQL.Recycler.irecycler;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Bundle extras = getIntent().getExtras();
        String email = "";
        String login = "";
        String password = "";
        String check = "";
        if (extras != null){
            check = extras.getString("Bool");
            login = extras.getString("Login");
            password = extras.getString("Pass");
            email = extras.getString("Email");


        }
        Boolean checkuno = check.equals("True");

        RecyclerView recyclerView = findViewById(R.id.recycler_view);

        List<irecycler> items = new ArrayList<irecycler>();
        items.add(new irecycler("G4M3R HERO", "6450",R.drawable.xkomhero));
        items.add(new irecycler("MSI Trident", "8000",R.drawable.trident));
        items.add(new irecycler("Acer Nitro 50", "6000",R.drawable.asus));
        items.add(new irecycler("komputronik ultimate x712", "11111",R.drawable.ultimate));

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new adapter(getApplicationContext(),items, login, password, email, checkuno));
        Context context = getApplicationContext();

        new SQLliteHelper(context);

        }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Bundle extras = getIntent().getExtras();
        String email = "";
        String login = "";
        String haslo = "";
        String check = "";
        if (extras != null){
            check = extras.getString("Bool");
            login = extras.getString("Login");
            haslo = extras.getString("Pass");
            email = extras.getString("Email");


        }
        MenuInflater inflater = getMenuInflater();
        if (check != null && check.equalsIgnoreCase("True")){
            inflater.inflate(R.menu.menupologin, menu);
        }
        else{
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