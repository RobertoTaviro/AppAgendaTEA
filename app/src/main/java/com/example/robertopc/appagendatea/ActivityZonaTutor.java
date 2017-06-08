package com.example.robertopc.appagendatea;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.StringTokenizer;

public class ActivityZonaTutor extends AppCompatActivity{

    String IP = "http://appteatfg.esy.es";
    String INSERT = IP + "/insertar_usuario.php";
    ActivityZonaTutor.ObtenerWebService hiloconexion;
    TextView nombretutorz, correoetutorz;
    String datosEntrada, datosTutor, datosRutaImagen, pimagen = "";
    ImageView fotoPerfilTutor, fotonuevousuario;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private NavigationView navView;
    ImageView imageViewu;
    RecyclerView rvZTu;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zona_tutor);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog ad = createDialogoNewUser(view.getContext());
                ad.show();
            }
        });

        nombretutorz = (TextView)findViewById(R.id.nombretutorz);
        correoetutorz = (TextView)findViewById(R.id.correoetutorz);
        fotoPerfilTutor = (ImageView) findViewById(R.id.fotoPerfilTutor);
        Intent intent = this.getIntent();
        if (intent == null){
            Log.d("Tag", "La actividad no se ha llamado mediante un intent.");
        } else{
            datosEntrada = getIntent().getStringExtra("tutor");
            try {
                JSONObject jdatosentrada = new JSONObject(datosEntrada);
                datosTutor = jdatosentrada.getString("tutor");
                JSONObject jdatostutor = new JSONObject(datosTutor);
                nombretutorz.setText(jdatostutor.getString("username"));
                correoetutorz.setText(jdatostutor.getString("correoe"));
                datosRutaImagen = jdatostutor.getString("imagen");
                if(!datosRutaImagen.equals("")){
                    File imgFile = new  File("/sdcard/AppAgendaTea/"+datosRutaImagen);
                    if(imgFile.exists()){
                        Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                        fotoPerfilTutor.setImageBitmap(myBitmap);
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        navView = (NavigationView)findViewById(R.id.navigation_view_perfil);
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener(){
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                //Checking if the item is in checked state or not, if not make it in checked state
                if (menuItem.isChecked()) menuItem.setChecked(false);
                else menuItem.setChecked(true);

                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()) {
                    //Replacing the main content with ContentFragment Which is our Inbox View;
                    case R.id.perfilid:
                        return true;
                    case R.id.galeriaid:
                        Intent intentgaleria = new Intent(ActivityZonaTutor.this, ActivityZonaTutorGaleria.class);
                        startActivity(intentgaleria.putExtra("tutor", getIntent().getStringExtra("tutor")));
                        return true;
                    case R.id.listaagendasid:
                        Intent intentagenda = new Intent(ActivityZonaTutor.this, ActivityZonaTutorAgenda.class);
                        startActivity(intentagenda.putExtra("tutor", getIntent().getStringExtra("tutor")));
                        return true;
                }
                return true;
            }
        });

        RecyclerView rv = (RecyclerView)findViewById(R.id.rvZTid);
    }


    public AlertDialog createDialogoNewUser(Context act) {

        AlertDialog.Builder builder = new AlertDialog.Builder(act);

        LayoutInflater inflater = ActivityZonaTutor.this.getLayoutInflater();

        View v = inflater.inflate(R.layout.nuevo_usuario, null);

        builder.setView(v);



        Button btnCrearU = (Button) v.findViewById(R.id.crearu);
        Button btnCameraU = (Button) v.findViewById(R.id.addphotocamerau);
        Button btnMemortU = (Button) v.findViewById(R.id.addphotomemoryu);

        final EditText nombreu = (EditText)v.findViewById(R.id.usuariou);
        final EditText fechanacu = (EditText)v.findViewById(R.id.fechanacimientou);
        final Spinner genero = (Spinner)v.findViewById(R.id.spinneru);
        imageViewu = (ImageView) v.findViewById(R.id.imageviewu);

        btnCameraU.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent(nombreu.getText().toString());

            }

        });


        btnCrearU.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ocultarTeclado(v.getContext());
                Boolean bool = true;
               if(fechanacu.getText().toString().isEmpty()){
                   fechanacu.setError("Rellene el campo");
                   bool=false;
               }
               if(nombreu.getText().toString().isEmpty()) {
                    nombreu.setError("Rellene el campo");
                   bool=false;
                }
                if(fechanacu.getText().toString().length()!=10){
                   fechanacu.setError("Comprueba la estructura.");
                   bool=false;
               }

                if (bool){
                    hiloconexion = new ActivityZonaTutor.ObtenerWebService();
                    hiloconexion.execute(INSERT,"3",nombreu.getText().toString(),fechanacu.getText().toString(),genero.getSelectedItem().toString(),pimagen);

                }





            }
        });

        return builder.create();
    }



    private void dispatchTakePictureIntent(String nombreu) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }

    private String getPictureName(){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_HHmmss");
        String timestamp = sdf.format(new Date());
        return "Usuario_"+timestamp+".png";
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Toast.makeText(ActivityZonaTutor.this, "result code bien", Toast.LENGTH_LONG);
            Bundle extras = data.getExtras();
            Bitmap cameraImage = (Bitmap) extras.get("data");
            imageViewu.setImageBitmap(cameraImage); //cuidado null exception
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
            String devuelve = "Fallo, no se ha creado";


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
                    jsonParam.put("nombre", params[2]);
                    jsonParam.put("imagen", params[5]);
                    jsonParam.put("fechaNacimiento", params[3]);
                    jsonParam.put("genero", params[4]);
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
                            devuelve = "usuario creado correctamente";

                        } else if (resultJSON == "2") {
                            devuelve = "El usuario no pudo crearse";
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
        Intent intent = new Intent(ActivityZonaTutor.this, ActivityPrincipal.class);
        startActivity(intent.putExtra("tutor", getIntent().getStringExtra("tutor")));
        finish();
    }
}
