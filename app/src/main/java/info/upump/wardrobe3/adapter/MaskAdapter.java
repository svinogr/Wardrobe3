package info.upump.wardrobe3.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import info.upump.wardrobe3.R;
import info.upump.wardrobe3.model.Mask;
import info.upump.wardrobe3.model.MaskViewHolder;

/**
 * Created by explo on 14.01.2018.
 */

public class MaskAdapter extends RecyclerView.Adapter<MaskViewHolder> {
    List<Mask> masks = new ArrayList<>();

    public MaskAdapter(List<Mask> masks) {
        this.masks = masks;
    }

    @Override
    public MaskViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card_item,parent,false);
        return new MaskViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MaskViewHolder holder, int position) {
        holder.bind(masks.get(position));

    }

    @Override
    public int getItemCount() {
        return masks.size();
    }
}
