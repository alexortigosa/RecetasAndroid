package com.example.alexandreortigosa.appfi.recetas;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
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
        /*Drawable drawable = context.getResources().getDrawable(R.drawable.addcamera);
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
        }*/
        //new setImagesListTask(holder.imgReceta,context).execute(receta.getPhoto());
        holder.nombre.setText(receta.getName());
        //holder.desc.setText(receta.getDescripccio().substring(0,25));
        if(receta.getPhoto()!=null)
            holder.imgReceta.setImageBitmap(decodeSampledBitmapFromFile(context,receta.getPhoto(),100,100));
        else holder.imgReceta.setImageBitmap(decodeSampledBitmapFromResource(context.getResources(),R.drawable.addcamera,100,100));

        return row;
    }

    static class RecetaHolder{
        ImageView imgReceta;
        TextView nombre;
        //TextView desc;

    }
    public static Bitmap decodeSampledBitmapFromFile(Context context,String path,
                                                     int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        Uri selectedImage = Uri.parse(path);
        InputStream imageStream = null;
        try {
            imageStream = context.getContentResolver().openInputStream(selectedImage);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        options.inJustDecodeBounds = true;
        //BitmapFactory.decodeStream(imageStream,null,options);
        BitmapFactory.decodeFile(path,options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        //return BitmapFactory.decodeStream(imageStream, null, options);
        return BitmapFactory.decodeFile(path, options);
    }

    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }
    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                         int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }
}
