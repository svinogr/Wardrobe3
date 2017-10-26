package info.upump.wardrobe3.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by explo on 26.10.2017.
 */

public class DataBaseHelper extends SQLiteOpenHelper {
    private Context context;
    public final static String DATABASE_NAME= "wardrobe.db";
    public final static String TABLE_MAIN_MENU= "main_menu";
    public final static int DATA_BASE_VERSION=1;
    private String  dbPath;
    public  static DataBaseHelper instance;

    public static  final  String TABLE_KEY_ID="_id";
    public static  final  String TABLE_KEY_NAME="name";
    public static  final  String TABLE_KEY_IMG="img";

    private static final String CREATE_MAIN_TABLE =
            "CREATE TABLE " + TABLE_MAIN_MENU +
                    "(" +
                    TABLE_KEY_ID + " integer NOT NULL primary key autoincrement, " +
                    TABLE_KEY_NAME + " text, " +
                    TABLE_KEY_IMG + " text)";


    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATA_BASE_VERSION);
        this.context = context;
        dbPath = "/data/data/info.upump.wardrobe3/databases/" + DATABASE_NAME;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_MAIN_TABLE);
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
