package com.example.alexandreortigosa.appfi.recetas;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import java.util.List;

public class addSubs extends AppCompatActivity {

    private ListView list;
    private SimpleCursorAdapter dataAdapter;
    private gestDB gesdb;
    private Context myContext;
    private AlertDialog dialog;
    private View dialogLayout;
    private ArrayAdapter<Ingrediente> aAdapter;
    private ArrayAdapter<Ingrediente> aContentAdapter;
    private List<IngredienteReceta> lIngredientes;
    private List<IngredienteReceta> lContentIngredientes;
    private Button bAddIngredientes;
    private IngredienteReceta ingSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_subs);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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

    private void refresList(){
        //set the changed data

        //notify that the model changed
        aAdapter.notifyDataSetChanged();
        aContentAdapter.notifyDataSetChanged();


    }

}
