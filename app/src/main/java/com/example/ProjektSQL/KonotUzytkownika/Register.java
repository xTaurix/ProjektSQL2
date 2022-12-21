package com.example.ProjektSQL.KonotUzytkownika;

import android.content.ContentValues;
import android.content.Intent;
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


public class Register extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        Button button = findViewById(R.id.zarejestrujprzycisk);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText editText = findViewById(R.id.zaloguj);
                EditText editText1 = findViewById(R.id.email);
                EditText editText2 = findViewById(R.id.haslo);
                EditText editText3 = findViewById(R.id.adres);
                EditText editText4 = findViewById(R.id.numertelefonu);
                if (editText.getText().toString().matches("")){
                    showAlertDialog(view, "Login");
                }else {
                    if (editText1.getText().toString().matches("")){
                        showAlertDialog(view, "Email");
                    }else {
                    if (editText2.getText().toString().matches("")){
                        showAlertDialog(view, "Haslo");
                    }else {
                        if (editText3.getText().toString().matches("")){
                            showAlertDialog(view, "Adres");
                        }else {
                            if (editText4.getText().toString().matches("")){
                                showAlertDialog(view, "Nr.Telefonu");
                            }else {


                                SQLliteHelper sqLliteHelper =  new SQLliteHelper(Register.this);
                                SQLiteDatabase db = sqLliteHelper.getWritableDatabase();


                                ContentValues values = new ContentValues();
                                values.put(SQLliteHelper.SQLscheme.COLUMN_NAME_TITLE, editText.getText().toString());
                                values.put(SQLliteHelper.SQLscheme.PASSWORD, editText2.getText().toString());
                                values.put(SQLliteHelper.SQLscheme.EMAIL, editText1.getText().toString());
                                values.put(SQLliteHelper.SQLscheme.NUMBER_PH, editText4.getText().toString());
                                values.put(SQLliteHelper.SQLscheme.ADRES, editText3.getText().toString());

                                db.insert(SQLliteHelper.SQLscheme.TABLE_NAME, null, values);
                                Intent intent = new Intent(Register.this, Login.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);


                            }
                        }
                    }
                    }
                }

            }
        });


    }
    public void showAlertDialog(View v, String pole){
        AlertDialog.Builder alert = new AlertDialog.Builder(Register.this);
        alert.setTitle("podano niepoprawne dane w polu: " + pole);
        alert.setMessage("proszę wprowadź poprawne dane");
        alert.create().show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
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
