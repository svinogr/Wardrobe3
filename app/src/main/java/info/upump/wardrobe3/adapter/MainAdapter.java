package info.upump.wardrobe3.adapter;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.daimajia.swipe.SwipeLayout;

import java.util.List;
import java.util.concurrent.ExecutionException;

import info.upump.wardrobe3.R;
import info.upump.wardrobe3.dialog.MainItemDialog;
import info.upump.wardrobe3.dialog.MainItemOperationAsynck;
import info.upump.wardrobe3.model.MainMenuItem;
import info.upump.wardrobe3.model.MainMenuViewHolder;
import info.upump.wardrobe3.model.SubItem;

/**
 * Created by explo on 29.10.2017.
 */

public class MainAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>  {
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
        SwipeLayout swipeLayout =  (SwipeLayout)view.findViewById(R.id.swap);
        swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);
        return new MainMenuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        mHolder = ((MainMenuViewHolder) holder);
        mHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(mainMenuItemList.get(position).getName());
                //TODO переход на новый фрагмент
            }
        });
        mHolder.position =  position;
        mHolder.name.setText(mainMenuItemList.get(position).getName());
        mHolder.deleteBtb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean result = false;
                MainItemOperationAsynck deleteItemAsynck  = new MainItemOperationAsynck(activity, MainItemOperationAsynck.DELETE);
                deleteItemAsynck.execute(mainMenuItemList.get(position));
                try {
                    result = deleteItemAsynck.get();
                    if(result){
                        mainMenuItemList.remove(position);
                        notifyDataSetChanged();
                        Toast.makeText(activity,String.valueOf(mainMenuItemList.size()),Toast.LENGTH_LONG).show();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    result = false;
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }


            }
        });
        mHolder.editBtb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               MainMenuItem mainMenuItem = mainMenuItemList.get(position);
                DialogFragment dialogFragment = new MainItemDialog();
                Bundle bundle = new Bundle();
                bundle.putInt("operation",MainItemOperationAsynck.UPDATE);
                bundle.putLong("id", mainMenuItem.getId());
                bundle.putString("name",  mainMenuItem.getName());
                dialogFragment.setArguments(bundle);
                dialogFragment.show(activity.getFragmentManager(), MainItemDialog.TAG);

            }
        });

    }

    @Override
    public int getItemCount() {
        return mainMenuItemList.size();
    }




}
