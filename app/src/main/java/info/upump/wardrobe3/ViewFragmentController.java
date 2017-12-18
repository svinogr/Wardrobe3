package info.upump.wardrobe3;

import info.upump.wardrobe3.model.MainMenuItem;

/**
 * Created by explo on 29.10.2017.
 */

public interface ViewFragmentController<T> {
    void addNewItem(T object);
    void updateItem(T object);
    void deleteItem(int position);
    void showSnackBar();
    void snackBarUndo();

    void editItem(int positionMainItem);

    void cancelUpdate();
    long getIdDb();

    String getFragmentTag();


    void restartLoader();
}
