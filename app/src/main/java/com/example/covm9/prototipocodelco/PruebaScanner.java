package com.example.covm9.prototipocodelco;


import android.app.Activity;
import android.app.ProgressDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

import library.imagezoom.ImageViewTouch;


public class PruebaScanner extends Activity {
    //Tag para el control de trazas de LOG
    private static String APP_TAG = "ECTDownloadData";
    //Constante String con la URL de la imagen a descargar
    private static String IMG_URL = "http://expocodetech.com/wp-content/uploads/2014/09/cloud-storage-android.jpg";

    public static final String DIRECCION_CONEXION = "http://qrcodech.16mb.com/codigosqr/GetDataArchivo.php";
    public static final int TIMEOUT = 1000*15;
    ProgressDialog progressDialog;
    String[] data;
    public ImageView imageView;

    String codigoMaquina;
    String link;
    String qr;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prueba_scanner);

        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        Date d = new Date();
        String dayOfTheWeek = sdf.format(d);

        if(dayOfTheWeek.equals("miércoles")){

            progressDialog = new ProgressDialog(PruebaScanner.this);
            progressDialog.setCancelable(false);
            progressDialog.setTitle("Conectando, Por favor espere");
            progressDialog.show();
            JsonRead cargar = new JsonRead();
            cargar.execute();

        }

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

    private class JsonRead extends AsyncTask<Void,Void, String> {

        int tap;
        String datos="";

        protected String doInBackground(Void... params) {

            try{
                URL url = new URL(DIRECCION_CONEXION);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setConnectTimeout(TIMEOUT);

                InputStream is = httpURLConnection.getInputStream();
                while ((tap = is.read()) != -1){
                    datos+=(char) tap;
                }
                is.close();
                httpURLConnection.disconnect();
                return datos;
            }catch (MalformedURLException e){
                e.printStackTrace();
                return "Exception: " + e.getMessage();
            }
            catch (IOException e){
                e.printStackTrace();
                return "Exception: " + e.getMessage();
            }
        }

        protected void onPostExecute(String s) {
            progressDialog.dismiss();
            BDHelper asistente = new BDHelper(PruebaScanner.this, "bdentrega", null, 1);
            SQLiteDatabase bd = asistente.getWritableDatabase();
            asistente.actualizarArchivo(bd);
            String err=null;
            try{
                qr = getIntent().getStringExtra("qr");
                int indexFinal = s.indexOf("]");
                String resto = s.substring(1,indexFinal);
                int index;
                String objeto;
                while(resto.length() > 0) {
                    index = resto.indexOf("}");
                    objeto = resto.substring(0,index+1);
                    JSONObject root = new JSONObject(objeto);
                    link = root.getString("link");
                    codigoMaquina = root.getString("codigoMaquina");
                    if(codigoMaquina.equals(qr)){
                        new DownloadImageTask().execute(link);
                    }
                    if(index+1 >= resto.length()) {
                        resto = "";
                    }else {
                        resto = resto.substring(index + 2);
                    }
                }
                bd.close();

            }catch (JSONException e){
                Toast.makeText(PruebaScanner.this,R.string.errorActualizacion,Toast.LENGTH_LONG).show();
                e.printStackTrace();
                err = "Exception: " + e.getMessage();
            }
            catch (Exception e){
                Toast.makeText(PruebaScanner.this,R.string.errorActualizacion, Toast.LENGTH_LONG).show();
            }

        }

    }

}