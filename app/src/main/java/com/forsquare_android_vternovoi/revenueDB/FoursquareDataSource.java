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

import java.util.ArrayList;
import java.util.List;

/*
  Created by valentin on 11.12.15.
 */
public class FoursquareDataSource {
    private static FoursquareDBHelper dbHelper;
    private SQLiteDatabase database;
    private String[] allColumnsVenues = {FoursquareDBHelper.COLUMN_ID, FoursquareDBHelper.COLUMN_NAME,
            FoursquareDBHelper.COLUMN_CONTACT_ID, FoursquareDBHelper.COLUMN_LOCATION_ID, FoursquareDBHelper.COLUMN_URL, FoursquareDBHelper.COLUMN_RATING, FoursquareDBHelper.COLUMN_RATING_COLOR};
    private String[] allColumnsContact = {FoursquareDBHelper.COLUMN_ID, FoursquareDBHelper.COLUMN_PHONE};
    private String[] allColumnsLocation = {FoursquareDBHelper.COLUMN_ID, FoursquareDBHelper.COLUMN_ADDRESS, FoursquareDBHelper.COLUMN_LAT, FoursquareDBHelper.COLUMN_LNG,
            FoursquareDBHelper.COLUMN_CITY};

    public FoursquareDataSource(Context context) {
        dbHelper = FoursquareDBHelper.getInstance(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public void deleteVenueTable() {
        database.delete(FoursquareDBHelper.VENUES_TABLE_NAME, null, null);
    }//changed from onUpgrade


    public void persistVenues(List<Item> itemList) {
        database.beginTransaction();
        try {
            for (Item item : itemList) {
                Venue venue = item.getVenue();
                ContentValues values = new ContentValues();
                values.put(FoursquareDBHelper.COLUMN_ID, venue.getId());
                values.put(FoursquareDBHelper.COLUMN_NAME, venue.getName());
                values.put(FoursquareDBHelper.COLUMN_CONTACT_ID, persistContact(venue.getContact())); //retrieve ID and make a record
                values.put(FoursquareDBHelper.COLUMN_LOCATION_ID, persistLocation(venue.getLocation()));//
                values.put(FoursquareDBHelper.COLUMN_RATING, venue.getRating());
                values.put(FoursquareDBHelper.COLUMN_RATING_COLOR, venue.getRatingColor());
                database.insert(FoursquareDBHelper.VENUES_TABLE_NAME, null, values);
                database.insertWithOnConflict(FoursquareDBHelper.VENUES_TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE);
            }
            database.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            database.endTransaction();
        }
    }

    public List<Venue> getAllVenues() {
        List<Venue> venueList = new ArrayList<>();
        Cursor cursor = database.query(FoursquareDBHelper.VENUES_TABLE_NAME, allColumnsVenues, null, null, null,
                null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Venue venue = cursorToVenue(cursor);
            venueList.add(venue);
            cursor.moveToNext();
        }
        return venueList;
    }

    public long persistContact(Contact contact) {
        ContentValues values = new ContentValues();
        values.put(FoursquareDBHelper.COLUMN_PHONE, contact.getFormattedPhone());
        long insertId = database.insertWithOnConflict(FoursquareDBHelper.CONTACTS_TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE);
        return insertId;
    }

    public long persistLocation(Location location) {
        ContentValues values = new ContentValues();
        values.put(FoursquareDBHelper.COLUMN_ADDRESS, location.getAddress());
        values.put(FoursquareDBHelper.COLUMN_LAT, location.getLat());
        values.put(FoursquareDBHelper.COLUMN_LNG, location.getLng());
        values.put(FoursquareDBHelper.COLUMN_ADDRESS, location.getAddress());
        long insertId = database.insertWithOnConflict(FoursquareDBHelper.LOCATIONS_TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE);
        return insertId;
    }


    //get nested contact/location/photo by Id
    public Contact getContactById(long id) {
        String restrict = FoursquareDBHelper.COLUMN_ID + "=" + id;
        Cursor cursor = database.query(true, FoursquareDBHelper.CONTACTS_TABLE_NAME, allColumnsContact, restrict, null, null, null,
                null, null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            Contact contact = cursorToContact(cursor);
            return contact;
        }
        // Make sure to close the cursor
        cursor.close();
        return null;

    }

    public Location getLocationById(long id) {
        String restrict = FoursquareDBHelper.COLUMN_ID + "=" + id;
        Cursor cursor = database.query(true, FoursquareDBHelper.LOCATIONS_TABLE_NAME, allColumnsLocation, restrict, null, null, null,
                null, null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            Location location = cursorToLocation(cursor);
            return location;
        }
        // Make sure to close the cursor
        cursor.close();
        return null;
    }


    private Venue cursorToVenue(Cursor cursor) {
        Venue venue = new Venue();
        venue.setId(cursor.getString(0));
        venue.setName(cursor.getString(1));
        venue.setContact(getContactById(cursor.getLong(2)));
        venue.setLocation(getLocationById(cursor.getLong(3)));
        venue.setUrl(cursor.getString(4));
        venue.setRating(cursor.getDouble(5));
        venue.setRatingColor(cursor.getString(6));
        return venue;
    }

    private Contact cursorToContact(Cursor cursor) {
        Contact contact = new Contact();
        contact.setFormattedPhone(cursor.getString(1));
        return contact;
    }

    private Location cursorToLocation(Cursor cursor) {
        Location location = new Location();
        location.setAddress(cursor.getString(1));
        location.setLat(cursor.getDouble(2));
        location.setLng(cursor.getDouble(3));
        location.setCity(cursor.getString(4));
        return location;
    }

    //todo tips interaction
}
