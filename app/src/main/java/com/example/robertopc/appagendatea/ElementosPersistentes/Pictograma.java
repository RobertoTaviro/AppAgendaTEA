package com.example.robertopc.appagendatea.ElementosPersistentes;

import android.graphics.Bitmap;

/**
 * Created by RobertoPC on 27/06/2017.
 */

public class Pictograma {
    private String id, nombre, ruta;
    private Bitmap imagen;

    public Pictograma() {
    }

    public Pictograma(String id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public Pictograma(String id, String nombre, String ruta, Bitmap imagen) {
        this.id = id;
        this.nombre = nombre;
        this.ruta = ruta;
        this.imagen = imagen;
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
