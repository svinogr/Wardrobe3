package info.upump.wardrobe3;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import info.upump.wardrobe3.adapter.DressingAdapter;
import info.upump.wardrobe3.model.SubItem;

/**
 * Created by explo on 03.01.2018.
 */

public class DressingFragment extends Fragment  implements ViewTag{
    public static final String TAG = "dressingFragment";
    private RecyclerView body, head, legs, foot;
    private List<SubItem> subItemsHead, subItemListBody, subItemListLegs, subItemListFoot;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);

        DisplayMetrics displaymetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int screenHeight = displaymetrics.heightPixels/4;

        System.out.println(screenHeight);

        View inflate = inflater.inflate(R.layout.fragment_dressing, container, false);
        head = inflate.findViewById(R.id.dressing_recycler_head);
        body = inflate.findViewById(R.id.dressing_recycler_body);
        legs = inflate.findViewById(R.id.dressing_recycler_legs);
        foot = inflate.findViewById(R.id.dressing_recycler_foot);

        LinearLayout linearLayoutHead = inflate.findViewById(R.id.dressing_recycler_layout_head);
        LinearLayout linearLayoutBody = inflate.findViewById(R.id.dressing_recycler_layout_body);
        LinearLayout linearLayoutFoot = inflate.findViewById(R.id.dressing_recycler_layout_legs);
        LinearLayout linearLayoutLegs = inflate.findViewById(R.id.dressing_recycler_layout_foot);
        LinearLayout linearLayoutRec = inflate.findViewById(R.id.dressing_recycler_layout_rec);
        LinearLayout linearLayoutRoot = inflate.findViewById(R.id.dressing_recycler_layout_root);


/*
        linearLayoutHead.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, screenHeight));
        linearLayoutBody.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, screenHeight));
        linearLayoutFoot.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, screenHeight));
        linearLayoutLegs.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, screenHeight));*/




        LinearLayoutManager linearLayoutManagerHead = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager linearLayoutManagerBody = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager linearLayoutManagerLegs = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL, false);
        LinearLayoutManager linearLayoutManagerFoot = new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL, false);


        head.setLayoutManager(linearLayoutManagerHead);
        body.setLayoutManager(linearLayoutManagerBody);
        legs.setLayoutManager(linearLayoutManagerLegs);
        foot.setLayoutManager(linearLayoutManagerFoot);

        SnapHelper snapHelperHead = new PagerSnapHelper();
        snapHelperHead.attachToRecyclerView(head);
        SnapHelper snapHelperBody = new PagerSnapHelper();
        snapHelperBody.attachToRecyclerView(body);
        SnapHelper snapHelperLegs = new PagerSnapHelper();
        snapHelperLegs.attachToRecyclerView(legs);
        SnapHelper snapHelperFoot = new PagerSnapHelper();
        snapHelperFoot.attachToRecyclerView(foot);

        subItemsHead = new ArrayList<>();
        subItemListBody = new ArrayList<>();
        subItemListLegs = new ArrayList<>();
        subItemListFoot = new ArrayList<>();

        DressingAdapter subItemAdapterHead = new DressingAdapter(subItemsHead,getActivity());
        head.setAdapter(subItemAdapterHead);
        DressingAdapter subItemAdapterBody = new DressingAdapter(subItemListBody,getActivity());
        body.setAdapter(subItemAdapterBody);
        DressingAdapter subItemAdapterLegs = new DressingAdapter(subItemListLegs,getActivity());
        legs.setAdapter(subItemAdapterLegs);
        DressingAdapter subItemAdapterFoot = new DressingAdapter(subItemListFoot,getActivity());
        foot.setAdapter(subItemAdapterFoot);

        return inflate;

    }

    @Override
    public String getFragmentTag() {
        return TAG;
    }
}
