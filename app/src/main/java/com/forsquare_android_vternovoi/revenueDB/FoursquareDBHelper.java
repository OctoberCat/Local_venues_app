package com.forsquare_android_vternovoi.revenueDB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by valentin on 09.12.15.
 */
public class FoursquareDBHelper extends SQLiteOpenHelper {
    private final static String _id = "_id";

    public FoursquareDBHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
