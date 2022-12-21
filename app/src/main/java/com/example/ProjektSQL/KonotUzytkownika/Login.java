package com.example.ProjektSQL.KonotUzytkownika;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ProjektSQL.BazaDanych.SQLliteHelper;
import com.example.ProjektSQL.MainActivity;
import com.example.ProjektSQL.R;

public class Login extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);



        EditText editText = findViewById(R.id.zaloguj);
        EditText editText1 = findViewById(R.id.haslo);
        Button button = findViewById(R.id.zalogujprzycisk);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SQLliteHelper sqLliteHelper = new SQLliteHelper(Login.this);
                SQLiteDatabase db1 = sqLliteHelper.getReadableDatabase();
                String checkpass = editText1.getText().toString();
                String checklogin = editText.getText().toString();


                String[] projection = {
                        SQLliteHelper.SQLscheme.COLUMN_NAME_TITLE,
                        SQLliteHelper.SQLscheme.EMAIL,
                        SQLliteHelper.SQLscheme.PASSWORD

                };
                String selection  = SQLliteHelper.SQLscheme.COLUMN_NAME_TITLE + "=?";
                String selection1 = SQLliteHelper.SQLscheme.EMAIL + "=?";
                String[] selectionArgs = {checklogin};
                String sortOrder = SQLliteHelper.SQLscheme.COLUMN_NAME_TITLE + " DESC";
                String sortOrder1 = SQLliteHelper.SQLscheme.EMAIL + " DESC";
                Cursor cursor = db1.query(SQLliteHelper.SQLscheme.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                Cursor cursor1 = db1.query(SQLliteHelper.SQLscheme.TABLE_NAME, projection, selection1, selectionArgs, null, null, sortOrder1);

                if (cursor.getCount() == 0){

                    if (cursor1.getCount() == 0){
                        showAlertDialog(view, "Login");
                    } else {
                        while (cursor1.moveToNext()){
                            String haslo = cursor1.getString(cursor1.getColumnIndexOrThrow(SQLliteHelper.SQLscheme.PASSWORD));
                            if (checkpass.equals(haslo)){
                                Intent intent = new Intent(Login.this, MainActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.putExtra("Bool", "True");
                                intent.putExtra("Email", checklogin);
                                intent.putExtra("Pass", checkpass);
                                startActivity(intent);
                            }

                        }
                    }
                } else{
                    while (cursor.moveToNext()){
                        String haslo = cursor.getString(cursor.getColumnIndexOrThrow(SQLliteHelper.SQLscheme.PASSWORD));
                        if (checkpass.equals(haslo)){
                            Intent intent = new Intent(Login.this, MainActivity.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.putExtra("Bool", "True");
                            intent.putExtra("Login", checklogin);
                            intent.putExtra("Pass", checkpass);
                            startActivity(intent);
                        }

                    }
                }
                cursor.close();

            }
        });

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);


    }

    public void showAlertDialog(View v, String pole){
        AlertDialog.Builder alert = new AlertDialog.Builder(Login.this);
        alert.setTitle("podano niepoprawne dane w polu: " + pole);
        alert.setMessage("proszę wprowadź poprawne dane");
        alert.create().show();
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int i = item.getItemId();
        Intent intent12 = new Intent(this, MainActivity.class);

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

        }
        return super.onOptionsItemSelected(item);
    }
}
