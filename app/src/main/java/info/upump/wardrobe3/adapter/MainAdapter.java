package info.upump.wardrobe3.adapter;

import android.app.Activity;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import info.upump.wardrobe3.MainActivity;
import info.upump.wardrobe3.R;
import info.upump.wardrobe3.SubFragment;
import info.upump.wardrobe3.model.MainMenuItem;
import info.upump.wardrobe3.model.MainMenuViewHolder;

/**
 * Created by explo on 29.10.2017.
 */

public class MainAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<MainMenuItem> mainMenuItemList;
    private MainMenuViewHolder mHolder;
    private Activity activity;


    public MainAdapter(List<MainMenuItem> mainMenuItemList, Activity activity) {
        this.mainMenuItemList = mainMenuItemList;
        this.activity = activity;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card_main_menu, parent, false);
        return new MainMenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        mHolder = ((MainMenuViewHolder) holder);
        mHolder.name.setText(mainMenuItemList.get(position).getName());
        mHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SubFragment fragment = new SubFragment();
                FragmentTransaction fragmentTransaction = ((MainActivity) activity).getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
                fragmentTransaction.replace(R.id.mainContainer, fragment, SubFragment.TAG);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();

            }
        });

        mHolder.name.setText(mainMenuItemList.get(position).getName());



    }

    @Override
    public int getItemCount() {
        return mainMenuItemList.size();
    }


}
