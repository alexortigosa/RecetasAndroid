package com.example.alexandreortigosa.appfi.recetas;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by alexandreortigosa on 22/11/15.
 */
public class DatosPrueba {

    private gestDB slite;



    public DatosPrueba(gestDB ulite) {
        slite=ulite;
    }

    public void generarIngredientes(int numero){

        slite.open();
        for(int i=0; i<=numero;i++){
            slite.insertIngrediente(new Ingrediente("Ingrediente" + String.valueOf(numero)));
        }
        slite.close();


    }
    public void generarRecetas(int numero){

        String nombre = "Nombre Receta ";
        String Descripcción ="Lorem ipsum dolor sit amet, consectetur adipiscing elit. Quisque ut blandit tellus, nec lobortis odio. Nulla commodo sapien eget consequat gravida. Mauris id viverra odio. Suspendisse interdum dictum faucibus. Fusce vel feugiat dui. Quisque fermentum sed libero posuere lacinia. Cras non elit vitae arcu eleifend rhoncus. Vestibulum quis tempus dui. Aenean nibh sapien, suscipit ut commodo nec, commodo mollis neque. Aenean ultricies dolor eget massa consequat euismod.\n" +
                "\n" +
                "Integer vitae ligula et dui semper commodo. Mauris enim est, aliquam non dui a, euismod accumsan felis. Vestibulum at imperdiet est, eu euismod sem. Etiam vulputate vel risus ac ullamcorper. Ut placerat purus vitae commodo pellentesque. Donec congue dictum tellus. Donec laoreet dui in ante tincidunt scelerisque. Proin ut faucibus purus. Donec eget maximus odio, et commodo turpis. Morbi auctor enim sed dapibus pulvinar. Morbi dui magna, ornare id faucibus sed, sagittis at elit. Interdum et malesuada fames ac ante ipsum primis in faucibus. Donec at metus metus. Vestibulum gravida arcu ac purus efficitur, eu rhoncus justo luctus. Aliquam nec lobortis sem. ";
        slite.open();
        for(int i=0; i<=numero;i++){
            slite.insertReceta(new Receta(nombre+String.valueOf(i),Descripcción));
        }
        slite.close();


    }





}
