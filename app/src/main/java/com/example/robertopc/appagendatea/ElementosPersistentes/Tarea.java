package com.example.robertopc.appagendatea.ElementosPersistentes;

import android.graphics.Bitmap;

import java.util.ArrayList;

/**
 * Created by RobertoPC on 27/06/2017.
 */

public class Tarea {

    private String id, nombre, dia;
    private byte[] imagen;
    private ArrayList<Pictograma> glosario;


    public Tarea(String id, String nombre, String dia,  byte[] imagen, ArrayList<Pictograma> glosario) {
        this.id = id;
        this.nombre = nombre;
        this.dia = dia;
        this.glosario = glosario;
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

    public ArrayList<Pictograma> getGlosario() {
        return glosario;
    }

    public void setGlosario(ArrayList<Pictograma> glosario) {
        this.glosario = glosario;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public byte[] getImagen() {
        return imagen;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }

    public void insertarPictograma (Pictograma pictograma){
        this.glosario.add(pictograma);
    }

    public Pictograma getPictograma(int position){
        return this.glosario.get(position);
    }


}
