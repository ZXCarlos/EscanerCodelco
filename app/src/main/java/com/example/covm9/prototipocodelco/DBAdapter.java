package com.example.covm9.prototipocodelco;

/**
 ########################################################################
 # Copyright (C) 2016 Estefania Flores Carlos Varas <efs0013@gmail.com> #
 # <covm091@gmail.com> 	                                                #
 # 									                                    #
 # This program is free software: you can redistribute it and/or modify #
 # it under the terms of the GNU General Public License as published by #
 # the Free Software Foundation, either version 3 of the License, or 	#
 # (at your option) any later version. 					                #
 # 									                                    #
 # This program is distributed in the hope that it will be useful, 	    #
 # but WITHOUT ANY WARRANTY; without even the implied warranty of     	#
 # MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the     	#
 # GNU General Public License for more details.                  		#
 # 				                                    					#
 # You should have received a copy of the GNU General Public License 	#
 # along with this program. If not, see <http://www.gnu.org/licenses/>. #
 ########################################################################
 **/

import android.annotation.TargetApi;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.os.Build;

import org.apache.http.util.ByteArrayBuffer;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Esta clase proporciona el ambiente que se alojara la base de datos y entre otros.
 * @author: Estefania Flores Sandoval
 * @author: Carlos Varas Miranda
 * @version: 1.0.0 22/02/2016
 */
public class DBAdapter {
    //campos de la clase
    private SQLiteDatabase database = null;
    private String dbPath;
    private String dbName;
    private Context context;

    /**
     * Metodo constructor den la clase DBAdapter
     * @param context Parametro que define le contexto de la clase.
     * @param dbName Parametro que representa el nombre de la base de datos
     */
    public DBAdapter(Context context, String dbName) {
        this.context = context;
        this.dbName = dbName;
        dbPath = "/data/data/" + context.getPackageName() + "/databases/";
    }//Cierre del constructor DBAdapter

    /**
     * Metodo que copia la base de datos
     */
    public void copyDB() {
        try {
            try {
                database = SQLiteDatabase.openDatabase(dbPath + dbName, null,
                        SQLiteDatabase.OPEN_READONLY);
            } catch (SQLiteException e) {
                // Checking if required dirs exist as sometimes they do not and
                // they need to be created.
                File dbDir = new File(dbPath);
                if (!dbDir.exists()) {
                    dbDir.mkdirs();
                }
                // Copying db from assets to db directory.
                InputStream in = context.getAssets().open(dbName);
                OutputStream out = new FileOutputStream(dbPath + dbName);
                bufCopy(in, out);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        if (database != null) {
            database.close();
        }
    }//Cierre del metodo copyDB

    /**
     * Metodo que abre la base de datos.
     * @return database de tipo SQLiteDatabase.
     */
    public SQLiteDatabase openDB() {
        database = SQLiteDatabase.openDatabase(dbPath + dbName, null,
                SQLiteDatabase.OPEN_READONLY);
        return database;
    }//Cierre del metodo openDB

    /**
     * Metodo que ejecuta las querys
     * @param query Parametro que representa a la consulta qe se desea realizar a la base de datos.
     * @return cursor tipo Cursor
     */
    public Cursor runQuery(String query) {
        Cursor cursor = database.rawQuery(query, null);
        return cursor;
    }//Cierre del metodo runQuery

    /**
     * Metodo que retorna un dato dentro de alguna tabla de la base de datos.
     * @param column Parametro que contiene el nombre de la culumna a analisar.
     * @param table Parametro que representa el nombre de la tabla.
     * @param match Parametro que define al dato a buscar
     * @return text tipo string.
     */
    public String getStringEntry(String column, String table, String match) {
        String text = null;
        Cursor c = database.rawQuery("SELECT " + column + " FROM " + table
                + " WHERE " + column + "=" + "'" + match + "'", null);
        if (c != null) {
            if (c.moveToFirst()) {
                text = c.getString(c.getColumnIndex(column));
            }
        }
        return text;
    }//Cierre del metodo getStringEntry

    /**
     * Metodo que retorna la posición de un dato dentro de alguna tabla de la base de datos.
     * @param column Parametro que contiene el nombre de la culumna a analisar.
     * @param table Parametro que representa el nombre de la tabla.
     * @param match Parametro que define al dato a buscar
     * @return number tipo int.
     */
    public int getIntEntry(String column, String table, String match) {
        int number = 0;
        Cursor c = database.rawQuery("SELECT " + column + " FROM " + table
                + " WHERE " + column + "=" + match, null);
        if (c != null) {
            if (c.moveToFirst()) {
                number = c.getInt(c.getColumnIndex(column));
            }
        }
        return number;
    }//Cierre del metodo getIntEntry

    /**
     * Metodo que retorna la posición de un dato dentro de alguna tabla de la base de datos.
     * @param column Parametro que contiene el nombre de la culumna a analisar.
     * @param table Parametro que representa el nombre de la tabla.
     * @param match Parametro que define al dato a buscar
     * @return number tipo long.
     */
    public long getLongEntry(String column, String table, String match) {
        long number = 0;
        Cursor c = database.rawQuery("SELECT " + column + " FROM " + table
                + " WHERE " + column + "=" + match, null);
        if (c != null) {
            if (c.moveToFirst()) {
                number = c.getInt(c.getColumnIndex(column));
            }
        }
        return number;
    }//Cierre del metodo getLongEntry

    /**
     * Metodo que retorna la posición de un dato dentro de alguna tabla de la base de datos.
     * @param column Parametro que contiene el nombre de la culumna a analisar.
     * @param table Parametro que representa el nombre de la tabla.
     * @param match Parametro que define al dato a buscar
     * @return number tipo double.
     */
    public double getDoubleEntry(String column, String table, String match) {
        double number = 0;
        Cursor c = database.rawQuery("SELECT " + column + " FROM " + table
                + " WHERE " + column + "=" + match, null);
        if (c != null) {
            if (c.moveToFirst()) {
                number = c.getInt(c.getColumnIndex(column));
            }
        }
        return number;
    }//Cierre del metodo getDoubleEntry

    /**
     * Metodo que elimina una tabla de la base de datos.
     * @param tableName Parametro que representa el nombre de la tabla.
     */
    public void dropTable(String tableName) {
        database.rawQuery("DROP TABLE " + tableName, null);
    }//Cierre del metodo dropTable

    /**
     * Metodo que entrega la cantidad de registros de una tabla
     * @param tableName Parametro que representa el nombre de la tabla.
     * @return count de tipo long.
     */
    public long tableCount(String tableName) {
        long count = DatabaseUtils.queryNumEntries(database, tableName);
        return count;
    }//Cierre del metodo tableCount

    /**
     * Metodo que elimina a la base de datos
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    public void deleteDB() { // The .deleteDatabase method is supported on API 16 and higher.
        File oldDB = new File(dbPath + dbName);
        SQLiteDatabase.releaseMemory();
        SQLiteDatabase.deleteDatabase(oldDB);
    }//Cierre del metodo deleteDB

    /**
     * Metodo que obtiene a la base de datos
     * @return database de tipo SQLiteDatabase
     */
    public SQLiteDatabase getDB() {
        return database;
    }//Cierre del metodo getDB

    /**
     * Metodo que cierra la base de datos
     */
    public void closeDB() {
        database.close();
    }//Cierre del metodo closeDB

    /**
     * Metodo que descarga una base de datos dado un link.
     * @param context Parametro que diefine el contexto de la descarga.
     * @param dbName Parametro que reprecenta el nombre de la base de datos a descargar.
     * @param urlLink Parametro que contiene la ur de la base de datos a descargar.
     */
    public void downloadDB(Context context, String dbName, String urlLink) {
        try {
            URL url = new URL(urlLink);
            URLConnection ucon = url.openConnection();
            InputStream is = ucon.getInputStream();
            BufferedInputStream bis = new BufferedInputStream(is);
            ByteArrayBuffer baf = new ByteArrayBuffer(50);
            int current = 0;
            while ((current = bis.read()) != -1) {
                baf.append((byte) current);
            }
            FileOutputStream fos = context.openFileOutput(dbName,
                    Context.MODE_PRIVATE);
            fos.write(baf.toByteArray());
            fos.close();
            File dbFile = new File(context.getFilesDir() + "/" + dbName);
            InputStream in = new FileInputStream(dbFile);
            OutputStream out = new FileOutputStream(dbPath + dbName);
            bufCopy(in, out);
            dbFile.delete();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }//Cierre del metodo downloadDB

    /**
     * Metodo que realiza una copia del buffer
     * @param in Parametro de entrada
     * @param out Parametro de salida
     */
    private void bufCopy(InputStream in, OutputStream out) {
        try {
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
            in.close();
            out.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }//Cierre del metodo bufCopy
}//Cierre de la clase