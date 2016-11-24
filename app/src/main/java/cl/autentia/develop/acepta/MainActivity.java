package cl.autentia.develop.acepta;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_FIRMAR_DOCUMENTO = 10001;
    private static final int REQUEST_CODE_DESCARGA_DOCUMENTO = 10003;

    Button second, third;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        second = (Button) findViewById(R.id.sec_but);
        third = (Button) findViewById(R.id.thi_but);

        second.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent("cl.autentia.entel.descargadocumento"),REQUEST_CODE_DESCARGA_DOCUMENTO);
            }
        });

        third.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivityForResult(new Intent("cl.autentia.entel.firmadocumento"), REQUEST_CODE_FIRMAR_DOCUMENTO);
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Bundle bundle = data.getExtras();

        if (requestCode == REQUEST_CODE_FIRMAR_DOCUMENTO) {
            if (resultCode == RESULT_OK) {

                if (bundle == null) {
                    new AlertDialog.Builder(this).setMessage("Respuesta de Firmar Documento vacía ").setPositiveButton("Aceptar", null).create().show();
                    return;
                }

                new AlertDialog.Builder(this).setMessage(bundle.getString("result")).setPositiveButton("Aceptar", null).create().show();
            } else {
                new AlertDialog.Builder(this).setMessage("Error en la llamada Firmar Documento").setPositiveButton("Aceptar", null).create().show();
            }
        }

        if (requestCode == REQUEST_CODE_DESCARGA_DOCUMENTO) {
            if (resultCode == RESULT_OK) {

                if (bundle == null) {
                    new AlertDialog.Builder(this).setMessage("Sin datos ").setPositiveButton("Aceptar", null).create().show();
                    return;
                }

                new AlertDialog.Builder(this).setMessage(bundle.getString("result")).setPositiveButton("Aceptar", null).create().show();
            } else {
                new AlertDialog.Builder(this).setMessage(bundle.getString("result")).setPositiveButton("Aceptar", null).create().show();
            }
        }
    }
}

