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

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Esta clase que define las acciones que realiza la pantalla .
 * @author: Estefania Flores Sandoval
 * @author: Carlos Varas Miranda
 * @version: 1.0.0 22/02/2016
 */
public class PruebaScanner extends AppCompatActivity {
    //Campos de la clase
    //Tag para el control de trazas de LOG
    private static String APP_TAG = "ECTDownloadData";
    //Constante String con la URL de la imagen a descargar
    private static String IMG_URL = "http://expocodetech.com/wp-content/uploads/2014/09/cloud-storage-android.jpg";

    public ImageView imageView;
    String link;
    String qr;
    String codigoSap;
    String nombreSap;
    int cantidadDisponible;

    //First We Declare Titles And Icons For Our Navigation Drawer List View
    //This Icons And Titles Are holded in an Array as you can see

    String TITLES[] = new String [999];
    int ICONS[] = new int [999];

    //Similarly we Create a String Resource for the name and email in the header view
    //And we also create a int resource for profile picture in the header view

    String NAME = "Codigo Sap";
    String EMAIL = "Divicion Chuquicamata";
    int PROFILE = R.drawable.logoc;

    RecyclerView mRecyclerView;                           // Declaring RecyclerView
    RecyclerView.Adapter mAdapter;                        // Declaring Adapter For Recycler View
    RecyclerView.LayoutManager mLayoutManager;            // Declaring Layout Manager as a linear layout manager
    DrawerLayout Drawer;                                  // Declaring DrawerLayout

    ActionBarDrawerToggle mDrawerToggle;                  // Declaring Action Bar Drawer Toggle

    /**
     *Este metodo genera el ambiente de la pantalla de .
     * @param savedInstanceState El parametro esta por defecto en la creación en android, Guarda el estado de la instancia.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prueba_scanner);

        Toolbar toolbar = (Toolbar) findViewById(R.id.tool_bar);
        setSupportActionBar(toolbar);
        //------------------Start BD------------------------
        qr = getIntent().getStringExtra("qr");
        int pos = 0;
        BDHelper asistente = new BDHelper(PruebaScanner.this, "bdentrega", null, 1);
        SQLiteDatabase bd = asistente.getWritableDatabase();
        Cursor fila = bd.rawQuery("select * from Sap where CodigoMaquina= '" + qr + "'", null);
        int total = fila.getCount();
        fila.moveToFirst();
        while(pos < total){
            int numcol;
            numcol = fila.getColumnIndex("codigoSap");
            codigoSap = fila.getString(numcol);
            numcol = fila.getColumnIndex("nombreSap");
            nombreSap = fila.getString(numcol);
            numcol = fila.getColumnIndex("cantidadDisponible");
            cantidadDisponible = fila.getInt(numcol);
            cargaArray(codigoSap,nombreSap,cantidadDisponible,pos);
            pos++;
            fila.moveToNext();
        }
        fila.close();
        bd.close();
        //------------------End BD------------------------
        mRecyclerView = (RecyclerView) findViewById(R.id.RecyclerView); // Assigning the RecyclerView Object to the xml View

        mRecyclerView.setHasFixedSize(true);                            // Letting the system know that the list objects are of fixed size

        mAdapter = new MyAdapter(TITLES,ICONS,NAME,EMAIL,PROFILE);       // Creating the Adapter of MyAdapter class(which we are going to see in a bit)
        // And passing the titles,icons,header view name, header view email,
        // and header view profile picture

        mRecyclerView.setAdapter(mAdapter);                              // Setting the adapter to RecyclerView

        mLayoutManager = new LinearLayoutManager(this);                 // Creating a layout Manager

        mRecyclerView.setLayoutManager(mLayoutManager);                 // Setting the layout Manager


        Drawer = (DrawerLayout) findViewById(R.id.DrawerLayout);        // Drawer object Assigned to the view
        mDrawerToggle = new ActionBarDrawerToggle(this,Drawer, toolbar,R.string.openDrawer,R.string.closeDrawer){

            /**
             * Este metodo se encarga de abrir un drawer view
             * @param drawerView
             */
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                // code here will execute once the drawer is opened( As I dont want anything happened whe drawer is
                // open I am not going to put anything here)
            }

            /**
             * Este metodo se encarga de cerrar un drawer view
             * @param drawerView
             */
            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                // Code here will execute once drawer is closed
            }



        }; // Drawer Toggle Object Made
        Drawer.setDrawerListener(mDrawerToggle); // Drawer Listener set to the Drawer toggle
        mDrawerToggle.syncState();

        FrameLayout.LayoutParams fp = new FrameLayout.LayoutParams (FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT,
                Gravity.TOP | Gravity.LEFT);
        FrameLayout view = (FrameLayout) findViewById(R.id.fra);
        //setContentView (view);

        imageView = new ImageView(this);
        //Use line below for large images if you have hardware rendering turned on
        //imageView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        // Line below is optional, depending on what scaling method you want
        view.addView(imageView, fp);

        imageView.setScaleType(ImageView.ScaleType.MATRIX);
        view.setOnTouchListener(new PanAndZoomListener(view, imageView, PanAndZoomListener.Anchor.TOPLEFT));
        link = getIntent().getStringExtra("link");

        new DownloadImageTask().execute(link);


    }//Cierre del metodo onCreate

    public void cargaArray(String codSap, String nomSap, int cantDisponible,int pos){
        TITLES[pos] = codSap+"-"+nomSap+"("+cantDisponible+")";

        switch (nomSap){
            case "Hardware":
                ICONS[pos] = R.drawable.ic_hardware;
                break;
            case "Motor":
                ICONS[pos] = R.drawable.ic_motor;
                break;
            case "Correa":
                ICONS[pos] = R.drawable.ic_cinta;
                break;
            case "Cadena":
                ICONS[pos] = R.drawable.ic_cadena;
                break;
            case "Herramientas":
                ICONS[pos] = R.drawable.ic_herramienta;
                break;
            case "Engranage":
                ICONS[pos] = R.drawable.ic_gears;
                break;
            default:
                break;
        }
    }

    /**
     * Esta clase que define las acciones que permiten la descarga y visualizacion de las fichas.
     * @author: Estefania Flores Sandoval
     * @author: Carlos Varas Miranda
     * @version: 1.0.0 22/02/2016
     */
    private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        /** El sistema Android ejecuta las sentencias del método doInBackground
         * en fondo a traves de un un Worker thread y le pasa los parámetros en la llamada
         * al método AsyncTask.execute()*/
        @Override
        protected Bitmap doInBackground(String... urls) {
            return downloadImagen(urls[0]);
        }
        /** El sistema Android llama a este método para realizar los trabajos necesarios
         * en el UI thread y le entrega el resultado obtenido de la ejecución realizada
         * en el método doInBackground() */
        protected void onPostExecute(Bitmap result) {
            //Ya aquí estamos en el UI Thread o Main Thread por lo que podemos actualizar la
            // imagen del Image View para mostrala la imagen descargada
            imageView.setImageBitmap(result);
        }//cierre del metodo onPostExecute

        /**
         * Este metodo descarga la ficha en formato imagen
         * @param baseUrl
         * @return
         */
        private Bitmap downloadImagen(String baseUrl) {
            Bitmap myBitmap = null;
            try {
                //Se define el objeto URL
                URL url = new URL(baseUrl);
                //Se arma y configura un objeto de conexión HttpURLConnection
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                //Recibimos la respuesta de la petición en formato InputStream
                InputStream input = connection.getInputStream();
                //Decodificamos el InputStream a un objeto BitMap
                myBitmap = BitmapFactory.decodeStream(input);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return myBitmap;
        }//cierre del metodo downloadImagen
    }//cierre de la clase DownloadImageTask
}//cierre de la clase