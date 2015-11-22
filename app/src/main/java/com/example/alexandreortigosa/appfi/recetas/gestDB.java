package com.example.alexandreortigosa.appfi.recetas;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

/**
 * Created by alexandreortigosa on 22/11/15.
 */
public class gestDB {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "dataBase.db";

    //Definición de tabla login
    private static final String TABLE_RECETAS = "RECETAS";
    public static final class Recetas implements BaseColumns {//
        private Recetas() {}
        public static final String ID_RECETA = "id_login";
        public static final String NAME = "name";
        public static final String DESC = "description";
        public static final String IMAGEN = "image";
        public static final String TYPE = "tipo";
    }
    //Definición de tabla usuarios
    private static final String TABLE_INGREDIENTE = "INGREDIENTES";
    public static final class Ingredientes implements BaseColumns {
        private Ingredientes() {}
        public static final String ID_INGREDIENTE = "id_ingrediente";
        public static final String NOMBRE = "nombre";
    }

    //Definición de tabla usuarios
    private static final String TABLE_RECETAINGREDIENTES = "RECETASINGREDIENTES";
    public static final class recetasIngredientes implements BaseColumns {
        private recetasIngredientes() {}
        public static final String ID_RECETAINGREDIENTE = "id_recetaingrediente";
        public static final String ID_RECETA = "id_receta";
        public static final String ID_INGREDIENTE = "id_ingrediente";
    }

    //Definición de tabla usuarios
    private static final String TABLE_SUBSTITUTIVOS = "SUBSTITUTIVOS";
    public static final class Substitutivos implements BaseColumns {
        private Substitutivos() {}
        public static final String ID_RECETAINGREDIENTE = "id_substitutivo";
        public static final String ID_INGREDIENTE = "id_ingrediente";
        public static final String ID_SUB = "id_subs";
    }

    //Definición de tabla usuarios
    private static final String TABLE_TIPOS = "TIPOS";
    public static final class Tipos implements BaseColumns {
        private Tipos() {}
        public static final String ID_TIPOS = "id_tipo";
        public static final String NOMBRE = "nombre";
    }

    // Sentencias para la creación de tablas
    private static final String INGREDIENTE_TABLE_CREATE = "create table " + TABLE_INGREDIENTE
            + " (" + Ingredientes.ID_INGREDIENTE + " integer primary key AUTOINCREMENT, "
            + Ingredientes.NOMBRE + " text not null);";




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

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            //Se elimina la versión anterior de la tabla
            db.execSQL("DROP TABLE IF EXISTS "+TABLE_INGREDIENTE);


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
}
