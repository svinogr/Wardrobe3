package info.upump.wardrobe3;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

import info.upump.wardrobe3.adapter.MainAdapter;
import info.upump.wardrobe3.dialog.MainItemOperationAsync;
import info.upump.wardrobe3.loader.LoaderMainMenu;
import info.upump.wardrobe3.model.MainMenuItem;

/**
 * Created by explo on 26.10.2017.
 */

public class MainFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<MainMenuItem>>, ViewFragmentController<MainMenuItem> {
    private List<MainMenuItem> mainMenuItemList = new ArrayList<>();
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mainAdapter;
    public static final String TAG = "mainFragment";
    private MainMenuItem tempMainItem;
    private int temPositionMainItem;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("onCreateView-Main");
        setRetainInstance(true);

        View root = inflater.inflate(R.layout.fragment_main, container, false);

        LinearLayoutManager linearLayoutManagerForRecycledView = new LinearLayoutManager(getContext());

        mainAdapter = new MainAdapter(mainMenuItemList, getActivity());
        //mainAdapter = new MainAdapterWithSwipeLayout(mainMenuItemList,getActivity());

        recyclerView = root.findViewById(R.id.recycledFragmentMain);
        recyclerView.setLayoutManager(linearLayoutManagerForRecycledView);
        recyclerView.setAdapter(mainAdapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(createItemTouchHelperCallback());
        itemTouchHelper.attachToRecyclerView(recyclerView);

        return root;
    }

    private ItemTouchHelper.Callback createItemTouchHelperCallback() {
        ItemTouchHelper.Callback callback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                switch (direction) {
                    case 8:
                        break;
                    case 4:
                        temPositionMainItem = viewHolder.getAdapterPosition();
                        tempMainItem = mainMenuItemList.get(temPositionMainItem);
                        deleteItem(tempMainItem);
                        break;
                }

            }
        };
        return callback;
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
        System.out.println("onLoadFinished-Main");
        mainMenuItemList.clear();
        mainMenuItemList.addAll(data);
        mainAdapter.notifyDataSetChanged();

    }

    @Override
    public void onLoaderReset(Loader<List<MainMenuItem>> loader) {

    }

    public RecyclerView.Adapter getMainAdapter() {
        return mainAdapter;
    }

    public List<MainMenuItem> getMainMenuItemList() {
        return mainMenuItemList;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }


    @Override
    public void addNewItem(MainMenuItem object) {
        MainItemOperationAsync addItemAsync = new MainItemOperationAsync(getActivity(), MainItemOperationAsync.SAVE);
        addItemAsync.execute(object);
        long resultAdding = 0;
        try {
            resultAdding = addItemAsync.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
            resultAdding = 0;
        }
        if (resultAdding > 0) {
            if (mainMenuItemList != null) {
                object.setId(resultAdding);
                mainMenuItemList.add(object);
                int endOdList = mainMenuItemList.size() - 1;
                mainAdapter.notifyItemInserted(endOdList);
                recyclerView.smoothScrollToPosition(endOdList);
                System.out.println("обновили адаптер");

            }

        }

    }

    @Override
    public void updateItem(MainMenuItem object) {

        MainItemOperationAsync addItemAsynck = new MainItemOperationAsync(getActivity(), MainItemOperationAsync.UPDATE);
        addItemAsynck.execute(object);
        long resultupdate = 0;
        try {
            resultupdate = addItemAsynck.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
            resultupdate = 0;
        }
        if (resultupdate > 0) {
            if (mainMenuItemList != null) {
                int index;
                for (MainMenuItem m : mainMenuItemList) {
                    if (m.getId() == object.getId()) {
                        m.setName(object.getName());
                        index = mainMenuItemList.indexOf(m);
                        mainAdapter.notifyItemChanged(index);
                        break;
                    }

                }


                System.out.println("обновили адаптер");
            }
        }

    }

    @Override
    public void deleteItem(MainMenuItem object) {

        MainItemOperationAsync deleteAsync = new MainItemOperationAsync(getActivity(), MainItemOperationAsync.DELETE);
        deleteAsync.execute(object);
        long resultDelete = 0;
        try {
            resultDelete = deleteAsync.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
            resultDelete = 0;
        }
        System.out.println(resultDelete);
        if (resultDelete > 0) {
            if (mainMenuItemList != null) {
                int index;
                for (MainMenuItem m : mainMenuItemList) {
                    if (m.getId() == object.getId()) {
                        index = mainMenuItemList.indexOf(m);
                        mainMenuItemList.remove(index);
                        mainAdapter.notifyItemRemoved(index);
                        return;
                    }

                }


                System.out.println("удалили итем");
            }
        }

    }


}
