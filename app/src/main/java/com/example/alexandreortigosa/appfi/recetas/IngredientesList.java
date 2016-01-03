package com.example.alexandreortigosa.appfi.recetas;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.app.ListActivity;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

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
    private Receta receta;
    private String STATUS;


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
    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS;
    }

    public void setReceta(Receta receta) {
        this.receta = receta;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myFragmentView=inflater.inflate(R.layout.activity_ingredientes_list, container, false);
        list = (ListView) myFragmentView.findViewById(R.id.listaingredientes);
        switch (STATUS){
            case RecetaDeatails.STATE_ADD:
                break;
            case RecetaDeatails.STATE_EDIT:
                setList();
                break;
            case RecetaDeatails.STATE_SHOW:
                setList();
                break;
            default:
                break;
        }

        return myFragmentView;

    }

    private void setList(){

        gesdb=new gestDB(getActivity().getApplicationContext());
        gesdb.open();
        /*Cursor cursor = gesdb.fetchAllIngredientesReceta(receta.getId());
        //startManagingCursor(cursor);
        String[] columns = new String[]{gestDB.Ingredientes.ID_INGREDIENTE,gestDB.Ingredientes.NOMBRE};
        int[] to = new int[]{R.id.idIngrediente,R.id.nameIngrediente};
        dataAdapter = new SimpleCursorAdapter(getActivity().getApplicationContext(),R.layout.row_ingrediente,cursor,columns,to);
        list.setAdapter(dataAdapter);*/

        iAdapter = new IngredientesRecetasAdapter(getActivity().getApplicationContext(),R.layout.row_ingrediente_new,receta.getIngredientes());
        list.setAdapter(iAdapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

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
