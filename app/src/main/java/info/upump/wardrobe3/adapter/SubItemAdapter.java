package info.upump.wardrobe3.adapter;


import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import info.upump.wardrobe3.R;
import info.upump.wardrobe3.SubFragment;
import info.upump.wardrobe3.dialog.Constants;
import info.upump.wardrobe3.dialog.MainItemDialog;
import info.upump.wardrobe3.dialog.OperationAsync;
import info.upump.wardrobe3.dialog.SubItemDialog;
import info.upump.wardrobe3.model.EnumMask;
import info.upump.wardrobe3.model.SubItem;
import info.upump.wardrobe3.model.SubItemViewHolder;

public class SubItemAdapter extends RecyclerView.Adapter<SubItemViewHolder> implements MaskCreator {
    protected List<SubItem> subItemList;
    protected AppCompatActivity activity;
    protected static final Bitmap DEAFAULT_PHOTO = Bitmap.createBitmap(300, 300,
            Bitmap.Config.ARGB_8888);
    protected int enumMaskOrdinal = 0;
    protected static final int MASK_WIDTH = 300;
    protected static final int MASK_HEIGHT = 300;

    public SubItemAdapter(List<SubItem> subItemList, Activity activity, int enumMask) {
        this.subItemList = subItemList;
        this.activity = (AppCompatActivity) activity;
        System.out.println("enumMask " + enumMask);
        this.enumMaskOrdinal = enumMask;
    }

    @Override
    public SubItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card_sub_item, parent, false);
        return new SubItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final SubItemViewHolder holder, final int position) {
        holder.imageView.setImageBitmap(createMask(subItemList.get(position)));
        holder.textView.setText(subItemList.get(position).getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SubItem subItem = subItemList.get(position);

                Bundle bundle = new Bundle();
                bundle.putInt(OperationAsync.OPERATION, OperationAsync.OPEN);
                bundle.putLong(Constants.ID_PARENT, subItem.getIdMainItem());
                bundle.putLong(Constants.ID, subItem.getId());
                bundle.putString(Constants.NAME, subItem.getName());
                bundle.putFloat(Constants.COST, subItem.getCost());
                bundle.putString(Constants.DESCRIPTION, subItem.getDescription());
                bundle.putString(Constants.IMG, subItem.getImg());

                SubItemDialog dialogFragment = new SubItemDialog();
                dialogFragment.setArguments(bundle);
                dialogFragment.show(activity.getSupportFragmentManager(), SubItemDialog.TAG);
            }
        });
    }

    @Override
    public int getItemCount() {
        return subItemList.size();
    }

    @Override
    public Bitmap createMask(SubItem subItem) {
        System.out.println("masl" + enumMaskOrdinal);
        EnumMask enumMask = EnumMask.values()[enumMaskOrdinal];
        int resourceMask = enumMask.getResource();
        System.out.println(resourceMask);
        Bitmap bitmapMask = Bitmap.createScaledBitmap(Bitmap.createBitmap(
                BitmapFactory.decodeResource(activity.getResources(), resourceMask)), MASK_WIDTH, MASK_HEIGHT, false);

        Bitmap result = Bitmap.createBitmap(bitmapMask.getWidth(), bitmapMask.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas mCanvas = new Canvas(result);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        try {
            Bitmap bitmap;
            if (subItem.getImg() != null) {
                //получаем картинку из URI и приводим ее к нужному размеру для кардвью

                try {
                    Uri uriImg = Uri.parse(subItem.getImg());
                    System.out.println(uriImg);

                    System.out.println("razr " + activity.getApplicationContext().checkCallingPermission(Manifest.permission.READ_EXTERNAL_STORAGE));

                    try {
                        //   String bitmap1 = MediaStore.Images.Media.insertImage(activity.getContentResolver(), uriImg.getPath(),"n","desd");
                        //   System.out.println("tumbmail "+bitmap1);
                        System.out.println(uriImg.getPath());
                        System.out.println(uriImg.getEncodedPath());
                        System.out.println(uriImg.getEncodedSchemeSpecificPart());
                        System.out.println(uriImg.getSchemeSpecificPart());
                        System.out.println(uriImg.getScheme());
                        System.out.println(uriImg.getEncodedFragment());
                        System.out.println(uriImg.getHost());
                        System.out.println(uriImg.getQueryParameter("content"));
                        List<String> pathSegments = uriImg.getPathSegments();
                        for (String s : pathSegments) {
                            System.out.println(s);
                        }
                        bitmap = MediaStore.Images.Media.getBitmap(activity.getContentResolver(), uriImg);

                        //    bitmap = MediaStore.Images.Media.getContentUri(uriImg.toString());
                    } catch (java.lang.SecurityException e) {
                        System.out.println("ебаные фоточки гугла");
                        bitmap = DEAFAULT_PHOTO;
                        bitmap.eraseColor(Color.BLUE);
                    }
                    //   bitmap = Bitmap.createScaledBitmap(bitmap1, MASK_HEIGHT, MASK_WIDTH, true);
                } catch (FileNotFoundException e) {
                    bitmap = DEAFAULT_PHOTO;
                    bitmap.eraseColor(Color.BLUE);
                }
            } else {
                bitmap = DEAFAULT_PHOTO;
                bitmap.eraseColor(Color.BLUE);
            }
            mCanvas.drawBitmap(bitmap, 0, 0, null);
            mCanvas.drawBitmap(bitmapMask, 0, 0, paint);
            // paint.setXfermode(null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

}
