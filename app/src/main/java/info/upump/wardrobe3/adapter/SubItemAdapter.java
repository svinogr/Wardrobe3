package info.upump.wardrobe3.adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.IOException;
import java.util.List;

import info.upump.wardrobe3.R;
import info.upump.wardrobe3.model.EnumMask;
import info.upump.wardrobe3.model.SubItem;
import info.upump.wardrobe3.model.SubItemViewHolder;

public class SubItemAdapter extends RecyclerView.Adapter<SubItemViewHolder> implements MaskCreator {
    private List<SubItem> subItemList;
    private Activity activity;
    private static final Bitmap DEAFAULT_PHOTO = Bitmap.createBitmap(300, 300,
            Bitmap.Config.ARGB_8888);


    private Bitmap bitmapMask;
    //
    private int enumMaskOrdinal = 0;
    private static final int MASK_WIDTH = 300;
    private static final int MASK_HEIGHT = 300;


    /*    public SubItemAdapter(List<SubItem> subItemList, Activity activity) {
            this.subItemList = subItemList;
            this.activity = activity;
          //  this.bitmapMask = Bitmap.createScaledBitmap(Bitmap.createBitmap(BitmapFactory.decodeResource(activity.getResources(), R.drawable.mask_circle)), 300, 300, false);
        }*/
    public SubItemAdapter(List<SubItem> subItemList, Activity activity, int enumMask) {
        this.subItemList = subItemList;
        this.activity = activity;
        this.enumMaskOrdinal = enumMask;
    }

    @Override
    public SubItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card_sub_item, parent, false);
        return new SubItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final SubItemViewHolder holder, int position) {
        //holder.imageView.setImageDrawable(null);

        //  holder.linearLayout.removeAllViews();
        //   holder.setIsRecyclable(false);
       /* Bitmap result = Bitmap.createBitmap(bitmapMask.getWidth(), bitmapMask.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas mCanvas = new Canvas(result);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));


        System.out.println("v adaptere img " + subItemList.get(position).getImg());
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(activity.getContentResolver(), Uri.parse(subItemList.get(position).getImg()));

            mCanvas.drawBitmap(bitmap, 0, 0, null);
            mCanvas.drawBitmap(bitmapMask, 0, 0, paint);
            paint.setXfermode(null);
            holder.imageView.setImageBitmap(result);
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        holder.imageView.setImageBitmap(createMask(subItemList.get(position)));

        //   GetPhotoWithMaskAsynk getPhotoWithMaskAsynk = new GetPhotoWithMaskAsynk(holder, subItemList, bitmapMask, activity);
        // getPhotoWithMaskAsynk.execute(position);


        holder.textView.setText(subItemList.get(position).getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO открытие деталей
            }
        });

    }


    @Override
    public int getItemCount() {
        return subItemList.size();
    }


    @Override
    public Bitmap createMask(SubItem subItem) {

        EnumMask enumMask = EnumMask.values()[enumMaskOrdinal];
        int resourceMask = enumMask.getResource();

        Bitmap bitmapMask = Bitmap.createScaledBitmap(Bitmap.createBitmap(
                BitmapFactory.decodeResource(activity.getResources(), resourceMask)), MASK_WIDTH, MASK_HEIGHT, false);

        Bitmap result = Bitmap.createBitmap(bitmapMask.getWidth(), bitmapMask.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas mCanvas = new Canvas(result);
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_IN));
        try {
            Bitmap bitmap;
            if (subItem.getImg() != null) {
                bitmap = MediaStore.Images.Media.getBitmap(activity.getContentResolver(), Uri.parse(subItem.getImg()));
            } else {
                bitmap = DEAFAULT_PHOTO;
                bitmap.eraseColor(Color.BLUE);
            }
            mCanvas.drawBitmap(bitmap, 0, 0, null);
            mCanvas.drawBitmap(bitmapMask, 0, 0, paint);
            paint.setXfermode(null);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }
}
