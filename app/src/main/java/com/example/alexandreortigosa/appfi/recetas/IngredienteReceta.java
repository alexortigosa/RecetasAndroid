package com.example.alexandreortigosa.appfi.recetas;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by referralsLoLGlobal on 02/01/2016.
 */
public class IngredienteReceta extends Ingrediente {

    private List<Ingrediente> substitutivos = new ArrayList<>();



    public int getIdInterno() {
        return idInterno;
    }

    private int idInterno;

    public List<Ingrediente> getSubstitutivos() {
        Collections.sort(substitutivos, new Comparator<Ingrediente>() {
            @Override
            public int compare(Ingrediente ingrediente, Ingrediente t1) {
                return ingrediente.getName().compareTo(t1.getName());
            }
        });
        return substitutivos;
    }

   public void addSubstitutivo(Ingrediente ing){
       substitutivos.add(ing);
   }

    public void setSubstitutivos(List<Ingrediente> substitutivos) {
        Collections.sort(substitutivos, new Comparator<Ingrediente>() {
            @Override
            public int compare(Ingrediente ingrediente, Ingrediente t1) {
                return ingrediente.getName().compareTo(t1.getName());
            }
        });
        this.substitutivos = substitutivos;
    }

    public IngredienteReceta(String name) {
        super(name);
    }

    public IngredienteReceta(String name, int id) {
        super(name, id);
    }

    public IngredienteReceta(String name, int id, int idInterno) {
        super(name, id);
        this.idInterno = idInterno;
    }



}
