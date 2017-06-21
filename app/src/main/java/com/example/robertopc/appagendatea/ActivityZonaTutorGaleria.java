package com.example.robertopc.appagendatea;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.robertopc.appagendatea.ElementosPersistentes.DataBaseManager;
import com.example.robertopc.appagendatea.ElementosPersistentes.DbHelper;
import com.example.robertopc.appagendatea.ElementosPersistentes.Familia;
import com.example.robertopc.appagendatea.ElementosPersistentes.Usuario;
import com.example.robertopc.appagendatea.Utils.RVAdapterFamilia;
import com.example.robertopc.appagendatea.Utils.RVAdapterUsuario;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


public class ActivityZonaTutorGaleria extends AppCompatActivity {

    String IP = "http://appteatfg.esy.es";
    String INSERT = IP + "/insertar_familia.php";
    ActivityZonaTutorGaleria.ObtenerWebService hiloconexion;
    ImageView imageviewfamilia;
    String datosEntrada, datosTutor, pimagen = "", pid;
    private NavigationView navView;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    RecyclerView rvf;
    ArrayList<Familia> familias;
    DataBaseManager db;
    AlertDialog ad;
    Cursor cursor;
    SimpleCursorAdapter adapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zona_tutor_galeria);
        familias = new ArrayList<Familia>();

        Intent intent = this.getIntent();
        if (intent == null){
            Log.d("Tag", "La actividad no se ha llamado mediante un intent.");
        } else{
            datosEntrada = getIntent().getStringExtra("tutor");
            try {
                JSONObject jdatosentrada = new JSONObject(datosEntrada);
                datosTutor = jdatosentrada.getString("tutor");
                JSONObject jdatostutor = new JSONObject(datosTutor);
                pid = jdatostutor.getString("id");

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        db = new DataBaseManager(this, pid);

        navView = (NavigationView)findViewById(R.id.navigation_view_galeria);
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
                        Intent intentperfil = new Intent(ActivityZonaTutorGaleria.this, ActivityZonaTutor.class);
                        startActivity(intentperfil.putExtra("tutor", getIntent().getStringExtra("tutor")));
                        return true;
                    case R.id.galeriaid:
                       return true;
                    case R.id.listaagendasid:
                        Intent intentagenda = new Intent(ActivityZonaTutorGaleria.this, ActivityZonaTutorAgenda.class);
                        startActivity(intentagenda.putExtra("tutor", getIntent().getStringExtra("tutor")));
                        return true;
                }
                return true;
            }
        });

        rvf = (RecyclerView)findViewById(R.id.rvZTgid);
        LinearLayoutManager llm = new LinearLayoutManager(this.getApplicationContext());
        rvf.setLayoutManager(llm);
        RVAdapterFamilia adapter = new RVAdapterFamilia(familias);
        rvf.setAdapter(adapter);
        generateFamilyList();


        String[] from = new String[]{db.getfId(),db.getfNombre(), db.getfRuta()};
        int[] to = new int[]{android.R.id.text1, android.R.id.text2};

        cursor = db.cargarCursorFamilia();
        //TODO: aqui
        //adapter = new RVAdapterFamilia(this,, cursor, from, to);

    }

    public void refresh(View v){
        generateFamilyList();
    }

    public void generateFamilyList(){
        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
        rvf.setLayoutManager(llm);
        RVAdapterFamilia adapter = new RVAdapterFamilia(familias);
        rvf.setAdapter(adapter);
    }


    public void nuevaFamilia(View view){

        ad = createDialogoNewFamily(view.getContext());
        ad.show();

    }

    public AlertDialog createDialogoNewFamily(Context act) {

        AlertDialog.Builder builder = new AlertDialog.Builder(act);

        LayoutInflater inflater = ActivityZonaTutorGaleria.this.getLayoutInflater();

        View v = inflater.inflate(R.layout.nueva_familia, null);

        builder.setView(v);

        Button btnCrearF = (Button) v.findViewById(R.id.crearf);
        Button btnCameraF = (Button) v.findViewById(R.id.addphotocameraf);
        Button btnMemortF = (Button) v.findViewById(R.id.addphotofrommemoryf);
        imageviewfamilia = (ImageView) v.findViewById(R.id.imageviewf);
        final EditText nombref = (EditText)v.findViewById(R.id.nombrefamiliaf);

        btnCameraF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();

            }

        });

        btnCrearF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ocultarTeclado(v.getContext());
                Boolean bool = true;
                if(nombref.getText().toString().isEmpty()){
                    nombref.setError("Rellene el campo");
                    bool=false;
                }
                if (bool){
                    /**hiloconexion = new ActivityZonaTutorGaleria.ObtenerWebService();
                    hiloconexion.execute(INSERT,"3",nombref.getText().toString(),pimagen);*/

                    if (db.insertar_familia(nombref.getText().toString(), pimagen)==-1){
                        Toast toast = Toast.makeText(getApplicationContext(), "No se ha insertado la familia, revisar los campos", Toast.LENGTH_SHORT);
                        toast.show();
                    }
                    else{
                        ad.dismiss();
                    }


                }
            }
        });

        return builder.create();
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
        return "Familia_"+timestamp+".png";
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Toast.makeText(ActivityZonaTutorGaleria.this, "result code bien", Toast.LENGTH_LONG);
            Bundle extras = data.getExtras();
            Bitmap cameraImage = (Bitmap) extras.get("data");
            imageviewfamilia.setImageBitmap(cameraImage); //cuidado null exception
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
                    jsonParam.put("imagen", params[3]);
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
                            devuelve = "Familia creada correctamente";

                        } else if (resultJSON == "2") {
                            devuelve = "La Familia no pudo crearse";
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
        Intent intent = new Intent(ActivityZonaTutorGaleria.this, ActivityPrincipal.class);
        startActivity(intent.putExtra("tutor", getIntent().getStringExtra("tutor")));
        finish();
    }

}
