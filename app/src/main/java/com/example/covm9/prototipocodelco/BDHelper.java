package com.example.covm9.prototipocodelco;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Estefania Flores y Carlos Varas on 28-01-2016.
 */
public class BDHelper extends SQLiteOpenHelper {
    public BDHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        //se crea la tabla Divisiones
        db.execSQL("create table divisio(codigoDivision text primary key," +
                "nombre text)");

        //se crea la tabla Area
        db.execSQL("create table area(codigoArea text primary key," +
                "nombreArea text," +
                "descripcionArea text,"+
                "FOREIGN KEY (codigoDivision) REFERENCES division (codigoDivision)");

        //se crea la tabla Fichas
        db.execSQL("create table fichas(idFicha text primary key," +
                "nombre text," +
                "DescripcionFicha text," +
                "link text,"+
                "FOREIGN KEY (codigoQr) REFERENCES qr (codigoQr))");

        //se crea la tabla QR
        db.execSQL("create table qr(codigoQr text primary key," +
                "FOREIGN KEY (codigoArea) REFERENCES area (codigoArea))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void actualizarQr(SQLiteDatabase db){
        db.execSQL("DROP TABLE qr");
        //se crea la tabla QR
        db.execSQL("create table qr(codigoQr text primary key," +
                "FOREIGN KEY (codigoArea) REFERENCES area (codigoArea))");
    }

    public void actualizarFicha(SQLiteDatabase db){
        db.execSQL("DROP TABLE ficha");
        //se crea la tabla Fichas
        db.execSQL("create table fichas(idFicha text primary key," +
                "nombre text," +
                "DescripcionFicha text," +
                "link text,"+
                "FOREIGN KEY (codigoQr) REFERENCES qr (codigoQr))");
    }
    public String buscarFicha (SQLiteDatabase db, String codigoQr, int posicion ){

        Cursor fila = db.rawQuery("select link from ficha where codigoQr= '" + codigoQr + "'", null);
        String link = fila.getString(posicion);
        return  link;
    }

    public String buscarDivision (SQLiteDatabase db, String nombreDivision){

        Cursor fila = db.rawQuery("select codigoDivision from division where nombreDivision= '" + nombreDivision + "'", null);
        String codigo = fila.getString(0);
        return  codigo;
    }

    public Cursor buscarArea (SQLiteDatabase db, String codigoDivision){

        Cursor fila = db.rawQuery("select * from area where codigoDivision= '" + codigoDivision + "'", null);
        return  fila;
    }
}
