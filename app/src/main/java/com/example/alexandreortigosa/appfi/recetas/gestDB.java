package com.example.alexandreortigosa.appfi.recetas;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * Created by alexandreortigosa on 22/11/15.
 */
public class gestDB {

    private static final int DATABASE_VERSION = 13;
    private static final String DATABASE_NAME = "dataBase.db";

    //Definición de tabla login
    private static final String TABLE_RECETAS = "RECETAS";



    public static final class Recetas implements BaseColumns {//
        private Recetas() {}
        public static final String ID_RECETA = "_id";
        public static final String NAME = "name";
        public static final String DESC = "description";
        public static final String IMAGEN = "image";
        public static final String TYPE = "tipo";
    }
    //Definición de tabla usuarios
    private static final String TABLE_INGREDIENTE = "INGREDIENTES";
    public static final class Ingredientes implements BaseColumns {
        private Ingredientes() {}
        public static final String ID_INGREDIENTE = "_id";
        public static final String NOMBRE = "nombre";
    }

    //Definición de tabla usuarios
    private static final String TABLE_RECETAINGREDIENTES = "RECETASINGREDIENTES";
    public static final class recetasIngredientes implements BaseColumns {
        private recetasIngredientes() {}
        public static final String ID_RECETAINGREDIENTE = "_id";
        public static final String ID_RECETA = "id_receta";
        public static final String ID_INGREDIENTE = "id_ingrediente";
    }

    //Definición de tabla usuarios
    private static final String TABLE_SUBSTITUTIVOS = "SUBSTITUTIVOS";
    public static final class Substitutivos implements BaseColumns {
        private Substitutivos() {}
        public static final String ID_RECETAINGREDIENTE = "_id";
        public static final String ID_INGREDIENTE = "id_ingrediente";
        public static final String ID_SUB = "id_subs";
    }

    //Definición de tabla usuarios
    private static final String TABLE_TIPOS = "TIPOS";
    public static final class Tipos implements BaseColumns {
        private Tipos() {}
        public static final String ID_TIPOS = "_id";
        public static final String NOMBRE = "nombre";
    }

    // Sentencias para la creación de tablas
    private static final String INGREDIENTE_TABLE_CREATE = "CREATE TABLE if not exists " + TABLE_INGREDIENTE + " (" +
             Ingredientes.ID_INGREDIENTE + " integer PRIMARY KEY autoincrement, " +
             Ingredientes.NOMBRE + " text not null);";

    private static final String RECETAS_TABLE_CREATE = "CREATE TABLE if not exists " + TABLE_RECETAS + " (" +
            Recetas.ID_RECETA + " integer PRIMARY KEY autoincrement, " +
            Recetas.NAME + " text not null, " +
            Recetas.DESC + " text, "+
            Recetas.IMAGEN + " text, "+
            Recetas.TYPE + " text);";

    private static final String RECETAS_INGREDIENTES_TABLE_CREATE = "CREATE TABLE if not exists " + TABLE_RECETAINGREDIENTES + " (" +
            recetasIngredientes.ID_RECETAINGREDIENTE + " integer PRIMARY KEY autoincrement, " +
            recetasIngredientes.ID_RECETA + " integer not null, " +
            recetasIngredientes.ID_INGREDIENTE + " integer not null);";

    private static final String SUBSTITUTIVOS_TABLE_CREATE = "CREATE TABLE if not exists " + TABLE_SUBSTITUTIVOS + " (" +
            Substitutivos.ID_RECETAINGREDIENTE + " integer PRIMARY KEY autoincrement, " +
            Substitutivos.ID_INGREDIENTE + " integer not null, " +
            Substitutivos.ID_SUB + " integer not null);";




    private Context context;
    private SQLiteDatabase db;
    private DBHandler openHelper;

    public gestDB(Context context) {

        this.context = context;
        this.openHelper = new DBHandler(context);
    }

    public gestDB open(){
        this.db=openHelper.getWritableDatabase();
        return this;
    }

    public void close(){
        this.db.close();
    }


        private static class DBHandler extends SQLiteOpenHelper {

            public DBHandler(Context context) {
                super(context, DATABASE_NAME, null, DATABASE_VERSION);
            }

            @Override
        public void onCreate(SQLiteDatabase db) {
            //Se ejecuta la sentencia SQL de creación de la tabla
            db.execSQL(INGREDIENTE_TABLE_CREATE);
                db.execSQL(RECETAS_TABLE_CREATE);
                db.execSQL(RECETAS_INGREDIENTES_TABLE_CREATE);
                db.execSQL(SUBSTITUTIVOS_TABLE_CREATE);

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            //Se elimina la versión anterior de la tabla
            db.execSQL("DROP TABLE IF EXISTS "+TABLE_INGREDIENTE);
            db.execSQL("DROP TABLE IF EXISTS "+TABLE_RECETAS);
            db.execSQL("DROP TABLE IF EXISTS "+TABLE_RECETAINGREDIENTES);
            db.execSQL("DROP TABLE IF EXISTS "+TABLE_SUBSTITUTIVOS);


            //Se crea la nueva versión de la tabla
            onCreate(db);
        }
    }

    public long insertIngrediente(Ingrediente ing){
        ContentValues values = new ContentValues();
        //values.put(Ingredientes.ID_INGREDIENTE, ing.getId());
        values.put(Ingredientes.NOMBRE, ing.getName());
        long id = db.insert(TABLE_INGREDIENTE, null, values);
        return id;
    }

    public long changeStringsReceta(Receta rec){
        ContentValues values = new ContentValues();
        //values.put(Ingredientes.ID_INGREDIENTE, ing.getId());
        values.put(Recetas.NAME, rec.getName());
        values.put(Recetas.DESC, rec.getDescripccio());
        long id =  db.update(TABLE_RECETAS, values, Recetas.ID_RECETA + "=?", new String[]{String.valueOf(rec.getId())});
        return id;
    }

    public long changePhotoReceta(Receta receta) {
        ContentValues values = new ContentValues();
        //values.put(Ingredientes.ID_INGREDIENTE, ing.getId());
        values.put(Recetas.IMAGEN,receta.getPhoto());
        long id =  db.update(TABLE_RECETAS, values, Recetas.ID_RECETA + "=?", new String[]{String.valueOf(receta.getId())});
        return id;
    }

    public long insertReceta(Receta receta){
        ContentValues values = new ContentValues();
        //values.put(Ingredientes.ID_INGREDIENTE, ing.getId());
        values.put(Recetas.NAME, receta.getName());
        values.put(Recetas.DESC,receta.getDescripccio());
        values.put(Recetas.IMAGEN,receta.getPhoto());
        values.put(Recetas.TYPE,"1");
        long id = db.insert(TABLE_RECETAS, null, values);
        for(IngredienteReceta ingRec:receta.getIngredientes()){
            values = new ContentValues();
            values.put(recetasIngredientes.ID_RECETA,id);
            values.put(recetasIngredientes.ID_INGREDIENTE,ingRec.getId());
            long idIngredienteReceta  = db.insert(TABLE_RECETAINGREDIENTES,null,values);
            for(Ingrediente ing:ingRec.getSubstitutivos()){
                values = new ContentValues();
                values.put(Substitutivos.ID_INGREDIENTE, idIngredienteReceta);
                values.put(Substitutivos.ID_SUB,ing.getId());
                long idSub = db.insert(TABLE_SUBSTITUTIVOS,null,values);
            }
        }
        return id;
    }
    public long insertIngredientesReceta(Receta receta){
        ContentValues values = new ContentValues();
        //values.put(Ingredientes.ID_INGREDIENTE, ing.getId());

        for(IngredienteReceta ingRec:receta.getIngredientes()){
            values = new ContentValues();
            values.put(recetasIngredientes.ID_RECETA,receta.getId());
            values.put(recetasIngredientes.ID_INGREDIENTE,ingRec.getId());
            long idIngredienteReceta  = db.insert(TABLE_RECETAINGREDIENTES,null,values);
            for(Ingrediente ing:ingRec.getSubstitutivos()){
                values = new ContentValues();
                values.put(Substitutivos.ID_INGREDIENTE, idIngredienteReceta);
                values.put(Substitutivos.ID_SUB,ing.getId());
                long idSub = db.insert(TABLE_SUBSTITUTIVOS,null,values);
            }
        }
        return receta.getId();
    }

    public Cursor cursorAllIngredientes(){


        return this.db.rawQuery("SELECT "+Ingredientes.NOMBRE+" FROM "+TABLE_INGREDIENTE,null);
    }

    public Cursor fetchAllIngredientes(){
        Cursor mCursor = db.query(TABLE_INGREDIENTE,new String[] {Ingredientes.ID_INGREDIENTE,Ingredientes.NOMBRE},null,null,null,null,Ingredientes.NOMBRE);
        if(mCursor != null && mCursor.getCount()>0) mCursor.moveToFirst();
        return  mCursor;
    }

    public List<Receta> getListRecetas(){
        Cursor mCursor = db.query(TABLE_RECETAS,new String[] {Recetas.ID_RECETA,Recetas.NAME,Recetas.DESC,Recetas.IMAGEN},null,null,null,null,Recetas.NAME);
        List<Receta> result=new ArrayList<>();
        if(mCursor != null && mCursor.getCount()>0){
            mCursor.moveToFirst();
            do{
                Receta rec=new Receta(mCursor.getInt(mCursor.getColumnIndex(Recetas.ID_RECETA)),mCursor.getString(mCursor.getColumnIndex(Recetas.NAME)),mCursor.getString(mCursor.getColumnIndex(Recetas.DESC)),mCursor.getString(mCursor.getColumnIndex(Recetas.IMAGEN)));
                result.add(rec);
            }
            while (mCursor.moveToNext());
        }
        return  result;
    }

    public List<Ingrediente> fetchListAllIngredientes(){
        Cursor mCursor = db.query(TABLE_INGREDIENTE,new String[] {Ingredientes.ID_INGREDIENTE,Ingredientes.NOMBRE},null,null,null,null,Ingredientes.NOMBRE);
        List<Ingrediente> result = new ArrayList<>();

        if(mCursor != null && mCursor.getCount()>0) {
            mCursor.moveToFirst();
            do{
                Ingrediente ing = new Ingrediente(mCursor.getString(mCursor.getColumnIndex(Ingredientes.NOMBRE)),mCursor.getInt(mCursor.getColumnIndex(Ingredientes.ID_INGREDIENTE)));
                result.add(ing);
            }
            while (mCursor.moveToNext());
        }
        return  result;
    }

    public List<IngredienteReceta> fetchListAllIngredientesReceta(){
        Cursor mCursor = db.query(TABLE_INGREDIENTE,new String[] {Ingredientes.ID_INGREDIENTE,Ingredientes.NOMBRE},null,null,null,null,Ingredientes.NOMBRE);
        List<IngredienteReceta> result = new ArrayList<>();

        if(mCursor != null && mCursor.getCount()>0) {
            mCursor.moveToFirst();
            do{
                IngredienteReceta ing = new IngredienteReceta(mCursor.getString(mCursor.getColumnIndex(Ingredientes.NOMBRE)),mCursor.getInt(mCursor.getColumnIndex(Ingredientes.ID_INGREDIENTE)));
                result.add(ing);
            }
            while (mCursor.moveToNext());
        }
        return  result;
    }

    public Cursor fetchAllRecetas(){
        Cursor mCursor = db.query(TABLE_RECETAS,new String[] {Recetas.ID_RECETA,Recetas.NAME,Recetas.DESC,Recetas.IMAGEN,Recetas.TYPE},null,null,null,null,null);
        if(mCursor != null && mCursor.getCount()>0) mCursor.moveToFirst();
        return  mCursor;
    }

    public Receta recogerReceta(int id){
        Cursor mCursor = db.query(TABLE_RECETAS,new String[] {Recetas.ID_RECETA,Recetas.NAME,Recetas.DESC,Recetas.IMAGEN,Recetas.TYPE},Recetas.ID_RECETA+" = ?",  new String[] {String.valueOf(id)},null,null,null);
        Receta rResult;
        if(mCursor != null && mCursor.getCount()>0)
        {
            mCursor.moveToFirst();
            rResult = new Receta(mCursor.getString(mCursor.getColumnIndex(Recetas.NAME)),mCursor.getString(mCursor.getColumnIndex(Recetas.DESC)));
            rResult.setId(mCursor.getInt(mCursor.getColumnIndex(Recetas.ID_RECETA)));
            rResult.setPhoto(mCursor.getString(mCursor.getColumnIndex(Recetas.IMAGEN)));
            return  rResult;
        }
        else return null;


    }

    public void updateIngredientes(Receta rec){
        ContentValues values = new ContentValues();
        for(IngredienteReceta ingRec:rec.getIngredientes()){
            db.delete(TABLE_SUBSTITUTIVOS,Substitutivos.ID_INGREDIENTE+"=?",new String[]{String.valueOf(ingRec.getIdInterno())});
        }
        db.delete(TABLE_RECETAINGREDIENTES,recetasIngredientes.ID_RECETA+"=?",new String[]{String.valueOf(rec.getId())});

    }

    public void guardarReceta(Receta receta){

    }

    public Cursor fetchAllIngredientesReceta(int id){
        String myQuery = "SELECT ING."+Ingredientes.ID_INGREDIENTE+", ING."+Ingredientes.NOMBRE+" FROM "+
                TABLE_RECETAINGREDIENTES+" REC "+
                "JOIN "+TABLE_INGREDIENTE+" ING ON ING."+Ingredientes.ID_INGREDIENTE+"=REC."+recetasIngredientes.ID_INGREDIENTE+" "+
                "WHERE REC."+recetasIngredientes.ID_RECETA+"=?";
        Cursor mCursor = db.rawQuery(myQuery,new String[]{String.valueOf(id)});
        if(mCursor != null && mCursor.getCount()>0) mCursor.moveToFirst();
        return  mCursor;
    }

    public List<IngredienteReceta> fetchAllIngredientesRecetaList(int id){
        String myQuery = "SELECT REC."+recetasIngredientes.ID_RECETAINGREDIENTE+" AS idRecetaIngrediente, ING."+Ingredientes.ID_INGREDIENTE+", ING."+Ingredientes.NOMBRE+" FROM "+
                TABLE_RECETAINGREDIENTES+" REC "+
                "JOIN "+TABLE_INGREDIENTE+" ING ON ING."+Ingredientes.ID_INGREDIENTE+"=REC."+recetasIngredientes.ID_INGREDIENTE+" "+
                "WHERE REC."+recetasIngredientes.ID_RECETA+"=?";

        String myQuery2 = "SELECT ING."+Ingredientes.ID_INGREDIENTE+", ING."+Ingredientes.NOMBRE+" FROM "+
                TABLE_SUBSTITUTIVOS+" REC "+
                "JOIN "+TABLE_INGREDIENTE+" ING ON ING."+Ingredientes.ID_INGREDIENTE+"=REC."+Substitutivos.ID_SUB+" "+
                "WHERE REC."+Substitutivos.ID_INGREDIENTE+"=?";

        Cursor mCursor = db.rawQuery(myQuery,new String[]{String.valueOf(id)});
        List<IngredienteReceta> result = new ArrayList<>();
        if(mCursor != null && mCursor.getCount()>0) {
            mCursor.moveToFirst();
            do{
                    int idRecetaIngrediente = mCursor.getInt(mCursor.getColumnIndex("idRecetaIngrediente"));
                    IngredienteReceta ingRec = new IngredienteReceta(mCursor.getString(mCursor.getColumnIndex(Ingredientes.NOMBRE)),mCursor.getInt(mCursor.getColumnIndex(Ingredientes.ID_INGREDIENTE)),idRecetaIngrediente);
                    Cursor iCursor = db.rawQuery(myQuery2,new String[]{String.valueOf(idRecetaIngrediente)});
                    if(iCursor != null && iCursor.getCount()>0) {
                        iCursor.moveToFirst();
                        do{
                            Ingrediente ing=new Ingrediente(iCursor.getString(iCursor.getColumnIndex(Ingredientes.NOMBRE)),iCursor.getInt(iCursor.getColumnIndex(Ingredientes.ID_INGREDIENTE)));
                            ingRec.addSubstitutivo(ing);
                        }
                        while (iCursor.moveToNext());
                    }
                    result.add(ingRec);
            } while (mCursor.moveToNext());

        }
        return  result;
    }
}
