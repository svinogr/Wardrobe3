package info.upump.wardrobe3.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import info.upump.wardrobe3.R;

/**
 * Created by explo on 26.10.2017.
 */

public class DataBaseHelper extends SQLiteOpenHelper {
    private Context context;
    public final static String DATABASE_NAME = "wardrobe.db";
    public final static String TABLE_MAIN_MENU = "main_menu";
    public final static String TABLE_SUB_ITEM = "sub_item";
    public final static int DATA_BASE_VERSION = 1;
    private String dbPath;
    public static DataBaseHelper instance;

    public static final String TABLE_KEY_ID = "_id";
    public static final String TABLE_KEY_NAME = "name";
    public static final String TABLE_KEY_IMG = "img";

    private static final String CREATE_MAIN_TABLE =
            "CREATE TABLE " + TABLE_MAIN_MENU +
                    "(" +
                    TABLE_KEY_ID + " integer NOT NULL primary key autoincrement, " +
                    TABLE_KEY_NAME + " text, " +
                    TABLE_KEY_IMG + " text)";

    public static final String TABLE_KEY_COST = "cost";
    public static final String TABLE_KEY_ID_MAIN = "id_main";
    public static final String TABLE_KEY_DESCRIPTION = "description";
    public static final String CREATE_SUB_ITEM_TABLE =
            "CREATE TABLE " + TABLE_SUB_ITEM +
                    "(" +
                    TABLE_KEY_ID + " integer NOT NULL primary key autoincrement, " +
                    TABLE_KEY_NAME + " text, " +
                    TABLE_KEY_IMG + " text, " +
                    TABLE_KEY_COST + " float, " +
                    TABLE_KEY_ID_MAIN + " integer, " +
                    TABLE_KEY_DESCRIPTION + " text)";


    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATA_BASE_VERSION);
        this.context = context;
        dbPath = context.getString(R.string.db_path) + DATABASE_NAME;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_MAIN_TABLE);
        db.execSQL(CREATE_SUB_ITEM_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public static synchronized DataBaseHelper getHelper(Context context) {
        if (instance == null) {
            instance = new DataBaseHelper(context);
        }
        return instance;
    }
}
