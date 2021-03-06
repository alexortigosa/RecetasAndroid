package com.example.alexandreortigosa.appfi.recetas;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class MainActivity extends BaseActivity implements View.OnClickListener{


    private Button bIngredientes;
    private Button bRecetas;
    private Button bAbout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bIngredientes = (Button) findViewById(R.id.button_ing);
        bIngredientes.setOnClickListener(this);
        bRecetas = (Button) findViewById(R.id.button_recetas_main);
        bRecetas.setOnClickListener(this);
        bAbout=(Button) findViewById(R.id.buttonAbout);
        bAbout.setOnClickListener(this);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.button_ing:
                goToIngredientes();
                break;
            case R.id.button_recetas_main:
                goToRecetas();
                break;
            case R.id.buttonAbout:
                goToAbout();
                break;
        }
    }

    public void goToIngredientes(){
        Intent intent = new Intent(getApplicationContext(), Ingredientes.class);
        startActivity(intent);
    }

    public void goToAbout(){
        Intent intent = new Intent(getApplicationContext(), aboutUs.class);
        startActivity(intent);
    }

    public void goToRecetasDetail(){
        Intent intent = new Intent(getApplicationContext(), RecetasContainer.class);
        startActivity(intent);
    }

    public void goToRecetas(){
        Intent intent = new Intent(getApplicationContext(), Recetas.class);
        startActivity(intent);
    }
}
