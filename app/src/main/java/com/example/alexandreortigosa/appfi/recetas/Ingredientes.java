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
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import java.util.List;

public class Ingredientes extends AppCompatActivity {

    private ListView list;
    private SimpleCursorAdapter dataAdapter;
    private gestDB gesdb;
    private Context myContext;
    private AlertDialog dialog;
    private View dialogLayout;
    private boolean adding;
    private IngredientesAdapterAdd aContentAdapter;
    private List<Ingrediente> lContentIngredientes;
    EditText search;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredientes);
        gesdb=new gestDB(getApplicationContext());
        myContext=this;
        adding=false;

        list = (ListView) findViewById(R.id.listViewIngredientes);
        search = (EditText) findViewById(R.id.addIngredientesSearch);



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
                                        if(gesdb.insertIngrediente(ingrediente)==-109){
                                            Snackbar.make(view, R.string.dialog_exist_text, Snackbar.LENGTH_SHORT)
                                                    .setAction("Action", null).show();

                                        }
                                        else {
                                            Snackbar.make(view, R.string.dialog_add_text, Snackbar.LENGTH_SHORT)
                                                    .setAction("Action", null).show();

                                            refresList();
                                        }


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
        lContentIngredientes=gesdb.fetchListAllIngredientes();
        aContentAdapter = new IngredientesAdapterAdd(getApplicationContext(),R.layout.row_ingrediente_new_add,lContentIngredientes,IngredientesAdapterAdd.MODE_ING);
        search.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //System.out.println("Text ["+s+"]");

                aContentAdapter.getFilter().filter(s.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        /*Cursor cursor = gesdb.fetchAllIngredientes();
        //startManagingCursor(cursor);
        String[] columns = new String[]{gestDB.Ingredientes.NOMBRE};
        int[] to = new int[]{R.id.nameIngredienteLast};
        dataAdapter = new SimpleCursorAdapter(getApplicationContext(),R.layout.row_ingrediente_last,cursor,columns,to);*/
        list.setAdapter(aContentAdapter);

    }

    private void refresList(){

        aContentAdapter.notifyDataSetChanged();
        lContentIngredientes=gesdb.fetchListAllIngredientes();
        aContentAdapter = new IngredientesAdapterAdd(getApplicationContext(),R.layout.row_ingrediente_new_add,lContentIngredientes,IngredientesAdapterAdd.MODE_ING);

        aContentAdapter.getFilter().filter(search.getText().toString());
        list.setAdapter(aContentAdapter);

    }

}
