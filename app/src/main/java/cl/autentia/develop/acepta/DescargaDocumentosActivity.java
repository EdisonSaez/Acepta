package cl.autentia.develop.acepta;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;


import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Timer;
import java.util.TimerTask;

import static android.R.attr.button;


public class DescargaDocumentosActivity extends AppCompatActivity {
    private ProgressDialog progress;
    private String KEY_EXITO = "0";
    private String DESC_EXITO = "MD_EXITO";
    private String KEY_ERROR = "1";
    private String DESC_ERROR= "MD_ERROR_DESCARGA_DOCUMENTOS";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        descarga();
    }

    public void descarga() {
        progress = new ProgressDialog(this);
        progress.setMessage("Descargando Pdf...");
        progress.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progress.setIndeterminate(false);
        progress.setProgress(0);
        progress.show();

        final int totalProgressTime = 100;
        final Thread t = new Thread() {
            @Override
            public void run() {
                int jumpTime = 0;

                while (jumpTime < totalProgressTime) {
                    try {
                        sleep(200);
                        jumpTime += 5;
                        progress.setProgress(jumpTime);
                        if (jumpTime == 100) {
                            progress.dismiss();
                            createHandler();
                        }
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        };
        t.start();
    }

    private void createHandler() {
        Thread thread = new Thread() {
            public void run() {
                Looper.prepare();

                final Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        open();
                    }
                }, 100);

                Looper.loop();
            }
        };
        thread.start();
    }


    public void open() {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setMessage("Que resultado quiere enviar");
        alertDialogBuilder.setPositiveButton("Enviar exito",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        try {

                            JSONObject json = new JSONObject(String.format("{\"Erc\":\"%s\",\"ErcText\":\"%s\"}", KEY_EXITO , DESC_EXITO));

                            Intent intent = new Intent();
                            intent.putExtra("result", json.toString());

                            setResult(RESULT_OK, intent);

                            finish();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

        alertDialogBuilder.setNegativeButton("Enviar error", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {

                    JSONObject json = new JSONObject(String.format("{\"Erc\":\"%s\",\"ErcText\":\"%s\"}", KEY_ERROR, DESC_ERROR));

                    Intent intent = new Intent();
                    intent.putExtra("result", json.toString());

                    setResult(RESULT_CANCELED, intent);

                    finish();

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
}
