package com.example.robertopc.appagendatea.ElementosPersistentes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by RobertoPC on 17/06/2017.
 */

public class DataBaseManager {

    private static final String TABLE_NAME_FAMILIA = "familia";
    private static final String TABLE_NAME_AGENDA = "agendas";
    private static final String TABLE_NAME_TAREA = "tarea";
    private static final String TABLE_NAME_PICTOGRAMA = "pictograma";

    private static final String F_ID = "_id";
    private static final String F_NOMBRE = "nombre";
    private static final String F_IMAGEN = "imagen";
    private static final String F_PICTOS = "pictogramas";
    //JSONOBJECT  pictogramas = {"pictogramas":[{"id":"id1","nombre":"nombre1","imagen":"imagen1"},{"id":"id12","nombre":"nombre2","imagen":"imagen2"}]}

    private static final String A_ID = "_id";
    private static final String A_NOMBRE = "nombre";
    private static final String A_TAREAS = "tareas";
//JSONOBJECT  tareas = {"tareas":[{"id":"id1","nombre":"nombre1","dia":"Lunes","glosario":"","imagen":"imagen1"}]}

    private static final String T_ID = "_id";
    private static final String T_NOMBRE = "nombre";
    private static final String T_DIA = "dia";
    private static final String T_GLOSARIO = "glosario";
    private static final String T_IMAGEN = "imagen";

    private static final String P_ID = "_id";
    private static final String P_NOMBRE = "nombre";
    private static final String P_IMAGEN = "imagen";



    private static final String CREATE_TABLE_FAMILIA = "create table " + getTableNameFamilia()+ " ("
            + F_ID + " integer primary key autoincrement,"
            + F_NOMBRE + " text not null,"
            + F_IMAGEN +" BLOB,"
            + F_PICTOS + " text);";

    private static final String CREATE_TABLE_AGENDA = "create table " + getTableNameAgenda()+ " ("
            + A_ID + " integer primary key autoincrement,"
            + A_NOMBRE + " text not null,"
            + A_TAREAS + " text);";

    private static final String CREATE_TABLE_TAREA = "create table " + getTableNameTarea()+ " ("
            + T_ID + " integer primary key autoincrement,"
            + T_NOMBRE + " text not null,"
            + T_DIA + " text,"
            + T_GLOSARIO + " text,"
            + T_IMAGEN +" BLOB);";

    private static final String CREATE_TABLE_PICTOGRAMA = "create table " + getTableNamePictograma()+ " ("
            + P_ID + " integer primary key autoincrement,"
            + P_NOMBRE + " text not null,"
            + P_IMAGEN +" BLOB);";



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

    public static String getfImagen() {
        return F_IMAGEN;
    }

    public static String getTableNameTarea() {
        return TABLE_NAME_TAREA;
    }

    public static String getTableNamePictograma() {
        return TABLE_NAME_PICTOGRAMA;
    }

    public static String gettId() {
        return T_ID;
    }

    public static String gettNombre() {
        return T_NOMBRE;
    }

    public static String gettImagen() {
        return T_IMAGEN;
    }

    public static String gettDia() {
        return T_DIA;
    }

    public static String gettGlosario() {
        return T_GLOSARIO;
    }

    public static String getpId() {
        return P_ID;
    }

    public static String getpNombre() {
        return P_NOMBRE;
    }

    public static String getCreateTableTarea() {
        return CREATE_TABLE_TAREA;
    }

    public static String getpImagen() {
        return P_IMAGEN;
    }

    public static String getCreateTablePictograma() {
        return CREATE_TABLE_PICTOGRAMA;
    }

    public static String getfPictos() {
        return F_PICTOS;
    }

    public static String getaTareas() {
        return A_TAREAS;
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



    public ContentValues generarContentValues_familia (String nombre, byte[] imagen, String pictogramas){
        ContentValues valores = new ContentValues();
        valores.put(F_NOMBRE, nombre);
        valores.put(F_IMAGEN, imagen);
        valores.put(F_PICTOS, pictogramas);
        return valores;
    }
    public Long insertar_familia (String nombre, byte[] imagen, String pictogramas) {
        return db.insert(TABLE_NAME_FAMILIA, null, generarContentValues_familia(nombre, imagen, pictogramas));
    }

    public Cursor cargarCursorFamilia(){

        String [] familias = new String[]{F_ID, F_NOMBRE, F_IMAGEN, F_PICTOS};
        return db.query(getTableNameFamilia(), familias, null, null, null, null, null);
    }

    public List<Familia> getFamiliasList(){
        List<Familia> list = new ArrayList<>();
        Cursor c  = cargarCursorFamilia();
        while (c.moveToNext()){
            Familia familia = new Familia();
            familia.setId(c.getString(0));
            familia.setNombre(c.getString(1));
            familia.setImagen(c.getBlob(2));
            familia.setPictogramas(getArrayListPictosFromJSONString(c.getString(3)));
            list.add(familia);
        }
        return list;
    }

    public ArrayList<Pictograma> getArrayListPictosFromJSONString (String string){
        JSONObject respuestaJSON = null;
        ArrayList<Pictograma> pictos = new ArrayList<>();
        try {
            respuestaJSON = new JSONObject(string);
            JSONArray usuarioJSON = respuestaJSON.getJSONArray("pictogramas");
            for(int i=0;i<usuarioJSON.length();i++){
                Pictograma picto = new Pictograma();
                picto.setId(usuarioJSON.getJSONObject(i).getString("id"));
                picto.setNombre(usuarioJSON.getJSONObject(i).getString("nombre"));
                picto.setImagen(usuarioJSON.getJSONObject(i).getString("imagen").getBytes());
                pictos.add(picto);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return pictos;
    }

    public List<Pictograma> getPictoFromFamiliaPos(int pos){
        Cursor c  = cargarCursorFamilia();
        while (c.moveToPosition(pos)){
            return getArrayListPictosFromJSONString(c.getString(3));
        }
        return null;
    }

    public void añadirPictogramaAFamilia(int pos_fam, String nuevo_pictograma){
        Cursor c = cargarCursorFamilia();
        c.moveToPosition(pos_fam);
        String incomplete_picto = c.getString(3);
        incomplete_picto = incomplete_picto.substring(0,incomplete_picto.length()-2);
        String complete_picto = incomplete_picto+","+nuevo_pictograma+"]}";
//JSONOBJECT  pictogramas = {"pictogramas":[{"id":"id1","nombre":"nombre1","imagen":"imagen1"},{"id":"id12","nombre":"nombre2","imagen":"imagen2"}]}
        db.update(TABLE_NAME_FAMILIA, generarContentValues_familia(c.getString(1),c.getBlob(2), complete_picto), F_NOMBRE + "=?", new String[]{c.getString(1)});
    }





    public ContentValues generarContentValues_agenda (String nombre){
        ContentValues valores = new ContentValues();
        valores.put(A_NOMBRE, nombre);
        valores.put(A_TAREAS, "{\"tareas\":[{\"id\":\"id_0\",\"nombre\":\"Nueva Tarea\",\"dia\":\"Lunes\",\"glosario\":\"\",\"imagen\":\"imagen1\"}]}");
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

    public ContentValues generarContentValues_pictograma (String nombre, byte[] imagen){
        ContentValues valores = new ContentValues();
        valores.put(P_NOMBRE, nombre);
        valores.put(P_IMAGEN, imagen);
        return valores;
    }
    public Long insertar_pictograma (String nombre, byte[] imagen) {
        return db.insert(TABLE_NAME_PICTOGRAMA, null, generarContentValues_pictograma(nombre, imagen));
    }

    public Cursor cargarCursorPictograma(){
        String [] pictogramas = new String[]{P_ID, P_NOMBRE, P_IMAGEN};
        return db.query(getTableNamePictograma(), pictogramas, null, null, null, null, null);
    }

    public List<Pictograma> getPictogramaList(){
        List<Pictograma> list = new ArrayList<>();
        Cursor c  = cargarCursorPictograma();
        while (c.moveToNext()){
            Pictograma pictograma = new Pictograma();
            pictograma.setId(c.getString(0));
            pictograma.setNombre(c.getString(1));
            pictograma.setImagen(c.getBlob(2));
            list.add(pictograma);
        }
        return list;
    }

    public ContentValues generarContentValues_Tarea (String nombre, byte[] imagen){
        ContentValues valores = new ContentValues();
        valores.put(F_NOMBRE, nombre);
        valores.put(F_IMAGEN, imagen);
        return valores;
    }
    public Long insertar_Tarea (String nombre, byte[] imagen) {
        return db.insert(TABLE_NAME_FAMILIA, null, generarContentValues_Tarea(nombre, imagen));
    }

    public Cursor cargarCursorTarea(){

        String [] familias = new String[]{F_ID, F_NOMBRE, F_IMAGEN};
        return db.query(getTableNameFamilia(), familias, null, null, null, null, null);
    }

    public List<Familia> getTareasList(){
        List<Familia> list = new ArrayList<>();
        Cursor c  = cargarCursorFamilia();
        while (c.moveToNext()){
            Familia familia = new Familia();
            familia.setId(c.getString(0));
            familia.setNombre(c.getString(1));
            familia.setImagen(c.getBlob(2));
            list.add(familia);
        }


        return list;
    }

















}
