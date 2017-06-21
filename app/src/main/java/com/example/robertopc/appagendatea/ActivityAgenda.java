package com.example.robertopc.appagendatea;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

public class ActivityAgenda extends AppCompatActivity {
    final Context context = this;

    AlertDialog adc, adm;
    String datosEntrada, datosTutor;
    CheckBox cbcolor, cbreloj;
    SharedPreferences sharprefs;
    ConstraintLayout back, lateral, canvas, top, canvas2, canvas3;
    Button menuButton;
    TextView triangle;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agenda);
        sharprefs = getSharedPreferences("Preferences",context.MODE_PRIVATE);
        SharedPreferences sharpref = getPreferences(context.MODE_PRIVATE);
        String valor = sharpref.getString("Color","Null");

        back = (ConstraintLayout)findViewById(R.id.agendaBackgroundid);
        lateral= (ConstraintLayout)findViewById(R.id.agendalateralid);
        canvas= (ConstraintLayout)findViewById(R.id.agendacanvasid);
        top= (ConstraintLayout)findViewById(R.id.agendatopid);
        canvas2= (ConstraintLayout)findViewById(R.id.agendacanvas2id);
        canvas3= (ConstraintLayout)findViewById(R.id.agendacanvas3id);
        triangle = (TextView) findViewById(R.id.agendatriangleid);








        menuButton = (Button) findViewById(R.id.agendamenuAgendaBottonID) ;
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adc = createDialogoCreden(context);
                adc.show();
            }
        });
        if (valor.equals("BlancoNegro")){
            putBlancoNegro();
        } else {
            putColor();
        }
    }


    public AlertDialog createDialogoCreden(Context act) {

        AlertDialog.Builder builder = new AlertDialog.Builder(act);

        LayoutInflater inflater = ActivityAgenda.this.getLayoutInflater();

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
                                    adc.dismiss();
                                    //wait(200);
                                    adm = createDialogoMenu(context);
                                    adm.show();
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

    public AlertDialog createDialogoMenu(Context act) {
        SharedPreferences sharpref = getPreferences(context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharpref.edit();
        String valor = sharpref.getString("Color","Null");

        AlertDialog.Builder builder = new AlertDialog.Builder(act);

        LayoutInflater inflater = ActivityAgenda.this.getLayoutInflater();

        View v = inflater.inflate(R.layout.menu_dialog_agenda, null);

        builder.setView(v);

        cbcolor = (CheckBox) v.findViewById(R.id.checkBoxBlancoNegroid);
        cbreloj = (CheckBox) v.findViewById(R.id.checkBoxRelojid);
        if (valor.equals("BlancoNegro")){
            cbcolor.setChecked(true);
        }
        cbcolor.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences sharpref = getPreferences(context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharpref.edit();
                String valor = sharpref.getString("Color","Null");
                if (isChecked){
                    if (!valor.equals("BlancoNegro")){
                        editor.putString("Color","BlancoNegro");
                        editor.commit();
                        putBlancoNegro();
                    }
                } else {
                    if (!valor.equals("Color")) {
                        editor.putString("Color", "Color");
                        editor.commit();
                        putColor();
                    }
                }
            }
        });







        return builder.create();
    }

    private void putBlancoNegro(){
        back.setBackgroundColor(Color.WHITE);
        menuButton.setTextColor(Color.WHITE);
        lateral.setBackgroundColor(Color.BLACK);
        canvas.setBackgroundColor(Color.WHITE);
        top.setBackgroundColor(Color.DKGRAY);
        canvas2.setBackgroundColor(Color.LTGRAY);
        canvas3.setBackgroundColor(Color.LTGRAY);
        triangle.setTextColor(Color.DKGRAY);
    }
    private void putColor(){
        back.setBackgroundColor(getResources().getColor(R.color.Fondo));
        menuButton.setTextColor(Color.BLACK);
        lateral.setBackgroundColor(getResources().getColor(R.color.LateralMenuColor));
        canvas.setBackgroundColor(getResources().getColor(R.color.Fondo));
        top.setBackgroundColor(getResources().getColor(R.color.Secundary));
        canvas2.setBackgroundColor(getResources().getColor(R.color.Fondo));
        canvas3.setBackgroundColor(getResources().getColor(R.color.Fondo));
        triangle.setTextColor(getResources().getColor(R.color.Secundary));
    }

}
