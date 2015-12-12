package com.forsquare_android_vternovoi.revenueDB;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.forsquare_android_vternovoi.models.Contact;
import com.forsquare_android_vternovoi.models.Item;
import com.forsquare_android_vternovoi.models.Location;
import com.forsquare_android_vternovoi.models.Venue;

import java.util.List;

/**
 * Created by valentin on 11.12.15.
 */
public class FoursquareDataSource {
    private SQLiteDatabase database;
    private FoursquareDBHelper dbHelper;
    private int version = 1;

    public FoursquareDataSource(Context context) {
        dbHelper = new FoursquareDBHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public void dropAndUpgrade() {
        dbHelper.onUpgrade(database, version, version++);
    }//do u feel shame, right?


    //venues processing venues
    public Venue createVenue(String id, String name, long contactId, long locationId,
                             String url, Double rating, String ratingColor) {

        ContentValues values = new ContentValues();
        values.put(FoursquareDBHelper.COLUMN_ID, id);
        values.put(FoursquareDBHelper.COLUMN_NAME, name);
        values.put(FoursquareDBHelper.COLUMN_CONTACT_ID, contactId);
        values.put(FoursquareDBHelper.COLUMN_LOCATION_ID, locationId);
        values.put(FoursquareDBHelper.COLUMN_RATING, rating);
        values.put(FoursquareDBHelper.COLUMN_RATING_COLOR, ratingColor);
        long insertId = database.insert(FoursquareDBHelper.VENUES_TABLE_NAME, null, values);
        return null;
    }

    public Venue createVenue(Item item) {
        Venue venue = item.getVenue();
        ContentValues values = new ContentValues();
        values.put(FoursquareDBHelper.COLUMN_ID, venue.getId());
        values.put(FoursquareDBHelper.COLUMN_NAME, venue.getName());
        values.put(FoursquareDBHelper.COLUMN_CONTACT_ID, createContact(venue.getContact()));
        values.put(FoursquareDBHelper.COLUMN_LOCATION_ID, createLocation(venue.getLocation()));
        values.put(FoursquareDBHelper.COLUMN_RATING, venue.getRating());
        values.put(FoursquareDBHelper.COLUMN_RATING_COLOR, venue.getRatingColor());
        long insertId = database.insert(FoursquareDBHelper.VENUES_TABLE_NAME, null, values);
        return null;
    }

    public void createVenues(List<Item> itemList) {
        for (Item item : itemList) {
            createVenue(item);
        }

    }

    public List<Venue> getAllVenues() {
        return null;
    }

    //
    public long createContact(Contact contact) {
        long insertId = 0;
        return insertId;
    }

    public long createLocation(Location location) {
        long insertId = 0;
        return insertId;
    }

    private Venue cursorToVenue(Cursor cursor) {
        Venue venue = new Venue();
        venue.setId(cursor.getString(0));
        venue.setName(cursor.getString(1));
        //venue.s(cursor.getString(2));

        return venue;
    }
}
