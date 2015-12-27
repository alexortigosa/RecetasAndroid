package com.example.alexandreortigosa.appfi.recetas;

import android.graphics.Color;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class RecetasContainer extends AppCompatActivity implements RecetaDeatails.OnFragmentInteractionListener,IngredientesList.OnFragmentInteractionListener {
    private RecetasDetAdapter rAdapter;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recetas_container);
        rAdapter = new RecetasDetAdapter(getSupportFragmentManager(),RecetasContainer.this);

        ViewPager vPager = (ViewPager) findViewById(R.id.viewpager);
        vPager.setAdapter(rAdapter);

        // Give the TabLayout the ViewPager (material)
        TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setTabTextColors(Color.DKGRAY, Color.BLACK);
        tabLayout.setupWithViewPager(vPager);
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
