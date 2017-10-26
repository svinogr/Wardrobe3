package info.upump.wardrobe3.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by explo on 26.10.2017.
 */

public class DBDao {
    protected SQLiteDatabase database;
    private DataBaseHelper dataBaseHelper;
    private Context context;

    public DBDao(Context context) {
        this.context = context;
        dataBaseHelper = DataBaseHelper.getHelper(context);
        open();
    }

    public void open() {
        if (dataBaseHelper == null)
            dataBaseHelper = DataBaseHelper.getHelper(context);
        database = dataBaseHelper.getWritableDatabase();

    }

    public void close() {
        dataBaseHelper.close();
        database = null;
    }
}
