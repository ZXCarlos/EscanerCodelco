package com.example.covm9.prototipocodelco;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class Fichas extends AppCompatActivity {
    String qr;
    String linkFT;
    String linkFS;
    String linkR;
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
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Fichas.this, PruebaScanner.class);
                i.putExtra("link", linkR);
                startActivity(i);
            }
        });

        Button cr = (Button) findViewById(R.id.FT);
        cr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Fichas.this, PruebaScanner.class);
                i.putExtra("link", linkFT);
                startActivity(i);
            }
        });

        Button qr = (Button) findViewById(R.id.FS);
        qr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Fichas.this, PruebaScanner.class);
                i.putExtra("link", linkFS);
                startActivity(i);
            }
        });

    }


}
