package com.example.robertopc.appagendatea.ElementosPersistentes;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

/**
 * Created by RobertoPC on 09/06/2017.
 */

public class Usuario {

    String id, nombre, ruta, fechaNacimiento, genero;
    Bitmap imagen;

    public Usuario() {
    }

    public Usuario(String nombre) {
        this.nombre = nombre;
    }

    public Usuario(String nombre, String fechaNacimiento, String genero) {
        this.nombre = nombre;
        this.fechaNacimiento = fechaNacimiento;
        this.genero = genero;
    }

    public Usuario(String nombre, String ruta, String fechaNacimiento, String genero, Bitmap imagen) {
        this.nombre = nombre;
        this.ruta = ruta;
        this.fechaNacimiento = fechaNacimiento;
        this.genero = genero;
        this.imagen = imagen;
    }

    public Usuario(JSONObject objetoJSON) {
        try {
            this.id = objetoJSON.getString("id");
            this.nombre = objetoJSON.getString("nombre");
            this.ruta = objetoJSON.getString("imagen");
            this.fechaNacimiento = objetoJSON.getString("fechaNacimiento");
            this.genero = objetoJSON.getString("genero");

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

    public String getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(String fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public Bitmap getImagen() {
        return imagen;
    }

    public void setImagen(Bitmap imagen) {
        this.imagen = imagen;
    }
}
