package info.upump.wardrobe3.dialog;

import android.content.Context;
import android.os.AsyncTask;

import info.upump.wardrobe3.db.MainTableDao;
import info.upump.wardrobe3.model.MainMenuItem;

/**
 * Created by explo on 29.10.2017.
 */

public class MainItemOperationAsynck extends AsyncTask<MainMenuItem, Void, Boolean> {
    MainTableDao mainTableDao;
    public static final int DELETE = 0;
    public static final int UPDATE = 2;
    public static final int SAVE = 1;
    private  int operation;


    public MainItemOperationAsynck(Context context, int operation) {
        mainTableDao = new MainTableDao(context);
        this.operation = operation;

    }

    @Override
    protected Boolean doInBackground(MainMenuItem... params) {
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
        }
        // наверно нужно закрыть базу
    //    mainTableDao.close();
        if(res>0) {
            return true;
        }else return false;
    }
}
