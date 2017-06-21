package com.example.robertopc.appagendatea.ElementosPersistentes;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

/**
 * Created by RobertoPC on 16/06/2017.
 */

public class Familia {
    private String id, nombre, ruta;
    private Bitmap imagen;

    public Familia() {
    }

    public Familia(String id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public Familia(String id, String nombre, String ruta, Bitmap imagen) {
        this.id = id;
        this.nombre = nombre;
        this.ruta = ruta;
        this.imagen = imagen;
    }

    public Familia(JSONObject objetoJSON) {
        try {
            this.id = objetoJSON.getString("id");
            this.nombre = objetoJSON.getString("nombre");
            this.ruta = objetoJSON.getString("imagen");

            if(!ruta.equals("")){
                File imgFile = new  File("/sdcard/AppAgendaTea/"+ruta);
                if(imgFile.exists()){
                    this.imagen = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    public Bitmap getImagen() {
        return imagen;
    }

    public void setImagen(Bitmap imagen) {
        this.imagen = imagen;
    }


}
