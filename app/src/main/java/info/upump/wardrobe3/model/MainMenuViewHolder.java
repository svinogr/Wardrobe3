package info.upump.wardrobe3.model;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import info.upump.wardrobe3.R;

/**
 * Created by explo on 29.10.2017.
 */

public class MainMenuViewHolder extends RecyclerView.ViewHolder {
    public TextView name;
    public ImageView image;

    public MainMenuViewHolder(View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.recycled_text_view_item);
        image = itemView.findViewById(R.id.recycled_image_item);

    }
    public void bind(MenuItemContainer mainMenuItem){
        name.setText(mainMenuItem.getItemMenu().getName());
        image.setImageBitmap(mainMenuItem.getItemMenuImage());
    }


}
