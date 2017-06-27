package com.example.robertopc.appagendatea.ElementosPersistentes;

import java.util.ArrayList;

/**
 * Created by RobertoPC on 27/06/2017.
 */

public class Tarea {

    private String id, nombre, dia;
    private ArrayList<Pictograma> glosario;


    public Tarea(String id, String nombre, String dia, ArrayList<Pictograma> glosario) {
        this.id = id;
        this.nombre = nombre;
        this.dia = dia;
        this.glosario = glosario;
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


    public void insertarPictograma (Pictograma pictograma){
        this.glosario.add(pictograma);
    }

    public Pictograma getPictograma(int position){
        return this.glosario.get(position);
    }


}
