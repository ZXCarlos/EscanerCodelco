package com.example.covm9.prototipocodelco;


import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import library.imagezoom.ImageViewTouch;


public class PruebaScanner extends Activity {
    //Tag para el control de trazas de LOG
    private static String APP_TAG = "ECTDownloadData";
    //Constante String con la URL de la imagen a descargar
    private static String IMG_URL = "http://expocodetech.com/wp-content/uploads/2014/09/cloud-storage-android.jpg";

    public ImageView imageView;


    String qr;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prueba_scanner);


        FrameLayout.LayoutParams fp = new FrameLayout.LayoutParams (FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT,
                Gravity.TOP | Gravity.LEFT);
        FrameLayout view = new FrameLayout (this);
        setContentView (view);

        imageView = new ImageView(this);
        //Use line below for large images if you have hardware rendering turned on
        //imageView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        // Line below is optional, depending on what scaling method you want
        view.addView(imageView, fp);

        imageView.setScaleType(ImageView.ScaleType.MATRIX);
        view.setOnTouchListener(new PanAndZoomListener(view, imageView, PanAndZoomListener.Anchor.TOPLEFT));
        qr = getIntent().getStringExtra("qr");
        new DownloadImageTask().execute(qr);


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
            imageView.setImageBitmap(result);
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