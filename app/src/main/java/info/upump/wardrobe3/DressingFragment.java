package info.upump.wardrobe3;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import info.upump.wardrobe3.adapter.DressingAdapter;
import info.upump.wardrobe3.adapter.SubItemAdapter;
import info.upump.wardrobe3.model.SubItem;

/**
 * Created by explo on 03.01.2018.
 */

public class DressingFragment extends Fragment {
    private RecyclerView body, head, legs, foot;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        View inflate = inflater.inflate(R.layout.fragment_dressing, container, false);
        head = inflate.findViewById(R.id.dressing_recycler_head);
        body = inflate.findViewById(R.id.dressing_recycler_body);
        legs = inflate.findViewById(R.id.dressing_recycler_legs);
        foot = inflate.findViewById(R.id.dressing_recycler_foot);

        LinearLayoutManager linearLayoutManagerHead = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager linearLayoutManagerBody = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager linearLayoutManagerLegs = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager linearLayoutManagerFoot = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL, false);

        head.setLayoutManager(linearLayoutManagerHead);
        body.setLayoutManager(linearLayoutManagerBody);
        legs.setLayoutManager(linearLayoutManagerLegs);
        foot.setLayoutManager(linearLayoutManagerFoot);

        SnapHelper snapHelper = new PagerSnapHelper();
        snapHelper.attachToRecyclerView(head);


        List<SubItem> list = new ArrayList<>();
        SubItem subItem = new SubItem();
        SubItem subItem2 = new SubItem();
        SubItem subItem3 = new SubItem();
        subItem.setName("ssssss");
        subItem2.setName("ssssss");
        subItem3.setName("ssssss");
        list.add(subItem);
        list.add(subItem2);
        list.add(subItem3);

        DressingAdapter subItemAdapter = new DressingAdapter(list,getActivity(),1);
        head.setAdapter(subItemAdapter);

        return inflate;

    }
}
