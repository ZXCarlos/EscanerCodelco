package com.example.covm9.prototipocodelco;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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

public class PagPrincipal extends AppCompatActivity {

    public static final String DIRECCION_CONEXION = "http://qrcodech.16mb.com/codigosqr/GetDataArchivo.php";
    public static final int TIMEOUT = 1000*15;
    ProgressDialog progressDialog;
    boolean PRIMERA;

    private BDHelper BD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pag_principal);
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        Date d = new Date();
        String dayOfTheWeek = sdf.format(d);

        //se revisa si es la primera ves que se entra a la aplicacion
        boolean isFirstTime = MyPreferences.isFirst(PagPrincipal.this);

        //si es la primera vez

        if(isFirstTime) {

            progressDialog = new ProgressDialog(PagPrincipal.this);
            progressDialog.setCancelable(false);
            progressDialog.setTitle("Conectando, Por favor espere");
            progressDialog.show();
            JsonRead cargar = new JsonRead();
            cargar.execute();
            if(dayOfTheWeek.equals("lunes")){
                boolean isDay = MyPreferences.isDay(PagPrincipal.this);
            }
        }else if(dayOfTheWeek.equals("lunes")){
            boolean isDay = MyPreferences.isDay(PagPrincipal.this);
            if(isDay){
                progressDialog = new ProgressDialog(PagPrincipal.this);
                progressDialog.setCancelable(false);
                progressDialog.setTitle("Conectando, Por favor espere");
                progressDialog.show();
                JsonRead cargar = new JsonRead();
                cargar.execute();
            }
        }else if(!dayOfTheWeek.equals("lunes")){
            boolean isDay = MyPreferences.isDay(PagPrincipal.this);
            if(!isDay){
                boolean isNoFirstTime = MyPreferences.isNoDay(PagPrincipal.this);
            }
        }





        Button sw = (Button) findViewById(R.id.button4);
        sw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PagPrincipal.this, PagWeb.class);
                i.putExtra("URL", "http://www.codelco.com");
                startActivity(i);
            }
        });

        Button cr = (Button) findViewById(R.id.button3);
        cr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PagPrincipal.this, Creditos.class);
                startActivity(i);
            }
        });

        Button qr = (Button) findViewById(R.id.button);
        qr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PagPrincipal.this, Escaner.class);
                startActivity(i);
            }
        });

        Button ayuda = (Button) findViewById(R.id.button2);
        ayuda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PagPrincipal.this, Ayuda.class);
                startActivity(i);
            }
        });


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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class JsonRead extends AsyncTask<Void,Void, String>{

        int tap;
        String datos="";

        @Override
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

        @Override
        protected void onPostExecute(String s) {
            progressDialog.dismiss();
            BDHelper asistente = new BDHelper(PagPrincipal.this, "bdentrega", null, 1);
            SQLiteDatabase bd = asistente.getWritableDatabase();
            asistente.actualizarArchivo(bd);
            String err=null;
            try{
                int indexFinal = s.indexOf("]");
                String resto = s.substring(1,indexFinal);
                int index;
                String objeto;
                while(resto.length() > 0) {
                    index = resto.indexOf("}");
                    objeto = resto.substring(0,index+1);
                    JSONObject root = new JSONObject(objeto);
                    String codigoA = root.getString("codigoArchivo");
                    String descripcionA = root.getString("descripcion");
                    String linkFR = root.getString("linkRuta");
                    String codigoM = root.getString("codigoMaquina");
                    String linkFT = root.getString("linkFTecnica");
                    String linkFS = root.getString("linkFSeguridad");
                    asistente.cargarArchivo(codigoA, descripcionA, linkFR, codigoM, linkFT, linkFS,  bd);
                    if(index+1 >= resto.length()) {
                        resto = "";
                    }else {
                        resto = resto.substring(index + 2);
                    }
                }
                bd.close();
                if(PRIMERA) {
                    PRIMERA = false;
                }else {
                    Toast.makeText(PagPrincipal.this, R.string.actualizacion, Toast.LENGTH_SHORT).show();
                }
            }catch (JSONException e){
                Toast.makeText(PagPrincipal.this,R.string.errorActualizacion,Toast.LENGTH_LONG).show();
                e.printStackTrace();
                err = "Exception: " + e.getMessage();
            }
            catch (Exception e){
                Toast.makeText(PagPrincipal.this,R.string.errorActualizacion,Toast.LENGTH_LONG).show();
            }
        }
    }
}
