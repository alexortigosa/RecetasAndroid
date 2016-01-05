package com.example.alexandreortigosa.appfi.recetas;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.security.PublicKey;
import java.util.List;

/**
 * Created by referralsLoLGlobal on 02/01/2016.
 */
public class IngredientesRecetaAdapterAdd extends ArrayAdapter<IngredienteReceta> {
    Context context;
    int layoutResourceId;
    List<IngredienteReceta> ingredientes;
    public static final String MODE_ADD="ADDING";
    public static final String MODE_OPT="OPTIONS";
    String mode;

    public IngredientesRecetaAdapterAdd(Context context, int resource, List<IngredienteReceta> objects, String MODE) {
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
        Drawable drawable=context.getResources().getDrawable(android.R.drawable.ic_input_add);;
        switch (mode){
            case MODE_ADD:
                 drawable = context.getResources().getDrawable(android.R.drawable.ic_input_add);
                break;
            case MODE_OPT:
                drawable = context.getResources().getDrawable(android.R.drawable.ic_input_get);
                break;
            default:
                break;
        }

        holder.imgReceta.setBackground(drawable);
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
