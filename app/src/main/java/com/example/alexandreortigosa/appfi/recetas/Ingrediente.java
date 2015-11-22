package com.example.alexandreortigosa.appfi.recetas;

/**
 * Created by alexandreortigosa on 22/11/15.
 */
public class Ingrediente {
    private int id;
    private String Name;

    public Ingrediente(String name) {
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
}
