package com.example.robertopc.appagendatea;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;

public class ActivityAcceso extends AppCompatActivity implements OnClickListener {

    String IP = "http://appteatfg.esy.es";
    String GET_BY_CORREO = IP + "/obtener_tutor_por_correoe.php";
    ActivityAcceso.ObtenerWebService hiloconexion;
    EditText password, correoe;
    Button btnAccederl;


    public void registrar(View v){
        Intent intent = new Intent(ActivityAcceso.this, ActivityRegistrarTutor.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acceso);

        password = (EditText)findViewById(R.id.passwordl);
        correoe = (EditText) findViewById(R.id.correoel);
        correoe.requestFocus();
        btnAccederl = (Button)findViewById(R.id.accederl);

        btnAccederl.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.accederl:
                this.ocultarTeclado(v.getContext());
                hiloconexion = new ActivityAcceso.ObtenerWebService();
                String cadenallamada = GET_BY_CORREO + "?correoe=" + correoe.getText().toString();
                hiloconexion.execute(cadenallamada,"2",password.getText().toString()); // Parámetros que recibe doInBackground

                break;

            default:

                break;
        }
    }

    public class ObtenerWebService extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... params) {

            String cadena = params[0];
            URL url = null; // Url de donde queremos obtener información
            String devuelve = "ERROR DE CONEXIÓN";

            if (params[1] == "2") {    // consulta por correoe

                try {
                    url = new URL(cadena);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection(); //Abrir la conexión
                    connection.setRequestProperty("User-Agent", "Mozilla/5.0" +
                            " (Linux; Android 1.5; es-ES) Ejemplo HTTP");
                    //connection.setHeader("content-type", "application/json");

                    int respuesta = connection.getResponseCode();
                    StringBuilder result = new StringBuilder();

                    if (respuesta == HttpURLConnection.HTTP_OK) {


                        InputStream in = new BufferedInputStream(connection.getInputStream());  // preparo la cadena de entrada

                        BufferedReader reader = new BufferedReader(new InputStreamReader(in));  // la introduzco en un BufferedReader

                        // El siguiente proceso lo hago porque el JSONOBject necesita un String y tengo
                        // que tranformar el BufferedReader a String. Esto lo hago a traves de un
                        // StringBuilder.

                        String line;
                        while ((line = reader.readLine()) != null) {
                            result.append(line);        // Paso toda la entrada al StringBuilder
                        }

                        //Creamos un objeto JSONObject para poder acceder a los atributos (campos) del objeto.
                        JSONObject respuestaJSON = new JSONObject(result.toString());   //Creo un JSONObject a partir del StringBuilder pasado a cadena
                        //Accedemos al vector de resultados

                        String resultJSON = respuestaJSON.getString("estado");   // estado es el nombre del campo en el JSON
                        //devuelve = resultJSON;
                        if (resultJSON.equals("1")) {      // hay un alumno que mostrar
                            if (respuestaJSON.getJSONObject("tutor").getString("password").equals(params[2].toString())){
                                devuelve = "Contraseña correcta";
                            } else {
                                devuelve = "Contraseña incorrecta";
                            }

                        } else if (resultJSON.equals("2")) {
                            devuelve = "No hay tutor con este correoe";
                        }
                        devuelve = devuelve +"&"+ result.toString();

                    }
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return devuelve;


            }
            return null;
        }
        @Override
        protected void onCancelled(String s) {
            super.onCancelled(s);
        }

        @Override
        protected void onPostExecute(String s) {

            String[] separador = s.split("&");


            Toast toast = Toast.makeText(getApplicationContext(), separador[0], Toast.LENGTH_LONG);
            toast.show();
            if (separador[0].equals("Contraseña correcta")) {
                Intent intent = new Intent(ActivityAcceso.this, ActivityPrincipal.class);
                startActivity(intent.putExtra("tutor",separador[1]));
            }
            //super.onPostExecute(s);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

    }
    public void ocultarTeclado (Context c){
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager)getSystemService(c.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public void onBackPressedOnClick(View  v){
        ocultarTeclado(v.getContext());
        this.onBackPressed();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


}