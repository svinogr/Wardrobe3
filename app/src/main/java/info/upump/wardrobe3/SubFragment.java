package info.upump.wardrobe3;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by explo on 30.10.2017.
 */

public class SubFragment extends Fragment {
    public static final String TAG = "subFragment";
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
        setRetainInstance(true);
        View root = inflater.inflate(R.layout.fragment_sub_item, container, false);

        LinearLayoutManager linearLayoutManagerForRecycledView = new LinearLayoutManager(getContext());

       return root;
    }
}
