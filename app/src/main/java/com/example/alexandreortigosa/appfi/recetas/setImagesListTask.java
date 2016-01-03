package com.example.alexandreortigosa.appfi.recetas;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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

    @Override
    protected Bitmap doInBackground(String... params) {
        Bitmap yourSelectedImage = null;

            if (params[0] != null) {
                /*Uri selectedImage = Uri.parse(params[0]);
                InputStream imageStream = contextReference.get().getContentResolver().openInputStream(selectedImage);
                yourSelectedImage = BitmapFactory.decodeStream(imageStream);*/


                Bitmap ThumbImage = ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(params[0]),
                        THUMBSIZE, THUMBSIZE);

            }


        return yourSelectedImage;

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
}