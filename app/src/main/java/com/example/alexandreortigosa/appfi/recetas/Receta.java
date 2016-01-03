package com.example.alexandreortigosa.appfi.recetas;

import android.content.Context;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by referralsLoLGlobal on 22/12/2015.
 */
public class Receta implements Serializable{
    private List<IngredienteReceta> ingredientes;
    private String descripccio;
    private String photo;
    private List<String> categorias;
    private String name;
    private int id;


    public Receta(){

    }

    public Receta(int id, String name, String descripccio, String photo) {
        this.id = id;
        this.name = name;
        this.photo = photo;
        this.descripccio = descripccio;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Receta(String name, String desc){
        this.name=name;
        this.descripccio=desc;

    }

    public List<IngredienteReceta> getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(List<IngredienteReceta> ingredientes) {
        this.ingredientes = ingredientes;
    }

    public void anadirIngrediente(IngredienteReceta ing){
        ingredientes.add(ing);
    }

    public String getDescripccio() {
        return descripccio;
    }

    public void setDescripccio(String descripccio) {
        this.descripccio = descripccio;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public List<String> getCategorias() {
        return categorias;
    }

    public void setCategorias(LinkedList<String> categorias) {
        this.categorias = categorias;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void updateDadesReceta(Context context){
        gestDB ges = new gestDB(context);
        ges.open();
        ingredientes=ges.fetchAllIngredientesRecetaList(id);
        ges.close();

    }



    public void changeDescripcio(Context context,String des){
        descripccio=des;
        gestDB ges = new gestDB(context);
        ges.open();
        ges.changeStringsReceta(this);
        ges.close();

    }
    public void changeName(Context context,String name){
        gestDB ges = new gestDB(context);
        ges.open();
        this.name=name;
        ges.changeStringsReceta(this);
        ges.close();

    }

    public void guardarPhoto(Context context) {
        gestDB ges = new gestDB(context);
        ges.open();
        ges.changePhotoReceta(this);
        ges.close();
    }

    public IngredienteReceta getIngredienteReceta(int id) {
        for(IngredienteReceta ing: ingredientes){
            if(ing.getId()==id) return ing;
        }
        return null;
    }
    public void updateIngredientes(Context context,List<IngredienteReceta> lisIngredientes) {
        gestDB ges = new gestDB(context);
        ges.open();
        ges.updateIngredientes(this);
        ingredientes=lisIngredientes;
        ges.insertIngredientesReceta(this);
        ges.close();


    }
}
