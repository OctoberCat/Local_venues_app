package com.localvenues.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/*
 * Created by OctoberCat on 04.06.16.
 */
public class DBHelper extends SQLiteOpenHelper {
    public static final String DB_NAME = "LocalVenues.db";
    public static final int DB_VERSION = 1;

    public static final String COLUMN_ID = "_id";
    //TODO ADD PRICES TABLE AND REFERENCE
    public static final String TABLE_VENUES = "venues";
    public static final String COLUMN_VENUE_NAME = "name";
    public static final String COLUMN_RATING = "rating";
    public static final String COLUMN_RATING_COLOR = "rating_color";
    public static final String COLUMN_LOCATION_ID = "location_id";
    public static final String COLUMN_PHOTO_ID = "photo_id";
    public static final String COLUMN_PHONE = "phone";
    // according to model it should be separate table, but nah..
    //    public static final String COLUMN_CONTACT_ID = "contact_id";

    //tips
    public static final String TABLE_TIPS = "tips";
    public static final String COLUMN_VENUE_ID = "venue_id";
    public static final String COLUMN_TIP_TEXT = "text";
    public static final String COLUMN_AUTHOR_ID = "user_id";

    //locations
    public static final String TABLE_LOCATIONS = "locations";
    public static final String COLUMN_ADDRESS = "address";
    public static final String COLUMN_LAT = "lat";
    public static final String COLUMN_LNG = "lng";
//    public static final String COLUMN_CITY = "city";

    //authors
    public static final String TABLE_USERS = "users";
    public static final String COLUMN_FIRST_NAME = "first_name";
    public static final String COLUMN_LAST_NAME = "last_name";

    //photos
    public static final String TABLE_PHOTOS = "photos";
    public static final String COLUMN_PREFIX = "prefix";
    public static final String COLUMN_SUFFIX = "suffix";

    //category - many-to-many TODO implement categories and prices later
/*    public static final String TABLE_CATEGORIES = "categories";
    public static final String COLUMN_CATEGORY_NAME = "category_name";*/

    private static DBHelper dbHelperInstance;

    private DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    public static synchronized DBHelper getInstance(Context context) {
        if (dbHelperInstance == null) {
            return dbHelperInstance = new DBHelper(context.getApplicationContext());
        } else {
            return dbHelperInstance;
        }

    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        String STM_CREATE_TABLE = "CREATE TABLE ";

        String STM_PRIMARY_KEY = "PRIMARY KEY ";
        String STM_FOREIGN_KEY = " FOREIGN KEY ";
        String STM_REFERENCES = " REFERENCES ";
        String STM_TEXT = " TEXT ";
        String STM_AUTOINCREMENT = " AUTOINCREMENT, ";

        String STM_TEXT_PRIMARY_KEY = STM_TEXT + STM_PRIMARY_KEY;
        String STM_NOT_NULL = " NOT NULL, ";
        String STM_REAL = " REAL ";
        String STM_INTEGER = " INTEGER ";


        String CREATE_TABLE_VENUES = STM_CREATE_TABLE + TABLE_VENUES + " ( " +
                COLUMN_ID + STM_TEXT_PRIMARY_KEY + ", " +
                COLUMN_VENUE_NAME + STM_TEXT + STM_NOT_NULL +
                COLUMN_RATING + STM_REAL + STM_NOT_NULL +
                COLUMN_RATING_COLOR + STM_TEXT + STM_NOT_NULL +
                COLUMN_LOCATION_ID + STM_INTEGER + STM_NOT_NULL +
                COLUMN_PHOTO_ID + STM_TEXT + STM_NOT_NULL +
                COLUMN_PHONE + STM_TEXT + ", " +
                STM_FOREIGN_KEY + "(" + COLUMN_LOCATION_ID + ")" + STM_REFERENCES + TABLE_LOCATIONS + "(" + COLUMN_ID + "), " +
                STM_FOREIGN_KEY + "(" + COLUMN_PHOTO_ID + ")" + STM_REFERENCES + TABLE_PHOTOS + "(" + COLUMN_ID + ") "
                + ");";//

        String CREATE_TABLE_LOCATIONS = STM_CREATE_TABLE + TABLE_LOCATIONS + " ( " +
                COLUMN_ID + STM_INTEGER + STM_PRIMARY_KEY + STM_AUTOINCREMENT +
                COLUMN_ADDRESS + STM_TEXT + STM_NOT_NULL +
                COLUMN_LAT + STM_REAL + STM_NOT_NULL +
                COLUMN_LNG + STM_REAL + STM_NOT_NULL
//                COLUMN_CITY + STM_TEXT
                + ");";//

        String CREATE_TABLE_TIPS = STM_CREATE_TABLE + TABLE_TIPS + " ( " +
                COLUMN_ID + STM_TEXT_PRIMARY_KEY + ", " +
                COLUMN_VENUE_ID + STM_TEXT + STM_NOT_NULL +
                COLUMN_AUTHOR_ID + STM_TEXT + STM_NOT_NULL +
                COLUMN_TIP_TEXT + STM_TEXT + STM_NOT_NULL +
                STM_FOREIGN_KEY + "(" + COLUMN_VENUE_ID + ")" + STM_REFERENCES + TABLE_VENUES + "(" + COLUMN_ID + "), " +
                STM_FOREIGN_KEY + "(" + COLUMN_AUTHOR_ID + ")" + STM_REFERENCES + TABLE_USERS + "(" + COLUMN_ID + "), " +
                STM_FOREIGN_KEY + "(" + COLUMN_PHOTO_ID + ")" + STM_REFERENCES + TABLE_PHOTOS + "(" + COLUMN_ID + ") "
                + ");";

        String CREATE_TABLE_PHOTOS = STM_CREATE_TABLE + TABLE_PHOTOS + " ( " +
                COLUMN_ID + STM_TEXT_PRIMARY_KEY + ", " +/*STM_INTEGER + STM_PRIMARY_KEY + STM_AUTOINCREMENT +*/
                COLUMN_PREFIX + STM_TEXT + STM_NOT_NULL +
                COLUMN_SUFFIX + STM_TEXT + STM_NOT_NULL
                + ");";

        String CREATE_TABLE_USERS = STM_CREATE_TABLE + TABLE_USERS + " ( " +
                COLUMN_ID + STM_TEXT_PRIMARY_KEY + ", " +
                COLUMN_FIRST_NAME + STM_TEXT + STM_NOT_NULL +
                COLUMN_LAST_NAME + STM_TEXT + STM_NOT_NULL +
                STM_FOREIGN_KEY + " ( " + COLUMN_PHOTO_ID + " ) " + STM_REFERENCES + TABLE_PHOTOS + " (" + COLUMN_ID + " ) "
                + ");";

        db.execSQL(CREATE_TABLE_VENUES);
        db.execSQL(CREATE_TABLE_LOCATIONS);
        db.execSQL(CREATE_TABLE_TIPS);
        db.execSQL(CREATE_TABLE_PHOTOS);
        db.execSQL(CREATE_TABLE_USERS);

    }

    //The simplest implementation of onUpgrade() method
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        String STM_DROP_TABLE_IF_EXISTS = "DROP TABLE IF EXISTS ";
        if (oldVersion != newVersion) {
            db.execSQL(STM_DROP_TABLE_IF_EXISTS + TABLE_VENUES);
            db.execSQL(STM_DROP_TABLE_IF_EXISTS + TABLE_LOCATIONS);
            db.execSQL(STM_DROP_TABLE_IF_EXISTS + TABLE_PHOTOS);
            db.execSQL(STM_DROP_TABLE_IF_EXISTS + TABLE_TIPS);
            db.execSQL(STM_DROP_TABLE_IF_EXISTS + TABLE_USERS);
        }
    }
}
