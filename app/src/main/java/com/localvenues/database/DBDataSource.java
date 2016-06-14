package com.localvenues.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.localvenues.model.venueResponse.Contact;
import com.localvenues.model.venueResponse.Group_;
import com.localvenues.model.venueResponse.Item;
import com.localvenues.model.venueResponse.Item__;
import com.localvenues.model.venueResponse.Location;
import com.localvenues.model.venueResponse.Photos;
import com.localvenues.model.venueResponse.Venue;

import java.util.ArrayList;
import java.util.List;

/*
 * Created by OctoberCat on 09.06.16.
 */
public class DBDataSource {

    private SQLiteDatabase database;
    private DBHelper dbHelper;
    private final String LOG_TAG = "DBDataSource";
    private String[] allColumnsVenues = {
            DBHelper.COLUMN_ID,
            DBHelper.COLUMN_VENUE_NAME,
            DBHelper.COLUMN_RATING,
            DBHelper.COLUMN_RATING_COLOR,
            DBHelper.COLUMN_LOCATION_ID,
            DBHelper.COLUMN_PHOTO_ID,
            DBHelper.COLUMN_PHONE};

    private String[] allColumnsLocation = {
            DBHelper.COLUMN_ID,
            DBHelper.COLUMN_ADDRESS,
            DBHelper.COLUMN_LAT,
            DBHelper.COLUMN_LNG
    };
    private String[] allColumnsPhoto = {
            DBHelper.COLUMN_ID,
            DBHelper.COLUMN_PREFIX,
            DBHelper.COLUMN_SUFFIX
    };

    public DBDataSource(Context context) {
        dbHelper = DBHelper.getInstance(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        database.close();
    }

    public void saveVenue(Venue venue) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_ID, venue.getId());
        values.put(DBHelper.COLUMN_VENUE_NAME, venue.getName());
        values.put(DBHelper.COLUMN_RATING, venue.getRating());
        values.put(DBHelper.COLUMN_RATING_COLOR, venue.getRatingColor());
        values.put(DBHelper.COLUMN_PHONE, venue.getContact().getFormattedPhone());
        values.put(DBHelper.COLUMN_LOCATION_ID, saveLocation(venue.getLocation()));
        values.put(DBHelper.COLUMN_PHOTO_ID, savePhoto(venue.getPhotos().getGroups().get(0).getItems().get(0)));//bad

        database.insertWithOnConflict(DBHelper.TABLE_VENUES, null, values, SQLiteDatabase.CONFLICT_REPLACE);
    }

    public void saveVenueList(List<Item> itemList) {
        database.beginTransaction();
        try {
            for (Item item : itemList) {
                Venue venue = item.getVenue();
                saveVenue(venue);
            }
        } catch (Exception e) {
            Log.e(LOG_TAG, "exception during venue saving");
            e.printStackTrace();
        } finally {
            database.endTransaction();
        }
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

    public long saveLocation(Location location) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_ADDRESS, location.getAddress());
        values.put(DBHelper.COLUMN_LNG, location.getLng());
        values.put(DBHelper.COLUMN_LAT, location.getLat());
        return database.insertWithOnConflict(DBHelper.TABLE_LOCATIONS, null, values, SQLiteDatabase.CONFLICT_IGNORE);
    }

    public void saveAuthor() {

    }

    public void saveTip() {
    }

    public void saveTipsList() {
    }

    public void getAllRelevantTips(String venueID) {
    }

    public String savePhoto(Item__ item__) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_ID, item__.getId());
        values.put(DBHelper.COLUMN_PREFIX, item__.getPrefix());
        values.put(DBHelper.COLUMN_SUFFIX, item__.getSuffix());
        database.insertWithOnConflict(DBHelper.TABLE_PHOTOS, null, values, SQLiteDatabase.CONFLICT_IGNORE);
        return item__.getId();
    }

    private Location getRelevantLocation(int locationID) {
        String restrict = DBHelper.COLUMN_ID + "=" + locationID;
        Cursor cursor = database.query(true, DBHelper.TABLE_LOCATIONS, allColumnsLocation, restrict, null, null, null,
                null, null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            return cursorToLocation(cursor);
        }
        // Make sure to close the cursor
        if (cursor != null) {
            cursor.close();
        }
        return null;
    }

    private Location cursorToLocation(Cursor cursor) {
        Location location = new Location();
        location.setAddress(cursor.getString(1));
        location.setLat(cursor.getDouble(2));
        location.setLng(cursor.getDouble(3));
        return location;
    }

    private Photos getRelevantPhoto(int photoID) {
        String restrict = DBHelper.COLUMN_ID + "=" + photoID;
        Cursor cursor = database.query(true, DBHelper.TABLE_PHOTOS, allColumnsPhoto, restrict, null, null, null,
                null, null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            //because of big amount of nested lists and object this part looks really ugly. Reconsider possible solutions
            Item__ item__ = cursorToPhoto(cursor);
            Photos photos = new Photos();
            List<Item__> list = new ArrayList<>();
            list.add(item__);
            List<Group_> group_list = new ArrayList<>();
            Group_ group = new Group_();
            group.setItems(list);
            group_list.add(group);
            photos.setGroups(group_list);
            return photos;
        }
        // Make sure to close the cursor
        if (cursor != null) {
            cursor.close();
        }
        return null;
    }

    private Item__ cursorToPhoto(Cursor cursor) {
        Item__ item__ = new Item__();
        item__.setId(cursor.getString(0));
        item__.setPrefix(cursor.getString(1));
        item__.setSuffix(cursor.getString(2));
        return item__;

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
