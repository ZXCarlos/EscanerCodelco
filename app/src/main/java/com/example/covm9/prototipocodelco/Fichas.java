package com.example.covm9.prototipocodelco;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class Fichas extends AppCompatActivity {
    String qr;
    String linkFT;
    String linkFS;
    String linkFR;
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
            numcol = fila.getColumnIndex("linkFT");
            linkFT = fila.getString(numcol);
            numcol = fila.getColumnIndex("linkFS");
            linkFS = fila.getString(numcol);
            numcol = fila.getColumnIndex("linkFR");
            linkFR = fila.getString(numcol);

        }
        fila.close();
        bd.close();
        Button sw = (Button) findViewById(R.id.button4);
        sw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Fichas.this, PruebaScanner.class);
                i.putExtra("link", linkFT);
                startActivity(i);
            }
        });

        Button cr = (Button) findViewById(R.id.button3);
        cr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Fichas.this, PruebaScanner.class);
                i.putExtra("link", linkFS);
                startActivity(i);
            }
        });

        Button qr = (Button) findViewById(R.id.button);
        qr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Fichas.this, PruebaScanner.class);
                i.putExtra("link", linkFR);
                startActivity(i);
            }
        });

    }


}
