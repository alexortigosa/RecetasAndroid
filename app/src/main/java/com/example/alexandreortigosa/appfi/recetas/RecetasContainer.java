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
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class RecetasContainer extends AppCompatActivity implements RecetaDeatails.OnFragmentInteractionListener,IngredientesList.OnFragmentInteractionListener {
    private RecetasDetAdapter rAdapter;
    private int idReceta;
    private Receta receta;
    private String STATUS;
    static final String STATE_RECETA = "receta";
    private static final int INGREDIENTES_EDIT = 102;

   /* @Override
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
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        if (savedInstanceState != null) {
            // Restore value of members from saved state
            receta = (Receta)savedInstanceState.getSerializable(STATE_RECETA);
            idReceta=receta.getId();
        } else {
            receta = (Receta) intent.getSerializableExtra(getResources().getString(R.string.intent_Receta));
            idReceta = receta.getId();
            receta.updateDadesReceta(getApplicationContext());
        }

        //idReceta=intent.getIntExtra(gestDB.Recetas.NAME,9999);
        STATUS=intent.getStringExtra(RecetaDeatails.STATE);
        setContentView(R.layout.activity_recetas_container);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarReceta);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.backlittle));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        toolbar.setBackgroundColor(getResources().getColor(R.color.BackGroundColor));
        rAdapter = new RecetasDetAdapter(getSupportFragmentManager(),RecetasContainer.this);
        rAdapter.setearReceta(receta);
        ViewPager vPager = (ViewPager) findViewById(R.id.viewpager);
        vPager.setAdapter(rAdapter);



        // Give the TabLayout the ViewPager (material)
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setTabTextColors(Color.DKGRAY, Color.WHITE);
        tabLayout.setBackgroundColor(getResources().getColor(R.color.BackGroundColorPrimary));
        tabLayout.setupWithViewPager(vPager);
        rAdapter.setStatus(STATUS);
        switch (STATUS){
            case RecetaDeatails.STATE_SHOW:
                rAdapter.setearReceta(receta);
                break;
            case RecetaDeatails.STATE_EDIT:
                rAdapter.setearReceta(receta);
                break;
            case RecetaDeatails.STATE_ADD:
                break;
            default:
                break;

        }




    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(STATE_RECETA,receta);

        // Always call the superclass so it can save the view hierarchy state
        super.onSaveInstanceState(outState);

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
