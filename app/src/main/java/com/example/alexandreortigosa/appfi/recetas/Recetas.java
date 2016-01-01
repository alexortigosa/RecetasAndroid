package com.example.alexandreortigosa.appfi.recetas;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class Recetas extends AppCompatActivity {

    private ListView list;
    private SimpleCursorAdapter dataAdapter;
    private gestDB gesdb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recetas2);
        list = (ListView) findViewById(R.id.listViewRecetas);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                /*Intent intent = new Intent(getApplicationContext(), RecetasContainer.class);
                intent.putExtra(RecetaDeatails.STATE,RecetaDeatails.STATE_ADD);
                startActivity(intent);*/
                Intent intent = new Intent(getApplicationContext(), addReceta1.class);
                startActivity(intent);
            }
        });

        gesdb=new gestDB(getApplicationContext());
        gesdb.open();
        Cursor cursor = gesdb.fetchAllRecetas();
        //startManagingCursor(cursor);
        String[] columns = new String[]{gestDB.Recetas.ID_RECETA,gestDB.Recetas.NAME};
        int[] to = new int[]{R.id.textView4Receta,R.id.textView5Receta};
        dataAdapter = new SimpleCursorAdapter(getApplicationContext(),R.layout.row_receta,cursor,columns,to);
        list.setAdapter(dataAdapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Cursor c = dataAdapter.getCursor();

                if(c.moveToPosition(i)){
                    String name=c.getString(c.getColumnIndex(gestDB.Recetas.NAME));
                    int id = c.getInt(c.getColumnIndex(gestDB.Recetas.ID_RECETA));
                    Intent intent = new Intent(getApplicationContext(), RecetasContainer.class);
                    intent.putExtra(gestDB.Recetas.NAME,id);
                    intent.putExtra(RecetaDeatails.STATE,RecetaDeatails.STATE_SHOW);
                    startActivity(intent);
                    int a=0;
                }

            }
        });

    }

}
