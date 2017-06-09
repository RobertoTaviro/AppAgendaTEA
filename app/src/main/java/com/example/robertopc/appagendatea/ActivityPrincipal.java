package com.example.robertopc.appagendatea;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.robertopc.appagendatea.ElementosPersistentes.Usuario;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;


public class ActivityPrincipal extends AppCompatActivity {

    final Context context = this;
    FrameLayout frameCreden;
    Button menuButton;
    ImageButton iBtnAgenda;
    ConstraintLayout tablero, panelCred;
    FrameLayout panelCred1;
    String datosEntrada, datosTutor;
    List<Usuario> usuarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        tablero = (ConstraintLayout)findViewById(R.id.tablero);

        iBtnAgenda = (ImageButton) findViewById(R.id.agendaButton);
        menuButton = (Button)findViewById(R.id.menuButtonId);
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog ad = createDialogoCreden(context);
                ad.show();
            }
        });
    }


    int duration = Toast.LENGTH_SHORT;



    public AlertDialog createDialogoCreden(Context act) {

        AlertDialog.Builder builder = new AlertDialog.Builder(act);

        LayoutInflater inflater = ActivityPrincipal.this.getLayoutInflater();

        View v = inflater.inflate(R.layout.credenciales_zona_tutor, null);

        builder.setView(v);

        Button signin = (Button) v.findViewById(R.id.entrar_boton);
        final TextView textCreden = (TextView) v.findViewById(R.id.contrasena_input);
        final Intent currentIntent = this.getIntent();

        signin.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (currentIntent == null){
                            Log.d("Tag", "La actividad no se ha llamado mediante un intent.");
                        } else {
                            datosEntrada = getIntent().getStringExtra("tutor");
                            try {
                                JSONObject jdatosentrada = new JSONObject(datosEntrada);
                                datosTutor = jdatosentrada.getString("tutor");
                                JSONObject jdatostutor = new JSONObject(datosTutor);
                                if (textCreden.getText().toString().equals(jdatostutor.getString("password"))){
                                    Toast toast = Toast.makeText(getApplicationContext(), "Contraseña correcta", Toast.LENGTH_SHORT);
                                    toast.show();
                                    Intent intent = new Intent(ActivityPrincipal.this, ActivityZonaTutor.class);
                                    intent.putExtra("tutor", getIntent().getStringExtra("tutor"));
                                    startActivity(intent);
                                } else {
                                    Toast toast = Toast.makeText(getApplicationContext(), "Contraseña incorrecta, intentelo de nuevo", Toast.LENGTH_LONG);
                                    toast.show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    }
                }

        );

        return builder.create();
    }


    public void goAgenda(View v){
        Intent intent = new Intent(ActivityPrincipal.this, ActivityAgenda.class);
        startActivity(intent);
    }

    public void goAyuda(View v){
        Intent intent = new Intent(ActivityPrincipal.this, ActivityAyuda.class);
        startActivity(intent);
    }

}
