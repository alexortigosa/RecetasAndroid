package com.example.alexandreortigosa.appfi.recetas;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by referralsLoLGlobal on 02/01/2016.
 */
public class IngredientesAdapterAdd extends ArrayAdapter<Ingrediente> implements Filterable{
    Context context;
    int layoutResourceId;
    List<Ingrediente> ingredientes;
    List<Ingrediente> filterIngredientes;
    String mode;
    public static final String MODE_ADD="ADD";
    public static final String MODE_SUB="SUB";
    public static final String MODE_ING="ING";
    private ItemFilter mFilter = new ItemFilter();


    public IngredientesAdapterAdd(Context context, int resource, List<Ingrediente> objects,String MODE) {
        super(context, resource, objects);
        this.context=context;
        layoutResourceId=resource;
        ingredientes=objects;
        filterIngredientes=objects;
        mode=MODE;
    }

    public int getCount() {
        return ingredientes.size();
    }

    public Ingrediente getItem(int position) {
        return ingredientes.get(position);
    }

    public long getItemId(int position) {
        return ingredientes.indexOf(getItem(position));
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
                holder.imgReceta.setImageDrawable(context.getResources().getDrawable(android.R.drawable.ic_input_add));
                break;
            case MODE_SUB:
                holder.imgReceta.setImageDrawable(context.getResources().getDrawable(android.R.drawable.ic_input_delete));
                break;
            case MODE_ING:
                holder.imgReceta.setVisibility(View.INVISIBLE);
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

    @Override
    public Filter getFilter() {
        if (mFilter==null) {
            mFilter=new ItemFilter();
        }
        return mFilter;
    }

    private class ItemFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            String filterString = constraint.toString().toLowerCase();

            FilterResults results = new FilterResults();

            if(constraint != null && constraint.length() > 0){
                List<Ingrediente> nlist = new ArrayList<Ingrediente>();
                String filterableString ;

                for (Ingrediente ing:filterIngredientes
                        ) {
                    filterableString = ing.getName().toLowerCase();
                    if (filterableString.toLowerCase().contains(filterString)) {
                        Ingrediente ingaux = new Ingrediente(ing.getName(),ing.getId());
                        nlist.add(ingaux);
                    }

                }

                results.values = nlist;
                results.count = nlist.size();

            }
            else
            {
                results.count = filterIngredientes.size();
                results.values = filterIngredientes;
            }


            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            ingredientes = (List<Ingrediente>) results.values;
            notifyDataSetChanged();
        }

    }
}
