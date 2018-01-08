package info.upump.wardrobe3.loader;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.content.AsyncTaskLoader;

import java.util.ArrayList;
import java.util.List;

import info.upump.wardrobe3.db.MainTableDao;
import info.upump.wardrobe3.model.EnumMask;
import info.upump.wardrobe3.model.MainMenuItem;

/**
 * Created by explo on 26.10.2017.
 */

public class LoaderMainMenu extends AsyncTaskLoader<List<MainMenuItem>> {
    MainTableDao mainTableDao;

    public LoaderMainMenu(Context context) {
        super(context);
        mainTableDao = new MainTableDao(context);
    }

    @Override
    public List<MainMenuItem> loadInBackground() {
        System.out.println("loadInBackground loader ");
        List<MainMenuItem> list = new ArrayList<>();
        Cursor cursor= mainTableDao.getAll();
        MainMenuItem mainMenuItem;
        if(cursor.moveToFirst()){
            do {
                mainMenuItem = new MainMenuItem();
                mainMenuItem.setId(cursor.getInt(0));
                mainMenuItem.setName(cursor.getString(1));
                mainMenuItem.setImg(cursor.getString(2));
                mainMenuItem.setEnumMask(EnumMask.values()[cursor.getInt(3)]);

                list.add(mainMenuItem);
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
    public void deliverResult(List<MainMenuItem> data) {
        super.deliverResult(data);
    }

    @Override
    protected void onForceLoad() {
        super.onForceLoad();
    }
}
