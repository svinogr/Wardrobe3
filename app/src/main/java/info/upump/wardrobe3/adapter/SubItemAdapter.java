package info.upump.wardrobe3.adapter;

import android.app.Activity;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.File;
import java.util.List;

import info.upump.wardrobe3.R;
import info.upump.wardrobe3.model.MainMenuViewHolder;
import info.upump.wardrobe3.model.SubItem;
import info.upump.wardrobe3.model.SubItemViewHolder;

import static android.support.v4.content.FileProvider.getUriForFile;

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
      //  holder.linearLayout.removeAllViews();

        holder.imageView.setImageURI(null);//TODO неплохобы какую нить заглушку
        holder.textView.setText(subItemList.get(position).getName());
        if (subItemList.get(position).getImg() != null) {
            holder.imageView.setImageURI(Uri.parse(subItemList.get(position).getImg()));
        }
        //holder.imageView.setImageDrawable(null); TODO здесь красивая маска

        if(subItemList.get(position).getImg()!=null){
            File directory = new File(activity.getFilesDir(), "images");
            File file = new File(directory.getPath(), "/" + subItemList.get(position).getImg());
            System.out.println("clean "+subItemList.get(position).getImg());

            Uri uri = getUriForFile(activity, "info.upump.wardrobe3.fileprovider", file);
            System.out.println("v holdere "+uri.toString());
            holder.imageView.setImageURI(uri);
        }


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
