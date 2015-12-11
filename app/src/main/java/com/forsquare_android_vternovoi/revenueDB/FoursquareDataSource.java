package com.forsquare_android_vternovoi.revenueDB;

import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.forsquare_android_vternovoi.models.Venue;

import java.util.List;

/**
 * Created by valentin on 11.12.15.
 */
public class FoursquareDataSource {
    private SQLiteDatabase database;
    private FoursquareDBHelper dbHelper;
    private int version = 1;

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public void dropAndUpgrade() {
        dbHelper.onUpgrade(database, version, version++);
    }//do u feel shame, right?

    public Venue createVenue() {
        return null;
    }

    public List<Venue> getAllVenues() {
        return null;
    }

    private Venue cursorToVenue(Cursor cursor) {
        Venue venue = new Venue();
        venue.setId(cursor.getString(0));
        venue.setName(cursor.getString(1));
        //venue.s(cursor.getString(2));

        return venue;
    }
}
