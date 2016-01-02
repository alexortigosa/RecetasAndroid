package com.example.alexandreortigosa.appfi.recetas;

import java.io.Serializable;
import java.util.List;

/**
 * Created by referralsLoLGlobal on 01/01/2016.
 */
public class CustomListIng implements Serializable {
    private List<Ingrediente> ingredientes;

    public CustomListIng(List<Ingrediente> ingredientes) {
        this.ingredientes = ingredientes;
    }

    public List<Ingrediente> getIngredientes() {
        return ingredientes;
    }
}
