package info.upump.wardrobe3.dialog;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import info.upump.wardrobe3.R;
import info.upump.wardrobe3.adapter.MainAdapter;
import info.upump.wardrobe3.adapter.MaskAdapter;
import info.upump.wardrobe3.model.EnumMask;
import info.upump.wardrobe3.model.MainMenuItem;
import info.upump.wardrobe3.model.Mask;

/**
 * Created by explo on 14.01.2018.
 */

public class MainItemFragment extends Fragment {
    private EditText nameEditText;
    private RecyclerView recyclerView;
    private FloatingActionButton floatingActionButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View root = inflater.inflate(R.layout.main_item_detail_fragment,container,false);
        nameEditText = root.findViewById(R.id.main_item_detail_edit_text);
        recyclerView = root.findViewById(R.id.main_item_detail_recycled);
        floatingActionButton = root.findViewById(R.id.main_item_detail_fab);

        LinearLayoutManager linearLayoutManagerForRecycledView = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linearLayoutManagerForRecycledView);
        ///
        List<Mask> testList = new ArrayList<>();
        for(int i = 0; i<3; i++ ) {
            for (EnumMask enumMask : EnumMask.values()) {
                Mask mask = new Mask(Bitmap.createScaledBitmap(Bitmap.createBitmap(
                        BitmapFactory.decodeResource(getContext().getResources(), enumMask.getResource())), 300, 300, false), enumMask.getVisibleName());
                testList.add(mask);
            }
        }

        ///
        System.out.println(testList.size());
        MaskAdapter maskAdapter = new MaskAdapter(testList);
        recyclerView.setAdapter(maskAdapter);


        return root;
    }

    public static MainItemFragment getInstanceMainDialog(MainMenuItem mainMenuItem, int operation) {
        MainItemFragment mainItemFragment = new MainItemFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(OperationAsync.OPERATION, operation);
        if (mainMenuItem != null) {
            bundle.putLong(Constants.ID, mainMenuItem.getId());
            bundle.putString(Constants.NAME, mainMenuItem.getName());
            // bundle.putString(Constants.IMG, mainMenuItem.getImg()); TODO включить в случае реализации индивидуальной картинки
        }
        mainItemFragment.setArguments(bundle);
        return mainItemFragment;

    }
}
