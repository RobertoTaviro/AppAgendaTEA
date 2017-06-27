package com.example.robertopc.appagendatea.ElementosPersistentes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by RobertoPC on 17/06/2017.
 */

public class DataBaseManager {

    private static final String TABLE_NAME_FAMILIA = "familia";
    private static final String TABLE_NAME_AGENDA = "agendas";

    private static final String F_ID = "_id";
    private static final String F_NOMBRE = "nombre";
    private static final String F_RUTA = "ruta";

    private static final String A_ID = "_id";
    private static final String A_NOMBRE = "nombre";



    private static final String CREATE_TABLE_FAMILIA = "create table " + getTableNameFamilia()+ " ("
            + F_ID + " integer primary key autoincrement,"
            + F_NOMBRE + " text not null,"
            + F_RUTA + " text);";

    private static final String CREATE_TABLE_AGENDA = "create table " + getTableNameAgenda()+ " ("
            + A_ID + " integer primary key autoincrement,"
            + A_NOMBRE + " text not null);";



    private DbHelper helper;
    private SQLiteDatabase db;



    public DataBaseManager (Context context, String tutor_id){
        helper = new DbHelper(context, tutor_id);
        db = helper.getWritableDatabase();
    }

    public static String getTableNameFamilia() {
        return TABLE_NAME_FAMILIA;
    }

    public static String getCreateTableFamilia() {
        return CREATE_TABLE_FAMILIA;
    }

    public static String getfId() {
        return F_ID;
    }

    public static String getfNombre() {
        return F_NOMBRE;
    }

    public static String getfRuta() {
        return F_RUTA;
    }

    public DbHelper getHelper() {
        return helper;
    }

    public static String getTableNameAgenda() {
        return TABLE_NAME_AGENDA;
    }

    public static String getaId() {
        return A_ID;
    }

    public static String getaNombre() {
        return A_NOMBRE;
    }

    public static String getCreateTableAgenda() {
        return CREATE_TABLE_AGENDA;
    }

    public SQLiteDatabase getDb() {
        return db;
    }

    public ContentValues generarContentValues_familia (String nombre, String ruta){
        ContentValues valores = new ContentValues();
        valores.put(F_NOMBRE, nombre);
        valores.put(F_RUTA, ruta);
        return valores;
    }
    public Long insertar_familia (String nombre, String ruta) {
        return db.insert(TABLE_NAME_FAMILIA, null, generarContentValues_familia(nombre, ruta));
    }

    public Cursor cargarCursorFamilia(){

        String [] familias = new String[]{F_ID, F_NOMBRE, F_RUTA};
        return db.query(getTableNameFamilia(), familias, null, null, null, null, null);
    }

    public ContentValues generarContentValues_agenda (String nombre){
        ContentValues valores = new ContentValues();
        valores.put(A_NOMBRE, nombre);
        return valores;
    }

    public Long insertar_agenda (String nombre) {
        return db.insert(TABLE_NAME_AGENDA, null, generarContentValues_agenda(nombre));
    }

    public Cursor cargarCursorAgenda(){

        String [] agendas = new String[]{A_ID, A_NOMBRE};
        return db.query(getTableNameAgenda(), agendas, null, null, null, null, null);
    }

    public List<Agenda> getAgendaList(){
        List<Agenda> list = new ArrayList<>();
        Cursor c  = cargarCursorAgenda();
        while (c.moveToNext()){
            Agenda agenda = new Agenda();
            agenda.setId(c.getString(0));
            agenda.setNombre(c.getString(1));
            list.add(agenda);
        }


        return list;
    }

    //TODO: crear las tablas de las nuevas clases, tarea y pictograma. Crear el layout de editar agenda. enlazar las familias y los picto. let's go
















}
