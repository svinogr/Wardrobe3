package info.upump.wardrobe3;

import android.app.DialogFragment;
import android.content.Context;
import android.content.Intent;
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

import info.upump.wardrobe3.adapter.SubItemAdapter;
import info.upump.wardrobe3.callback.SwipeCallback;
import info.upump.wardrobe3.dialog.MainItemDialog;
import info.upump.wardrobe3.dialog.MainItemOperationAsync;
import info.upump.wardrobe3.dialog.SubItemOperationAsync;
import info.upump.wardrobe3.loader.LoaderSubItem;
import info.upump.wardrobe3.model.MainMenuItem;
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
        SubItemOperationAsync addItemAsync = new SubItemOperationAsync(getActivity(), SubItemOperationAsync.SAVE);
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

    }

    @Override
    public void deleteItem(int position) {

    }

    @Override
    public void showSnackBar() {

    }

    @Override
    public void snackBarUndo() {

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
