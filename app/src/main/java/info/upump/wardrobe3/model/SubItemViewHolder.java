package info.upump.wardrobe3.model;

import android.Manifest;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.FileNotFoundException;
import java.io.IOException;

import info.upump.wardrobe3.R;
import info.upump.wardrobe3.dialog.MainItemOperationAsync;
import info.upump.wardrobe3.dialog.SubItemDialog;

/**
 * Created by explo on 02.11.2017.
 */

public class SubItemViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener{
    private static final int MASK_WIDTH = 300;
    private static final int MASK_HEIGHT = 300;
    private static final Bitmap DEFAULT_PHOTO = Bitmap.createBitmap(300, 300,
            Bitmap.Config.ARGB_8888);
    private TextView textView;
    private ImageView imageView;
    private  int enumMaskOrdinal = 0;
    private Context context;
    private SubItem subItem;
    private AppCompatActivity activity;

    public SubItemViewHolder(View itemView) {
        super(itemView);
        itemView.setOnClickListener(this);
        this.context = itemView.getContext();
        textView = itemView.findViewById(R.id.recycled_text_view_item);
        imageView = itemView.findViewById(R.id.recycled_image_item);

    }
    public void bind(SubItem subItem, int enumMaskOrdinal,AppCompatActivity activity){
        this.enumMaskOrdinal = enumMaskOrdinal;
        this.subItem = subItem;
        this.activity = activity;
        textView.setText(subItem.name);
        imageView.setImageBitmap(createMask(subItem));

    }

    private Bitmap createMask(SubItem subItem) {
        System.out.println("masl" + enumMaskOrdinal);
        EnumMask enumMask = EnumMask.values()[enumMaskOrdinal];
        int resourceMask = enumMask.getResource();
        System.out.println(resourceMask);
        Bitmap bitmapMask = Bitmap.createScaledBitmap(Bitmap.createBitmap(
                BitmapFactory.decodeResource(context.getResources(), resourceMask)), MASK_WIDTH, MASK_HEIGHT, false);

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

                    System.out.println("razr " + context.getApplicationContext().checkCallingPermission(Manifest.permission.READ_EXTERNAL_STORAGE));

                    try {

                        bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), uriImg);

                        //    bitmap = MediaStore.Images.Media.getContentUri(uriImg.toString());
                    } catch (java.lang.SecurityException e) {
                        System.out.println("ебаные фоточки гугла");
                        bitmap = DEFAULT_PHOTO;
                        bitmap.eraseColor(Color.BLUE);
                    }
                    //   bitmap = Bitmap.createScaledBitmap(bitmap1, MASK_HEIGHT, MASK_WIDTH, true);
                } catch (FileNotFoundException e) {
                    bitmap = DEFAULT_PHOTO;
                    bitmap.eraseColor(Color.BLUE);
                }
            } else {
                bitmap = DEFAULT_PHOTO;
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

    @Override
    public void onClick(View v) {
        SubItemDialog.getDetalDialog(subItem, MainItemOperationAsync.OPEN).show(activity.getSupportFragmentManager(),SubItemDialog.TAG);

    }
}
