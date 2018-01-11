package info.upump.wardrobe3.model;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import info.upump.wardrobe3.R;

/**
 * Created by explo on 29.10.2017.
 */

public class MainMenuViewHolder extends RecyclerView.ViewHolder {
    public TextView name;

    public MainMenuViewHolder(View itemView) {
        super(itemView);
        name = itemView.findViewById(R.id.name_main_menu);
    }


}
