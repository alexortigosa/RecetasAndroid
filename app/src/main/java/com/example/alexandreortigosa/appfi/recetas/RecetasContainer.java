package com.example.alexandreortigosa.appfi.recetas;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class RecetasContainer extends AppCompatActivity implements RecetaDeatails.OnFragmentInteractionListener,IngredientesList.OnFragmentInteractionListener {
    private RecetasDetAdapter rAdapter;
    private int idReceta;
    private Receta receta;
    private String STATUS;
    private static final int INGREDIENTES_EDIT = 102;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //return super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_base, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id){



            default:
                return true;

        }

        //return super.onOptionsItemSelected(item);
    }

    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch(requestCode) {
            case INGREDIENTES_EDIT:
                if(resultCode== Activity.RESULT_OK){
                    CustomListIng lIngredientesaux = (CustomListIng) data.getSerializableExtra(getResources().getString(R.string.add_Ingredientes_Intent));
                    receta.setIngredientes(lIngredientesaux.getIngredientes());
                    rAdapter.setearReceta(receta);
                    rAdapter.updateListaIng();

                }
        }
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        receta=(Receta)intent.getSerializableExtra(getResources().getString(R.string.intent_Receta));
        idReceta=receta.getId();
        receta.updateDadesReceta(getApplicationContext());
        //idReceta=intent.getIntExtra(gestDB.Recetas.NAME,9999);
        STATUS=intent.getStringExtra(RecetaDeatails.STATE);
        setContentView(R.layout.activity_recetas_container);
        rAdapter = new RecetasDetAdapter(getSupportFragmentManager(),RecetasContainer.this);

        ViewPager vPager = (ViewPager) findViewById(R.id.viewpager);
        vPager.setAdapter(rAdapter);



        // Give the TabLayout the ViewPager (material)
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setTabTextColors(Color.DKGRAY, Color.BLACK);
        tabLayout.setupWithViewPager(vPager);
        rAdapter.setStatus(STATUS);
        switch (STATUS){
            case RecetaDeatails.STATE_SHOW:
                rAdapter.setearReceta(receta);
                //rAdapter.setearReceta(idReceta);
                break;
            case RecetaDeatails.STATE_EDIT:
                rAdapter.setearReceta(receta);
                //rAdapter.setearReceta(idReceta);
                break;
            case RecetaDeatails.STATE_ADD:
                break;
            default:
                break;

        }




    }

    @Override
    public void onFragmentInteractionMem(Uri uri) {


    }

    @Override
    public void onFragmentInteractionFinished() {


    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
