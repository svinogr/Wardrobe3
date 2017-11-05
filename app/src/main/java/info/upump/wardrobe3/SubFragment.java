package info.upump.wardrobe3;

import android.content.Context;
import android.content.Intent;
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
import info.upump.wardrobe3.dialog.OperationAsync;
import info.upump.wardrobe3.dialog.SubItemOperationAsync;
import info.upump.wardrobe3.loader.LoaderSubItem;
import info.upump.wardrobe3.model.SubItem;

/**
 * Created by explo on 30.10.2017.
 */

public class SubFragment extends Fragment implements ViewFragmentController<SubItem>, LoaderManager.LoaderCallbacks<List<SubItem>> {
    public static final String TAG = "subFragment";
    private RecyclerView recyclerView;
    private List<SubItem> subItemList = new ArrayList<>();
    private SubItemAdapter subItemAdapter;
    private long idParent;
    private SubItem tempSubItem;
    private int tempPositionSubItem;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true);
        idParent = getArguments().getLong("idParent");
        System.out.println("из фрагме " + idParent);
        View root = inflater.inflate(R.layout.fragment_sub_item, container, false);

        subItemAdapter = new SubItemAdapter(subItemList, getActivity());

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
                System.out.println("обновили адаптер");

            }

        }

    }


    @Override
    public void updateItem(SubItem object) {
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
        Snackbar.make(getView(),getString(R.string.action_to_delite_item_snack_bar), Snackbar.LENGTH_LONG )
                .setAction(R.string.action_snak_bar_undo, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        snackBarUndo();

                    }
                }).show();

    }

    @Override
    public void snackBarUndo() {
        if(tempSubItem != null){
            SubItemOperationAsync subItemOperationAsync = new SubItemOperationAsync(getContext(),OperationAsync.INSERT);
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
            if(resultInsert>-1){
                System.out.println(resultInsert);
                subItemList.add(tempPositionSubItem,tempSubItem);
                subItemAdapter.notifyItemInserted(tempPositionSubItem);
                // mainAdapter.notifyItemChanged(temPositionMainItem);
                tempSubItem = null;
                tempPositionSubItem = 0;
            }
        }
    }

    @Override
    public void editItem(int positionSubItem) {
        SubItem subItem = subItemList.get(positionSubItem);
        Intent intent = new Intent(getContext(), SubItemDetail.class);
        intent.putExtra("id", subItem.getId());
        intent.putExtra("name", subItem.getName());
        intent.putExtra("img", subItem.getImg());
        System.out.println(subItem.getCost());
        intent.putExtra("cost", String.valueOf(subItem.getCost()));
        intent.putExtra("description", subItem.getDescription());
        intent.putExtra("idMainItem", subItem.getIdMainItem());
        intent.putExtra("edit", SubItemDetail.EDIT);
        getActivity().startActivityForResult(intent, MainActivity.DETAIL_EDIT_SUB_ACTIVITY_ITEM_RESULT);

    }

    @Override
    public void cancelUpdate() {
        //ненужно так как на экране не видно фрагмента, а видно детефд активити

    }

    @Override
    public Loader<List<SubItem>> onCreateLoader(int id, Bundle args) {
        long idP = getArguments().getLong("idParent");
        System.out.println("coplfybt kjflthf " + idP);
        LoaderSubItem loaderSubItem = new LoaderSubItem(getContext(), idP);
        return loaderSubItem;
    }

    @Override
    public void onLoadFinished(Loader<List<SubItem>> loader, List<SubItem> data) {
        System.out.println("onLoadFinished-Main");
        subItemList.clear();
        subItemList.addAll(data);
        subItemAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLoaderReset(Loader<List<SubItem>> loader) {

    }

    public long getIdParent() {
        return idParent;
    }

    public void setIdParent(long idParent) {
        this.idParent = idParent;
    }

}
