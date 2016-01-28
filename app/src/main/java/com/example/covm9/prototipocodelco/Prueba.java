package com.example.covm9.prototipocodelco;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Prueba extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prueba);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        String qrC = getIntent().getStringExtra("qr");
        String digito = qrC.substring(64, 97);
        TextView et = (TextView) findViewById(R.id.textView4);
        et.setText(digito);
        String digito2 = qrC.substring(105,124 );
        TextView et2 = (TextView) findViewById(R.id.textView5);
        et2.setText(digito2);
        String digito3 = qrC.substring(129,139);
        TextView et3 = (TextView) findViewById(R.id.textView6);
        et3.setText(digito3);


    }

}
