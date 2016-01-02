package com.example.alexandreortigosa.appfi.recetas;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by referralsLoLGlobal on 02/01/2016.
 */
public class IngredienteReceta extends Ingrediente {

    private List<Ingrediente> substitutivos = new ArrayList<>();

    public List<Ingrediente> getSubstitutivos() {
        return substitutivos;
    }

   public void addSubstitutivo(Ingrediente ing){
       substitutivos.add(ing);
   }

    public void setSubstitutivos(List<Ingrediente> substitutivos) {
        this.substitutivos = substitutivos;
    }



    public IngredienteReceta(String name) {
        super(name);
    }

    public IngredienteReceta(String name, int id) {
        super(name, id);
    }


}
