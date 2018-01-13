package info.upump.wardrobe3;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.DialogFragment;
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
import info.upump.wardrobe3.callback.SwipeCallback;
import info.upump.wardrobe3.dialog.MainItemDialog;
import info.upump.wardrobe3.dialog.MainItemOperationAsync;
import info.upump.wardrobe3.dialog.OperationAsync;
import info.upump.wardrobe3.loader.LoaderMainMenu;
import info.upump.wardrobe3.model.MainMenuItem;

/**
 * Created by explo on 26.10.2017.
 */

public class MainFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<MainMenuItem>>, ViewFragmentControllerCallback<MainMenuItem>, View.OnClickListener {
    private static final int CREATE_NEW_MAIN_ITEM = 1;
    private List<MainMenuItem> mainMenuItemList = new ArrayList<>();
    private RecyclerView recyclerView;
    private RecyclerView.Adapter mainAdapter;
    public static final String TAG = "mainFragment";
    private MainMenuItem tempMainItem;
    private int temPositionMainItem;
    private FloatingActionButton floatingActionButton;
    public ViewFragmentControllerCallback<MainMenuItem> iViewFragmentControllerCallback;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //  setRetainInstance(true);

        View root = inflater.inflate(R.layout.fragment_main, container, false);
        floatingActionButton = root.findViewById(R.id.main_fragment_fab);
        floatingActionButton.setOnClickListener(this);

        LinearLayoutManager linearLayoutManagerForRecycledView = new LinearLayoutManager(getContext());

        mainAdapter = new MainAdapter(mainMenuItemList, getActivity());

        recyclerView = root.findViewById(R.id.main_fragment_recycled);
        recyclerView.setLayoutManager(linearLayoutManagerForRecycledView);
        recyclerView.setAdapter(mainAdapter);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(createItemTouchHelperCallback());
        itemTouchHelper.attachToRecyclerView(recyclerView);
        setTitle();

        return root;
    }

    private ItemTouchHelper.Callback createItemTouchHelperCallback() {
        ItemTouchHelper.Callback callback = new SwipeCallback(this);
        return callback;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getLoaderManager().initLoader(0, null, this);
    }

    @Override
    public Loader<List<MainMenuItem>> onCreateLoader(int id, Bundle args) {
        System.out.println("создается лоадер");
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

    @Override
    public void addNewItem(MainMenuItem object) {
        MainItemOperationAsync addItemAsync = new MainItemOperationAsync(getActivity(), OperationAsync.SAVE);
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
        MainItemOperationAsync addItemAsync = new MainItemOperationAsync(getActivity(), OperationAsync.UPDATE);
        addItemAsync.execute(object);
        long resultUpdate = 0;
        try {
            resultUpdate = addItemAsync.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
            resultUpdate = 0;
        }
        if (resultUpdate > 0) {
            if (mainMenuItemList != null) {
                int index;
                for (MainMenuItem m : mainMenuItemList) {
                    if (m.getId() == object.getId()) {
                        m.setName(object.getName());
                        m.setEnumMask(object.getEnumMask());
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
    public void deleteItem(int position) {

        MainMenuItem object = mainMenuItemList.get(position);
        tempMainItem = object;
        temPositionMainItem = position;

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
                        showSnackBar();
                        return;
                    }
                }

                System.out.println("удалили итем");
            }

        }


    }

    @Override
    public void showSnackBar() {
        System.out.println("snack");
        Snackbar.make(getView(), getString(R.string.action_to_delite_item_snack_bar), Snackbar.LENGTH_LONG)
                .setAction(R.string.action_snak_bar_undo, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        snackBarUndo();

                    }
                }).show();

    }

    @Override
    public void snackBarUndo() {
        if (tempMainItem != null) {
            MainItemOperationAsync mainItemOperationAsync = new MainItemOperationAsync(getContext(), OperationAsync.INSERT);
            mainItemOperationAsync.execute(tempMainItem);
            long resultInsert;
            try {
                resultInsert = mainItemOperationAsync.get();
                System.out.println(resultInsert);
                System.out.println(tempMainItem.getId());
            } catch (InterruptedException e) {
                e.printStackTrace();
                resultInsert = 0;
            } catch (ExecutionException e) {
                e.printStackTrace();
                resultInsert = 0;
            }
            if (resultInsert > -1) {
                System.out.println(resultInsert);
                mainMenuItemList.add(temPositionMainItem, tempMainItem);
                mainAdapter.notifyItemInserted(temPositionMainItem);
                // mainAdapter.notifyItemChanged(temPositionMainItem);
                tempMainItem = null;
                temPositionMainItem = 0;
            }
        }

    }

    @Override
    public void editItem(int positionMainItem) {
        //добавить новый вызов чеорез статик
        temPositionMainItem = positionMainItem;
        MainItemDialog dialogFragment = new MainItemDialog();
        MainMenuItem mainMenuItem = mainMenuItemList.get(positionMainItem);
        Bundle bundle = new Bundle();
        bundle.putInt("operation", OperationAsync.UPDATE);
        bundle.putLong("id", mainMenuItem.getId());
        bundle.putString("name", mainMenuItem.getName());
        dialogFragment.setArguments(bundle);
        android.support.v4.app.FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        dialogFragment.show(fragmentManager, MainItemDialog.TAG);

    }


    @Override
    public void cancelUpdate() {
        mainAdapter.notifyItemChanged(temPositionMainItem);
        temPositionMainItem = 0;

    }

    @Override
    public long getIdDb() {
        return 0;
    }

    @Override
    public String getFragmentTag() {
        return TAG;
    }

    @Override
    public void restartLoader() {
        getLoaderManager().restartLoader(0, null, this);
    }

    @Override
    public void setTitle() {
        getActivity().setTitle("Комод");

    }

    @Override
    public void onClick(View v) {
        System.out.println(v.getId());
        switch (v.getId()) {
            case R.id.main_fragment_fab:
                DialogFragment instanceMainDialog = MainItemDialog.getInstanceMainDialog(null, OperationAsync.SAVE);
                instanceMainDialog.setTargetFragment(this, CREATE_NEW_MAIN_ITEM);
                instanceMainDialog.show(getActivity().getSupportFragmentManager(), MainItemDialog.TAG);
                break;
        }
    }

    public static MainFragment getInstanceMainFragment(){
        return new MainFragment();

    }

}
