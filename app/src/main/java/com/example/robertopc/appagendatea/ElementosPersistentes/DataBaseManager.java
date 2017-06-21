package com.example.robertopc.appagendatea.ElementosPersistentes;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by RobertoPC on 17/06/2017.
 */

public class DataBaseManager {

    private static final String TABLE_NAME_FAMILIA = "familia";

    private static final String F_ID = "_id";
    private static final String F_NOMBRE = "nombre";
    private static final String F_RUTA = "ruta";

    private static final String CREATE_TABLE_FAMILIA = "create table " + TABLE_NAME_FAMILIA+ " ("
            + F_ID + " integer primary key autoincrement,"
            + F_NOMBRE + " text not null,"
            + F_RUTA + " text);";



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


}
