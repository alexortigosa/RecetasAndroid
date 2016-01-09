package com.example.alexandreortigosa.appfi.recetas;

import java.util.Comparator;

/**
 * Created by referralsLoLGlobal on 09/01/2016.
 */
public class ComparatorIngredienteReceta implements Comparator<IngredienteReceta> {
    @Override
    public int compare(IngredienteReceta ingredienteReceta, IngredienteReceta t1) {
        return ingredienteReceta.getName().compareTo(t1.getName());
    }
}
