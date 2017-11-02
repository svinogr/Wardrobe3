package info.upump.wardrobe3.dialog;

import android.content.Context;
import android.os.AsyncTask;

import info.upump.wardrobe3.db.MainTableDao;
import info.upump.wardrobe3.model.MainMenuItem;

/**
 * Created by explo on 29.10.2017.
 */

public class MainItemOperationAsync extends AsyncTask<MainMenuItem, Void, Long> {
    public static final int INSERT = 3;
    MainTableDao mainTableDao;
    public static final int DELETE = 0;
    public static final int UPDATE = 2;
    public static final int SAVE = 1;
    private  int operation;


    public MainItemOperationAsync(Context context, int operation) {
        mainTableDao = new MainTableDao(context);
        this.operation = operation;

    }

    @Override
    protected Long doInBackground(MainMenuItem... params) {
        MainMenuItem mainMenuItem = params[0];
        long res=0;

        switch (operation){
            case DELETE:
                res = mainTableDao.delete(mainMenuItem);
                break;
            case UPDATE:
                res = mainTableDao.update(mainMenuItem);
                break;
            case SAVE:
                res =  mainTableDao.save(mainMenuItem);
                break;
            case INSERT:
                mainTableDao.inserWithManualId(mainMenuItem);
        }
        // наверно нужно закрыть базу
    //    mainTableDao.close();
        return  res;
    }
}
