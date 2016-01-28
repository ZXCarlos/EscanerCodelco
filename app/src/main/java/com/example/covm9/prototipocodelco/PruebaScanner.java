package com.example.covm9.prototipocodelco;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.util.Linkify;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class PruebaScanner extends AppCompatActivity {
    //Tag para el control de trazas de LOG
    private static String APP_TAG = "ECTDownloadData";
    //Constante String con la URL de la imagen a descargar
    private static String IMG_URL = "http://expocodetech.com/wp-content/uploads/2014/09/cloud-storage-android.jpg";

    //Image View para mostrar la Imagen Descargada
     public ImageView mImageView;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prueba_scanner);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String qr = getIntent().getStringExtra("qr");
        new DownloadImageTask().execute(qr);
        mImageView = (ImageView) findViewById(R.id.mImageView);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_pag_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


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
            mImageView.setImageBitmap(result);
         }

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
         }
    }

}