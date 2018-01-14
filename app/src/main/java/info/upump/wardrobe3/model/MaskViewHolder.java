package info.upump.wardrobe3.model;

import android.graphics.Bitmap;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import info.upump.wardrobe3.R;

/**
 * Created by explo on 14.01.2018.
 */

public class MaskViewHolder extends RecyclerView.ViewHolder {
    private ImageView imageView;
    private TextView textView;
    public MaskViewHolder(View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.recycled_image_item);
        textView = itemView.findViewById(R.id.recycled_text_view_item);
    }
    public void bind(Mask mask){
        this.imageView.setImageBitmap(mask.getBitmap());
        this.textView.setText(mask.getName());
    }
}
