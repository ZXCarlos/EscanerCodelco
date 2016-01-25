package com.example.covm9.prototipocodelco;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;

public class Escaner extends AppCompatActivity {
    static final String ACTION_SCAN = "com.google.zxing.client.android.SCAN";
    String qr_id;
    LinearLayout pantalla;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_escaner);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        pantalla = (LinearLayout) findViewById(R.id.pantalla);
        pantalla.requestFocus();
    }
    public void scanBar(View v) {

        try {
            Intent intent = new Intent(ACTION_SCAN);
            intent.putExtra("SCAN_MODE", "PRODUCT_MODE");
            startActivityForResult(intent, 0);
        } catch (ActivityNotFoundException anfe) {
            showDialog(Escaner.this, "No Scanner Found", "Download a scanner code activity?", "Yes", "No").show();
        }
    }

    public void scanQR(View v) {

        try {
            Intent intent = new Intent(ACTION_SCAN);
            intent.putExtra("SCAN_MODE", "QR_CODE_MODE");
            startActivityForResult(intent, 0);
        } catch (ActivityNotFoundException anfe) {
            showDialog(Escaner.this, "No se encontró el escáner para poder analizar los códigos QR" , "¿Desea descargar el escáner en su celular?", "Si", "No").show();
        }
    }

    private static AlertDialog showDialog(final Activity act, CharSequence title, CharSequence message, CharSequence buttonYes, CharSequence buttonNo) {

        AlertDialog.Builder downloadDialog = new AlertDialog.Builder(act);
        downloadDialog.setTitle(title);
        downloadDialog.setMessage(message);
        downloadDialog.setPositiveButton(buttonYes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int i) {
                Uri uri = Uri.parse("market://search?q=pname:" + "com.google.zxing.client.android");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                try {
                    act.startActivity(intent);
                } catch (ActivityNotFoundException anfe) {
                }
            }
        });
        downloadDialog.setNegativeButton(buttonNo, new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        return downloadDialog.show();

    }

    //on ActivityResult method

    public void onActivityResult(int requestCode, int resultCode, Intent intent) {

        if (requestCode == 0) {
            if (resultCode == RESULT_OK) {

                //get the extras that are returned from the intent
                String contents = intent.getStringExtra("SCAN_RESULT");
                //se guarda en el string el codigo qr
                qr_id = contents;
                //se obtiene el primer caracter que indica si es un medico, medicina o ubicacion
                String digito = qr_id.substring(0, 1);
                Intent info = new Intent(Escaner.this, PagWeb.class);
                info.putExtra("qr", qr_id);
                startActivity(info);

               /* switch (digito) {
                    case "D":
                        Intent info = new Intent(Main4Activity.this, GuardarDoctor.class);
                        info.putExtra("qr", qr_id);
                        startActivity(info);
                        break;
                    case "M":
                        Intent info2 = new Intent(Main4Activity.this, GuardarMedicamento.class);
                        info2.putExtra("qr", qr_id);
                        startActivity(info2);
                        break;
                    case "H":
                        Intent infoHora = new Intent(Main4Activity.this,HoraNueva.class);
                        infoHora.putExtra("qr",qr_id);
                        startActivity(infoHora);
                        break;

                    case "U":
                        BDHelper asistente = new BDHelper(Main4Activity.this, "bdentrega", null, 1);
                        SQLiteDatabase bd = asistente.getWritableDatabase();
                        Cursor c = bd.rawQuery("SELECT COUNT(*) FROM ubicacion WHERE id='" + qr_id + "'", null);
                        c.moveToFirst();
                        int count = c.getInt(0);
                        if(count == 1) {
                            Intent info3 = new Intent(Main4Activity.this, DatosUbicacion.class);
                            info3.putExtra("qr", qr_id);
                            startActivity(info3);
                        }else {
                            Toast.makeText(this, R.string.noHayUbicacion, Toast.LENGTH_LONG).show();
                        }
                        break;

                }*/
            }
        }
    }

}
