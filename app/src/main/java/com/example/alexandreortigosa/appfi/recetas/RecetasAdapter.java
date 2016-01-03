package com.example.alexandreortigosa.appfi.recetas;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
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
public class RecetasAdapter extends ArrayAdapter<Receta> {
    Context context;
    int layoutResourceId;
    List<Receta> recetas;

    public RecetasAdapter(Context context, int resource, List<Receta> objects) {
        super(context, resource, objects);
        this.context=context;
        layoutResourceId=resource;
        recetas=objects;
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
            holder.nombre=(TextView)row.findViewById(R.id.RecetaListName);
            holder.imgReceta=(ImageView)row.findViewById(R.id.RecetaListPhoto);

            row.setTag(holder);
        }
        else{
            holder=(RecetaHolder)row.getTag();
        }

        Receta receta = recetas.get(position);
        Drawable drawable = context.getResources().getDrawable(R.drawable.addcamera);
        holder.imgReceta.setImageDrawable(drawable);
        if (holder.imgReceta != null && receta.getPhoto()!=null) {
            Uri selectedImage = Uri.parse(receta.getPhoto());
            InputStream imageStream = null;
            try {
                imageStream = context.getContentResolver().openInputStream(selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
           holder.imgReceta.setImageBitmap(BitmapFactory.decodeStream(imageStream));
            //new setImagesListTask(holder.imgReceta,context).execute(receta.getPhoto());
        }
        holder.nombre.setText(receta.getName());
        //holder.desc.setText(receta.getDescripccio().substring(0,25));
        return row;
    }

    static class RecetaHolder{
        ImageView imgReceta;
        TextView nombre;
        //TextView desc;

    }
}
