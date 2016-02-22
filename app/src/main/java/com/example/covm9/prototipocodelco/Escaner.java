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

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

/**
 * Esta clase que define las acciones que realiza la pantalla de Escaner.
 * @author: Estefania Flores Sandoval
 * @author: Carlos Varas Miranda
 * @version: 1.0.0 22/02/2016
 */
public class Escaner extends AppCompatActivity {
    //campos de la clase
    static final String ACTION_SCAN = "com.google.zxing.client.android.SCAN";
    String qr_id;
    LinearLayout pantalla;

    /**
     *Este metodo genera el ambiente de la pantalla de creditos.
     * @param savedInstanceState El parametro esta por defecto en la creación en android, Guarda el estado de la instancia.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_escaner);
        pantalla = (LinearLayout) findViewById(R.id.pantalla);
        pantalla.requestFocus();
    }//Cierre del metodo onCreate

    /**
     * Metodo que sugiere la descarga del scanner de qr a utilizar por la app cuando no esta instalado(English).
     * @param vista
     */
    public void scanBar(View vista) {
        try {
            Intent intent = new Intent(ACTION_SCAN);
            intent.putExtra("SCAN_MODE", "PRODUCT_MODE");
            startActivityForResult(intent, 0);
        } catch (ActivityNotFoundException anfe) {
            showDialog(Escaner.this, "No Scanner Found", "Download a scanner code activity?", "Yes", "No").show();
        }
    }//Cierre del metodo scanBar

    /**
     * Metodo que sugiere la descarga del scanner de qr a utilizar por la app cuando no esta instalado(Español).
     * @param vista
     */
    public void scanQR(View vista) {

        try {
            Intent intent = new Intent(ACTION_SCAN);
            intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
            startActivityForResult(intent, 0);
        } catch (ActivityNotFoundException anfe) {
            showDialog(Escaner.this, "No se encontró el escáner para poder analizar los códigos QR" , "¿Desea descargar el escáner en su celular?", "Si", "No").show();
        }
    }//cierre del metodo scanQR.

    /**
     * Metodo que muestra la alerta de no deteccion de la app del scanner a utilizar y conecta con la descarga de esta.
     * @param act Parmatro que reprecenta la pantalla que ejerce la actividad
     * @param title Parametro que entrega el titulo de la alerta
     * @param message Parametro que reprecenta el mensaje de alerta a mostrar
     * @param buttonYes Parametro que instancia al boton si/yes.
     * @param buttonNo Parametro que instancia al boton no.
     * @return downloadDialog.show() de tipo AlertDialog
     */
    private static AlertDialog showDialog(final Activity act, CharSequence title, CharSequence message, CharSequence buttonYes, CharSequence buttonNo) {
        AlertDialog.Builder downloadDialog = new AlertDialog.Builder(act);
        downloadDialog.setTitle(title);
        downloadDialog.setMessage(message);
        downloadDialog.setPositiveButton(buttonYes, new DialogInterface.OnClickListener() {
            /**
             * Metodo que define la accion del boton Si/Yes.
             * @param dialogInterface Parametro por defecto de la accion del boton de alerta.
             * @param i Parametro por defecto de la accion del boton de alerta.
             */
            public void onClick(DialogInterface dialogInterface, int i) {
                Uri uri = Uri.parse("market://search?q=pname:" + "com.google.zxing.client.android");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                try {
                    act.startActivity(intent);
                } catch (ActivityNotFoundException anfe) {
                }
            }//Cierre del metodo onClick
        });
        downloadDialog.setNegativeButton(buttonNo, new DialogInterface.OnClickListener() {
            /**
             * Metodo que define la accion del boton No.
             * @param dialogInterface Parametro por defecto de la accion del boton de alerta.
             * @param i Parametro por defecto de la accion del boton de alerta.
             */
            public void onClick(DialogInterface dialogInterface, int i) {
            }//Cierre del metodo onClick
        });
        return downloadDialog.show();

    }//Cierre del metodo showDialog

    /**
     * Metodo que se encarga de analisar que accion se debe realizar con el codigo encontrado.
     * @param requestCode Parametro que se define como el codigo solicitado.
     * @param resultCode Parametro que representa al codigo resultado.
     * @param intent Parametro que entrega la intención.
     */
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {
                //get the extras that are returned from the intent
                String codigoqr = intent.getStringExtra("SCAN_RESULT");
                //se guarda en el string el codigo qr
                qr_id = codigoqr;
                //se obtiene el primer caracter que indica si es un medico, medicina o ubicacion
                //String digito = qr_id.substring(64, 65);
                Intent info = new Intent(Escaner.this, Fichas.class);
                info.putExtra("qr", qr_id);
                startActivity(info);
            }
        }
    }//Cierre del metodo onActivityResult

}//Cierre de la clase
