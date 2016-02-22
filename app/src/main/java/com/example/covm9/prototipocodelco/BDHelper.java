package com.example.covm9.prototipocodelco;

import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by Estefania Flores y Carlos Varas on 28-01-2016.
 */
public class BDHelper extends SQLiteOpenHelper {
    private static  String DB_PATH = "data/data/com.example.covm9.prototipocodelco/databases/";
    private static  String DB_NAME = "quotes.db";
    private SQLiteDatabase myDataBase;
    private Context myContext;

    /**
     * Constructor
     * Toma referencia hacia el contexto de la aplicación que lo invoca para poder acceder a los 'assets' y 'resources' de la aplicación.
     * Crea un objeto DBOpenHelper que nos permitirá controlar la apertura de la base de datos.
     * @param context
     */
    public BDHelper(Context context) {

        super(context, DB_NAME, null, 1);
        this.myContext = context;

    }

    /**
     * Crea una base de datos vacía en el sistema y la reescribe con nuestro fichero de base de datos.
     * */
    public void createDataBase() throws IOException {

        boolean dbExist = checkDataBase();

        if(dbExist){
//la base de datos existe y no hacemos nada.
        }else{
//Llamando a este método se crea la base de datos vacía en la ruta por defecto del sistema
//de nuestra aplicación por lo que podremos sobreescribirla con nuestra base de datos.
            this.getReadableDatabase();

            try {

                copyDataBase();

            } catch (IOException e) {
                throw new Error("Error copiando Base de Datos");
            }
        }

    }

    /**
     * Comprueba si la base de datos existe para evitar copiar siempre el fichero cada vez que se abra la aplicación.
     * @return true si existe, false si no existe
     */
    private boolean checkDataBase(){

        SQLiteDatabase checkDB = null;

        try{

            String myPath = DB_PATH + DB_NAME;
            checkDB = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

        }catch(SQLiteException e){

//si llegamos aqui es porque la base de datos no existe todavía.

        }
        if(checkDB != null){

            checkDB.close();

        }
        return checkDB != null ? true : false;
    }

    /**
     * Copia nuestra base de datos desde la carpeta assets a la recién creada
     * base de datos en la carpeta de sistema, desde dónde podremos acceder a ella.
     * Esto se hace con bytestream.
     * */
    private void copyDataBase() throws IOException{

//Abrimos el fichero de base de datos como entrada
        InputStream myInput = myContext.getAssets().open(DB_NAME);

//Ruta a la base de datos vacía recién creada
        String outFileName = DB_PATH + DB_NAME;

//Abrimos la base de datos vacía como salida
        OutputStream myOutput = new FileOutputStream(outFileName);

//Transferimos los bytes desde el fichero de entrada al de salida
        byte[] buffer = new byte[1024];
        int length;
        while ((length = myInput.read(buffer))>0){
            myOutput.write(buffer, 0, length);
        }

//Liberamos los streams
        myOutput.flush();
        myOutput.close();
        myInput.close();

    }

    public void open() throws SQLException {

//Abre la base de datos
        try {
            createDataBase();
        } catch (IOException e) {
            throw new Error("Ha sido imposible crear la Base de Datos");
        }

        String myPath = DB_PATH + DB_NAME;
        myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);

    }

    @Override
    public synchronized void close() {
        if(myDataBase != null)
            myDataBase.close();
        super.close();
    }


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
                "linkR text,"+
                "codigoMaquina text,"+
                "linkFT text,"+
                "linkFS text,"+
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
                "linkR text,"+
                "codigoMaquina text,"+
                "linkFT text,"+
                "linkFS text,"+
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

    public void cargarArchivo(String CodigoArchivo,String Descripcion,String LinkR, String CodigoMaquina, String LinkFT, String LinkFS,  SQLiteDatabase bd) {


        //se crea un registro para insertar datos
        ContentValues registro = new ContentValues();
        //se agregan los datos del medico al registro
        registro.put("codigoArchivo", CodigoArchivo);
        registro.put("descripcion", Descripcion);
        registro.put("linkR", LinkR);
        registro.put("codigoMaquina", CodigoMaquina);
        registro.put("linkFT", LinkFT);
        registro.put("linkFS", LinkFS);

        //se agrega el archivo a la bd
        Log.d("CodigoArchivo", "" + CodigoArchivo);
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
