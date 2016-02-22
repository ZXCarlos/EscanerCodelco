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

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Esta clase proporciona el ambiente que se alojara la base de datos y entre otros.
 * @author: Estefania Flores Sandoval
 * @author: Carlos Varas Miranda
 * @version: 1.0.0 22/02/2016
 * */
public class PagWeb extends AppCompatActivity {

    /**
     *Este metodo genera el ambiente de la pantalla de Pagina Web.
     * @param savedInstanceState El parametro esta por defecto en la creación en android, Guarda el estado de la instancia.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pag_web);

        WebView browser = (WebView)findViewById(R.id.webkit);
        browser.setWebViewClient(new WebViewClient());
        String url = getIntent().getStringExtra("URL");
        String qr = getIntent().getStringExtra("qr");
        if(url==null){
            browser.loadUrl(qr);
        }else{
            browser.loadUrl(url);
        }
    }//Cierre del metodo onCreate

}
