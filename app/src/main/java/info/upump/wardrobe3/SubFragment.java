package info.upump.wardrobe3;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
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

import info.upump.wardrobe3.adapter.SubItemAdapter;
import info.upump.wardrobe3.callback.SwipeCallback;
import info.upump.wardrobe3.dialog.Constants;
import info.upump.wardrobe3.dialog.OperationAsync;
import info.upump.wardrobe3.dialog.SubItemDialog;
import info.upump.wardrobe3.dialog.SubItemOperationAsync;
import info.upump.wardrobe3.loader.LoaderSubItem;
import info.upump.wardrobe3.model.EnumMask;
import info.upump.wardrobe3.model.IItem;
import info.upump.wardrobe3.model.MainMenuItem;
import info.upump.wardrobe3.model.SubItem;

/**
 * Created by explo on 30.10.2017.
 */

public class SubFragment extends Fragment implements ViewFragmentControllerCallback<SubItem>, LoaderManager.LoaderCallbacks<List<SubItem>> {
    public static final String TAG = "subFragment";
    public static final int CAMERA_RESULT = 2;
    public static final int CHOOSE_PHOTO_RESULT = 3;
    private MainMenuItem mainMenuItem;
    private RecyclerView recyclerView;
    private List<SubItem> subItemList = new ArrayList<>();
    private SubItemAdapter subItemAdapter;
    private long idParent;
    private SubItem tempSubItem;
    private int tempPositionSubItem;
    private LoaderSubItem loaderSubItem;
    private int resourceMask;
    private String title;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
     //   idParent = getArguments().getLong("idParent");
        System.out.println("из фрагме " + idParent);
        setTitle();
        View root = inflater.inflate(R.layout.fragment_sub_item, container, false);
        getActivity().setTitle(mainMenuItem.getName());

        subItemAdapter = new SubItemAdapter(subItemList, getActivity(), resourceMask);

        LinearLayoutManager linearLayoutManagerForRecycledView = new LinearLayoutManager(getContext());

        recyclerView = root.findViewById(R.id.sub_fragment_recycled);
        recyclerView.setLayoutManager(linearLayoutManagerForRecycledView);
        recyclerView.setAdapter(subItemAdapter);

        SwipeCallback swipeCallback = new SwipeCallback(this);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swipeCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
        return root;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        getLoaderManager().initLoader(0, null, this);

    }


    @Override
    public void addNewItem(SubItem object) {
        System.out.println("addNewItem sub");
        SubItemOperationAsync addItemAsync = new SubItemOperationAsync(getActivity(), OperationAsync.SAVE);
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
            if (subItemList != null) {
                object.setId(resultAdding);
                subItemList.add(object);
                int endOdList = subItemList.size() - 1;
                subItemAdapter.notifyItemInserted(endOdList);
                recyclerView.smoothScrollToPosition(endOdList);
                System.out.println("обновили адаптер  в суб");

            }

        }

    }


    @Override
    public void updateItem(SubItem object) {
        System.out.println("updateItem id"+object.getId());
        SubItemOperationAsync addItemAsynck = new SubItemOperationAsync(getActivity(), OperationAsync.UPDATE);
        addItemAsynck.execute(object);
        long resultUpdate = 0;
        try {
            resultUpdate = addItemAsynck.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
            resultUpdate = 0;
        }
        if (resultUpdate > 0) {
            if (subItemList != null) {
                int index;
                for (SubItem m : subItemList) {
                    if (m.getId() == object.getId()) {
                        m.setName(object.getName());
                        m.setImgUriToString(object.getImgUriToString());
                        index = subItemList.indexOf(m);
                        subItemAdapter.notifyItemChanged(index);
                        break;
                    }
                }
            }
        }

    }

    @Override
    public void deleteItem(int position) {
        SubItem object = subItemList.get(position);
        tempSubItem = object;
        tempPositionSubItem = position;

        SubItemOperationAsync deleteAsync = new SubItemOperationAsync(getActivity(), OperationAsync.DELETE);
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
            if (subItemList != null) {
                int index;
                for (SubItem m : subItemList) {
                    if (m.getId() == object.getId()) {
                        index = subItemList.indexOf(m);
                        subItemList.remove(index);
                        subItemAdapter.notifyItemRemoved(index);
                        showSnackBar();
                        return;
                    }

                }


                System.out.println("удалили Subитем");
            }

        }

    }

    @Override
    public void showSnackBar() {
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
        if (tempSubItem != null) {
            SubItemOperationAsync subItemOperationAsync = new SubItemOperationAsync(getContext(), OperationAsync.INSERT);
            subItemOperationAsync.execute(tempSubItem);
            long resultInsert;
            try {
                resultInsert = subItemOperationAsync.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
                resultInsert = 0;
            } catch (ExecutionException e) {
                e.printStackTrace();
                resultInsert = 0;
            }
            if (resultInsert > -1) {
                System.out.println(resultInsert);
                subItemList.add(tempPositionSubItem, tempSubItem);
                subItemAdapter.notifyItemInserted(tempPositionSubItem);
                tempSubItem = null;
                tempPositionSubItem = 0;
            }
        }
    }

    @Override
    public void editItem(int positionSubItem) {
        tempPositionSubItem = positionSubItem;
        SubItem subItem = subItemList.get(positionSubItem);

        Bundle bundle = new Bundle();
        bundle.putInt(OperationAsync.OPERATION, OperationAsync.UPDATE);
        bundle.putLong(Constants.ID_PARENT, subItem.getIdMainItem());
        bundle.putLong(Constants.ID, subItem.getId());
        bundle.putString(Constants.NAME, subItem.getName());
        bundle.putFloat(Constants.COST, subItem.getCost());
        bundle.putString(Constants.DESCRIPTION, subItem.getDescription());
        bundle.putString(Constants.IMG, subItem.getImgUriToString());

        SubItemDialog dialogFragment = new SubItemDialog();
        dialogFragment.setArguments(bundle);
        dialogFragment.show(getActivity().getSupportFragmentManager(), SubItemDialog.TAG);

    }

    @Override
    public void cancelUpdate() {
        subItemAdapter.notifyItemChanged(tempPositionSubItem);
        tempPositionSubItem = 0;

    }

    @Override
    public long getIdDb() {
        return idParent;
    }

    @Override
    public String getFragmentTag() {
        return TAG;
    }

    @Override
    public void restartLoader() {
        getLoaderManager().restartLoader(0,null,this);
    }

    @Override
    public void setTitle() {
        getActivity().setTitle(title);
    }


    @Override
    public Loader<List<SubItem>> onCreateLoader(int id, Bundle args) {
        mainMenuItem = new MainMenuItem();
        mainMenuItem.setId(getArguments().getLong(Constants.ID));
        mainMenuItem.setEnumMask(EnumMask.values()[getArguments().getInt(Constants.MASK)]);//pizdec
        mainMenuItem.setName(getArguments().getString(Constants.NAME));
        loaderSubItem = new LoaderSubItem(getContext(), mainMenuItem.getId());
        return loaderSubItem;
    }

    @Override
    public void onLoadFinished(Loader<List<SubItem>> loader, List<SubItem> data) {
        System.out.println("onLoadFinished-Sub");
        subItemList.clear();
        subItemList.addAll(data);
        subItemAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(Loader<List<SubItem>> loader) {

    }


    @Override
    public void onStart() {
        super.onStart();
        System.out.println("onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        System.out.println("onResume");
    }

    @Override
    public void onPause() {
        super.onPause();
        System.out.println("onPause");
    }

    @Override
    public void onStop() {
        super.onStop();
        // onLoaderReset(loaderSubItem);

        System.out.println("onStop");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        //  subItemList = null;
        System.out.println("onDetach");
    }

    public long getIdParent() {
        return idParent;
    }
    public static SubFragment getInstanceSubFragment(IItem mainMenuItem){
        Bundle bundle = new Bundle();
        bundle.putLong(Constants.ID,mainMenuItem.getId());
        bundle.putInt(Constants.MASK,mainMenuItem.getEnumMask().ordinal());
        bundle.putString(Constants.NAME,mainMenuItem.getName());
        SubFragment subFragment = new SubFragment();
        subFragment.setArguments(bundle);
        return subFragment;
    }

}
