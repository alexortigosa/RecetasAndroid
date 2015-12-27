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

    public RecetasDetAdapter(FragmentManager fm, Context con) {
        super(fm);
        this.context=con;
        ingredientes = new IngredientesList();
        detalles = new RecetaDeatails();

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
