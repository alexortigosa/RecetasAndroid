package com.example.alexandreortigosa.appfi.recetas;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class addRecetaNew extends AppCompatActivity implements View.OnClickListener{

    EditText eName;
    EditText eDesc;
    ImageView imgView;
    Button bChecked;
    Button bCancel;
    Button bIngredientes;
    Button bSave;
    Receta receta;
    List<IngredienteReceta> lIngredientes;
    private static final int SELECT_PHOTO = 100;
    private static final int INGREDIENTES_ADD = 102;
    private static final String RECETA = "receta";
    private static final String LISTA = "lista";
    private gestDB gesdb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(savedInstanceState!=null){
            receta=(Receta)savedInstanceState.getSerializable(RECETA);
            CustomListIng aux = (CustomListIng)savedInstanceState.getSerializable(LISTA);
            lIngredientes=aux.getIngredientes();
        }
        else{
            receta=new Receta();
            lIngredientes= new ArrayList();
        }
        setContentView(R.layout.activity_add_receta_new);
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

        eName = (EditText)findViewById(R.id.addReceta_Name);
        eName.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                if(keyEvent.getKeyCode()==KeyEvent.KEYCODE_ENTER){
                    hideSoftKeyboard();
                    return true;
                }
                return false;
            }
        });
        eDesc = (EditText)findViewById(R.id.addReceta_Desc);
        imgView = (ImageView) findViewById(R.id.addRecetaPhoto);
        imgView.setImageDrawable(getResources().getDrawable(R.drawable.addcamera));
        imgView.setOnClickListener(this);
        bChecked=(Button) findViewById(R.id.addReceta_checkedButton);
        bCancel=(Button) findViewById(R.id.addReceta_cancelButton);
        bIngredientes=(Button) findViewById(R.id.addReceta_ButtonIng);
        bChecked.setOnClickListener(this);
        bCancel.setOnClickListener(this);
        bIngredientes.setOnClickListener(this);
        bSave=(Button)findViewById(R.id.addReceta_ButtonAdd);
        bSave.setOnClickListener(this);


    }

    private void selectImage(){
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, SELECT_PHOTO);
    }

    public void hideSoftKeyboard() {
        if(getCurrentFocus()!=null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putSerializable(RECETA, receta);
        outState.putSerializable(LISTA,new CustomListIng(lIngredientes));
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.addRecetaPhoto:
                selectImage();
                break;
            case R.id.addReceta_checkedButton:
                hideSoftKeyboard();
                break;
            case R.id.addReceta_cancelButton:
                eDesc.setText("");
                eDesc.setHint(R.string.addReceta_Desc);
                break;
            case R.id.addReceta_ButtonIng:
                Intent intent = new Intent(getApplicationContext(), addIngredientesReceta.class);
                intent.putExtra(getResources().getString(R.string.add_Ingredientes_Intent),new CustomListIng(lIngredientes));
                startActivityForResult(intent, INGREDIENTES_ADD);
                break;
            case R.id.addReceta_ButtonAdd:
                guardarReceta();
                break;
            default:
                break;
        }
    }

    private void guardarReceta(){
        receta.setName(eName.getText().toString());
        receta.setDescripccio(eDesc.getText().toString());
        receta.setIngredientes(lIngredientes);
        gesdb=new gestDB(getApplicationContext());
        gesdb.open();
        //gesdb.guardarReceta(receta);
        gesdb.insertReceta(receta);
        Intent i = new Intent();
        setResult(Activity.RESULT_OK, i);
        finish();
    }

    private void setImage(){

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        switch(requestCode) {
            case SELECT_PHOTO:
                if(resultCode == RESULT_OK){
                    Uri selectedImage = imageReturnedIntent.getData();
                    receta.setPhoto(getPath(selectedImage));
                    imgView.setImageBitmap(RecetasAdapter.decodeSampledBitmapFromFile(getApplicationContext(),receta.getPhoto(),200,200));
                }
                break;
            case INGREDIENTES_ADD:
                if(resultCode==Activity.RESULT_OK){
                    CustomListIng lIngredientesaux = (CustomListIng) imageReturnedIntent.getSerializableExtra(getResources().getString(R.string.add_Ingredientes_Intent));
                    lIngredientes=lIngredientesaux.getIngredientes();
                    Snackbar.make(eName, "Ingredientes añadidos", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }
        }
    }

    public String getPath(Uri uri) {
        // just some safety built in
        if( uri == null ) {
            // TODO perform some logging or show user feedback
            return null;
        }
        // try to retrieve the image from the media store first
        // this will only work for images selected from gallery
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        if( cursor != null ){
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        }
        // this is our fallback here
        return uri.getPath();
    }

}
