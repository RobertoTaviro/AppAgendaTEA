package com.example.robertopc.appagendatea.ElementosPersistentes;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by RobertoPC on 17/06/2017.
 */

public class DbHelper extends SQLiteOpenHelper{

    private static final String DB_NAME = "database.sqlite";

    private static final int DB_SCHEME_VERSION = 1;

    public DbHelper(Context context, String tutor_id){
        super(context, tutor_id+"_"+DB_NAME, null, DB_SCHEME_VERSION);
    }

    public DbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DataBaseManager.getCreateTableFamilia());

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
