package com.example.robertopc.appagendatea;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.robertopc.appagendatea.ElementosPersistentes.Agenda;
import com.example.robertopc.appagendatea.ElementosPersistentes.DataBaseManager;
import com.example.robertopc.appagendatea.ElementosPersistentes.Usuario;
import com.example.robertopc.appagendatea.Utils.OnAgendaListener;
import com.example.robertopc.appagendatea.Utils.RVAdapterAgenda;
import com.example.robertopc.appagendatea.Utils.RVAdapterUsuario;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ActivityZonaTutorAgenda extends AppCompatActivity {
    private NavigationView navView;
    AlertDialog ad;
    DataBaseManager db;
    String datosEntrada, datosTutor, pid = "";
    RecyclerView rv;
    List<Agenda> agendas;
    private Context context = this;
    OnAgendaListener onAgendaListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zona_tutor_agenda);

        onAgendaListener = new OnAgendaListener() {
            @Override
            public void onFondoClicked(int position) {
                Toast.makeText(ActivityZonaTutorAgenda.this,"Fondo - Posicion: "+position,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onEditClicket(int position) {
                Toast.makeText(ActivityZonaTutorAgenda.this,"Edit - Posicion: "+position,Toast.LENGTH_SHORT).show();
                Intent intentedit = new Intent(ActivityZonaTutorAgenda.this, ActivityEditarAgenda.class);
                startActivity(intentedit.putExtra("tutor", getIntent().getStringExtra("tutor")));
            }
        };

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.floatingActionButtonagendaid);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ad = createDialogoNewUser(view.getContext());
                ad.show();
            }
        });

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

        navView = (NavigationView)findViewById(R.id.navigation_view_agenda);
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
                        Intent intentperfil = new Intent(ActivityZonaTutorAgenda.this, ActivityZonaTutor.class);
                        startActivity(intentperfil.putExtra("tutor", getIntent().getStringExtra("tutor")));
                        return true;
                    case R.id.galeriaid:
                        Intent intentgaleria = new Intent(ActivityZonaTutorAgenda.this, ActivityZonaTutorGaleria.class);
                        startActivity(intentgaleria.putExtra("tutor", getIntent().getStringExtra("tutor")));
                        return true;
                    case R.id.listaagendasid:
                        return true;
                }
                return true;
            }
        });

        agendas = db.getAgendaList();

        rv = (RecyclerView)findViewById(R.id.recViewAgendasID);
        LinearLayoutManager llm = new LinearLayoutManager(this.getApplicationContext());
        rv.setLayoutManager(llm);
        RVAdapterAgenda adapter = new RVAdapterAgenda(agendas, context, onAgendaListener);
        rv.setAdapter(adapter);

        generateUserList();


    }

    public void refresh(View v){
        generateUserList();
    }

    public void generateUserList(){
        LinearLayoutManager llm = new LinearLayoutManager(getApplicationContext());
        rv.setLayoutManager(llm);
        RVAdapterAgenda adapter = new RVAdapterAgenda(agendas, context, onAgendaListener);
        rv.setAdapter(adapter);
    }


    public AlertDialog createDialogoNewUser(Context act) {

        AlertDialog.Builder builder = new AlertDialog.Builder(act);

        LayoutInflater inflater = ActivityZonaTutorAgenda.this.getLayoutInflater();

        View v = inflater.inflate(R.layout.nueva_agenda, null);

        builder.setView(v);

        Button crearAgenda = (Button)v.findViewById(R.id.buttonCrearNuevaAgendaid);
        final EditText nombre = (EditText) v.findViewById(R.id.edittextNuevaAgenda);

        crearAgenda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (db.insertar_agenda(nombre.getText().toString())==-1){
                    Toast toast = Toast.makeText(getApplicationContext(), "No se ha insertado la agenda, revisar los campos", Toast.LENGTH_SHORT);
                    toast.show();
                }
                else{
                    Toast toast = Toast.makeText(getApplicationContext(), "Agenda añadida correctamente", Toast.LENGTH_SHORT);
                    toast.show();
                    ad.dismiss();
                }

            }
        });




        return builder.create();
    }



}
