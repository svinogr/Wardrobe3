package info.upump.wardrobe3.db;

import android.content.Loader;
import android.database.Cursor;

import info.upump.wardrobe3.model.MainMenuItem;

/**
 * Created by explo on 26.10.2017.
 */

public interface DataBasicOperation<T> {
    Long save(T item);
    Cursor getAll();
    Long delete(T item);
    Long update(T item);
    Long insertWithManualId(T object);
    void deleteAllByParentId(T object);

    Cursor getByParentId(long id);
    Cursor getById(long id);
}
