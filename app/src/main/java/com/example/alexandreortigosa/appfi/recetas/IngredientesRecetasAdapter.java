package com.example.alexandreortigosa.appfi.recetas;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by referralsLoLGlobal on 02/01/2016.
 */
public class IngredientesRecetasAdapter extends ArrayAdapter<IngredienteReceta> {
    Context context;
    int layoutResourceId;
    List<IngredienteReceta> ingredientes;
    public IngredientesRecetasAdapter(Context context, int resource, List<IngredienteReceta> objects) {
        super(context, resource, objects);
        this.context=context;
        layoutResourceId=resource;
        ingredientes=objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row=convertView;
        IngredienteHolder holder;

        if(row==null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
            row=inflater.inflate(layoutResourceId,parent,false);

            holder= new IngredienteHolder();
            holder.name=(TextView)row.findViewById(R.id.IngredienteRecetaNewName);
            holder.delete=(ImageView)row.findViewById(R.id.IngredienteRecetaNewCancel);

            row.setTag(holder);
        }
        else{
            holder=(IngredienteHolder)row.getTag();
        }

        IngredienteReceta ingRec = ingredientes.get(position);
        holder.name.setText(ingRec.getName());
        return row;
    }

    static class IngredienteHolder{
        TextView name;
        ImageView delete;
    }


}
