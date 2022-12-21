package com.example.ProjektSQL.BazaDanych;


import android.content.Context;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import android.provider.BaseColumns;


import androidx.annotation.Nullable;



public final class SQLliteHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Database.db";

    public SQLliteHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }



    public static class SQLscheme implements BaseColumns{
        public static final String TABLE_NAME = "Uzytkownik";
        public static final String COLUMN_NAME_TITLE = "Login";
        public static final String PASSWORD = "Haslo";
        public static final String EMAIL = "Email";
        public static final String NUMBER_PH = "NrTelefonu";
        public static final String ADRES = "Adres";


    }
    public static class zamowienia implements BaseColumns{
        public static final String TABLE_NAME = "Zamowienia";
        public static final String COLUMN_NAME_TITLE = "Login";
        public static final String EMAIL = "Email";
        public static final String CenaZamowienia = "CenaZamowienia";
        public static final String Zamowienie = "Zamowienie";
        public static final String Obraz = "Obraz";
        public static final String NrZamowienia = "NrZamowienia";
    }
    public static class produkty implements BaseColumns{
        public static final String TABLE_NAME = "Produkty";
        public static final String NrZamowienia = "Nrzamowienia";
        public static final String EMAIL = "Email";
        public static final String Login = "Login";
        public static final String Cena = "Cena";
    }

    private static final String SQL_CREATE_ENTRIES = "CREATE TABLE " + SQLscheme.TABLE_NAME + " ( " + SQLscheme._ID + " INTEGER PRIMARY KEY, " + SQLscheme.COLUMN_NAME_TITLE + " TEXT, " + SQLscheme.PASSWORD + " TEXT, " +
            SQLscheme.EMAIL+ " TEXT, " + SQLscheme.NUMBER_PH + " NUMBER, " + SQLscheme.ADRES + " TEXT)";

    private static final String SQL_CREATE_ZAMOWIENIA = "CREATE TABLE " + zamowienia.TABLE_NAME + " ( " + zamowienia._ID + " INTEGER PRIMARY KEY, " + zamowienia.COLUMN_NAME_TITLE + " TEXT," + zamowienia.EMAIL + " TEXT,"
             + zamowienia.CenaZamowienia + " NUMBER," + zamowienia.Zamowienie + " TEXT," + zamowienia.Obraz + " NUMBER, " + zamowienia.NrZamowienia + " NUMBER)";
    private static final String  SQL_CREATE_PRODUKTY ="CREATE TABLE " + produkty.TABLE_NAME + " ( " + produkty._ID + " INTEGER PRIMARY KEY, " + produkty.NrZamowienia + " NUMBER, " + produkty.EMAIL + " TEXT," + produkty.Login + " TEXT, " + produkty.Cena + " NUMBER)";

    private static final String SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + SQLscheme.TABLE_NAME;
    private static final String SQL_DELETE_ENTRIES1 = "DROP TABLE IF EXISTS " + zamowienia.TABLE_NAME;
    private static final String SQL_DELETE_ENTRIES2 = "DROP TABLE IF EXISTS " + produkty.TABLE_NAME;

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(SQL_CREATE_ENTRIES);
        sqLiteDatabase.execSQL(SQL_CREATE_ZAMOWIENIA);
        sqLiteDatabase.execSQL(SQL_CREATE_PRODUKTY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL(SQL_DELETE_ENTRIES);
        sqLiteDatabase.execSQL(SQL_DELETE_ENTRIES1);
        sqLiteDatabase.execSQL(SQL_DELETE_ENTRIES2);
        onCreate(sqLiteDatabase);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }

}
