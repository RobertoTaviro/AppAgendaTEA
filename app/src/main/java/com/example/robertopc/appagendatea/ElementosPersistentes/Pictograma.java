package com.example.robertopc.appagendatea.ElementosPersistentes;

import android.graphics.Bitmap;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by RobertoPC on 27/06/2017.
 */

public class Pictograma {
    private String id, nombre;
    private byte[] imagen;

    public Pictograma() {
    }

    public Pictograma(String id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public Pictograma(String id, String nombre, byte[] imagen) {
        this.id = id;
        this.nombre = nombre;
        this.imagen = imagen;
    }

    public Pictograma(JSONObject objetoJSON) {
        try {
            this.id = objetoJSON.getString("id");
            this.nombre = objetoJSON.getString("nombre");

            //TODO: cambiarlo por la petición a SQLite de una imagen

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

    public byte[] getImagen() {
        return imagen;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }


}

