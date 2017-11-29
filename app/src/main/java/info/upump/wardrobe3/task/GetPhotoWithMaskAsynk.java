package info.upump.wardrobe3.task;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;

import java.io.IOException;
import java.util.List;

import info.upump.wardrobe3.mask.MaskedDrawable;
import info.upump.wardrobe3.mask.MaskedDrawablePorterDuffDstIn;
import info.upump.wardrobe3.model.SubItem;
import info.upump.wardrobe3.model.SubItemViewHolder;

/**
 * Created by explo on 29.11.2017.
 */

public class GetPhotoWithMaskAsynk extends AsyncTask<Integer,Void, Drawable> {
    private SubItemViewHolder holder;
    private List<SubItem> subItems;
    private Activity activity;
    private Bitmap bitmapMask;

    public GetPhotoWithMaskAsynk(SubItemViewHolder holder, List<SubItem> subItems, Bitmap bitmapMask, Activity activity) {
        this.holder = holder;
        this.subItems = subItems;
        this.activity  = activity;
        this.bitmapMask = bitmapMask;
    }

    @Override
    protected Drawable doInBackground(Integer... params) {
        MaskedDrawable  drawable = new MaskedDrawablePorterDuffDstIn();
        drawable.setMaskBitmap(bitmapMask);
        if (subItems.get(params[0]).getImg() != null) {
            Uri uri = Uri.parse(subItems.get(params[0]).getImg());


            System.out.println(params[0]);


            System.out.println("uri " +uri);

            try {

                Bitmap bitmapPhoto = MediaStore.Images.Media.getBitmap(activity.getContentResolver(), uri);
                drawable.setPictureBitmap(bitmapPhoto);


            } catch (IOException e) {
                e.printStackTrace();
                drawable.setPictureBitmap(null);
            }

        }else {  drawable.setPictureBitmap(null);}

        System.out.println(drawable.getIntrinsicHeight());


        return drawable;
    }


    @Override
    protected void onPostExecute(Drawable drawable) {
    //    super.onPostExecute(drawable);
        drawable.toString();
        holder.imageView.setImageDrawable(drawable);
    }
}
