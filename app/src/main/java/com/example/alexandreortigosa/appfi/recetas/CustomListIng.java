package com.example.alexandreortigosa.appfi.recetas;

import java.io.Serializable;
import java.util.List;

/**
 * Created by referralsLoLGlobal on 01/01/2016.
 */
public class CustomListIng implements Serializable {
    private List<IngredienteReceta> ingredientes;

    public CustomListIng(List<IngredienteReceta> ingredientes) {
        this.ingredientes = ingredientes;
    }

    public List<IngredienteReceta> getIngredientes() {
        return ingredientes;
    }
}
