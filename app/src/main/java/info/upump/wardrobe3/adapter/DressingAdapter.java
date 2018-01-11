package info.upump.wardrobe3.adapter;

import android.app.Activity;
import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import info.upump.wardrobe3.R;
import info.upump.wardrobe3.dialog.Constants;
import info.upump.wardrobe3.dialog.OperationAsync;
import info.upump.wardrobe3.dialog.SubItemDialog;
import info.upump.wardrobe3.model.SubItem;
import info.upump.wardrobe3.model.SubItemViewHolder;

/**
 * Created by explo on 03.01.2018.
 */

public class DressingAdapter extends SubItemAdapter {
    private int levelFrom;
    private int levelTo;
    private static final int DEFAULT_MASK = 0;

    public DressingAdapter(List<SubItem> subItemList, Activity activity ) {
        super(subItemList, activity, DEFAULT_MASK);

        Uri parse = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE+"://" + activity.getResources().getResourcePackageName(R.drawable.question)+
                '/' +  activity.getResources().getResourceTypeName(R.drawable.question) + '/' + activity.getResources().getResourceEntryName(R.drawable.question));
        SubItem subItem = new SubItem();
        subItem.setName("выберите ящик");
        subItem.setImg(parse.toString());
        subItemList.add(subItem);
    }


    @Override
    public void onBindViewHolder(final SubItemViewHolder holder, final int position) {
        System.out.println(subItemList.get(position).getImg());
        System.out.println(subItemList.size());
    //    holder.imageView.setImageBitmap(createMask(subItemList.get(position)));
        holder.imageView.setImageURI(Uri.parse(subItemList.get(position).getImg()));
        holder.textView.setText(subItemList.get(position).getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            //выбор адаптера
            }
        });
    }

}
