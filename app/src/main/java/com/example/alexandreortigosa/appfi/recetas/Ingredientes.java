package com.example.alexandreortigosa.appfi.recetas;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

public class Ingredientes extends AppCompatActivity {

    private ListView list;
    private SimpleCursorAdapter dataAdapter;
    private gestDB gesdb;
    private Context myContext;
    private AlertDialog dialog;
    private View dialogLayout;
    private boolean adding;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredientes);
        gesdb=new gestDB(getApplicationContext());
        myContext=this;
        adding=false;

        list = (ListView) findViewById(R.id.listViewIngredientes);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        toolbar.setBackgroundColor(getResources().getColor(R.color.BackGroundColor));

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fabIngredientes);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                if (!adding) {
                    adding=true;
                    // 1. Instantiate an AlertDialog.Builder with its constructor
                    AlertDialog.Builder builder = new AlertDialog.Builder(myContext);

// 2. Chain together various setter methods to set the dialog characteristics
                    builder.setTitle(R.string.dialog_add_titulo);

                    // Get the layout inflater
                    LayoutInflater inflater = (LayoutInflater) myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                    dialogLayout = inflater.inflate(R.layout.dialog_add_ingrediente, null);
                    // Inflate and set the layout for the dialog
                    // Pass null as the parent view because its going in the dialog layout
                    builder.setView(dialogLayout)
                            .setPositiveButton(R.string.dialog_add_ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int id) {
                                    EditText dialText = (EditText) dialogLayout.findViewById(R.id.dialogText);

                                    if (dialText.getText().length() != 0) {
                                        Ingrediente ingrediente = new Ingrediente(dialText.getText().toString());
                                        gesdb.insertIngrediente(ingrediente);

                                        refresList();
                                    }
                                    else{
                                        Snackbar.make(view, R.string.dialog_empty_text, Snackbar.LENGTH_SHORT)
                                                .setAction("Action", null).show();
                                    }
                                    adding=false;
                                }
                            }).setNegativeButton(R.string.dialog_add_cancel, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            adding=false;
                            dialog.cancel();
                            dialog.dismiss();
                        }
                    });
// 3. Get the AlertDialog from create()
                    dialog = builder.create();
                    dialog.show();
                }
            }
        });

        gesdb=new gestDB(getApplicationContext());
        gesdb.open();
        refresList();

    }

    private void refresList(){
        Cursor cursor = gesdb.fetchAllIngredientes();
        //startManagingCursor(cursor);
        String[] columns = new String[]{gestDB.Ingredientes.NOMBRE};
        int[] to = new int[]{R.id.nameIngredienteLast};
        dataAdapter = new SimpleCursorAdapter(getApplicationContext(),R.layout.row_ingrediente_last,cursor,columns,to);
        list.setAdapter(dataAdapter);

    }

}
