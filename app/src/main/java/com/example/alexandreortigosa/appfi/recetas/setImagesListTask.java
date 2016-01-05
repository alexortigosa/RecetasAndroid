package com.example.alexandreortigosa.appfi.recetas;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.ref.WeakReference;

/**
 * Created by referralsLoLGlobal on 02/01/2016.
 */


public class setImagesListTask extends AsyncTask<String, Void, Bitmap> {
    private final WeakReference<ImageView> imageViewReference;
    private final WeakReference<Context> contextReference;
    final int THUMBSIZE = 64;


    public setImagesListTask(ImageView imageView, Context context) {
        imageViewReference = new WeakReference<ImageView>(imageView);
        contextReference = new WeakReference<Context>(context);

    }

    /*public setImagesListTask(ImageView imageView) {
        imageViewReference = new WeakReference<ImageView>(imageView);


    }*/

    @Override
    protected Bitmap doInBackground(String... params) {
        //Bitmap yourSelectedImage = null;

            /*if (params[0] != null) {
                Uri selectedImage = Uri.parse(params[0]);
                InputStream imageStream = null;
                try {
                    imageStream = contextReference.get().getContentResolver().openInputStream(selectedImage);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                yourSelectedImage = BitmapFactory.decodeStream(imageStream);


                /*Bitmap ThumbImage = ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(params[0]),
                        THUMBSIZE, THUMBSIZE);*/

            //}


        //return yourSelectedImage;
        if(params[0]!=null)
            return decodeSampledBitmapFromFile(contextReference.get(),params[0],100,100);
        else return decodeSampledBitmapFromResource(contextReference.get().getResources(),R.drawable.addcamera,100,100);


    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        if (isCancelled()) {
            bitmap = null;
        }

        if (imageViewReference != null) {
            ImageView imageView = imageViewReference.get();
            if (imageView != null) {
                if (bitmap != null) {
                    imageView.setImageBitmap(bitmap);
                }
            }
        }
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