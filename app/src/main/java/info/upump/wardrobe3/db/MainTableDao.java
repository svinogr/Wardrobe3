package info.upump.wardrobe3.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import info.upump.wardrobe3.model.MainMenuItem;
import info.upump.wardrobe3.model.SubItem;

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
        cv.put(DataBaseHelper.TABLE_KEY_MASK, item.getEnumMask().ordinal());
        System.out.println("сохраняю главное меню");
        return database.insert(DataBaseHelper.TABLE_MAIN_MENU, null, cv);

    }

    @Override
    public Cursor getAll() {
        Cursor cursor = database.query(DataBaseHelper.TABLE_MAIN_MENU,
                new String[]{
                        DataBaseHelper.TABLE_KEY_ID,
                        DataBaseHelper.TABLE_KEY_NAME,
                        DataBaseHelper.TABLE_KEY_IMG,
                        DataBaseHelper.TABLE_KEY_MASK},
                null, null, null, null, null, null
        );
        return cursor;

    }

    @Override
    public Long delete(MainMenuItem item) {
        SubItem subItem = new SubItem();
        subItem.setIdMainItem(item.getId());
        SubItemTableDao dao = new SubItemTableDao(context);

        dao.deleteAllByParentId(subItem);


        return (long) database.delete(DataBaseHelper.TABLE_MAIN_MENU, DataBaseHelper.TABLE_KEY_ID + "=" + item.getId(), null);

    }

    @Override
    public Long update(MainMenuItem item) {
        ContentValues cv = new ContentValues();
        cv.put(DataBaseHelper.TABLE_KEY_NAME, item.getName() );
        cv.put(DataBaseHelper.TABLE_KEY_IMG, item.getImg());
        cv.put(DataBaseHelper.TABLE_KEY_MASK, item.getEnumMask().ordinal());

      return (long) database.update(DataBaseHelper.TABLE_MAIN_MENU, cv, DataBaseHelper.TABLE_KEY_ID + "=" + item.getId(), null);


    }

    @Override
    public Long insertWithManualId(MainMenuItem item) {
        ContentValues cv = new ContentValues();
        cv.put(DataBaseHelper.TABLE_KEY_ID, item.getId());
        cv.put(DataBaseHelper.TABLE_KEY_NAME, item.getName() );
        cv.put(DataBaseHelper.TABLE_KEY_IMG, item.getImg());
        cv.put(DataBaseHelper.TABLE_KEY_MASK, item.getEnumMask().ordinal());
      return database.insert(DataBaseHelper.TABLE_MAIN_MENU, null, cv);

    }

    @Override
    public void deleteAllByParentId(MainMenuItem object) {
        //NOP
    }

    @Override
    public Cursor getByParentId(long id) {
        return null;
    }
}
