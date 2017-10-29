package info.upump.wardrobe3.model;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import info.upump.wardrobe3.R;

/**
 * Created by explo on 29.10.2017.
 */

public class MainMenuViewHolder extends RecyclerView.ViewHolder {
    public TextView name;
    public ImageButton deleteBtb;
    public ImageButton editBtb;
    public int position;

    public MainMenuViewHolder(View itemView) {
        super(itemView);
        position = itemView.getVerticalScrollbarPosition();
        name = itemView.findViewById(R.id.name_main_menu);
        deleteBtb = itemView.findViewById(R.id.deleteBtn);
        editBtb = itemView.findViewById(R.id.editBtn);
    }


}
