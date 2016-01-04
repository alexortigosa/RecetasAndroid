package com.example.alexandreortigosa.appfi.recetas;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by referralsLoLGlobal on 02/01/2016.
 */
public class IngredientesRecetaAdapterAdd extends ArrayAdapter<IngredienteReceta> {
    Context context;
    int layoutResourceId;
    List<IngredienteReceta> ingredientes;

    public IngredientesRecetaAdapterAdd(Context context, int resource, List<IngredienteReceta> objects) {
        super(context, resource, objects);
        this.context=context;
        layoutResourceId=resource;
        ingredientes=objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row=convertView;
        RecetaHolder holder;

        if(row==null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
            row=inflater.inflate(layoutResourceId,parent,false);

            holder= new RecetaHolder();
            //holder.desc=(TextView)row.findViewById(R.id.RecetaListDesc);
            holder.nombre=(TextView)row.findViewById(R.id.IngredienteRecetaNewNameAdd);


            row.setTag(holder);
        }
        else{
            holder=(RecetaHolder)row.getTag();
        }

        Ingrediente ing = ingredientes.get(position);
        Drawable drawable = context.getResources().getDrawable(R.drawable.addcamera);

        holder.nombre.setText(ing.getName());
        //holder.desc.setText(receta.getDescripccio().substring(0,25));
        return row;
    }

    static class RecetaHolder{
        //ImageView imgReceta;
        TextView nombre;
        //TextView desc;

    }
}
