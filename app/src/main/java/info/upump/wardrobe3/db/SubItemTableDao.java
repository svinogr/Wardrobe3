package info.upump.wardrobe3.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import info.upump.wardrobe3.model.MainMenuItem;
import info.upump.wardrobe3.model.SubItem;

/**
 * Created by explo on 02.11.2017.
 */

public class SubItemTableDao extends DBDao implements DataBasicOperation<SubItem> {
    public SubItemTableDao(Context context) {
        super(context);
    }

    @Override
    public Long save(SubItem item) {
        ContentValues cv = new ContentValues();
        cv.put(DataBaseHelper.TABLE_KEY_NAME, item.getName() );
        cv.put(DataBaseHelper.TABLE_KEY_IMG, item.getImg());
        cv.put(DataBaseHelper.TABLE_KEY_COST, item.getCost());
        cv.put(DataBaseHelper.TABLE_KEY_DESCRIPTION, item.getDescription());
        cv.put(DataBaseHelper.TABLE_KEY_ID_MAIN, item.getIdMainItem());

        System.out.println("сохраняю sub меню");
        return database.insert(DataBaseHelper.TABLE_SUB_ITEM, null, cv);

    }

    @Override
    public Cursor getAll() {
        return database.query(DataBaseHelper.TABLE_SUB_ITEM,
                new String[]{
                        DataBaseHelper.TABLE_KEY_ID,
                        DataBaseHelper.TABLE_KEY_NAME,
                        DataBaseHelper.TABLE_KEY_IMG},
                null, null, null, null, null, null
        );
    }

    @Override
    public Long delete(SubItem item) {
       /* Uri uri;
        if(item.getImg()!=null) {
            uri = Uri.parse(item.getImg());
            context.getContentResolver().delete(uri, null, null);
        }*/

        return (long) database.delete(DataBaseHelper.TABLE_SUB_ITEM, DataBaseHelper.TABLE_KEY_ID + "=" + item.getId(), null);
    }

    @Override
    public Long update(SubItem item) {
        System.out.println(" update db sub"+ item.getId());
        ContentValues cv = new ContentValues();
        cv.put(DataBaseHelper.TABLE_KEY_NAME, item.getName() );
        cv.put(DataBaseHelper.TABLE_KEY_IMG, item.getImg());
        cv.put(DataBaseHelper.TABLE_KEY_COST, item.getCost());
        cv.put(DataBaseHelper.TABLE_KEY_DESCRIPTION, item.getDescription());
        cv.put(DataBaseHelper.TABLE_KEY_ID_MAIN, item.getIdMainItem());
        return (long) database.update(DataBaseHelper.TABLE_SUB_ITEM, cv, DataBaseHelper.TABLE_KEY_ID + "=" + item.getId(), null);
    }

    @Override
    public Long insertWithManualId(SubItem item) {
        ContentValues cv = new ContentValues();
        cv.put(DataBaseHelper.TABLE_KEY_NAME, item.getName() );
        cv.put(DataBaseHelper.TABLE_KEY_IMG, item.getImg());
        cv.put(DataBaseHelper.TABLE_KEY_COST, item.getCost());
        cv.put(DataBaseHelper.TABLE_KEY_DESCRIPTION, item.getDescription());
        cv.put(DataBaseHelper.TABLE_KEY_ID_MAIN, item.getIdMainItem());
        cv.put(DataBaseHelper.TABLE_KEY_ID,item.getId());
        return database.insert(DataBaseHelper.TABLE_SUB_ITEM, null, cv);
    }

    @Override
    public void deleteAllByParentId(SubItem item) {
        database.delete(DataBaseHelper.TABLE_SUB_ITEM, DataBaseHelper.TABLE_KEY_ID_MAIN + "=" + item.getIdMainItem(), null);

    }

    @Override
    public Cursor getByParentId(long id) {
        System.out.println("id  v dao "+id);
        return database.query(DataBaseHelper.TABLE_SUB_ITEM,
                new String[]{
                        DataBaseHelper.TABLE_KEY_ID,
                        DataBaseHelper.TABLE_KEY_NAME,
                        DataBaseHelper.TABLE_KEY_IMG,
                        DataBaseHelper.TABLE_KEY_COST,
                        DataBaseHelper.TABLE_KEY_ID_MAIN,
                        DataBaseHelper.TABLE_KEY_DESCRIPTION},
                DataBaseHelper.TABLE_KEY_ID_MAIN + "=? ", new String[]{String.valueOf(id)}, null, null, null, null);
    }

    @Override
    public Cursor getById(long id){
        return database.query(DataBaseHelper.TABLE_SUB_ITEM,
                new String[]{
                        DataBaseHelper.TABLE_KEY_ID,
                        DataBaseHelper.TABLE_KEY_NAME,
                        DataBaseHelper.TABLE_KEY_IMG,
                        DataBaseHelper.TABLE_KEY_COST,
                        DataBaseHelper.TABLE_KEY_ID_MAIN,
                        DataBaseHelper.TABLE_KEY_DESCRIPTION},
                DataBaseHelper.TABLE_KEY_ID + "=? ", new String[]{String.valueOf(id)}, null, null, null, null);
    }


}
