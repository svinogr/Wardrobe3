package info.upump.wardrobe3.dialog;

import android.content.Context;
import android.os.AsyncTask;

import info.upump.wardrobe3.db.SubItemTableDao;
import info.upump.wardrobe3.model.SubItem;

/**
 * Created by explo on 02.11.2017.
 */

public class SubItemOperationAsync extends AsyncTask<SubItem,Void,Long> {
    public static final int INSERT = 3;
   private SubItemTableDao subItemTableDao;
    public static final int DELETE = 0;
    public static final int UPDATE = 2;
    public static final int SAVE = 1;
    private  int operation;

    public SubItemOperationAsync(Context context, int operation) {
        subItemTableDao = new SubItemTableDao(context);
        this.operation = operation;

    }

    @Override
    protected Long doInBackground(SubItem... params) {
        SubItem mainMenuItem = params[0];
        long res=0;

        switch (operation){
            case DELETE:
                res = subItemTableDao.delete(mainMenuItem);
                break;
            case UPDATE:
                res = subItemTableDao.update(mainMenuItem);
                break;
            case SAVE:
                res =  subItemTableDao.save(mainMenuItem);
                break;
            case INSERT:
                subItemTableDao.insertWithManualId(mainMenuItem);
        }
        // наверно нужно закрыть базу
        //    mainTableDao.close();
        return  res;
    }
}
