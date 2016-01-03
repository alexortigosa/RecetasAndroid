package com.example.alexandreortigosa.appfi.recetas;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class addIngredientesReceta extends AppCompatActivity {

    private ListView list;
    private SimpleCursorAdapter dataAdapter;
    private gestDB gesdb;
    private Context myContext;
    private AlertDialog dialog;
    private View dialogLayout;
    private ArrayAdapter<IngredienteReceta> aAdapter;
    private ArrayAdapter<IngredienteReceta> aContentAdapter;
    private List<IngredienteReceta> lIngredientes;
    private List<IngredienteReceta> lContentIngredientes;
    private Button bAddIngredientes;
    private IngredienteReceta ingSelected;
    private static final int INGREDIENTES_SUB = 103;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch(requestCode) {
            case INGREDIENTES_SUB:
                if(resultCode==Activity.RESULT_OK){
                    IngredienteReceta ingRec = (IngredienteReceta) data.getSerializableExtra(getResources().getString(R.string.add_Ingredientes_Subs_Intent));
                    updateSubs(ingRec);
                }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_ingredientes_receta);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setBackgroundColor(getResources().getColor(R.color.BackGroundColor));
        setSupportActionBar(toolbar);
        Intent intent = getIntent();
        CustomListIng cLi = (CustomListIng) intent.getSerializableExtra(getResources().getString(R.string.add_Ingredientes_Intent));

        gesdb=new gestDB(getApplicationContext());
        gesdb.open();
        lContentIngredientes= cLi.getIngredientes();
        lIngredientes=gesdb.fetchListAllIngredientesReceta();
        lIngredientes=reduceIngregientes();
        aAdapter = new ArrayAdapter<IngredienteReceta>(getApplicationContext(),R.layout.row_ingrediente_adding,lIngredientes);
        aContentAdapter = new ArrayAdapter<IngredienteReceta>(getApplicationContext(),R.layout.row_ingrediente_adding,lContentIngredientes);
        bAddIngredientes = (Button) findViewById(R.id.addIngredientesRecetaResult);
        bAddIngredientes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent();
                i.putExtra(getResources().getString(R.string.add_Ingredientes_Intent),new CustomListIng(lContentIngredientes));
                setResult(Activity.RESULT_OK,i);
                finish();
            }
        });
        myContext=this;


        list = (ListView) findViewById(R.id.addRecetalistViewIngreDientes);
        list.setAdapter(aContentAdapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                IngredienteReceta ingSeleceted = (IngredienteReceta) list.getItemAtPosition(i);
                ingSeleceted = getIngredienteReceta(ingSeleceted.getId());
                android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(myContext);
                // Get the layout inflater
                LayoutInflater inflater = getLayoutInflater();
                View v = inflater.inflate(R.layout.dialog_ingrediente_sub_receta, null);
                ListView listSubs = (ListView) v.findViewById(R.id.RecetaSubsShow);
                listSubs.setClickable(false);
                List<Ingrediente> listaux = ingSeleceted.getSubstitutivos();
                ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(), R.layout.row_ingrediente_adding, ingSeleceted.getSubstitutivos());
                listSubs.setAdapter(adapter);
                builder.setTitle(R.string.dialog_Receta_Substitutivos_title);
                builder.setView(v)

                        // Add action buttons
                        .setPositiveButton("ACEPTAR", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {


                            }
                        });
                builder.create().show();
            }
        });
        registerForContextMenu(list);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    // 1. Instantiate an AlertDialog.Builder with its constructor
                    AlertDialog.Builder builder = new AlertDialog.Builder(myContext);

                    // 2. Chain together various setter methods to set the dialog characteristics
                    builder.setTitle(R.string.dialog_add_titulo);

                    // Get the layout inflater
                    LayoutInflater inflater = (LayoutInflater) myContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

                    dialogLayout = inflater.inflate(R.layout.fragment_dialog, null);
                    final ListView listAdding = (ListView) dialogLayout.findViewById(R.id.addInglistView);

                    listAdding.setAdapter(aAdapter);
                    listAdding.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                            IngredienteReceta ing = (IngredienteReceta)listAdding.getItemAtPosition(i);
                            lContentIngredientes.add(ing);
                            lIngredientes.remove(ing);
                            refresList();
                        }
                    });
                    // Inflate and set the layout for the dialog
                    // Pass null as the parent view because its going in the dialog layout
                    builder.setView(dialogLayout)
                            .setPositiveButton(R.string.dialog_add_ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int id) {

                                }
                            }).setNegativeButton(R.string.dialog_add_cancel, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                            dialog.cancel();
                            dialog.dismiss();
                        }
                    });
                    // 3. Get the AlertDialog from create()
                    dialog = builder.create();
                    dialog.show();

            }
        });
    }

    public IngredienteReceta getIngredienteReceta(int id) {
        for(IngredienteReceta ing: lContentIngredientes){
            if(ing.getId()==id) return ing;
        }
        return null;
    }

    private List<IngredienteReceta> reduceIngregientes() {
        List<IngredienteReceta> lisAux=new ArrayList<>();
        for (IngredienteReceta ingRec:lIngredientes){
                Boolean is=false;
                for(Ingrediente ingRecaux:lContentIngredientes){
                  if(ingRec.getId()==ingRecaux.getId()) is=true;
                }
            if(!is) lisAux.add(ingRec);

        }
        return lisAux;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v.getId() == R.id.addRecetalistViewIngreDientes) {
            ListView lv = (ListView) v;
            AdapterView.AdapterContextMenuInfo acmi = (AdapterView.AdapterContextMenuInfo) menuInfo;
            ingSelected = (IngredienteReceta) lv.getItemAtPosition(acmi.position);
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.menu_contect_add_recetas, menu);


        }
    }

    private void borrarItem(){
        android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(myContext);
        builder.setTitle(R.string.add_Ingredientes_delete_title);
        builder.setPositiveButton(R.string.add_Ingredientes_delete_borrar, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                lIngredientes.add(ingSelected);
                lContentIngredientes.remove(ingSelected);
                refresList();
            }
        }).setNegativeButton(R.string.dialog_add_cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        }).create().show();
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.actionBorrar:
                borrarItem();
                return true;
            case R.id.actionSubstitus:
                irSelectSubs();
                return true;

            default:
                return super.onContextItemSelected(item);
        }

    }

    private IngredienteReceta searchIngredienteReceta(int id){
        IngredienteReceta ing;
        for(IngredienteReceta aux: lContentIngredientes){
            if (aux.getId()==id) return aux;
        }
        return null;
    }
    private void irSelectSubs(){
        Intent intent = new Intent(getApplicationContext(), addSubs.class);
        intent.putExtra(getResources().getString(R.string.add_Ingredientes_Subs_Intent),searchIngredienteReceta(ingSelected.getId()));
        startActivityForResult(intent, INGREDIENTES_SUB);
    }

    private void updateSubs(IngredienteReceta ingRec){
        int id=ingRec.getId();
        for(IngredienteReceta aux: lContentIngredientes){
            if (aux.getId()==id) {
                aux.setSubstitutivos(ingRec.getSubstitutivos());
            }
        }
    }

    private void refresList(){
        //set the changed data

        //notify that the model changed
        aAdapter.notifyDataSetChanged();
        aContentAdapter.notifyDataSetChanged();


    }

    }
