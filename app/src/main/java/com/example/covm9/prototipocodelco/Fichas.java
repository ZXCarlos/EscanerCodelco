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

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Esta clase que define las acciones que realiza la pantalla de Fichas.
 * @author: Estefania Flores Sandoval
 * @author: Carlos Varas Miranda
 * @version: 1.0.0 22/02/2016
 * */
public class Fichas extends AppCompatActivity {
    //Campos de la clase
    String qr;
    String linkFT;
    String linkFS;
    String linkR;

    /**
     *Este metodo genera el ambiente de la pantalla de Fichas.
     * @param savedInstanceState El parametro esta por defecto en la creación en android, Guarda el estado de la instancia.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fichas);

        qr = getIntent().getStringExtra("qr");
        BDHelper asistente = new BDHelper(Fichas.this, "bdentrega", null, 1);
        SQLiteDatabase bd = asistente.getWritableDatabase();
        Cursor fila = bd.rawQuery("select * from Archivo where CodigoMaquina= '" + qr + "'", null);
        if (fila.moveToFirst()) {

            int numcol;
            numcol = fila.getColumnIndex("linkR");
            linkR = fila.getString(numcol);
            numcol = fila.getColumnIndex("linkFT");
            linkFT = fila.getString(numcol);
            numcol = fila.getColumnIndex("linkFS");
            linkFS = fila.getString(numcol);

        }
        fila.close();
        bd.close();
        Button sw = (Button) findViewById(R.id.FR);
        sw.setOnClickListener(new View.OnClickListener() {
            /**
             * Metodo que define la accion del boton que realiza la accion del boton.
             * @param vista Parametro de tipo View que permite la acción del boton.
             */
            @Override
            public void onClick(View vista) {
                Intent i = new Intent(Fichas.this, PruebaScanner.class);
                i.putExtra("link", linkR);
                i.putExtra("qr", qr);
                startActivity(i);
            }
        });

        Button cr = (Button) findViewById(R.id.FT);
        cr.setOnClickListener(new View.OnClickListener() {
            /**
             * Metodo que define la accion del boton que realiza la accion del boton.
             * @param vista Parametro de tipo View que permite la acción del boton.
             */
            @Override
            public void onClick(View vista) {
                Intent i = new Intent(Fichas.this, PruebaScanner.class);
                i.putExtra("link", linkFT);
                i.putExtra("qr", qr);
                startActivity(i);
            }
        });

        Button qrButton = (Button) findViewById(R.id.FS);
        qrButton.setOnClickListener(new View.OnClickListener() {
            /**
             * Metodo que define la accion del boton que realiza la accion del boton.
             * @param vista Parametro de tipo View que permite la acción del boton.
             */
            @Override
            public void onClick(View vista) {
                Intent i = new Intent(Fichas.this, PruebaScanner.class);
                i.putExtra("link", linkFS);
                i.putExtra("qr", qr);
                startActivity(i);
            }
        });

    }//Cierre del metodo onCreate


}//Cierre de la clase
