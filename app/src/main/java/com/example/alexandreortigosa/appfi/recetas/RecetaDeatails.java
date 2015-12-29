package com.example.alexandreortigosa.appfi.recetas;

import android.app.Activity;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.text.method.KeyListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Switch;
import android.widget.TextView;

import java.io.File;

public class RecetaDeatails extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static String STATE = "STATUSRECETA";
    public static final String STATE_EDIT = "EDITANDO";
    public static final String STATE_ADD = "ANADIENDO";
    public static final String STATE_SHOW="SHOW";
    private String STATUS;
    private View myFragmentView;
    EditText  nombre;
    EditText  desc;
    ImageView photo;
    Receta receta;
    KeyListener kName;
    KeyListener kDesc;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    private OnFragmentInteractionListener mListener;



    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RankingMem.
     */
    // TODO: Rename and change types and number of parameters
    public static RecetaDeatails newInstance(String param1, String param2) {
        RecetaDeatails fragment = new RecetaDeatails();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public RecetaDeatails() {
        // Required empty public constructor
    }

    public void setReceta(Receta receta) {
        this.receta = receta;
    }


    public void setInfoReceta(int id){

    }

    public void setSTATUS(String STATUS) {
        this.STATUS = STATUS;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        myFragmentView=inflater.inflate(R.layout.content_receta_deatails, container, false);
        nombre=(EditText ) myFragmentView.findViewById(R.id.DetalleRecetaName);
        desc=(EditText ) myFragmentView.findViewById(R.id.DetalleRecetaDesc);
        photo=(ImageView) myFragmentView.findViewById(R.id.DetalleRecetaPhoto);
        kName=nombre.getKeyListener();
        kDesc=desc.getKeyListener();


        setStyle();
        switch (STATUS){
            case (STATE_ADD):
                setAdd();
                break;
            case (STATE_EDIT):
                setInfo();
                break;
            case (STATE_SHOW):
                setInfo();
                break;
            default:
                break;
        }



        return myFragmentView;

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

    public void setInfo(){
        nombre.setText(receta.getName());
        desc.setText(receta.getDescripccio());
        File imgFile = new  File(receta.getPhoto());

        if(imgFile.exists()){

            Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());



            photo.setImageBitmap(myBitmap);

        }
    }

    public void setAdd(){
            photo.setImageDrawable(getResources().getDrawable(R.drawable.addcamera));


    }

    public void setStyle(){
        switch (STATUS){
            case (STATE_SHOW):

                nombre.setBackgroundColor(getResources().getColor(R.color.transparent));
                nombre.setFocusable(false);
                nombre.setClickable(false);
                nombre.setFocusableInTouchMode(false);
                nombre.setEnabled(false);
                nombre.setKeyListener(null);
                desc.setBackgroundColor(getResources().getColor(R.color.transparent));
                desc.setFocusable(false);
                desc.setClickable(false);
                desc.setFocusableInTouchMode(false);
                desc.setEnabled(false);
                desc.setKeyListener(null);
                break;
            case (STATE_EDIT):
                nombre.setBackgroundColor(getResources().getColor(R.color.ColorPrimaryDark));
                nombre.setFocusable(true);
                nombre.setEnabled(true);
                nombre.setClickable(true);
                nombre.setFocusableInTouchMode(true);
                nombre.setKeyListener(kName); //You can store it in some variable and use it over here while making non editable.
                desc.setBackgroundColor(getResources().getColor(R.color.ColorPrimaryDark));
                desc.setFocusable(true);
                desc.setEnabled(true);
                desc.setClickable(true);
                desc.setFocusableInTouchMode(true);
                desc.setKeyListener(kDesc); //You can store it in some variable and use it over here while making non editable.
                break;
            case (STATE_ADD):
                nombre.setBackgroundColor(getResources().getColor(R.color.ColorPrimaryDark));
                nombre.setFocusable(true);
                nombre.setEnabled(true);
                nombre.setClickable(true);
                nombre.setFocusableInTouchMode(true);
                nombre.setKeyListener(kName); //You can store it in some variable and use it over here while making non editable.
                desc.setBackgroundColor(getResources().getColor(R.color.ColorPrimaryDark));
                desc.setFocusable(true);
                desc.setEnabled(true);
                desc.setClickable(true);
                desc.setFocusableInTouchMode(true);
                desc.setKeyListener(kDesc); //You can store it in some variable and use it over here while making non editable.
                break;
            default:
                break;
        }

    }


   public interface OnFragmentInteractionListener {
       // TODO: Update argument type and name
       public void onFragmentInteractionMem(Uri uri);
       public void onFragmentInteractionFinished();
       public void onFragmentInteraction(Uri uri);
   }
}
