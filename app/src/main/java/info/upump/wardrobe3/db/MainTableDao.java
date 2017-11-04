package info.upump.wardrobe3.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import info.upump.wardrobe3.model.MainMenuItem;

/**
 * Created by explo on 26.10.2017.
 */

public class MainTableDao extends DBDao implements DataBasicOperation<MainMenuItem>
{

    public MainTableDao(Context context) {
        super(context);
    }

    @Override
    public Long save(MainMenuItem item) {
        ContentValues cv = new ContentValues();
        cv.put(DataBaseHelper.TABLE_KEY_NAME, item.getName() );
        cv.put(DataBaseHelper.TABLE_KEY_IMG, item.getImg());
        System.out.println("сохраняю главное меню");
        return database.insert(DataBaseHelper.TABLE_MAIN_MENU, null, cv);

    }

    @Override
    public Cursor getAll() {
        Cursor cursor = database.query(DataBaseHelper.TABLE_MAIN_MENU,
                new String[]{
                        DataBaseHelper.TABLE_KEY_ID,
                        DataBaseHelper.TABLE_KEY_NAME,
                        DataBaseHelper.TABLE_KEY_IMG},
                null, null, null, null, null, null
        );
        return cursor;

    }

    @Override
    public Long delete(MainMenuItem item) {
        return (long) database.delete(DataBaseHelper.TABLE_MAIN_MENU, DataBaseHelper.TABLE_KEY_ID + "=" + item.getId(), null);

    }

    @Override
    public Long update(MainMenuItem item) {
        ContentValues cv = new ContentValues();
        cv.put(DataBaseHelper.TABLE_KEY_NAME, item.getName() );
        cv.put(DataBaseHelper.TABLE_KEY_IMG, item.getImg());

      return (long) database.update(DataBaseHelper.TABLE_MAIN_MENU, cv, DataBaseHelper.TABLE_KEY_ID + "=" + item.getId(), null);


    }

    @Override
    public Long insertWithManualId(MainMenuItem item) {
        ContentValues cv = new ContentValues();
        cv.put(DataBaseHelper.TABLE_KEY_ID, item.getId());
        cv.put(DataBaseHelper.TABLE_KEY_NAME, item.getName() );
        cv.put(DataBaseHelper.TABLE_KEY_IMG, item.getImg());
      return database.insert(DataBaseHelper.TABLE_MAIN_MENU, null, cv);

    }

    @Override
    public Cursor getByParentId(long id) {
        return null;
    }
}
