package com.example.alexandreortigosa.appfi.recetas;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class addReceta1 extends Activity implements View.OnClickListener{

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
    private gestDB gesdb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        receta=new Receta();
        setContentView(R.layout.activity_add_receta1);
        lIngredientes= new ArrayList();
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

                    try {
                        Uri selectedImage = imageReturnedIntent.getData();
                        receta.setPhoto(selectedImage.toString());
                        InputStream imageStream = getContentResolver().openInputStream(selectedImage);
                        Bitmap yourSelectedImage = BitmapFactory.decodeStream(imageStream);
                        imgView.setImageBitmap(yourSelectedImage);

                    }
                    catch (FileNotFoundException e){
                        e.printStackTrace();
                    }

                }
                break;
            case INGREDIENTES_ADD:
                if(resultCode==Activity.RESULT_OK){
                   CustomListIng lIngredientesaux = (CustomListIng) imageReturnedIntent.getSerializableExtra(getResources().getString(R.string.add_Ingredientes_Intent));
                    lIngredientes=lIngredientesaux.getIngredientes();
                }
        }
    }
}
