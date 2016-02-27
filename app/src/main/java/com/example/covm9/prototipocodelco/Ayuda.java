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

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

/**
 * Esta clase que define las acciones que realiza la pantalla de ayuda.
 * @author: Estefania Flores Sandoval
 * @author: Carlos Varas Miranda
 * @version: 1.0.0 22/02/2016
 */
public class Ayuda extends Activity {

    //campo de la clase
    public String fono;

    /**
     *Este metodo genera el ambiente de la pantalla de ayuda.
     * @param savedInstanceState El parametro esta por defecto en la creación en android, Guarda el estado de la instancia.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ayuda);


        ImageButton ambuButton = (ImageButton) findViewById(R.id.imageButton2);
        ambuButton.setOnClickListener(new View.OnClickListener() {
            /**
             * Metodo que define la accion del boton que realiza la llamada.
             * @param vista Parametro de tipo View que permite la acción del boton.
             */
            @Override
            public void onClick(View vista) {
                fono = "+56950462323";
                AlertDialog.Builder dialogo1 = new AlertDialog.Builder(Ayuda.this);
                dialogo1.setTitle("Importante");
                dialogo1.setMessage("¿Seguro que desea llamar al servicio de emergencia de coldelco?");
                dialogo1.setCancelable(false);
                dialogo1.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        call();
                    }
                });
                dialogo1.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogo1, int id) {
                        Toast t=Toast.makeText(Ayuda.this,"Se a cancelado la llamada.", Toast.LENGTH_SHORT);
                    }
                });
                dialogo1.show();
            }//Cierre del metodo onClick
        });
        ImageButton oneButton = (ImageButton) findViewById(R.id.imageButton);
        oneButton.setOnClickListener(new View.OnClickListener() {
            /**
             * Metodo que define la accion del boton relacionado con el sistema de emergencia de codelco.
             * @param vista Parametro de tipo View que permite la acción del boton.
             */
            @Override
            public void onClick(View vista) {
                Toast.makeText(Ayuda.this, "Sistemas de Emergencia de Codelco", Toast.LENGTH_SHORT).show();
            }//Cierre del metodo onClick
        });
        ImageButton twoButton = (ImageButton) findViewById(R.id.imageButton3);
        twoButton.setOnClickListener(new View.OnClickListener() {
            /**
             * Metodo que define la accion del boton relacionado con el sistema de emergencia de codelco.
             * @param vista Parametro de tipo View que permite la acción del boton.
             */
            @Override
            public void onClick(View vista) {
                Toast.makeText(Ayuda.this, "Cuando Se Active una emergencia sonara una alarma de alerta", Toast.LENGTH_SHORT).show();
            }//Cierre del metodo onClick
        });
        ImageButton threeButton = (ImageButton) findViewById(R.id.imageButton4);
        threeButton.setOnClickListener(new View.OnClickListener() {
            /**
             * Metodo que define la accion del boton relacionado con el caso de accidente llame al CCE.
             * @param vista Parametro de tipo View que permite la acción del boton.
             */
            @Override
            public void onClick(View vista) {
                Toast.makeText(Ayuda.this, "En caso de accidente llame al CCE", Toast.LENGTH_SHORT).show();
            }//Cierre del metodo onClick
        });
        ImageButton fourButton = (ImageButton) findViewById(R.id.imageButton5);
        fourButton.setOnClickListener(new View.OnClickListener() {
            /**
             * Metodo que define la accion del boton relacionado con el caso de un accidente grabe.
             * @param vista Parametro de tipo View que permite la acción del boton.
             */
            @Override
            public void onClick(View vista) {
                Toast.makeText(Ayuda.this, "En caso de un accidente grabe solicite una ambulancia", Toast.LENGTH_SHORT).show();
            }//Cierre del metodo onClick
        });
        ImageButton fiveButton = (ImageButton) findViewById(R.id.imageButton6);
        fiveButton.setOnClickListener(new View.OnClickListener() {
            /**
             * Metodo que define la accion del boton relacionado con el caso de proteccion industrial.
             * @param vista Parametro de tipo View que permite la acción del boton.
             */
            @Override
            public void onClick(View vista) {
                Toast.makeText(Ayuda.this, "En este caso solicite al CCE proteccion industrial", Toast.LENGTH_SHORT).show();
            }//Cierre del metodo onClick
        });
        ImageButton sixButton = (ImageButton) findViewById(R.id.imageButton7);
        sixButton.setOnClickListener(new View.OnClickListener() {
            /**
             * Metodo que define la accion del boton relacionado con el caso de solicitar los puntos de encuentro.
             * @param vista Parametro de tipo View que permite la acción del boton.
             */
            @Override
            public void onClick(View vista) {
                Toast.makeText(Ayuda.this, "Mostrar Mapa - En proceso", Toast.LENGTH_SHORT).show();
            }//Cierre del metodo onClick
        });
    }//Cierre del metodo onCreate

    /**
     * Metodo para realizar la llamada telefonica
     * */
    private void call() {
        try {
            if (fono.length() > 0) {
                //realiza la llamada
                Uri numero = Uri.parse("tel:" + fono.toString());
                Intent intent = new Intent(Intent.ACTION_CALL, numero);

                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                startActivity(intent);
            }
            else
            {
                //si no se escribio numero telefonico, avisa
                Toast.makeText(getBaseContext(), "Debe escribir un numero de telefono", Toast.LENGTH_SHORT).show();
            }

        }
        catch (ActivityNotFoundException activityException) {
            //si se produce un error, se muestra en el LOGCAT
            Log.e("Ayuda", "No se pudo realizar la llamada.", activityException);
        }

    }//Cierre del metodo call
}//Cierre de la clase


