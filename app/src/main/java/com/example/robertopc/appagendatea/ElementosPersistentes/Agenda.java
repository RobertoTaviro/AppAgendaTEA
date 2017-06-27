package com.example.robertopc.appagendatea.ElementosPersistentes;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by RobertoPC on 24/06/2017.
 */

public class Agenda {
    private String id, nombre;
    private String [] dias = {"Lunes","Martes","Miércoles","Jueves","Viernes","Sábado","Domingo"};
    private ArrayList<Tarea> tareas;

    public Agenda() {
    }

    public Agenda(String id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public Agenda(String id, String nombre, ArrayList<Tarea> tareas) {
        this.id = id;
        this.nombre = nombre;
        this.tareas = tareas;
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

    public ArrayList<Tarea> getTareas() {
        return tareas;
    }

    public void setTareas(ArrayList<Tarea> tareas) {
        this.tareas = tareas;
    }

    public void insertarTarea(Tarea tarea){
        this.tareas.add(tarea);
    }

    public Tarea getTarea(int position){
        return this.tareas.get(position);
    }
}
