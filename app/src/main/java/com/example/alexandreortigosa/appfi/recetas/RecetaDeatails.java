package com.example.alexandreortigosa.appfi.recetas;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.text.method.KeyListener;
import android.text.method.ScrollingMovementMethod;
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
import java.io.FileNotFoundException;
import java.io.InputStream;

public class RecetaDeatails extends Fragment implements View.OnLongClickListener{

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static String STATE = "STATUSRECETA";
    public static final String STATE_EDIT = "EDITANDO";
    public static final String STATE_ADD = "ANADIENDO";
    public static final String STATE_SHOW="SHOW";
    private static final String NAME="NOMBRE";
    private static final String DESC="DESCRIP";
    private static final String PHOTO="PHOTO";
    static final String STATE_RECETA = "receta";
    private static final int SELECT_PHOTO = 100;
    private String STATUS;
    private View myFragmentView;
    TextView  nombre;
    TextView  desc;
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

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch(requestCode) {
            case SELECT_PHOTO:
                if(resultCode == Activity.RESULT_OK){

                    try {
                        Uri selectedImage = data.getData();
                        receta.setPhoto(selectedImage.toString());
                        receta.guardarPhoto(getActivity().getApplicationContext());
                        InputStream imageStream = getActivity().getApplicationContext().getContentResolver().openInputStream(selectedImage);
                        Bitmap yourSelectedImage = BitmapFactory.decodeStream(imageStream);
                        photo.setImageBitmap(yourSelectedImage);


                    }
                    catch (FileNotFoundException e){
                        e.printStackTrace();
                    }

                }
                break;

        }
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
        if(savedInstanceState!=null)
            receta=(Receta)savedInstanceState.getSerializable(STATE_RECETA);
        myFragmentView=inflater.inflate(R.layout.content_receta_deatails, container, false);
        nombre=(TextView ) myFragmentView.findViewById(R.id.DetalleRecetaName);
        desc=(TextView ) myFragmentView.findViewById(R.id.DetalleRecetaDesc);
        photo=(ImageView) myFragmentView.findViewById(R.id.DetalleRecetaPhoto);
        kName=nombre.getKeyListener();
        kDesc=desc.getKeyListener();
        desc.setMovementMethod(new ScrollingMovementMethod());
        nombre.setOnLongClickListener(this);
        desc.setOnLongClickListener(this);
        photo.setOnLongClickListener(this);
        setInfo();

        return myFragmentView;

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(savedInstanceState!=null)
            receta=(Receta)savedInstanceState.getSerializable(STATE_RECETA);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(STATE_RECETA,receta);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

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

    public void setInfo()
    {
        nombre.setText(receta.getName());
        String descrip=receta.getDescripccio();
        if(descrip!=null && descrip.replace(" ","").length()>0){
            desc.setText(receta.getDescripccio());
        }
        else desc.setText(getResources().getString(R.string.receta_detalles_empty_desc));

        desc.setText(receta.getDescripccio());
        if(receta.getPhoto()!=null)
            photo.setImageBitmap(RecetasAdapter.decodeSampledBitmapFromFile(getActivity().getApplicationContext(),receta.getPhoto(),200,200));

        else {
            photo.setImageBitmap(RecetasAdapter.decodeSampledBitmapFromResource(getResources(),R.drawable.addcamera,200,200));
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

    @Override
    public boolean onLongClick(View view) {
        switch (view.getId()){
            case R.id.DetalleRecetaName:
                editConfirmation(R.id.DetalleRecetaName,NAME);
                break;
            case R.id.DetalleRecetaDesc:
                editConfirmation(R.id.DetalleRecetaDesc, DESC);
                break;
            case R.id.DetalleRecetaPhoto:
                editConfirmation(R.id.DetalleRecetaPhoto, PHOTO);
                break;

            default:
                break;
        }
        return false;
    }

    private void editConfirmation(final int idResorce, String MODE){

        final Boolean[] respuesta = {false};
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        switch (MODE){
            case (NAME):
                builder.setTitle(R.string.dialog_Receta_Edit_Name_check);
                break;
            case (DESC):
                builder.setTitle(R.string.dialog_Receta_Edit_Descripcion_check);
                break;
            case (PHOTO):
                builder.setTitle(R.string.dialog_Receta_Edit_Photo_check);
                break;
            default:
                break;

        }

        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
               launchAction(idResorce);

            }
        })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
        builder.create().show();



    }

    private void launchAction(int idResource){
        switch (idResource) {
            case R.id.DetalleRecetaName:
                launcEditDialog(R.id.DetalleRecetaName, NAME).show();
                break;
            case R.id.DetalleRecetaDesc:
                launcEditDialog(R.id.DetalleRecetaDesc, DESC).show();
                break;
            case R.id.DetalleRecetaPhoto:
                selectImage();
                break;

            default:
                break;
        }
    }

    private void selectImage(){
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, SELECT_PHOTO);
    }
    private AlertDialog launcEditDialog(int idResource, String MODE){
        final TextView in = (TextView) myFragmentView.findViewById(idResource);
        final String MODEAUX =MODE;
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_edit_receta, null);
        final EditText edit=(EditText)v.findViewById(R.id.dialogEditTextReceta);
        if(MODEAUX==NAME){
            edit.setMaxLines(1);
            edit.setLines(1);
            builder.setTitle(R.string.dialog_Receta_Edit_Name_Title);
        }
        else{
            edit.setMaxLines(20);
            edit.setLines(20);
            builder.setTitle(R.string.dialog_Receta_Edit_Descripcion_Title);
        }
        edit.setText(in.getText());
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout

        builder.setView(v)

                // Add action buttons
                .setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        in.setText(edit.getText());
                        switch (MODEAUX) {
                            case NAME:
                                receta.changeName(getActivity().getApplicationContext(), edit.getText().toString());
                                break;
                            case DESC:
                                receta.changeDescripcio(getActivity().getApplicationContext(), edit.getText().toString());
                                break;
                            default:
                                break;

                        }

                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
        return builder.create();
    }


    public interface OnFragmentInteractionListener {
       // TODO: Update argument type and name
       public void onFragmentInteractionMem(Uri uri);
       public void onFragmentInteractionFinished();
       public void onFragmentInteraction(Uri uri);
   }
}
