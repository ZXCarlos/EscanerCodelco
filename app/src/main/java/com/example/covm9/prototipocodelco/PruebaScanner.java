package com.example.covm9.prototipocodelco;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.ImageView;
import android.graphics.Matrix;
import android.widget.LinearLayout;
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
    public Matrix matrix = new Matrix();
    public float scale = 1f;
    public ScaleGestureDetector SGD;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prueba_scanner);

        mImageView = (ImageView) findViewById(R.id.mImageView);
        SGD = new ScaleGestureDetector(this, new ScaleListener());
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mImageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        /*mImageView.setOnTouchListener(new OnDoubleTapListener(this){
                                         @Override
                                     public void onDoubleTap (MotionEvent e){
                                             Toast.makeText(PruebaScanner.this, "Double Tap", Toast.LENGTH_SHORT).show();
                                         }
                                      });*/
        /*        //Toast.makeText(PruebaScanner.this, "Down", Toast.LENGTH_SHORT).show();
                mImageView.setY(100);
                mImageView.setScrollY(1);
            }

            @Override
            public void onSwipeLeft() {
                //Toast.makeText(PruebaScanner.this, "Left", Toast.LENGTH_SHORT).show();
                mImageView.setX(-100);
                mImageView.setScaleX(-100);
            }

            @Override
            public void onSwipeTop() {
                //Toast.makeText(PruebaScanner.this, "Up", Toast.LENGTH_SHORT).show();
                mImageView.setY(-100);
                mImageView.setScrollY(-100);
            }

            @Override
            public void onSwipeRight() {
                //Toast.makeText(PruebaScanner.this, "Right", Toast.LENGTH_SHORT).show();
                mImageView.setX(100);
                mImageView.setScaleX(1);
            }
        });*/

        String qr = getIntent().getStringExtra("qr");
        new DownloadImageTask().execute(qr);
        mImageView = (ImageView) findViewById(R.id.mImageView);

    }

    public boolean onTouchEvent(MotionEvent ev) {
        SGD.onTouchEvent(ev);
        return true;
    }

    private class ScaleListener extends ScaleGestureDetector.

            SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            scale *= detector.getScaleFactor();
            scale = Math.max(0.1f, Math.min(scale, 5.0f));

            matrix.setScale(scale, scale);
            mImageView.setImageMatrix(matrix);
            return true;
        }
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