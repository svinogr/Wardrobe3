package info.upump.wardrobe3.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import info.upump.wardrobe3.R;
import info.upump.wardrobe3.model.MainMenuViewHolder;
import info.upump.wardrobe3.model.SubItem;
import info.upump.wardrobe3.model.SubItemViewHolder;

/**
 * Created by explo on 02.11.2017.
 */

public class SubItemAdapter extends RecyclerView.Adapter<SubItemViewHolder> {
    private List<SubItem> subItemList;
    private Activity activity;

    public SubItemAdapter(List<SubItem> subItemList, Activity activity) {
        this.subItemList = subItemList;
        this.activity = activity;
    }

    @Override
    public SubItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card_sub_item, parent, false);
        return new SubItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SubItemViewHolder holder, int position) {
      holder.textView.setText(subItemList.get(position).getName());
      //holder.imageView.setImageDrawable(null); TODO здесь красивая маска
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               //TODO открытие деталей

            }
        });
    }

    @Override
    public int getItemCount() {
        return subItemList.size();
    }
}
