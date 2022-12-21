package com.example.ProjektSQL.ObslugaZamowienia;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ProjektSQL.Adapter.kadapter;
import com.example.ProjektSQL.BazaDanych.SQLliteHelper;
import com.example.ProjektSQL.MainActivity;
import com.example.ProjektSQL.R;
import com.example.ProjektSQL.Recycler.krecycler;

import java.util.ArrayList;
import java.util.List;

public class kosz extends AppCompatActivity {
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.koszyk);
        Bundle extras = getIntent().getExtras();
        String login;
        String email;
        String check = "";

        if (extras != null){
            check = extras.getString("Bool");


        }
        if (check != null && check.equalsIgnoreCase("True")){
            login = extras.getString("Login");
            email = extras.getString("Email");
            if (login != null && !login.equalsIgnoreCase("")){
                SQLliteHelper sqLliteHelper = new SQLliteHelper(kosz.this);
                SQLiteDatabase db1 = sqLliteHelper.getReadableDatabase();
                String[] projection = {
                        SQLliteHelper.zamowienia.Zamowienie,
                        SQLliteHelper.zamowienia.CenaZamowienia,
                        SQLliteHelper.zamowienia.Obraz,
                        SQLliteHelper.zamowienia.NrZamowienia

                };
                String selection  = SQLliteHelper.zamowienia.COLUMN_NAME_TITLE + "=? AND " + SQLliteHelper.zamowienia.NrZamowienia + "=?";
                String[] selectionArgs = {login, "0"};

                String sortOrder = SQLliteHelper.zamowienia.COLUMN_NAME_TITLE + " DESC";
                Cursor cursor = db1.query(SQLliteHelper.zamowienia.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                List<krecycler> items = new ArrayList<>();
                while (cursor.moveToNext()){
                    items.add(new krecycler(cursor.getString(cursor.getColumnIndexOrThrow(SQLliteHelper.zamowienia.Zamowienie)), cursor.getString(cursor.getColumnIndexOrThrow(SQLliteHelper.zamowienia.CenaZamowienia)), cursor.getInt(cursor.getColumnIndexOrThrow(SQLliteHelper.zamowienia.Obraz))));
                }
                cursor.close();
                RecyclerView recyclerView;
                recyclerView = findViewById(R.id.recycler_view12);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                recyclerView.setAdapter(new kadapter(items, getApplicationContext()));
                recyclerView.post(new Runnable() {
                    @Override
                    public void run() {
                        String pomoc;
                        int wynik = 0;
                        for (int i = 0; i < recyclerView.getAdapter().getItemCount(); i++) {
                            View selectedView = recyclerView.getChildAt(i);
                            TextView textView = selectedView .findViewById(R.id.cena12);
                            pomoc = textView.getText().toString();
                            wynik = wynik + Integer.parseInt(pomoc);
                        }
                        if (recyclerView.getAdapter().getItemCount() == 0){
                            TextView textView1 = findViewById(R.id.Koszt);
                            textView1.setText("Brak produktów w koszyku");
                        } else {
                            TextView textView1 = findViewById(R.id.Koszt);
                            textView1.setText("Koszt to: " + wynik);
                        }
                    }
                });
                Button button = findViewById(R.id.kup);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (items.size() == 0){
                            AlertDialog.Builder alert = new AlertDialog.Builder(kosz.this);
                            alert.setMessage("Brak produktów w koszyku, dodaj produkt do koszyka by złożyc zamówienie");
                            alert.create().show();

                        } else {
                            int numer = 0;
                            Bundle extras = getIntent().getExtras();
                            String login;
                            login = extras.getString("Login");
                            Log.v("TAG", login);
                            SQLliteHelper sqLliteHelper = new SQLliteHelper(kosz.this);
                            SQLiteDatabase db1 = sqLliteHelper.getReadableDatabase();

                            String [] projection = {
                                    SQLliteHelper.zamowienia.COLUMN_NAME_TITLE,
                                    SQLliteHelper.zamowienia.NrZamowienia,
                                    SQLliteHelper.zamowienia.EMAIL
                            };
                            String selection  = SQLliteHelper.zamowienia.COLUMN_NAME_TITLE + "=?";
                            String[] selectionArgs = {login};

                            String sortOrder = SQLliteHelper.zamowienia.NrZamowienia + " DESC";
                            Cursor cursor = db1.query(SQLliteHelper.zamowienia.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                            String email = "";
                            if (cursor != null){
                                if (cursor.moveToFirst()){
                                    numer = cursor.getInt(cursor.getColumnIndexOrThrow(SQLliteHelper.zamowienia.NrZamowienia));
                                    email = cursor.getString(cursor.getColumnIndexOrThrow(SQLliteHelper.zamowienia.EMAIL));
                                }
                            }
                            cursor.close();
                            SQLiteDatabase db = sqLliteHelper.getWritableDatabase();
                            ContentValues contentValues = new ContentValues();
                            contentValues.put(SQLliteHelper.zamowienia.NrZamowienia, String.valueOf(numer+1));
                            db.update(SQLliteHelper.zamowienia.TABLE_NAME, contentValues, SQLliteHelper.zamowienia.NrZamowienia + " =?", new String[]{"0"});
                            String pomoc;
                            int wynik = 0;
                            for (int i = 0; i < recyclerView.getAdapter().getItemCount(); i++) {
                                View selectedView = recyclerView.getChildAt(i);
                                TextView textView = selectedView .findViewById(R.id.cena12);
                                pomoc = textView.getText().toString();
                                wynik = wynik + Integer.parseInt(pomoc);
                            }
                            int cena = wynik;
                            numer = numer + 1;
                            ContentValues contentValues1 = new ContentValues();
                            contentValues1.put(SQLliteHelper.produkty.Login, login);
                            contentValues1.put(SQLliteHelper.produkty.NrZamowienia, numer);
                            contentValues1.put(SQLliteHelper.produkty.Cena, cena);
                            contentValues1.put(SQLliteHelper.produkty.EMAIL, email);
                            db.insert(SQLliteHelper.produkty.TABLE_NAME, null, contentValues1);


                            Intent intent = new Intent(getApplicationContext(), kosz.class);
                            PendingIntent pi = PendingIntent.getActivity(getApplicationContext(),0,intent,0);

                            String [] projection2 = {
                                    SQLliteHelper.SQLscheme.NUMBER_PH,
                                    SQLliteHelper.SQLscheme.EMAIL,
                                    SQLliteHelper.SQLscheme.COLUMN_NAME_TITLE,
                                    SQLliteHelper.SQLscheme.ADRES
                            };
                            String selection2 = SQLliteHelper.SQLscheme.COLUMN_NAME_TITLE + " =?";
                            String[] selectionArgs1 = {login};
                            if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED){
                                SmsManager sms = SmsManager.getDefault();
                                Cursor cursor1 = db1.query(SQLliteHelper.SQLscheme.TABLE_NAME, projection2, selection2, selectionArgs1, null, null, null);
                                if (cursor1.moveToFirst()){
                                    Intent intent2 = new Intent(Intent.ACTION_SEND);
                                    intent2.putExtra(Intent.EXTRA_EMAIL, new String[]{cursor1.getString(cursor1.getColumnIndexOrThrow(SQLliteHelper.SQLscheme.EMAIL))});
                                    intent2.putExtra(Intent.EXTRA_SUBJECT, "nowe zamówienie");
                                    intent2.putExtra(Intent.EXTRA_TEXT, "Twoje zamówienie" + numer + "zostało przekazane do realizacji" + cursor1.getString(cursor1.getColumnIndexOrThrow(SQLliteHelper.SQLscheme.ADRES)));
                                    intent2.setType("message/rfc822");
                                    startActivity(intent2);
                                    sms.sendTextMessage(String.valueOf(cursor1.getInt(cursor1.getColumnIndexOrThrow(SQLliteHelper.SQLscheme.NUMBER_PH))), null, "Twoje zamówienie o nr. " + numer + " w cenie " + cena + "zł zostało poddane realizacji, paczka zostanie wysłana na" + cursor1.getString(cursor1.getColumnIndexOrThrow(SQLliteHelper.SQLscheme.ADRES)), pi, null);
                                }
                            } else if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_DENIED){
                                if (ActivityCompat.shouldShowRequestPermissionRationale(kosz.this, Manifest.permission.SEND_SMS)){
                                    Toast.makeText(getApplicationContext(), "Potrzeben zezwolenie na sms", Toast.LENGTH_LONG).show();
                                    ActivityCompat.requestPermissions(kosz.this, new String[]{Manifest.permission.SEND_SMS}, 225);
                                    SmsManager sms = SmsManager.getDefault();
                                    Cursor cursor1 = db1.query(SQLliteHelper.SQLscheme.TABLE_NAME, projection2, selection2, selectionArgs1, null, null, null);
                                    if (cursor1.moveToFirst()){
                                        Intent intent2 = new Intent(Intent.ACTION_SEND);
                                        intent2.putExtra(Intent.EXTRA_EMAIL, new String[]{cursor1.getString(cursor1.getColumnIndexOrThrow(SQLliteHelper.SQLscheme.EMAIL))});
                                        intent2.putExtra(Intent.EXTRA_SUBJECT, "Nowe Zamówienie");
                                        intent2.putExtra(Intent.EXTRA_TEXT, "Twoje zamówienie" + numer + "zostało przekazane do realizacji" + cursor1.getString(cursor1.getColumnIndexOrThrow(SQLliteHelper.SQLscheme.ADRES)));
                                        intent2.setType("message/rfc822");
                                        startActivity(intent2);

                                        sms.sendTextMessage(String.valueOf(cursor1.getInt(cursor1.getColumnIndexOrThrow(SQLliteHelper.SQLscheme.NUMBER_PH))), null, "Twoje zamówienie o nr. " + numer + " w cenie " + cena + "zł zostało poddane realizacji, paczka zostanie wysłana na" + cursor1.getString(cursor1.getColumnIndexOrThrow(SQLliteHelper.SQLscheme.ADRES)), pi, null);
                                    }

                                }
                            }
                            Intent intent1 = new Intent(kosz.this, produkt.class);
                            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent1.putExtra("Login", login);
                            intent1.putExtra("Email", email);
                            intent1.putExtra("Bool", "True");
                            startActivity(intent1);

                        }

                    }
                });


            }
            if (email != null && !email.equalsIgnoreCase("")){
                SQLliteHelper sqLliteHelper = new SQLliteHelper(kosz.this);
                SQLiteDatabase db1 = sqLliteHelper.getReadableDatabase();
                String[] projection = {
                        SQLliteHelper.zamowienia.Zamowienie,
                        SQLliteHelper.zamowienia.CenaZamowienia,
                        SQLliteHelper.zamowienia.Obraz,
                        SQLliteHelper.zamowienia.NrZamowienia

                };
                String selection  = SQLliteHelper.zamowienia.EMAIL + "=? AND " + SQLliteHelper.zamowienia.NrZamowienia + "=?";
                String[] selectionArgs = {email, "0"};

                String sortOrder = SQLliteHelper.zamowienia.COLUMN_NAME_TITLE + " DESC";
                Cursor cursor = db1.query(SQLliteHelper.zamowienia.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                List<krecycler> items = new ArrayList<>();
                while (cursor.moveToNext()){
                    items.add(new krecycler(cursor.getString(cursor.getColumnIndexOrThrow(SQLliteHelper.zamowienia.Zamowienie)), cursor.getString(cursor.getColumnIndexOrThrow(SQLliteHelper.zamowienia.CenaZamowienia)), cursor.getInt(cursor.getColumnIndexOrThrow(SQLliteHelper.zamowienia.Obraz))));
                }
                cursor.close();
                RecyclerView recyclerView;
                recyclerView = findViewById(R.id.recycler_view12);
                recyclerView.setLayoutManager(new LinearLayoutManager(this));
                recyclerView.setAdapter(new kadapter(items, getApplicationContext()));
                recyclerView.post(new Runnable() {
                    @Override
                    public void run() {
                        String pomoc;
                        int wynik = 0;
                        for (int i = 0; i < recyclerView.getAdapter().getItemCount(); i++) {
                            View selectedView = recyclerView.getChildAt(i);
                            TextView textView = selectedView .findViewById(R.id.cena12);
                            pomoc = textView.getText().toString();
                            wynik = wynik + Integer.parseInt(pomoc);
                        }
                        if (recyclerView.getAdapter().getItemCount() == 0){
                            TextView textView1 = findViewById(R.id.Koszt);
                            textView1.setText("Brak produktów w koszyku");
                        } else {
                            TextView textView1 = findViewById(R.id.Koszt);
                            textView1.setText("Koszt to: " + wynik);
                        }
                    }
                });
                Button button = findViewById(R.id.kup);
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (items.size() == 0){
                            AlertDialog.Builder alert = new AlertDialog.Builder(kosz.this);
                            alert.setMessage("Brak produktów w koszyku, dodaj produkt do koszyka by złożyc zamówienie");
                            alert.create().show();

                        } else {
                            int numer = 0;
                            Bundle extras = getIntent().getExtras();
                            String email;
                            email = extras.getString("Email");
                            SQLliteHelper sqLliteHelper = new SQLliteHelper(kosz.this);
                            SQLiteDatabase db1 = sqLliteHelper.getReadableDatabase();

                            String [] projection = {
                                    SQLliteHelper.zamowienia.COLUMN_NAME_TITLE,
                                    SQLliteHelper.zamowienia.NrZamowienia,
                                    SQLliteHelper.zamowienia.EMAIL
                            };
                            String selection  = SQLliteHelper.zamowienia.EMAIL + "=?";
                            String[] selectionArgs = {email};

                            String sortOrder = SQLliteHelper.zamowienia.NrZamowienia + " DESC";
                            Cursor cursor = db1.query(SQLliteHelper.zamowienia.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                            String Login = "";
                            if (cursor != null){
                                if (cursor.moveToFirst()){
                                    numer = cursor.getInt(cursor.getColumnIndexOrThrow(SQLliteHelper.zamowienia.NrZamowienia));
                                    Login = cursor.getString(cursor.getColumnIndexOrThrow(SQLliteHelper.zamowienia.COLUMN_NAME_TITLE));
                                }
                            }
                            cursor.close();
                            SQLiteDatabase db = sqLliteHelper.getWritableDatabase();
                            ContentValues contentValues = new ContentValues();
                            contentValues.put(SQLliteHelper.zamowienia.NrZamowienia, String.valueOf(numer+1));
                            db.update(SQLliteHelper.zamowienia.TABLE_NAME, contentValues, SQLliteHelper.zamowienia.NrZamowienia + " =?", new String[]{"0"});
                            String pomoc;
                            int wynik = 0;
                            for (int i = 0; i < recyclerView.getAdapter().getItemCount(); i++) {
                                View selectedView = recyclerView.getChildAt(i);
                                TextView textView = selectedView .findViewById(R.id.cena12);
                                pomoc = textView.getText().toString();
                                wynik = wynik + Integer.parseInt(pomoc);
                            }
                            int cena = wynik;
                            numer = numer + 1;
                            ContentValues contentValues1 = new ContentValues();
                            contentValues1.put(SQLliteHelper.produkty.Login, Login);
                            contentValues1.put(SQLliteHelper.produkty.NrZamowienia, numer);
                            contentValues1.put(SQLliteHelper.produkty.Cena, cena);
                            contentValues1.put(SQLliteHelper.produkty.EMAIL, email);
                            db.insert(SQLliteHelper.produkty.TABLE_NAME, null, contentValues1);

                            Intent intent = new Intent(getApplicationContext(), kosz.class);
                            PendingIntent pi = PendingIntent.getActivity(getApplicationContext(),0,intent,0);

                            String [] projection2 = {
                                    SQLliteHelper.SQLscheme.NUMBER_PH,
                                    SQLliteHelper.SQLscheme.EMAIL,
                                    SQLliteHelper.SQLscheme.COLUMN_NAME_TITLE,
                                    SQLliteHelper.SQLscheme.ADRES
                            };
                            String selection2 = SQLliteHelper.SQLscheme.COLUMN_NAME_TITLE + " =?";
                            String[] selectionArgs1 = {Login};
                            if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED){
                                Log.v("TAG", "DZIALA");
                                SmsManager sms = SmsManager.getDefault();
                                Cursor cursor1 = db1.query(SQLliteHelper.SQLscheme.TABLE_NAME, projection2, selection2, selectionArgs1, null, null, null);
                                if (cursor1.moveToFirst()){
                                    Intent intent2 = new Intent(Intent.ACTION_SEND);
                                    intent2.putExtra(Intent.EXTRA_EMAIL, new String[]{cursor1.getString(cursor1.getColumnIndexOrThrow(SQLliteHelper.SQLscheme.EMAIL))});
                                    intent2.putExtra(Intent.EXTRA_SUBJECT, "Nowe Zamówienie");
                                    intent2.putExtra(Intent.EXTRA_TEXT, "Twoje zamówienie" + numer + "zostało przekazane do realizacji" + cursor1.getString(cursor1.getColumnIndexOrThrow(SQLliteHelper.SQLscheme.ADRES)));
                                    intent2.setType("message/rfc822");
                                    startActivity(intent2);
                                    sms.sendTextMessage(String.valueOf(cursor1.getInt(cursor1.getColumnIndexOrThrow(SQLliteHelper.SQLscheme.NUMBER_PH))), null, "Twoje zamówienie" + numer + "zostało przekazane do realizacji" + cursor1.getString(cursor1.getColumnIndexOrThrow(SQLliteHelper.SQLscheme.ADRES)), pi, null);
                                }
                            } else if (ContextCompat.checkSelfPermission(getApplicationContext(), Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_DENIED){
                                if (ActivityCompat.shouldShowRequestPermissionRationale(kosz.this, Manifest.permission.SEND_SMS)){
                                    Toast.makeText(getApplicationContext(), "potrzebne zezwolenie na sms", Toast.LENGTH_LONG).show();
                                    ActivityCompat.requestPermissions(kosz.this, new String[]{Manifest.permission.SEND_SMS}, 225);
                                    SmsManager sms = SmsManager.getDefault();
                                    Cursor cursor1 = db1.query(SQLliteHelper.SQLscheme.TABLE_NAME, projection2, selection2, selectionArgs1, null, null, null);
                                    if (cursor1.moveToFirst()){
                                        Intent intent2 = new Intent(Intent.ACTION_SEND);
                                        intent2.putExtra(Intent.EXTRA_EMAIL, new String[]{cursor1.getString(cursor1.getColumnIndexOrThrow(SQLliteHelper.SQLscheme.EMAIL))});
                                        intent2.putExtra(Intent.EXTRA_SUBJECT, "Nowe Zamówienie");
                                        intent2.putExtra(Intent.EXTRA_TEXT, "Twoje zamówienie" + numer + "zostało przekazane do realizacji" + cursor1.getString(cursor1.getColumnIndexOrThrow(SQLliteHelper.SQLscheme.ADRES)));
                                        intent2.setType("message/rfc822");
                                        startActivity(intent2);
                                        sms.sendTextMessage(String.valueOf(cursor1.getInt(cursor1.getColumnIndexOrThrow(SQLliteHelper.SQLscheme.NUMBER_PH))), null, "Twoje zamówienie" + numer + "zostało przekazane do realizacji" + cursor1.getString(cursor1.getColumnIndexOrThrow(SQLliteHelper.SQLscheme.ADRES)), pi, null);                                    }

                                }
                            }
                            Intent intent1 = new Intent(kosz.this, produkt.class);
                            intent1.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent1.putExtra("Login", login);
                            intent1.putExtra("Email", email);
                            intent1.putExtra("Bool", "True");
                            startActivity(intent1);
                        }

                    }
                });
            }

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

