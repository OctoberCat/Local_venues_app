package com.forsquare_android_vternovoi.revenueDB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by valentin on 09.12.15.
 */
public class FoursquareDBHelper extends SQLiteOpenHelper {


    public final static String VENUES_TABLE_NAME = "venues";
    //venue not OK todo manage photos table
    public final static String COLUMN_ID = "_id";
    public final static String COLUMN_NAME = "name";
    public final static String COLUMN_CONTACT_ID = "contact_id";
    public final static String COLUMN_LOCATION_ID = "location_id";
    public final static String COLUMN_URL = "url";
    public final static String COLUMN_RATING = "rating";
    public final static String COLUMN_RATING_COLOR = "rating_color";
    public final static String COLUMN_PHOTOS_ID = "photos_id"; //todo check this //upd store venue id in photos table
    //contacts OK
    public final static String CONTACTS_TABLE_NAME = "contacts";
    public final static String COLUMN_PHONE = "phone"; //formatted phone will be used
    //locations OK
    public final static String LOCATIONS_TABLE_NAME = "locations";
    public final static String COLUMN_ADDRESS = "address";
    public final static String COLUMN_LAT = "lat";
    public final static String COLUMN_LNG = "lng";
    public final static String COLUMN_CITY = "city";
    // tips not OK - todo fix model
    public final static String TIPS_TABLE_NAME = "tips";
    public final static String COLUMN_TIP_TEXT = "tip_text";
    public final static String COLUMN_CANONICAL_URL = "canonical_url";
    private final static String DATABASE_NAME = "foursquare.db";
    private final static int DATABASE_VERSION = 1;
    private static final String CREATE_VENUE_TABLE = " create table " + VENUES_TABLE_NAME + " ( " +
            COLUMN_ID + " text primary key, " + COLUMN_NAME + " text not null, "
            + COLUMN_CONTACT_ID + " integer, "
            + COLUMN_LOCATION_ID + " integer, " +
            COLUMN_URL + " text, " + COLUMN_RATING + " real not null, " + COLUMN_RATING_COLOR + " text not null "
            + ");";


/*    *//*    private static final String CREATE_VENUE_TABLE = " create table " + VENUES_TABLE_NAME + " ( " +
                COLUMN_ID + " text primary key, " + COLUMN_NAME + " text not null, "
                + COLUMN_CONTACT_ID + " integer, " + " foreign key(" + COLUMN_CONTACT_ID + ") references " + CONTACTS_TABLE_NAME +
                "(" + COLUMN_ID + "), "
                + COLUMN_LOCATION_ID + " integer, " + " foreign key(" + COLUMN_LOCATION_ID + ") references " + LOCATIONS_TABLE_NAME +
                "(" + COLUMN_ID + "), " +
                COLUMN_URL + " text not null, " + COLUMN_RATING + " real not null, " + COLUMN_RATING_COLOR + " text not null "
                + ");";*/
    private static final String CREATE_LOCATIONS_TABLE = " create table " + LOCATIONS_TABLE_NAME + " ( " + COLUMN_ID + " integer primary key autoincrement, " +
            COLUMN_ADDRESS + " text not null, " + COLUMN_LAT + " real not null," + COLUMN_LNG + " real not null," + COLUMN_CITY + " text " + ");";
    private static final String CREATE_CONTACTS_TABLE = " create table " + CONTACTS_TABLE_NAME + " ( " +
            COLUMN_ID + " integer primary key autoincrement, " + COLUMN_PHONE + " text " + ");";
    private static final String CREATE_TIPS_TABLE = " create table " + TIPS_TABLE_NAME + " ( " +
            COLUMN_ID + " integer primary key autoincrement, " + COLUMN_TIP_TEXT + " text " + COLUMN_CANONICAL_URL + " text not null " + ");";
    private static FoursquareDBHelper foursquareDBHelper = null;

    private FoursquareDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static FoursquareDBHelper getInstance(Context context) {
        if (foursquareDBHelper == null) {
            return foursquareDBHelper = new FoursquareDBHelper(context);
        } else {
            return foursquareDBHelper;
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_VENUE_TABLE);
        db.execSQL(CREATE_LOCATIONS_TABLE);
        db.execSQL(CREATE_CONTACTS_TABLE);
        db.execSQL(CREATE_TIPS_TABLE);
       /* db.execSQL();*/
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(FoursquareDBHelper.class.getName(), "Upgrading database from version "
                + oldVersion + " to " + newVersion
                + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + VENUES_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + CONTACTS_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + LOCATIONS_TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TIPS_TABLE_NAME);
        onCreate(db);
        Log.i("onUpgrade()", "check creation");
    }
}
