package com.example.alexandreortigosa.appfi.recetas;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by alexandreortigosa on 27/10/15.
 */
public class CustomSQLite extends SQLiteOpenHelper {
    String sqlCreateReceta = "CREATE TABLE Receta (id INTEGER PRIMARY KEY   AUTOINCREMENT, name TEXT, desc TEXT, img TEXT)";
    String sqlCreateIngrediente = "CREATE TABLE Ingrediente (id INTEGER PRIMARY KEY   AUTOINCREMENT,name TEXT)";
    String sqlCreateRecetaIngrediente = "CREATE TABLE RecetaIngrediente (id INTEGER PRIMARY KEY   AUTOINCREMENT,receta INTEGER,ingrediente INTEGER)";
    String sqlCreateSubsitutivos = "CREATE TABLE Substitutivos (id INTEGER PRIMARY KEY   AUTOINCREMENT,ingrediente INTEGER,substitutivo INTEGER)";


    public CustomSQLite(Context contexto, String nombre,
                         SQLiteDatabase.CursorFactory factory, int version) {
        super(contexto, nombre, factory, version);
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

        //Se crea la nueva versión de la tabla
        db.execSQL(sqlCreateReceta);
        db.execSQL(sqlCreateIngrediente);
        db.execSQL(sqlCreateRecetaIngrediente);
        db.execSQL(sqlCreateSubsitutivos);

    }
}
