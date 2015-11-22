package com.example.alexandreortigosa.appfi.recetas;

import android.app.ListActivity;
import android.database.Cursor;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;


public class IngredientesList extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredientes_list);
        ListView list = (ListView) findViewById(R.id.listaingredientes);
        gestDB ges = new gestDB(getApplicationContext());
        ges.open();
        Cursor cursor = ges.cursorAllIngredientes();
        startManagingCursor(cursor);
        String[] from = new String[]{"nombre"};
        int[] to = new int[]{R.id.text};

        SimpleCursorAdapter cursorAdapter = new SimpleCursorAdapter(this,R.layout.row_ingrediente,cursor,from,to);
        list.setAdapter(cursorAdapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_ingredientes_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
