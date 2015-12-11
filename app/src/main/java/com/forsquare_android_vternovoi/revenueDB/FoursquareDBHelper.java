package com.forsquare_android_vternovoi.revenueDB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by valentin on 09.12.15.
 */
public class FoursquareDBHelper extends SQLiteOpenHelper {
    //venues
 /*
    private String id;
    private String name;
    private Contact contact;
    private Location location;
    private Stats stats;
    private String url;
    private Double rating;
    private String ratingColor;
    private Hours hours;
    private Photos photos;
    private FeaturedPhotos featuredPhotos;
*/


    //venue not OK todo manage photos table

    private final static String VENUES_TABLE_NAME = "venues";
    private final static String COLUMN_ID = "_id";
    private final static String COLUMN_NAME = "name";
    private final static String COLUMN_CONTACT_ID = "contact_id";
    private final static String COLUMN_LOCATION_ID = "location_id";
    private final static String COLUMN_URL = "url";
    private final static String COLUMN_RATING = "rating";
    private final static String COLUMN_RATING_COLOR = "rating_color";
    private final static String COLUMN_PHOTOS_ID = "photos_id"; //todo check this

    //contacts OK
    private final static String CONTACTS_TABLE_NAME = "contacts";
    private final static String COLUMN_PHONE = "phone"; //formatted phone will be used

    //locations OK
    private final static String LOCATIONS_TABLE_NAME = "locations";
    private final static String COLUMN_ADDRESS = "address";
    private final static String COLUMN_LAT = "lat";
    private final static String COLUMN_LNG = "lng";
    private final static String COLUMN_CITY = "city";

    // tips not OK - todo fix model
    private final static String TIPS_TABLE_NAME = "tips";
    private final static String COLUMN_TIP_TEXT = "tip_text";
    private final static String COLUMN_CANONICAL_URL = "canonical_url";

    private final static String DATABASE_NAME = "foursquare.db";
    private final static int DATABASE_VERSION = 1;


    private static final String CREATE_VENUE_TABLE = " create table " + VENUES_TABLE_NAME + " ( " +
            COLUMN_ID + " text primary key, " + COLUMN_NAME + " text not null, "
            + COLUMN_CONTACT_ID + " integer, " + "foreign key(" + COLUMN_CONTACT_ID + ") references " + CONTACTS_TABLE_NAME +
            "(" + COLUMN_ID + "), "
            + COLUMN_LOCATION_ID + " integer, " + "foreign key(" + COLUMN_LOCATION_ID + ") references " + LOCATIONS_TABLE_NAME +
            "(" + COLUMN_ID + "), " +
            COLUMN_URL + " text not null, " + COLUMN_RATING + " real not null, " + COLUMN_RATING_COLOR + " text not null, "
            + ");";

    private static final String CREATE_CONTACTS_TABLE = " create table " + CONTACTS_TABLE_NAME + " ( " +
            COLUMN_ID + " integer primary key autoincrement, " + COLUMN_NAME + " text not null, "
            + COLUMN_CONTACT_ID + " integer not null, " + COLUMN_LOCATION_ID + " integer not null, " +
            COLUMN_URL + " text not null, " + COLUMN_RATING + " real not null, " + COLUMN_RATING_COLOR + " text not null, "
            + ");";

    public FoursquareDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
/*        db.execSQL();
        db.execSQL();
        db.execSQL();
        db.execSQL();*/
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
    }
}
