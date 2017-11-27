package info.upump.wardrobe3.adapter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.IOException;
import java.util.List;

import info.upump.wardrobe3.R;
import info.upump.wardrobe3.mask.MaskedDrawable;
import info.upump.wardrobe3.mask.MaskedDrawablePorterDuffDstIn;
import info.upump.wardrobe3.model.SubItem;
import info.upump.wardrobe3.model.SubItemViewHolder;

public class SubItemAdapter extends RecyclerView.Adapter<SubItemViewHolder> {
    private List<SubItem> subItemList;
    private Activity activity;

    private Bitmap bitmapMask;

    public SubItemAdapter(List<SubItem> subItemList, Activity activity) {
        this.subItemList = subItemList;
        this.activity = activity;
        this.bitmapMask = Bitmap.createScaledBitmap(Bitmap.createBitmap(BitmapFactory.decodeResource(activity.getResources(), R.drawable.mask_circle)), 300, 300, false);
    }

    @Override
    public SubItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card_sub_item, parent, false);
        return new SubItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final SubItemViewHolder holder, int position) {
      //    holder.imageView.setImageDrawable(null);
       // holder.linearLayout.removeAllViews();
        holder.setIsRecyclable(false);

        System.out.println(holder.itemView.getId());


        System.out.println("in=mg"+subItemList.get(position).getImg());

        holder.textView.setText(subItemList.get(position).getName());

        MaskedDrawable drawable = new MaskedDrawablePorterDuffDstIn();


        if (subItemList.get(position).getImg() != null) {
            Uri uri = Uri.parse(subItemList.get(position).getImg());
            System.out.println(subItemList.get(position).getImg());
            try {
                Bitmap  bitmapPhoto = MediaStore.Images.Media.getBitmap(activity.getContentResolver(), uri);
                drawable.setPictureBitmap(bitmapPhoto);

            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            drawable.setPictureBitmap(null);
        }
        drawable.setMaskBitmap(bitmapMask);
        holder.imageView.setImageDrawable(drawable);
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

}
