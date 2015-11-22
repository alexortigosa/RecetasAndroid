package com.example.alexandreortigosa.appfi.recetas;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by alexandreortigosa on 22/11/15.
 */
public class DatosPrueba {

    private gestDB slite;



    public DatosPrueba(gestDB ulite) {
        slite=ulite;
    }

    public void generarIngredientes(int numero){

        for(int i=0; i<=numero;i++){
            slite.insertIngrediente(new Ingrediente("Ingrediente" + String.valueOf(numero)));
        }


    }





}
