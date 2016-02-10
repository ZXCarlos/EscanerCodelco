package com.example.covm9.prototipocodelco;

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

public class Ayuda extends Activity {
    public String fono;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ayuda);

        ImageButton ambuButton = (ImageButton) findViewById(R.id.imageButton2);
        ambuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fono = "+56950462323";
                call();
            }
        });
        ImageButton oneButton = (ImageButton) findViewById(R.id.imageButton);
        oneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Ayuda.this, "Sistemas de Emergencia de Codelco", Toast.LENGTH_SHORT).show();
            }
        });
        ImageButton twoButton = (ImageButton) findViewById(R.id.imageButton3);
        twoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Ayuda.this, "Cuando Se Active una emergencia sonara una alarma de alerta", Toast.LENGTH_SHORT).show();
            }
        });
        ImageButton threeButton = (ImageButton) findViewById(R.id.imageButton4);
        threeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Ayuda.this, "En caso de accidente llame al CCE", Toast.LENGTH_SHORT).show();
            }
        });
        ImageButton fourButton = (ImageButton) findViewById(R.id.imageButton5);
        fourButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Ayuda.this, "En caso de un accidente grabe solicite una ambulancia", Toast.LENGTH_SHORT).show();
            }
        });
        ImageButton fiveButton = (ImageButton) findViewById(R.id.imageButton6);
        fiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Ayuda.this, "En este caso solicite al CCE proteccion industrial", Toast.LENGTH_SHORT).show();
            }
        });
        ImageButton sixButton = (ImageButton) findViewById(R.id.imageButton7);
        sixButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Ayuda.this, "Mostrar Mapa - En proceso", Toast.LENGTH_SHORT).show();
            }
        });
    }

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

    }
}


