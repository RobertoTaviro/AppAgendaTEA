package com.example.robertopc.appagendatea;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.robertopc.appagendatea.ElementosPersistentes.Tutor;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;


public class ActivityRegistrarTutor extends AppCompatActivity implements OnClickListener{

    String IP = "http://appteatfg.esy.es";
    String INSERT = IP + "/insertar_tutor.php";
    ActivityRegistrarTutor.ObtenerWebService hiloconexion;
    EditText username, password, correoe;
    Button btnRegistrar, btnMemory, btnCamera;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    ImageView imagentutor;
    String pnombre, ppass, pcorreo, pimagen = "";

    Tutor tutor;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_tutor);

        hiloconexion = new ActivityRegistrarTutor.ObtenerWebService();
        username = (EditText)findViewById(R.id.usernamer);
        password = (EditText)findViewById(R.id.passwordr);
        correoe = (EditText) findViewById(R.id.correoelectronicor);
        btnRegistrar = (Button)findViewById(R.id.registrarr);

        btnRegistrar.setOnClickListener(this);
        imagentutor = (ImageView)findViewById(R.id.imagenTutorr);

        btnCamera = (Button) findViewById(R.id.fromCamerar);
        btnCamera.setOnClickListener(this);
        btnMemory = (Button) findViewById(R.id.fromMemoryr);
        btnMemory.setOnClickListener(this);




    }

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        switch (v.getId()){
            case R.id.registrarr:
                username = (EditText)findViewById(R.id.usernamer);
                password = (EditText)findViewById(R.id.passwordr);
                correoe = (EditText) findViewById(R.id.correoelectronicor);
                pnombre = username.getText().toString();
                pcorreo = correoe.getText().toString();
                ppass = password.getText().toString();
                hiloconexion.execute(INSERT,"3",pnombre, pcorreo, ppass, pimagen); // Parámetros que recibe doInBackground


                break;
            case R.id.fromCamerar:

                dispatchTakePictureIntent();

                break;
            case  R.id.fromMemoryr:

                break;

            default:

                break;
        }
    }



    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    private String getPictureName(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String timestamp = sdf.format(new Date());
        return "Tutor_"+username.getText().toString()+"_"+timestamp+".png";
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Toast.makeText(ActivityRegistrarTutor.this, "result code bien", Toast.LENGTH_LONG);
            Bundle extras = data.getExtras();
            Bitmap cameraImage = (Bitmap) extras.get("data");
            imagentutor.setImageBitmap(cameraImage);
            FileOutputStream out = null;
            try {
                File pictureDirectory = Environment.getExternalStoragePublicDirectory("AppAgendaTea");
                String pictureName = getPictureName();
                pimagen = pictureName;
                File imageFile = new File(pictureDirectory, pictureName);
                out = new FileOutputStream(imageFile);
                cameraImage.compress(Bitmap.CompressFormat.PNG, 100, out);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (out != null) {
                        out.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public class ObtenerWebService extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... params) {

            String cadena = params[0];
            URL url = null; // Url de donde queremos obtener información
            String devuelve = "Fallo, no se ha insertado";


            if (params[1] == "3") {    // insert

                try {
                    HttpURLConnection urlConn;

                    DataOutputStream printout;
                    DataInputStream input;
                    url = new URL(cadena);
                    urlConn = (HttpURLConnection) url.openConnection();
                    urlConn.setDoInput(true);
                    urlConn.setDoOutput(true);
                    urlConn.setUseCaches(false);
                    urlConn.setRequestProperty("Content-Type", "application/json");
                    urlConn.setRequestProperty("Accept", "application/json");
                    urlConn.connect();
                    //Creo el Objeto JSON
                    JSONObject jsonParam = new JSONObject();
                    jsonParam.put("username", params[2]);
                    jsonParam.put("imagen", params[5]);
                    jsonParam.put("correoe", params[3]);
                    jsonParam.put("password", params[4]);
                    // Envio los parámetros post.
                    OutputStream os = urlConn.getOutputStream();
                    BufferedWriter writer = new BufferedWriter(
                            new OutputStreamWriter(os, "UTF-8"));
                    writer.write(jsonParam.toString());
                    writer.flush();
                    writer.close();

                    int respuesta = urlConn.getResponseCode();


                    StringBuilder result = new StringBuilder();

                    if (respuesta == HttpURLConnection.HTTP_OK) {

                        String line;
                        BufferedReader br = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
                        while ((line = br.readLine()) != null) {
                            result.append(line);
                            //response+=line;
                        }
                        devuelve = result.toString();

                        //Creamos un objeto JSONObject para poder acceder a los atributos (campos) del objeto.
                        JSONObject respuestaJSON = new JSONObject(result.toString());   //Creo un JSONObject a partir del StringBuilder pasado a cadena
                        //Accedemos al vector de resultados

                        String resultJSON = respuestaJSON.getString("estado");   // estado es el nombre del campo en el JSON

                        if (resultJSON == "1") {      // hay un alumno que mostrar
                            devuelve = "tutor insertado correctamente";

                        } else if (resultJSON == "2") {
                            devuelve = "El tutor no pudo insertarse";
                        }

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
            Toast toast = Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG);
            toast.show();
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