package info.upump.wardrobe3.adapter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import info.upump.wardrobe3.FragmentController;
import info.upump.wardrobe3.MainActivity;
import info.upump.wardrobe3.R;
import info.upump.wardrobe3.SubFragment;
import info.upump.wardrobe3.dialog.Constants;
import info.upump.wardrobe3.mask.PicCreator;
import info.upump.wardrobe3.model.MainMenuItem;
import info.upump.wardrobe3.model.MainMenuViewHolder;
import info.upump.wardrobe3.model.MenuItemContainer;

/**
 * Created by explo on 29.10.2017.
 */

public class MainAdapter extends RecyclerView.Adapter<MainMenuViewHolder> {
    private List<MainMenuItem> mainMenuItemList;
    private Context context;

    public MainAdapter(List<MainMenuItem> mainMenuItemList, Context context) {
        this.mainMenuItemList = mainMenuItemList;
        this.context = context;
    }

    @Override
    public MainMenuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card_item, parent, false);
        return new MainMenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MainMenuViewHolder holder, final int position) {
        PicCreator picCreator = new PicCreator(context);
        holder.bind(new MenuItemContainer(mainMenuItemList.get(position),picCreator.getSimpleMaskBitmap(mainMenuItemList.get(position).getEnumMask())));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainMenuItem mainMenuItem = mainMenuItemList.get(position);
                SubFragment subFragment = SubFragment.getInstanceSubFragment(mainMenuItem);
                if(context instanceof MainActivity) {
                    ((MainActivity) context).createFragment(subFragment);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mainMenuItemList.size();
    }


}
