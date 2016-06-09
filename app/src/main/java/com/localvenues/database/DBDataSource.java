package com.localvenues.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.localvenues.model.venueResponse.Contact;
import com.localvenues.model.venueResponse.Location;
import com.localvenues.model.venueResponse.Photos;
import com.localvenues.model.venueResponse.Venue;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by valentyn on 09.06.16.
 */
public class DBDataSource {

    private SQLiteDatabase database;
    private DBHelper dbHelper;

    private String[] allColumnsVenues = {
            DBHelper.COLUMN_ID,
            DBHelper.COLUMN_VENUE_NAME,
            DBHelper.COLUMN_RATING,
            DBHelper.COLUMN_RATING_COLOR,
            DBHelper.COLUMN_LOCATION_ID,
            DBHelper.COLUMN_PHOTO_ID,
            DBHelper.COLUMN_PHONE};

    public DBDataSource(Context context) {
        dbHelper = DBHelper.getInstance(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        database.close();
    }

    public void saveVenueList() {
    }

    public List<Venue> getAllVenues() {
        List<Venue> venueList = new ArrayList<>();
        Cursor cursor = database.query(DBHelper.TABLE_VENUES, allColumnsVenues, null, null, null,
                null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Venue venue = cursorToVenue(cursor);
            venueList.add(venue);
            cursor.moveToNext();
        }
        return venueList;
    }

    public void saveVenue(Venue venue) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_ID, venue.getId());
        values.put(DBHelper.COLUMN_VENUE_NAME, venue.getName());
        values.put(DBHelper.COLUMN_RATING, venue.getRating());
        values.put(DBHelper.COLUMN_RATING_COLOR, venue.getRatingColor());
        values.put(DBHelper.COLUMN_PHONE, venue.getContact().getFormattedPhone());
        values.put(DBHelper.COLUMN_LOCATION_ID, saveLocation(venue.getLocation()));
        values.put(DBHelper.COLUMN_PHOTO_ID, savePhoto());
    }

    public long saveLocation(Location location) {
        return 0;
    }

    public void saveAuthor() {

    }

    public void saveTip() {
    }

    public void saveTipsList() {
    }

    public void getAllRelevantTips(String venueID) {
    }

    public long savePhoto() {
    return 0;
    }

    private Location getRelevantLocation(int locationID) {
        return null;
    }

    private Photos getRelevantPhoto(int photoID) {
        return null;
    }

    private Venue cursorToVenue(Cursor cursor) {
        Venue venue = new Venue();
        venue.setId(cursor.getString(0));
        venue.setName(cursor.getString(1));
        venue.setRating(cursor.getDouble(2));
        venue.setRatingColor(cursor.getString(3));
        venue.setLocation(getRelevantLocation(cursor.getInt(4)));
        venue.setPhotos(getRelevantPhoto(cursor.getInt(5)));
        Contact contact = new Contact();
        contact.setPhone(cursor.getString(6));
        venue.setContact(contact);
        return venue;
    }

}
