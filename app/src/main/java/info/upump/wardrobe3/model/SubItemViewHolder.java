package info.upump.wardrobe3.model;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import info.upump.wardrobe3.R;

/**
 * Created by explo on 02.11.2017.
 */

public class SubItemViewHolder extends RecyclerView.ViewHolder {
    public TextView textView;
    public ImageView imageView;

    public SubItemViewHolder(View itemView) {
        super(itemView);
        textView = itemView.findViewById(R.id.sub_item_text_view);
        imageView = itemView.findViewById(R.id.sub_image_item);
    }


}
