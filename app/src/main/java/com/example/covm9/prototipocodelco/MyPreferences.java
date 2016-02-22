package com.example.covm9.prototipocodelco;

/**
 * Created by covm9 on 22-02-2016.
 */
import android.content.Context;
import android.content.SharedPreferences;
/**
 * Created by lenovo on 03/12/2015.
 */
public class MyPreferences {
    private static final String MY_PREFERENCES = "my_preferences";
    public static boolean isFirst(Context context){
        final SharedPreferences reader = context.getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE);
        final boolean first = reader.getBoolean("is_first", true);
        if(first){
            final SharedPreferences.Editor editor = reader.edit();
            editor.putBoolean("is_first", false);
            editor.commit();
        }
        return first;
    }
    public static boolean isDay(Context context){
        final SharedPreferences reader = context.getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE);
        final boolean day = reader.getBoolean("is_day", true);
        if(day){
            final SharedPreferences.Editor editor = reader.edit();
            editor.putBoolean("is_day", false);
            editor.commit();
        }
        return day;
    }

    public static boolean isNoDay(Context context){
        final SharedPreferences reader = context.getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE);
        final boolean day = reader.getBoolean("is_day", false);
        if(day){
            final SharedPreferences.Editor editor = reader.edit();
            editor.putBoolean("is_day", true);
            editor.commit();
        }
        return day;
    }
}