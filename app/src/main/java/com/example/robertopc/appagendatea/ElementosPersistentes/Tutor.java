package com.example.robertopc.appagendatea.ElementosPersistentes;

import android.graphics.Bitmap;

import java.util.StringTokenizer;

/**
 * Created by RobertoPC on 07/06/2017.
 */

public class Tutor {
    String nombre;
    String correoe;
    String contraseña;
    Bitmap imagen;
    String ruta;

    public Tutor(){
        this.nombre = "";
        this.correoe = "";
        this.contraseña = "";
        this.imagen = null;
        this.ruta = "";

    }
    public Tutor(String nombre){
        this.nombre = nombre;
        this.correoe = "";
        this.contraseña = "";
        this.imagen = null;
        this.ruta = "";

    }
    public Tutor(String nombre, String correoe, String contraseña){
        this.nombre = nombre;
        this.correoe = correoe;
        this.contraseña = contraseña;
        this.imagen = null;
        this.ruta = "";

    }

    public Tutor(String nombre, String correoe, String contraseña, Bitmap imagen, String ruta){
        this.nombre = nombre;
        this.correoe = correoe;
        this.contraseña = contraseña;
        this.imagen = imagen;
        this.ruta = ruta;

    }

    public String getNombre() {
        return nombre;
    }

    public String getCorreoe() {
        return correoe;
    }

    public String getContraseña() {
        return contraseña;
    }

    public Bitmap getImagen() {
        return imagen;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setCorreoe(String correoe) {
        this.correoe = correoe;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public void setImagen(Bitmap imagen) {
        this.imagen = imagen;
    }

    public String getRuta() {
        return ruta;
    }

    public void setRuta(String ruta) {
        this.ruta = ruta;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
