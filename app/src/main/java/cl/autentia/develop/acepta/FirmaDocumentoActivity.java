package cl.autentia.develop.acepta;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class FirmaDocumentoActivity extends AppCompatActivity {

    private FirmaContratoResponse fcrReturn;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_third);

        new AsyncTask<Void, Void, Void>() {

            @Override
            protected void onPreExecute() {

                progressDialog = new ProgressDialog(FirmaDocumentoActivity.this);
                progressDialog.setTitle("Firmando Documento");
                progressDialog.setMessage("Por favor, espere...");
                progressDialog.setCancelable(false);
                progressDialog.show();

            }

            @Override
            protected Void doInBackground(Void... voids) {


                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {

                fcrReturn = new FirmaContratoResponse();

                final List<FirmaContratoResponse> aFirmaDocResponseList = new FirmaContratoResponse().getFirmaContratoResponse();

                final CharSequence[] newCharSequences = fcrReturn.getCharSequenceFromList();

                AlertDialog.Builder builder = new AlertDialog.Builder(FirmaDocumentoActivity.this);
                builder.setTitle("Establecer Respuesta");
                builder.setSingleChoiceItems(newCharSequences, -1, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        for (FirmaContratoResponse fcr : aFirmaDocResponseList) {
                            if (fcr.description.equalsIgnoreCase(newCharSequences[i].toString())) {
                                fcrReturn = new FirmaContratoResponse(fcr.code, fcr.description);
                                break;
                            }
                        }
                    }
                });
                builder.setCancelable(false);
                builder.setPositiveButton("Enviar Respuesta", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        try {

                            JSONObject json = new JSONObject(String.format("{\"Code\":\"%s\",\"Description\":\"%s\"}", fcrReturn.code, fcrReturn.description));

                            progressDialog.dismiss();

                            Intent intent = new Intent();
                            intent.putExtra("result", json.toString());

                            setResult(RESULT_OK, intent);

                            finish();

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }).create().show();


                super.onPostExecute(aVoid);
            }
        }.execute();
    }


    public class FirmaContratoResponse {

        public static final int FC_EXITO = 0;
        public static final int FC_ERROR_CERTIFICADO_PUNTO_FED = 3;
        public static final int FC_ERROR_DEL_LECTOR_BIOMETRICO = 4;
        public static final int FC_ERROR_DESCARGA_DOCUMENTOS = 5;
        public static final int FC_ERROR_EN_GENERACION_DE_FIRMA = 6;
        public static final int FC_IDENTIFICACION_CON_PROBLEMAS = 7;
        public static final int FC_IDENTIFICACION_NEGATIVA = 8;
        public static final int FC_PROBLEMAS_VERIFICACION_DE_HUELLA = 9;
        public static final int FC_FALLA_DE_PUBLICACION = 10;
        public static final int FC_IDENTIFICACION_ANULADA = 11;
        public static final int FC_CONTRATO_RECHAZADO = 12;
        public static final int FC_ERROR_GENERICO = 999999999;

        public int code;
        public String description;

        public FirmaContratoResponse() {

        }

        public FirmaContratoResponse(int code, String description) {
            this.code = code;
            this.description = description;
        }

        public List<FirmaContratoResponse> getFirmaContratoResponse() {

            List<FirmaContratoResponse> responseList = new ArrayList<>();
            responseList.add(new FirmaContratoResponse(FC_EXITO, "Éxito"));
            responseList.add(new FirmaContratoResponse(FC_ERROR_CERTIFICADO_PUNTO_FED, "Error en certificado punto FED"));
            responseList.add(new FirmaContratoResponse(FC_ERROR_DEL_LECTOR_BIOMETRICO, "Error en lector biométrico"));
            responseList.add(new FirmaContratoResponse(FC_ERROR_DESCARGA_DOCUMENTOS, "Error en descarga de documentos"));
            responseList.add(new FirmaContratoResponse(FC_ERROR_EN_GENERACION_DE_FIRMA, "Error en generación de firma"));
            responseList.add(new FirmaContratoResponse(FC_IDENTIFICACION_CON_PROBLEMAS, "Identificación con problemas"));
            responseList.add(new FirmaContratoResponse(FC_IDENTIFICACION_NEGATIVA, "Identificación negativa"));
            responseList.add(new FirmaContratoResponse(FC_PROBLEMAS_VERIFICACION_DE_HUELLA, "Error al verificar huella"));
            responseList.add(new FirmaContratoResponse(FC_FALLA_DE_PUBLICACION, "Falla de publicación"));
            responseList.add(new FirmaContratoResponse(FC_IDENTIFICACION_ANULADA, "Indentificación anulada"));
            responseList.add(new FirmaContratoResponse(FC_CONTRATO_RECHAZADO, "Contrato rechazado"));
            responseList.add(new FirmaContratoResponse(FC_ERROR_GENERICO, "Error genérico, detalle:"));
            return responseList;
        }

        public CharSequence[] getCharSequenceFromList() {

            List<String> sItems = new ArrayList<String>();
            for (FirmaContratoResponse fcr : getFirmaContratoResponse()) {
                sItems.add(fcr.description);
            }
            return sItems.toArray(new CharSequence[sItems.size()]);
        }
    }
}
