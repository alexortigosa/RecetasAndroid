package com.example.alexandreortigosa.appfi.recetas;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class addSubs extends AppCompatActivity {

    private ListView list;
    private SimpleCursorAdapter dataAdapter;
    private gestDB gesdb;
    private Context myContext;
    private AlertDialog dialog;
    private View dialogLayout;
    private IngredientesAdapterAdd aAdapter;
    private IngredientesAdapterAdd aContentAdapter;
    private List<Ingrediente> lIngredientes;
    private List<Ingrediente> lContentIngredientes;
    private Button bAddIngredientes;
    private Ingrediente ingSelected;
    private IngredienteReceta cLi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_subs);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setBackgroundColor(getResources().getColor(R.color.BackGroundColor));
        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.backlittle));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });


        Intent intent = getIntent();
        cLi = (IngredienteReceta) intent.getSerializableExtra(getResources().getString(R.string.add_Ingredientes_Subs_Intent));

        gesdb=new gestDB(getApplicationContext());
        gesdb.open();
        lContentIngredientes= cLi.getSubstitutivos();
        lIngredientes=gesdb.fetchListAllIngredientes();
        lIngredientes=reduceIngregientes();
        aAdapter = new IngredientesAdapterAdd(getApplicationContext(),R.layout.row_ingrediente_new_add,lIngredientes,IngredientesAdapterAdd.MODE_ADD);
        aContentAdapter = new IngredientesAdapterAdd(getApplicationContext(),R.layout.row_ingrediente_new_add,lContentIngredientes,IngredientesAdapterAdd.MODE_SUB);
        bAddIngredientes = (Button) findViewById(R.id.addIngredienteSubButton);
        bAddIngredientes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cLi.setSubstitutivos(lContentIngredientes);
                Intent i = new Intent();
                i.putExtra(getResources().getString(R.string.add_Ingredientes_Subs_Intent),cLi);
                setResult(Activity.RESULT_OK,i);
                finish();
            }
        });
        myContext=this;


        list = (ListView) findViewById(R.id.addIngredienteSubstitutivo);
        list.setAdapter(aContentAdapter);
        TextView empty=(TextView)findViewById(R.id.emptyListSubs);
        list.setEmptyView(empty);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ingSelected=(Ingrediente)list.getItemAtPosition(i);
                android.support.v7.app.AlertDialog.Builder builder = new android.support.v7.app.AlertDialog.Builder(myContext);
                builder.setTitle(R.string.add_Substitutivos_delete_title);
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
        });

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
                TextView empty=(TextView)dialogLayout.findViewById(R.id.emptyListDialog);
                listAdding.setEmptyView(empty);
                listAdding.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        Ingrediente ing = (Ingrediente)listAdding.getItemAtPosition(i);
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
                        });
                // 3. Get the AlertDialog from create()
                dialog = builder.create();
                dialog.show();
            }
        });
    }

    private void refresList(){
        //set the changed data

        //notify that the model changed
        aAdapter.notifyDataSetChanged();
        aContentAdapter.notifyDataSetChanged();


    }
    private List<Ingrediente> reduceIngregientes() {
        List<Ingrediente> lisAux=new ArrayList<>();
        for (Ingrediente ingRec:lIngredientes){
            Boolean is=false;
            for(Ingrediente ingRecaux:lContentIngredientes){
                if(ingRec.getId()==ingRecaux.getId()) is=true;
            }
            if(!is) lisAux.add(ingRec);

        }
        return lisAux;
    }


}
