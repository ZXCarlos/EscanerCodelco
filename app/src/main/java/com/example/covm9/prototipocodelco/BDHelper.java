package com.example.covm9.prototipocodelco;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Estefania Flores y Carlos Varas on 28-01-2016.
 */
public class BDHelper extends SQLiteOpenHelper {
    public BDHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {


        //se crea la tabla Maquina
        db.execSQL("create table Maquina(codigoMaquina text primary key," +
                "nombreMaquina text)");

        //se crea la tabla archivo
        db.execSQL("create table Archivo(codigoArchivo text primary key," +
                "descripcion text," +
                "link text,"+
                "codigoMaquina text,"+
                "FOREIGN KEY (codigoMaquina) REFERENCES Maquina (codigoMaquina))");


        //se crea la tabla Sap
        db.execSQL("create table Sap (codigoSap text primary key," +
                "nombreSap text," +
                "descripcionSap text," +
                "cantidadDisponible int," +
                "codigoMaquina text," +
                "FOREIGN KEY (codigoMaquina) REFERENCES Maquina (codigoMaquina))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void actualizarArchivo(SQLiteDatabase db){
        db.execSQL("DROP TABLE Archivo");

        //se crea la tabla archivo
        db.execSQL("create table Archivo(codigoArchivo text primary key," +
                "descripcion text," +
                "link text,"+
                "codigoMaquina text,"+
                "FOREIGN KEY (codigoMaquina) REFERENCES Maquina (codigoMaquina))");
    }

    public void actualizarMaquina(SQLiteDatabase db){
        db.execSQL("DROP TABLE Maquina");

        //se crea la tabla Maquina
        db.execSQL("create table Maquina(codigoMaquina text primary key," +
                "nombreMaquina text)");
    }


    public void actualizarSap(SQLiteDatabase db){
        db.execSQL("DROP TABLE Sap");

        //se crea la tabla Sap
        db.execSQL("create table Sap (codigoSap text primary key," +
                "nombreSap text," +
                "descripcionSap text," +
                "cantidadDisponible int," +
                "codigoMaquina text,"+
                "FOREIGN KEY (codigoMaquina) REFERENCES Maquina (codigoMaquina))");

    }


    public void cargarMaquina(String codigoMaquina,String nombreMaquina, SQLiteDatabase bd) {


        //se crea un registro para insertar datos
        ContentValues registro = new ContentValues();
        //se agregan los datos del medico al registro
        registro.put("codigoMaquina", codigoMaquina);
        registro.put("nombreMaquina", nombreMaquina);
        //se agrega la maquina a la bd
        Log.d("codMaquina", "" + codigoMaquina);
        bd.insert("Maquina", null, registro);
    }

    public void cargarArchivo(String CodigoArchivo,String Descriocion,String Link,String CodigoMaquina,  SQLiteDatabase bd) {


        //se crea un registro para insertar datos
        ContentValues registro = new ContentValues();
        //se agregan los datos del medico al registro
        registro.put("codigoArchivo", CodigoArchivo);
        registro.put("descripcion", Descriocion);
        registro.put("link", Link);
        registro.put("codigoMaquina", CodigoMaquina);

        //se agrega el archivo a la bd
        Log.d("CodArchivo", "" + CodigoArchivo);
        bd.insert("Archivo", null, registro);


    }

    public void cargarSap(String CodigoSap,String NombreSap, String DescripcionSap, int CantidadDisponible, String CodigoMaquina, SQLiteDatabase bd) {


        //se crea un registro para insertar datos
        ContentValues registro = new ContentValues();
        //se agregan los datos del medico al registro
        registro.put("codigoSap", CodigoSap);
        registro.put("nombreSap", NombreSap);
        registro.put("descripcionSap", DescripcionSap);
        registro.put("cantidadDisponible", CantidadDisponible);
        registro.put("codigoMaquina", CodigoMaquina);
        //se agrega el doctor a la bd
        Log.d("CodSap", "" + CodigoMaquina);
        bd.insert("Sap", null, registro);


    }
}
