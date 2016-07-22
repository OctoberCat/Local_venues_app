package com.localvenues.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.localvenues.model.tipResponse.Photo_;
import com.localvenues.model.tipResponse.Photo__;
import com.localvenues.model.tipResponse.User;
import com.localvenues.model.venueResponse.Contact;
import com.localvenues.model.venueResponse.FeaturedPhotos;
import com.localvenues.model.venueResponse.Item;
import com.localvenues.model.venueResponse.Item___;
import com.localvenues.model.venueResponse.Location;
import com.localvenues.model.venueResponse.Venue;

import java.util.ArrayList;
import java.util.List;

/*
 * Created by OctoberCat on 09.06.16.
 */
public class DBDataSource {

    private SQLiteDatabase database;
    private DBHelper dbHelper;
    private final String LOG_TAG = DBDataSource.class.getSimpleName();

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

    private String[] allColumnsTips = {
            DBHelper.COLUMN_ID,
            DBHelper.COLUMN_PHOTO_ID,
            DBHelper.COLUMN_VENUE_ID,
            DBHelper.COLUMN_AUTHOR_ID,
            DBHelper.COLUMN_TIP_TEXT
    };

    private String[] allColumnsUsers = {
            DBHelper.COLUMN_ID,
            DBHelper.COLUMN_PHOTO_ID,
            DBHelper.COLUMN_FIRST_NAME,
            DBHelper.COLUMN_LAST_NAME
    };

    private static long IdCount = 0;

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
        values.put(DBHelper.COLUMN_PHOTO_ID, savePhoto(venue.getFeaturedPhotos().getItems().get(0)));

        long insertResp = database.insertWithOnConflict(DBHelper.TABLE_VENUES, null, values, SQLiteDatabase.CONFLICT_REPLACE);
        Log.i(LOG_TAG, "inserting venue response " + insertResp);
    }

    public void saveVenueList(List<Item> itemList) {
        database.beginTransaction();
        try {
            for (Item item : itemList) {
                Venue venue = item.getVenue();
                saveVenue(venue);
            }
            database.setTransactionSuccessful();
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
        long insertLocationResp = database.insertWithOnConflict(DBHelper.TABLE_LOCATIONS, null, values, SQLiteDatabase.CONFLICT_REPLACE);
        Log.i(LOG_TAG, "inserting location response " + insertLocationResp);

        return insertLocationResp;
    }

    public String saveAuthor(User user) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_ID, user.getId());
        values.put(DBHelper.COLUMN_PHOTO_ID, savePhoto(user.getPhoto()));
        values.put(DBHelper.COLUMN_FIRST_NAME, user.getFirstName());
        values.put(DBHelper.COLUMN_LAST_NAME, user.getLastName());
        database.insertWithOnConflict(DBHelper.TABLE_USERS, null, values, SQLiteDatabase.CONFLICT_REPLACE);
        return user.getId();
    }

    private String savePhoto(Photo_ photo) {
        //todo user's photos don't have predefined id, so to keep it simple it will be faked
        ContentValues values = new ContentValues();
        String userPhotoId = "u" + IdCount++;
        values.put(DBHelper.COLUMN_ID, userPhotoId);
        values.put(DBHelper.COLUMN_PREFIX, photo.getPrefix());
        values.put(DBHelper.COLUMN_SUFFIX, photo.getSuffix());
        database.insertWithOnConflict(DBHelper.TABLE_PHOTOS, null, values, SQLiteDatabase.CONFLICT_REPLACE);
        return userPhotoId;
    }

    public void saveTip(com.localvenues.model.tipResponse.Item item, String venueId) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_ID, item.getId());
        values.put(DBHelper.COLUMN_VENUE_ID, venueId);
        values.put(DBHelper.COLUMN_AUTHOR_ID, saveAuthor(item.getUser()));
        values.put(DBHelper.COLUMN_TIP_TEXT, item.getText());
        if (item.getPhoto() != null) {
            Log.i(LOG_TAG, "saveTip: photo tip is not null");
            values.put(DBHelper.COLUMN_PHOTO_ID, savePhoto(item.getPhoto()));
        }

        long saveTipResp = database.insertWithOnConflict(DBHelper.TABLE_TIPS, null, values, SQLiteDatabase.CONFLICT_REPLACE);

        Log.i(LOG_TAG, "saveTip: saveTipResp" + saveTipResp);


    }

    private String savePhoto(Photo__ photo) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_ID, photo.getId());
        values.put(DBHelper.COLUMN_PREFIX, photo.getPrefix());
        values.put(DBHelper.COLUMN_SUFFIX, photo.getSuffix());
        return photo.getId();

    }

    public void saveTipsList(List<com.localvenues.model.tipResponse.Item> items, String venueId) {
        database.beginTransaction();
        try {
            for (com.localvenues.model.tipResponse.Item item : items) {
                saveTip(item, venueId);
            }
            database.setTransactionSuccessful();
        } catch (Exception e) {
            Log.e(LOG_TAG, "exception during tips saving");
            e.printStackTrace();
        } finally {
            database.endTransaction();
        }
    }

    public List<com.localvenues.model.tipResponse.Item> getAllRelevantTips(String venueID) {
        List<com.localvenues.model.tipResponse.Item> tipsList = new ArrayList<>();
        String restrict = DBHelper.COLUMN_VENUE_ID + "= \'" + venueID + "\'";
        Cursor cursor = database.query(true, DBHelper.TABLE_TIPS, allColumnsTips, restrict, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            com.localvenues.model.tipResponse.Item tip = cursorToTip(cursor);
            tipsList.add(tip);
            cursor.moveToNext();
        }

        return tipsList;
    }

    public String savePhoto(Item___ item___) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_ID, item___.getId());
        values.put(DBHelper.COLUMN_PREFIX, item___.getPrefix());
        values.put(DBHelper.COLUMN_SUFFIX, item___.getSuffix());

        long insertPhotoResp = database.insertWithOnConflict(DBHelper.TABLE_PHOTOS, null, values, SQLiteDatabase.CONFLICT_REPLACE);
        Log.i(LOG_TAG, "inserting photo response " + insertPhotoResp);
        return item___.getId();
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

    private FeaturedPhotos getRelevantPhoto(String photoID) {
        String restrict = DBHelper.COLUMN_ID + " = \'" + photoID + "\'";
        Cursor cursor = database.query(true, DBHelper.TABLE_PHOTOS, allColumnsPhoto, restrict, null, null, null,
                null, null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            //because of big amount of nested lists and object this part looks really ugly. Reconsider possible solutions
            Item___ item___ = cursorToPhoto(cursor);
            FeaturedPhotos photos = new FeaturedPhotos();
            List<Item___> list = new ArrayList<>();
            list.add(item___);
            photos.setItems(list);
            return photos;
        }
        // Make sure to close the cursor
        if (cursor != null) {
            cursor.close();
        }
        return null;
    }

    private Photo__ getRelevantTipPhoto(String photoID) {
        String restrict = DBHelper.COLUMN_ID + "= \'" + photoID + "\'";
        Cursor cursor = database.query(true, DBHelper.TABLE_PHOTOS, allColumnsPhoto, restrict, null, null, null, null, null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            //todo make similar cursor to method after model refactoring

            Photo__ photo__ = new Photo__();
            photo__.setId(cursor.getString(0));
            photo__.setPrefix(cursor.getString(1));
            photo__.setSuffix(cursor.getString(2));
            return photo__;
        }
        if (cursor != null) {
            cursor.close();
        }
        return null;
    }

    private Photo_ getRelevantUserPhoto(String photoID) {
        String restrict = DBHelper.COLUMN_ID + "= \'" + photoID + "\'";
        Cursor cursor = database.query(true, DBHelper.TABLE_PHOTOS, allColumnsPhoto, restrict, null, null, null, null, null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            Photo_ photo_ = new Photo_();
            photo_.setPrefix(cursor.getString(1));
            photo_.setSuffix(cursor.getString(2));
            return photo_;
        }
        if (cursor != null) {
            cursor.close();
        }
        return null;
    }

    private User getRelevantAuthor(String authorId) {
        String restrict = DBHelper.COLUMN_ID + "= \'" + authorId + "\'";
        Cursor cursor = database.query(true, DBHelper.TABLE_USERS, allColumnsUsers, restrict, null, null, null, null, null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            return cursorToUser(cursor);
        }
        if (cursor != null) {
            cursor.close();
        }
        return null;
    }

    private User cursorToUser(Cursor cursor) {
        User user = new User();
        user.setId(cursor.getString(0));
        user.setPhoto(getRelevantUserPhoto(cursor.getString(1)));
        user.setFirstName(cursor.getString(2));
        user.setLastName(cursor.getString(3));
        return user;
    }

    private Location cursorToLocation(Cursor cursor) {
        Location location = new Location();
        location.setAddress(cursor.getString(1));
        location.setLat(cursor.getDouble(2));
        location.setLng(cursor.getDouble(3));
        return location;
    }

    private Item___ cursorToPhoto(Cursor cursor) {
        Item___ item___ = new Item___();
        item___.setId(cursor.getString(0));
        item___.setPrefix(cursor.getString(1));
        item___.setSuffix(cursor.getString(2));
        return item___;

    }

    public Venue getRelevantVenue(String venueID) {
        String restrict = DBHelper.COLUMN_ID + "= \'" + venueID + "\'";
        Cursor cursor = database.query(true, DBHelper.TABLE_VENUES, allColumnsVenues, restrict, null, null, null, null, null);
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();
            return cursorToVenue(cursor);
        }
        if (cursor != null) {
            cursor.close();
        }
        return null;


    }

    private Venue cursorToVenue(Cursor cursor) {
        Venue venue = new Venue();
        venue.setId(cursor.getString(0));
        venue.setName(cursor.getString(1));
        venue.setRating(cursor.getDouble(2));
        venue.setRatingColor(cursor.getString(3));
        venue.setLocation(getRelevantLocation(cursor.getInt(4)));
        venue.setFeaturedPhotos(getRelevantPhoto(cursor.getString(5)));
        Contact contact = new Contact();
        contact.setFormattedPhone(cursor.getString(6));
        venue.setContact(contact);
        return venue;
    }

    private com.localvenues.model.tipResponse.Item cursorToTip(Cursor cursor) {
        com.localvenues.model.tipResponse.Item item = new com.localvenues.model.tipResponse.Item();
        item.setId(cursor.getString(0));
        //todo clean and improve data model
        item.setPhoto(getRelevantTipPhoto(cursor.getString(1)));
        item.setUser(getRelevantAuthor(cursor.getString(3)));
        item.setText(cursor.getString(4));
        return item;
    }
}
