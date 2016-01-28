package com.example.covm9.prototipocodelco;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by estefy on 28-01-2016.
 */
public class BDHelper extends SQLiteOpenHelper {
    public BDHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //se crea la tabla Fichas
        db.execSQL("create table fichas(id text primary key," +
                "nombre text," +
                "codigoQR int)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void cargarFichas(String id, String nombre, int codigoQR,SQLiteDatabase bd){

        //se crea un registro para insertar datos
        ContentValues registro = new ContentValues();
        //se agregan los datos del lugar al registro
        registro.put("id", id);
        registro.put("nombre", nombre);
        registro.put("codigoQR", codigoQR);

        //se agrega el lugar a la bd
        bd.insert("ubicacion", null, registro);


    }
}
