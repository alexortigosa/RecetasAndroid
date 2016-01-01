package com.example.alexandreortigosa.appfi.recetas;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class addReceta1 extends Activity implements View.OnClickListener{

    EditText eName;
    EditText eDesc;
    ImageView imgView;
    Button bChecked;
    Button bCancel;
    private static final int SELECT_PHOTO = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_receta1);
        eName = (EditText)findViewById(R.id.addReceta_Name);
        eDesc = (EditText)findViewById(R.id.addReceta_Desc);
        imgView = (ImageView) findViewById(R.id.addRecetaPhoto);
        imgView.setImageDrawable(getResources().getDrawable(R.drawable.addcamera));
        imgView.setOnClickListener(this);
        bChecked=(Button) findViewById(R.id.addReceta_checkedButton);
        bCancel=(Button) findViewById(R.id.addReceta_cancelButton);
        bChecked.setOnClickListener(this);
        bCancel.setOnClickListener(this);


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
            default:
                break;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);

        switch(requestCode) {
            case SELECT_PHOTO:
                if(resultCode == RESULT_OK){

                    try {
                        Uri selectedImage = imageReturnedIntent.getData();
                        InputStream imageStream = getContentResolver().openInputStream(selectedImage);
                        Bitmap yourSelectedImage = BitmapFactory.decodeStream(imageStream);
                        imgView.setImageBitmap(yourSelectedImage);

                    }
                    catch (FileNotFoundException e){
                        e.printStackTrace();
                    }

                }
        }
    }
}
