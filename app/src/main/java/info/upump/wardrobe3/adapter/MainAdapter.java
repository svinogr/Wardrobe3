package info.upump.wardrobe3.adapter;

import android.app.Activity;
import android.os.Bundle;
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
import info.upump.wardrobe3.model.MainMenuItem;
import info.upump.wardrobe3.model.MainMenuViewHolder;

/**
 * Created by explo on 29.10.2017.
 */

public class MainAdapter extends RecyclerView.Adapter<MainMenuViewHolder> {
    private List<MainMenuItem> mainMenuItemList;
    private MainMenuViewHolder mHolder;
    private Activity activity;
    private FragmentController fragmentController;


    public MainAdapter(List<MainMenuItem> mainMenuItemList, Activity activity) {
        this.mainMenuItemList = mainMenuItemList;
        this.activity = activity;
        if(activity instanceof FragmentController){
            fragmentController = (FragmentController)activity;
        }
    }

    @Override
    public MainMenuViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card_main_menu, parent, false);
        return new MainMenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MainMenuViewHolder holder, final int position) {
        final long id = mainMenuItemList.get(position).getId();
        final int resourceMask = mainMenuItemList.get(position).getEnumMask().ordinal();

        holder.name.setText(mainMenuItemList.get(position).getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                System.out.println("v adaotere if "+id);
                bundle.putLong("idParent",id);
                bundle.putInt("resourceMask",resourceMask);

                SubFragment fragment = new SubFragment();
                fragment.setArguments(bundle);
                fragmentController.setCurrentFragment(fragment);
                System.out.println(fragment.getTag());

            }
        });

        holder.name.setText(mainMenuItemList.get(position).getName());

    }

    @Override
    public int getItemCount() {
        return mainMenuItemList.size();
    }


}
