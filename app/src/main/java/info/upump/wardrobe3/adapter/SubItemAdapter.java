package info.upump.wardrobe3.adapter;


import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import info.upump.wardrobe3.R;
import info.upump.wardrobe3.model.SubItem;
import info.upump.wardrobe3.model.SubItemViewHolder;

public class SubItemAdapter extends RecyclerView.Adapter<SubItemViewHolder> {
    protected List<SubItem> subItemList;
    protected AppCompatActivity activity;
    protected int enumMaskOrdinal = 0;

    public SubItemAdapter(List<SubItem> subItemList, Activity activity, int enumMask) {
        this.subItemList = subItemList;
        this.activity = (AppCompatActivity) activity;
        System.out.println("enumMask " + enumMask);
        this.enumMaskOrdinal = enumMask;
    }

    @Override
    public SubItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card_item, parent, false);
        return new SubItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final SubItemViewHolder holder, final int position) {
        holder.bind(subItemList.get(position), enumMaskOrdinal, activity);
       /* holder.imageView.setImageBitmap(createMask(subItemList.get(position)));
        holder.textView.setText(subItemList.get(position).getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SubItem subItem = subItemList.get(position);

                Bundle bundle = new Bundle();
                bundle.putInt(OperationAsync.OPERATION, OperationAsync.OPEN);
                bundle.putLong(Constants.ID_PARENT, subItem.getIdMainItem());
                bundle.putLong(Constants.ID, subItem.getId());
                bundle.putString(Constants.NAME, subItem.getName());
                bundle.putFloat(Constants.COST, subItem.getCost());
                bundle.putString(Constants.DESCRIPTION, subItem.getDescription());
                bundle.putString(Constants.IMG, subItem.getImg());

                SubItemDialog dialogFragment = new SubItemDialog();
                dialogFragment.setArguments(bundle);
                dialogFragment.show(activity.getSupportFragmentManager(), SubItemDialog.TAG);
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return subItemList.size();
    }


}
