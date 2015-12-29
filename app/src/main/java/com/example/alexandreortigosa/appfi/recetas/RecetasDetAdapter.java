package com.example.alexandreortigosa.appfi.recetas;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by referralsLoLGlobal on 22/12/2015.
 */
public class RecetasDetAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 2;
    private String tabTitles[] = new String[] { "Descripción", "Ingredientes" };
    private Context context;
    IngredientesList ingredientes;
    RecetaDeatails detalles;
    Fragment tab = null;
    gestDB gesdb;
    Receta receta;


    String status;

    public RecetasDetAdapter(FragmentManager fm, Context con) {
        super(fm);
        this.context=con;
        gesdb=new gestDB(con);
        ingredientes = new IngredientesList();
        detalles = new RecetaDeatails();

    }

    public void setearReceta(int id){
        gesdb.open();
        receta=gesdb.recogerReceta(id);
        gesdb.close();
        detalles.setReceta(receta);
        ingredientes.setReceta(receta);
    }

    public void setStatus(String status) {
        this.status = status;
        detalles.setSTATUS(status);
        ingredientes.setSTATUS(status);
    }


    @Override
    public Fragment getItem(int position) {
        switch(position) {
            case 0:
                tab = detalles;
                break;
            case 1:
                tab = ingredientes;
                break;
        }
        return tab;
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    //pone el nombre en cada tab
    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }
}
