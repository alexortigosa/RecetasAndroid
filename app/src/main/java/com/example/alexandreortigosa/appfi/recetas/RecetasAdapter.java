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
public class RecetasAdapter extends ArrayAdapter<Receta> implements Filterable {
    Context context;
    int layoutResourceId;
    List<Receta> recetas;
    List<Receta> filterRecetas;
    private ItemFilter mFilter = new ItemFilter();

    public RecetasAdapter(Context context, int resource, List<Receta> objects) {
        super(context, resource, objects);
        this.context = context;
        layoutResourceId = resource;
        recetas = objects;
        filterRecetas = objects;
    }

    public int getCount() {
        return recetas.size();
    }

    public Receta getItem(int position) {
        return recetas.get(position);
    }

    public long getItemId(int position) {
        return recetas.indexOf(getItem(position));
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        RecetaHolder holder;

        if (row == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            row = inflater.inflate(layoutResourceId, parent, false);

            holder = new RecetaHolder();
            //holder.desc=(TextView)row.findViewById(R.id.RecetaListDesc);
            holder.nombre = (TextView) row.findViewById(R.id.RecetaListName);
            holder.imgReceta = (ImageView) row.findViewById(R.id.RecetaListPhoto);

            row.setTag(holder);
        } else {
            holder = (RecetaHolder) row.getTag();
        }

        Receta receta = recetas.get(position);
        holder.nombre.setText(receta.getName());
        String photo=receta.getPhoto();
        if (photo != null) {
            if (photo.contains("/Custom/id/")){
                switch (photo.substring(photo.length() - 1)){
                    case ("0"):
                        holder.imgReceta.setImageBitmap(decodeSampledBitmapFromResource(context.getResources(), R.drawable.photo1, 100, 100));
                        break;
                    case ("1"):
                        holder.imgReceta.setImageBitmap(decodeSampledBitmapFromResource(context.getResources(), R.drawable.photo2, 100, 100));
                        break;
                    case ("2"):
                        holder.imgReceta.setImageBitmap(decodeSampledBitmapFromResource(context.getResources(), R.drawable.photo3, 100, 100));
                        break;
                    case ("3"):
                        holder.imgReceta.setImageBitmap(decodeSampledBitmapFromResource(context.getResources(), R.drawable.photo4, 100, 100));
                        break;
                    case ("4"):
                        holder.imgReceta.setImageBitmap(decodeSampledBitmapFromResource(context.getResources(), R.drawable.photo5, 100, 100));
                        break;
                    case ("5"):
                        holder.imgReceta.setImageBitmap(decodeSampledBitmapFromResource(context.getResources(), R.drawable.photo6, 100, 100));
                        break;
                    default:
                        break;
                }

            }
            else holder.imgReceta.setImageBitmap(decodeSampledBitmapFromFile(context, photo, 100, 100));
        }
        else  holder.imgReceta.setImageBitmap(decodeSampledBitmapFromResource(context.getResources(), R.drawable.camera, 100, 100));

        return row;
    }

    static class RecetaHolder {
        ImageView imgReceta;
        TextView nombre;
        //TextView desc;

    }

    public static Bitmap decodeSampledBitmapFromFile(Context context, String path,
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
        BitmapFactory.decodeFile(path, options);

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

    @Override
    public Filter getFilter() {
        if (mFilter == null) {
            mFilter = new ItemFilter();
        }
        return mFilter;
    }

    private class ItemFilter extends Filter {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            String filterString = constraint.toString().toLowerCase();

            FilterResults results = new FilterResults();

            if (constraint != null && constraint.length() > 0) {
                List<Receta> nlist = new ArrayList<Receta>();
                String filterableString;

                for (Receta ing : filterRecetas
                        ) {
                    filterableString = ing.getName().toLowerCase();
                    if (filterableString.toLowerCase().contains(filterString)) {
                        Receta ingaux = new Receta(ing.getId(), ing.getName(), ing.getDescripccio(), ing.getPhoto());
                        nlist.add(ingaux);
                    }

                }

                results.values = nlist;
                results.count = nlist.size();

            } else {
                results.count = filterRecetas.size();
                results.values = filterRecetas;
            }


            return results;
        }

        @SuppressWarnings("unchecked")
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            recetas = (List<Receta>) results.values;
            notifyDataSetChanged();
        }
    }
}
