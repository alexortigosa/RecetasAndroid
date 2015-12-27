package com.example.alexandreortigosa.appfi.recetas;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by alexandreortigosa on 27/10/15.
 */
public class CustomSQLite extends SQLiteOpenHelper {
    private String sqlCreateReceta = "CREATE TABLE Receta (id INTEGER PRIMARY KEY   AUTOINCREMENT, name TEXT, desc TEXT, img TEXT)";
    private String sqlCreateIngrediente = "CREATE TABLE Ingrediente (id INTEGER PRIMARY KEY   AUTOINCREMENT,name TEXT)";
    private String sqlCreateRecetaIngrediente = "CREATE TABLE RecetaIngrediente (id INTEGER PRIMARY KEY   AUTOINCREMENT,receta INTEGER,ingrediente INTEGER)";
    private String sqlCreateSubsitutivos = "CREATE TABLE Substitutivos (id INTEGER PRIMARY KEY   AUTOINCREMENT,ingrediente INTEGER,substitutivo INTEGER)";
    private String sqlCreateTipos = "CREATE TABLE Tipos (id INTEGER PRIMARY KEY   AUTOINCREMENT,name TEXT)";
    SQLiteDatabase db;



    public CustomSQLite(Context contexto, String nombre,
                         SQLiteDatabase.CursorFactory factory, int version) {
        super(contexto, nombre, factory, version);
    }

    public void insertIngrediente(String name){
        db=this.getWritableDatabase();
        if(db != null) {
            db.execSQL("INSERT INTO Ingrediente (name) " +
                    "VALUES ('" + name + "')");
        }
        //Cerramos la base de datos
        db.close();
    }




    @Override
    public void onCreate(SQLiteDatabase db) {
        //Se ejecuta la sentencia SQL de creación de la tabla
        db.execSQL(sqlCreateReceta);
        db.execSQL(sqlCreateIngrediente);
        db.execSQL(sqlCreateRecetaIngrediente);
        db.execSQL(sqlCreateSubsitutivos);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Se elimina la versión anterior de la tabla
        db.execSQL("DROP TABLE IF EXISTS Receta");
        db.execSQL("DROP TABLE IF EXISTS Ingrediente");
        db.execSQL("DROP TABLE IF EXISTS RecetaIngrediente");
        db.execSQL("DROP TABLE IF EXISTS Substitutivos");
        db.execSQL("DROP TABLE IF EXISTS Tipos");

        //Se crea la nueva versión de la tabla
        db.execSQL(sqlCreateReceta);
        db.execSQL(sqlCreateIngrediente);
        db.execSQL(sqlCreateRecetaIngrediente);
        db.execSQL(sqlCreateSubsitutivos);
        db.execSQL(sqlCreateTipos);

    }


}
