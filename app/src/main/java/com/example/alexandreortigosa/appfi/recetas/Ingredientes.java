package com.example.alexandreortigosa.appfi.recetas;

import android.database.Cursor;
import android.os.Bundle;
import android.app.Activity;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class Ingredientes extends Activity {

    private ListView list;
    private SimpleCursorAdapter dataAdapter;
    private gestDB gesdb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredientes);
        list = (ListView) findViewById(R.id.listaingredientesUltima);
        gesdb=new gestDB(getApplicationContext());
        gesdb.open();
        Cursor cursor = gesdb.fetchAllIngredientes();
        //startManagingCursor(cursor);
        String[] columns = new String[]{gestDB.Ingredientes.NOMBRE};
        int[] to = new int[]{R.id.nameIngredienteLast};
        dataAdapter = new SimpleCursorAdapter(getApplicationContext(),R.layout.row_ingrediente_last,cursor,columns,to);
        list.setAdapter(dataAdapter);
    }

}
