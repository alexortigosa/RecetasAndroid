package com.example.alexandreortigosa.appfi.recetas;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class Recetas extends AppCompatActivity {

    private ListView list;
    private SimpleCursorAdapter dataAdapter;
    private gestDB gesdb;
    private static final int INGREDIENTES_ADD=107;
    private String[] columns = new String[]{gestDB.Recetas.ID_RECETA,gestDB.Recetas.NAME};
    private int[] to = new int[]{R.id.RecetaListName,R.id.RecetaListPhoto};
    Cursor cursor;
    private List<Receta> recetas = new ArrayList<>();
    private RecetasAdapter aRecetas;
    EditText search;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch(requestCode) {
            case INGREDIENTES_ADD:
                if (resultCode == RESULT_OK) {
                    Snackbar.make(list, "Receta guardada", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                       refreshList();
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recetas2);
        list = (ListView) findViewById(R.id.listViewRecetas);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        toolbar.setBackgroundColor(getResources().getColor(R.color.BackGroundColor));
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.backlittle));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), addRecetaNew.class);
                startActivityForResult(intent, INGREDIENTES_ADD);
            }
        });


        gesdb=new gestDB(getApplicationContext());
        gesdb.open();
        search = (EditText) findViewById(R.id.RecetasSearch);
        recetas=gesdb.getListRecetas();
        aRecetas = new RecetasAdapter(getApplicationContext(),R.layout.row_receta,recetas);
        search.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //System.out.println("Text ["+s+"]");

                aRecetas.getFilter().filter(s.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        list.setAdapter(aRecetas);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Receta rec = aRecetas.getItem(i);
                Intent intent = new Intent(getApplicationContext(), RecetasContainer.class);
                intent.putExtra(getResources().getString(R.string.intent_Receta), rec);
                intent.putExtra(RecetaDeatails.STATE, RecetaDeatails.STATE_SHOW);
                startActivity(intent);

            }
        });
        TextView empty=(TextView)findViewById(R.id.emptyListRecetas);
        list.setEmptyView(empty);
    }

    private void refreshList(){
        /*cursor = gesdb.fetchAllRecetas();
        dataAdapter.notifyDataSetChanged();*/
        recetas=gesdb.getListRecetas();
        aRecetas = new RecetasAdapter(getApplicationContext(),R.layout.row_receta,recetas);
        list.setAdapter(aRecetas);
        aRecetas.notifyDataSetChanged();

    }



}
