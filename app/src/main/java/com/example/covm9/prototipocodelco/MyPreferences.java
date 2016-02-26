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

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Esta clase proporciona el ambiente que se alojara la base de datos y entre otros.
 * @author: Estefania Flores Sandoval
 * @author: Carlos Varas Miranda
 * @version: 1.0.0 22/02/2016
 * */
public class MyPreferences {
    //Campo de la clase
    private static final String MY_PREFERENCES = "my_preferences";

    /**
     * Metodo que reconoce si se inicia por primera vez la app
     * @param context Parametro que define el contecto que se ejecuta el metodo
     * @return
     */
    public static boolean isFirst(Context context){
        final SharedPreferences reader = context.getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE);
        final boolean first = reader.getBoolean("is_first", true);
        if(first){
            final SharedPreferences.Editor editor = reader.edit();
            editor.putBoolean("is_first", false);
            editor.commit();
        }
        return first;
    }//Cierre del metodo isFirst

    /**
     * Metodo que reconoce si se inicia por primera vez la app
     * @param context Parametro que define el contecto que se ejecuta el metodo
     * @return
     */
    public static boolean isNoFirst(Context context){
        final SharedPreferences reader = context.getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE);
        final boolean first = reader.getBoolean("is_first", true);
        if(!first){
            final SharedPreferences.Editor editor = reader.edit();
            editor.putBoolean("is_first", true);
            editor.commit();
        }
        return first;
    }//Cierre del metodo isFirst

    /**
     * Metodo que reconoce si es el dia que se debe actualizar la app.
     * @param context Parametro que define el contecto que se ejecuta el metodo
     * @return
     */
    public static boolean isDay(Context context){
        final SharedPreferences reader = context.getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE);
        final boolean day = reader.getBoolean("is_day", true);
        if(day){
            final SharedPreferences.Editor editor = reader.edit();
            editor.putBoolean("is_day", false);
            editor.commit();
        }
        return day;
    }//Cierre del metodo isDay

    /**
     * Metodo que reconoce si no es el dia que se debe actualizar la app.
     * @param context Parametro que define el contecto que se ejecuta el metodo
     * @return
     */
    public static boolean isNoDay(Context context){
        final SharedPreferences reader = context.getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE);
        final boolean day = reader.getBoolean("is_day", true);
        if(!day){
            final SharedPreferences.Editor editor = reader.edit();
            editor.putBoolean("is_day", true);
            editor.commit();
        }
        return day;
    }//Cierre del metodo isNoDay
}