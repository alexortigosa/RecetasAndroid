package com.example.alexandreortigosa.appfi.recetas;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by referralsLoLGlobal on 02/01/2016.
 */
public class IngredientesAdapterAdd extends ArrayAdapter<Ingrediente> {
    Context context;
    int layoutResourceId;
    List<Ingrediente> ingredientes;
    String mode;
    public static final String MODE_ADD="ADD";
    public static final String MODE_SUB="SUB";


    public IngredientesAdapterAdd(Context context, int resource, List<Ingrediente> objects,String MODE) {
        super(context, resource, objects);
        this.context=context;
        layoutResourceId=resource;
        ingredientes=objects;
        mode=MODE;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row=convertView;
        RecetaHolder holder;

        if(row==null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
            row=inflater.inflate(layoutResourceId,parent,false);

            holder= new RecetaHolder();
            holder.imgReceta=(ImageView)row.findViewById(R.id.IngredienteRecetaNewCancelAdd);
            holder.nombre=(TextView)row.findViewById(R.id.IngredienteRecetaNewNameAdd);


            row.setTag(holder);
        }
        else{
            holder=(RecetaHolder)row.getTag();
        }

        Ingrediente ing = ingredientes.get(position);

        switch (mode){
            case MODE_ADD:
                holder.imgReceta.setBackground(context.getResources().getDrawable(android.R.drawable.ic_input_add));
                break;
            case MODE_SUB:
                holder.imgReceta.setBackground(context.getResources().getDrawable(android.R.drawable.ic_input_delete));
                break;
            default:
                break;

        }
        holder.nombre.setText(ing.getName());
        //holder.desc.setText(receta.getDescripccio().substring(0,25));
        return row;
    }

    static class RecetaHolder{
        ImageView imgReceta;
        TextView nombre;
        //TextView desc;

    }
}
