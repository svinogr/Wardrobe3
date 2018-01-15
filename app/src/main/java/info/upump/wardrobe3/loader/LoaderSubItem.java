package info.upump.wardrobe3.loader;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.content.AsyncTaskLoader;

import java.util.ArrayList;
import java.util.List;

import info.upump.wardrobe3.db.SubItemTableDao;
import info.upump.wardrobe3.model.SubItem;

/**
 * Created by explo on 02.11.2017.
 */

public class LoaderSubItem extends AsyncTaskLoader<List<SubItem>> {
      private SubItemTableDao subItemDao;
    private long id;

    public LoaderSubItem(Context context, long id) {
        super(context);
        System.out.println("создаем загрузкик для "+ id);
        subItemDao = new SubItemTableDao(context);
        this.id = id;
    }

    @Override
    public List<SubItem> loadInBackground() {
        System.out.println("loadInBackground loader ");
        List<SubItem> list = new ArrayList<>();
        Cursor cursor= subItemDao.getByParentId(id);
        SubItem subItem;
        if(cursor.moveToFirst()){
            do {
                subItem = new SubItem();
                subItem.setId(cursor.getInt(0));
                subItem.setName(cursor.getString(1));
                subItem.setImgUriToString(cursor.getString(2));
                subItem.setCost(cursor.getFloat(3));
                 // TODO дату вписать
                subItem.setIdMainItem(cursor.getLong(4));
                subItem.setDescription(cursor.getString(5));
                list.add(subItem);
            }while (cursor.moveToNext());
        }
        return list;
    }
    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        forceLoad();
    }

    @Override
    protected void onStopLoading() {
        super.onStopLoading();

    }

    @Override
    public void deliverResult(List<SubItem> data) {
        super.deliverResult(data);
    }

    @Override
    protected void onForceLoad() {
        super.onForceLoad();
    }
}
