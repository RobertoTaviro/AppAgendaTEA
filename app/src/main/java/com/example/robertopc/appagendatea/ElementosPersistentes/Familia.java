package com.example.robertopc.appagendatea.ElementosPersistentes;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by RobertoPC on 16/06/2017.
 */

public class Familia {
    private String id, nombre;
    private byte[] imagen;
    private ArrayList<Pictograma> pictogramas;

    public Familia() {
    }

    public Familia(String id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public Familia(String id, String nombre, byte[] imagen) {
        this.id = id;
        this.nombre = nombre;
        this.imagen = imagen;
    }

    public Familia(String id, String nombre, byte[] imagen, ArrayList<Pictograma> pictogramas) {
        this.id = id;
        this.nombre = nombre;
        this.imagen = imagen;
        this.pictogramas = pictogramas;
    }

    public Familia(JSONObject objetoJSON) {
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

    public ArrayList<Pictograma> getPictogramas() {
        return pictogramas;
    }

    public void addPictograma(Pictograma picto){
        pictogramas.add(picto);
    }

    public void setPictogramas(ArrayList<Pictograma> pictogramas) {
        this.pictogramas = pictogramas;
    }


}
