package com.example.alexandreortigosa.appfi.recetas;

import java.io.Serializable;

/**
 * Created by alexandreortigosa on 22/11/15.
 */
public class Ingrediente implements Serializable{
    private int id;
    private String Name;

    public Ingrediente(String name) {
        Name = name;
    }

    public Ingrediente(String name,int id) {
        this.id = id;
        Name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    @Override
    public String toString() {
        return getName();
    }
}
