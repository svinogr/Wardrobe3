package info.upump.wardrobe3.adapter;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import java.util.List;

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
    public DressingAdapter(List<SubItem> subItemList, Activity activity, int enumMask) {
        super(subItemList, activity, enumMask);
    }
    @Override
    public void onBindViewHolder(final SubItemViewHolder holder, final int position) {
        holder.imageView.setImageBitmap(createMask(subItemList.get(position)));
        holder.textView.setText(subItemList.get(position).getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            //выбор адаптера
            }
        });
    }

}
