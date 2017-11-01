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
import info.upump.wardrobe3.dialog.MainItemOperationAsync;
import info.upump.wardrobe3.model.MainMenuItem;
import info.upump.wardrobe3.model.MainMenuViewHolder;
import info.upump.wardrobe3.model.MainMenuViewHolderWithSwipeLayout;

/**
 * Created by explo on 29.10.2017.
 */

public class MainAdapterWithSwipeLayout extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<MainMenuItem> mainMenuItemList;
    private MainMenuViewHolderWithSwipeLayout mHolder;
    private Activity activity;


    public MainAdapterWithSwipeLayout(List<MainMenuItem> mainMenuItemList, Activity activity) {
        this.mainMenuItemList = mainMenuItemList;
        this.activity = activity;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card_main_menu_swipelayout, parent, false);
        SwipeLayout swipeLayout = (SwipeLayout) view.findViewById(R.id.swap);
        swipeLayout.setShowMode(SwipeLayout.ShowMode.PullOut);

        return new MainMenuViewHolderWithSwipeLayout(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        mHolder = ((MainMenuViewHolderWithSwipeLayout) holder);
        mHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              /*  if (mHolder.swiped) {
                System.out.println(mainMenuItemList.get(position).getName());
                System.out.println(mHolder.swiped);
                //TODO переход на новый фрагмент
                SubFragment fragment = new SubFragment();
                FragmentTransaction fragmentTransaction = ((MainActivity) activity).getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
                fragmentTransaction.replace(R.id.mainContainer, fragment, SubFragment.TAG);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
                }*/
                if(mHolder.swiped){
                    System.out.println("true "+mHolder.swiped);
                } else  { System.out.println("false "+mHolder.swiped+position);
/*
                    SubFragment fragment = new SubFragment();
                    FragmentTransaction fragmentTransaction = ((MainActivity) activity).getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE);
                    fragmentTransaction.replace(R.id.mainContainer, fragment, SubFragment.TAG);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();*/
                }
            }
        });
        mHolder.position = position;
        mHolder.name.setText(mainMenuItemList.get(position).getName());
        mHolder.swipeLayout.addSwipeListener(new SwipeLayout.SwipeListener() {
            @Override
            public void onStartOpen(SwipeLayout layout) {
            //    mHolder.swiped = true;
           /*     System.out.println("onStartOpen");*/
            }

            @Override
            public void onOpen(SwipeLayout layout) {
         /*   //    mHolder.swiped = true;
                System.out.println(mHolder.swiped );
                System.out.println("onOpen");*/
            }

            @Override
            public void onStartClose(SwipeLayout layout) {
             /*   mHolder.swiped = false;
                System.out.println("onStartClose");*/
            }

            @Override
            public void onClose(SwipeLayout layout) {
              /*  mHolder.swiped = false;
                System.out.println("onClose");
                System.out.println(mHolder.swiped);*/
            }

            @Override
            public void onUpdate(SwipeLayout layout, int leftOffset, int topOffset) {
                mHolder.swiped = !mHolder.swiped;
                System.out.println("onUpdate "+  mHolder.swiped +leftOffset+" "+mHolder.position);
            }

            @Override
            public void onHandRelease(SwipeLayout layout, float xvel, float yvel) {
               /* System.out.println("onHandRelease");*/


            }
        });
        mHolder.deleteBtb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                long result = 0;
                MainItemOperationAsync deleteItemAsynck = new MainItemOperationAsync(activity, MainItemOperationAsync.DELETE);
                deleteItemAsynck.execute(mainMenuItemList.get(position));
                try {
                    result = deleteItemAsynck.get();
                    if (result>0) {
                        mainMenuItemList.remove(position);
                        notifyDataSetChanged();
                        Toast.makeText(activity, String.valueOf(mainMenuItemList.size()), Toast.LENGTH_LONG).show();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                    result = 0;
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
                bundle.putInt("operation", MainItemOperationAsync.UPDATE);
                bundle.putLong("id", mainMenuItem.getId());
                bundle.putString("name", mainMenuItem.getName());
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
