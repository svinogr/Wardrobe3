package info.upump.wardrobe3;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import info.upump.wardrobe3.adapter.MainAdapter;
import info.upump.wardrobe3.loader.LoaderMainMenu;
import info.upump.wardrobe3.model.MainMenuItem;

/**
 * Created by explo on 26.10.2017.
 */

public class MainFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<MainMenuItem>> {
    private List<MainMenuItem> mainMenuItemList = new ArrayList<>();
    private RecyclerView recyclerView;
    private MainAdapter mainAdapter;
    public static final String TAG = "mainFragment";


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View root = inflater.inflate(R.layout.fragment_main, container, false);

        LinearLayoutManager linearLayoutManagerForRecycledView = new LinearLayoutManager(getContext());

        mainAdapter = new MainAdapter(mainMenuItemList,getActivity());

        recyclerView = root.findViewById(R.id.recycledFragmentMain);
        recyclerView.setLayoutManager(linearLayoutManagerForRecycledView);
        recyclerView.setAdapter(mainAdapter);


        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(0, null, this);


    }

    @Override
    public Loader<List<MainMenuItem>> onCreateLoader(int id, Bundle args) {
        LoaderMainMenu loaderMainMenu = new LoaderMainMenu(getContext());
        return loaderMainMenu;
    }

    @Override
    public void onLoadFinished(Loader<List<MainMenuItem>> loader, List<MainMenuItem> data) {
        mainMenuItemList.clear();
        mainMenuItemList.addAll(data);
        mainAdapter.notifyDataSetChanged();

    }

    @Override
    public void onLoaderReset(Loader<List<MainMenuItem>> loader) {

    }

    public MainAdapter getMainAdapter() {
        return mainAdapter;
    }
}
