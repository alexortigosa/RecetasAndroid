package com.example.alexandreortigosa.appfi.recetas;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.app.ListActivity;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class IngredientesList extends Fragment {

    private ListView list;
    private SimpleCursorAdapter dataAdapter;
    private IngredientesRecetasAdapter iAdapter;
    private gestDB gesdb;
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private View myFragmentView;
    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;
    private List<IngredienteReceta> ingredientes;
    private Receta receta;
    private String STATUS;
    private static final int INGREDIENTES_EDIT = 102;
    static final String STATE_RECETA = "receta";
    static final String STATE_LISTA = "lista";


    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(STATE_RECETA,receta);
        outState.putSerializable(STATE_LISTA,new CustomListIng(ingredientes));
        super.onSaveInstanceState(outState);

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RankingMem.
     */
    // TODO: Rename and change types and number of parameters
    public static IngredientesList newInstance(String param1, String param2) {
        IngredientesList fragment = new IngredientesList();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public IngredientesList() {
        // Required empty public constructor
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch(requestCode) {
            case INGREDIENTES_EDIT:
                if(resultCode== Activity.RESULT_OK){
                    CustomListIng lIngredientesaux = (CustomListIng) data.getSerializableExtra(getResources().getString(R.string.add_Ingredientes_Intent));
                    receta.updateIngredientes(getActivity().getApplicationContext(),lIngredientesaux.getIngredientes());
                    //receta.setIngredientes(lIngredientesaux.getIngredientes());
                    ingredientes=receta.getIngredientes();
                    setList();



                }
        }
    }

    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS;
    }

    public void setReceta(Receta receta) {
        this.receta = receta;
        ingredientes=receta.getIngredientes();
    }

    public void actualizaLista() {
        setList();
       iAdapter.notifyDataSetChanged();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if(savedInstanceState!=null){
            receta=(Receta)savedInstanceState.getSerializable(STATE_RECETA);
            CustomListIng aux = (CustomListIng)savedInstanceState.getSerializable(STATE_LISTA);
            ingredientes=aux.getIngredientes();
        }
        myFragmentView=inflater.inflate(R.layout.activity_ingredientes_list, container, false);
        list = (ListView) myFragmentView.findViewById(R.id.listaingredientes);
        setList();


        return myFragmentView;

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    private void setList(){


        iAdapter = new IngredientesRecetasAdapter(getActivity().getApplicationContext(),R.layout.row_ingrediente_new,ingredientes);
        list.setAdapter(iAdapter);
        TextView empty=(TextView)myFragmentView.findViewById(R.id.emptyListRecetasDetails);
        empty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editConfirmation();

            }
        });
        list.setEmptyView(empty);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                IngredienteReceta ingSeleceted = (IngredienteReceta) list.getItemAtPosition(i);
                ingSeleceted = receta.getIngredienteReceta(ingSeleceted.getId());
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                // Get the layout inflater
                LayoutInflater inflater = getActivity().getLayoutInflater();
                View v = inflater.inflate(R.layout.dialog_ingrediente_sub_receta, null);
                ListView listSubs = (ListView) v.findViewById(R.id.RecetaSubsShow);
                listSubs.setClickable(false);
                List<Ingrediente> listaux = ingSeleceted.getSubstitutivos();
                ArrayAdapter adapter = new ArrayAdapter(getActivity().getApplicationContext(), R.layout.row_ingrediente_adding, ingSeleceted.getSubstitutivos());
                listSubs.setAdapter(adapter);
                TextView empty=(TextView)v.findViewById(R.id.emptyListRecetasDetailsSubs);
                listSubs.setEmptyView(empty);
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
        list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                editConfirmation();
                return true;
            }
        });
        list.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                editConfirmation();

                return true;
            }
        });
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFragmentInteractionListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private void editConfirmation(){

        final Boolean[] respuesta = {false};
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.dialog_Receta_Edit_Ingredientes_check);

        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                Intent intent = new Intent(getActivity().getApplicationContext(), addIngredientesReceta.class);
                intent.putExtra(getResources().getString(R.string.add_Ingredientes_Intent),new CustomListIng(receta.getIngredientes()));
                startActivityForResult(intent, INGREDIENTES_EDIT);

            }
        })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
        builder.create().show();



    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }
}
