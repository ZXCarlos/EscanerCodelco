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

/**
 * Esta clase proporciona el ambiente que se alojara la base de datos y entre otros.
 * @author: Estefania Flores Sandoval
 * @author: Carlos Varas Miranda
 * @version: 1.0.0 22/02/2016
 * */
public class PagPrincipal extends AppCompatActivity {

    //Campos de la clase
    public static final String DIRECCION_CONEXION_Archivo = "http://qrcodech.16mb.com/codigosqr/GetDataArchivo.php";
    public static final String DIRECCION_CONEXION_SAP = "http://qrcodech.16mb.com/codigosqr/GetDataSap.php";
    public static final String DIRECCION_CONEXION_Maquina = "http://qrcodech.16mb.com/codigosqr/GetData.php";
    public static final int TIMEOUT = 1000*15;
    ProgressDialog progressDialog;
    boolean PRIMERA;

    private BDHelper BD;

    /**
     *Este metodo genera el ambiente de la pantalla de Pagina Principal.
     * @param savedInstanceState El parametro esta por defecto en la creación en android, Guarda el estado de la instancia.
     */
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
            JsonRead3 cargar3 = new JsonRead3();
            cargar3.execute();
            JsonRead2 cargar2 = new JsonRead2();
            cargar2.execute();
            JsonRead cargar = new JsonRead();
            cargar.execute();
            if(dayOfTheWeek.equals("lunes")){
                MyPreferences.isDay(PagPrincipal.this);
            }
        }else if(dayOfTheWeek.equals("lunes")){
            boolean isDay = MyPreferences.isDay(PagPrincipal.this);
            if(isDay){
                progressDialog = new ProgressDialog(PagPrincipal.this);
                progressDialog.setCancelable(false);
                progressDialog.setTitle("Conectando, Por favor espere");
                progressDialog.show();
                JsonRead3 cargar3 = new JsonRead3();
                cargar3.execute();
                JsonRead cargar = new JsonRead();
                cargar.execute();
                JsonRead2 cargar2 = new JsonRead2();
                cargar2.execute();
            }
        }else if(!dayOfTheWeek.equals("lunes")){
            boolean isDay = MyPreferences.isDay(PagPrincipal.this);
            if(!isDay){
                MyPreferences.isNoDay(PagPrincipal.this);
            }
        }

        Button sw = (Button) findViewById(R.id.button4);
        sw.setOnClickListener(new View.OnClickListener() {
            /**
             * Metodo que define la accion del boton Pagina Web.
             * @param vista Parametro de tipo View que permite la acción del boton.
             */
            @Override
            public void onClick(View vista) {
                Intent i = new Intent(PagPrincipal.this, PagWeb.class);
                i.putExtra("URL", "http://www.codelco.com");
                startActivity(i);
            }//Cierre del metodo onClick
        });

        Button cr = (Button) findViewById(R.id.button3);
        cr.setOnClickListener(new View.OnClickListener() {
            /**
             * Metodo que define la accion del boton creditos.
             * @param vista Parametro de tipo View que permite la acción del boton.
             */
            @Override
            public void onClick(View vista) {
                Intent i = new Intent(PagPrincipal.this, Creditos.class);
                startActivity(i);
            }//Cierre del metodo onClick
        });

        Button qr = (Button) findViewById(R.id.button);
        qr.setOnClickListener(new View.OnClickListener() {
            /**
             * Metodo que define la accion del boton scanner.
             * @param vista Parametro de tipo View que permite la acción del boton.
             */
            @Override
            public void onClick(View vista) {
                Intent i = new Intent(PagPrincipal.this, Escaner.class);
                startActivity(i);
            }//Cierre del metodo onClick
        });

        Button ayuda = (Button) findViewById(R.id.button2);
        ayuda.setOnClickListener(new View.OnClickListener() {
            /**
             * Metodo que define la accion del boton ayuda.
             * @param vista Parametro de tipo View que permite la acción del boton.
             */
            @Override
            public void onClick(View vista) {
                Intent i = new Intent(PagPrincipal.this, Ayuda.class);
                startActivity(i);
            }//Cierre del metodo onClick
        });

    }

    /**
     * Metodo que genera el menu a mostrar por la aplicación
     * @param menu Parametro de Menu que se define en el res
     * @return true
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_pag_principal, menu);
        return true;
    }//Cierre del metodo onCreateOptionsMenu

    /**
     * Metodo que genera las opciones a mostrar por el menu.
     * @param item Pametro que reprecentan los items que se ingresaran al menu
     * @return un boolean
     */
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
    }//Cierre del metodo onOptionsItemSelected

    /**
     * Esta clase proporciona el ambiente que se alojara la base de datos y entre otros.
     * @author: Estefania Flores Sandoval
     * @author: Carlos Varas Miranda
     * @version: 1.0.0 22/02/2016
     * */
    private class JsonRead extends AsyncTask<Void,Void, String>{

        int tap;
        String datos="";

        /**
         * Metodo que realiza la conexion con la base de datos.
         * @param params
         * @return string
         */
        @Override
        protected String doInBackground(Void... params) {

            try{
                URL url = new URL(DIRECCION_CONEXION_Archivo);
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
        }//Cierre del metodo doInBackground.

        /**
         * Metodo que carga los datos en la base de datos interna de la app.
         * @param s Parametro que contiene un string con los datos a cargar en la base de datos interna.
         */
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
                boolean first = MyPreferences.isNoFirst(PagPrincipal.this);
                Toast.makeText(PagPrincipal.this,R.string.errorActualizacion,Toast.LENGTH_LONG).show();
                e.printStackTrace();
                err = "Exception: " + e.getMessage();
            }
            catch (Exception e){
                boolean first = MyPreferences.isNoFirst(PagPrincipal.this);
                Toast.makeText(PagPrincipal.this,R.string.errorActualizacion,Toast.LENGTH_LONG).show();
            }
        }//Cierre del metodo  onPostExecute.
    }//Cierre de la clase JsonRead.
    private class JsonRead2 extends AsyncTask<Void,Void, String>{

        int tap;
        String datos="";

        /**
         * Metodo que realiza la conexion con la base de datos.
         * @param params
         * @return string
         */
        @Override
        protected String doInBackground(Void... params) {

            try{
                URL url = new URL(DIRECCION_CONEXION_SAP);
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
        }//Cierre del metodo doInBackground.

        /**
         * Metodo que carga los datos en la base de datos interna de la app.
         * @param s Parametro que contiene un string con los datos a cargar en la base de datos interna.
         */
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
                    String codigoSap = root.getString("codigoSap");
                    String codigoM = root.getString("codigoMaquina");
                    String nombreSap = root.getString("nombreSap");
                    String descripcionSap = root.getString("descripcionSap");
                    int cantidadDisponible = root.getInt("cantidadDisponible");
                    asistente.cargarSap(codigoSap, codigoM, nombreSap, descripcionSap, cantidadDisponible, bd);
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
                boolean first = MyPreferences.isNoFirst(PagPrincipal.this);
                Toast.makeText(PagPrincipal.this,R.string.errorActualizacion,Toast.LENGTH_LONG).show();
                e.printStackTrace();
                err = "Exception: " + e.getMessage();
            }
            catch (Exception e){
                boolean first = MyPreferences.isNoFirst(PagPrincipal.this);
                Toast.makeText(PagPrincipal.this,R.string.errorActualizacion,Toast.LENGTH_LONG).show();
            }
        }//Cierre del metodo  onPostExecute.
    }//Cierre de la clase JsonRead2.
    private class JsonRead3 extends AsyncTask<Void,Void, String>{

        int tap;
        String datos="";

        /**
         * Metodo que realiza la conexion con la base de datos.
         * @param params
         * @return string
         */
        @Override
        protected String doInBackground(Void... params) {

            try{
                URL url = new URL(DIRECCION_CONEXION_Maquina);
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
        }//Cierre del metodo doInBackground.

        /**
         * Metodo que carga los datos en la base de datos interna de la app.
         * @param s Parametro que contiene un string con los datos a cargar en la base de datos interna.
         */
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
                    String codigoM = root.getString("codigoMaquina");
                    String nombreMaquina = root.getString("nombreMaquina");
                    asistente.cargarMaquina(codigoM,nombreMaquina,bd);
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
                boolean first = MyPreferences.isNoFirst(PagPrincipal.this);
                Toast.makeText(PagPrincipal.this,R.string.errorActualizacion,Toast.LENGTH_LONG).show();
                e.printStackTrace();
                err = "Exception: " + e.getMessage();
            }
            catch (Exception e){
                boolean first = MyPreferences.isNoFirst(PagPrincipal.this);
                Toast.makeText(PagPrincipal.this,R.string.errorActualizacion,Toast.LENGTH_LONG).show();
            }
        }//Cierre del metodo  onPostExecute.
    }//Cierre de la clase JsonRead3.
}//Cierre de la clase.
