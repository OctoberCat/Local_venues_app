package com.forsquare_android_vternovoi.manager;

import android.content.Context;

import com.forsquare_android_vternovoi.models.Item;
import com.forsquare_android_vternovoi.models.Venue;
import com.forsquare_android_vternovoi.revenueDB.FoursquareDataSource;

import java.util.List;

/**
 * Created by valentin on 15.12.15.
 */
public class DataManager {

    private static DataManager manager = null;
    private static FoursquareDataSource dataSource;
    private List<Venue> venuesResultList;

    private DataManager() {

    }


    public static DataManager getInstance(Context context) {
        if (manager == null) {
            dataSource = new FoursquareDataSource(context);
            return manager = new DataManager();
        } else {
            return manager;
        }

    }

    public List<Venue> getVenuesFromDB() {
        dataSource.open();
        venuesResultList = dataSource.getAllVenues();
        dataSource.close();
        return venuesResultList;
    }

    public void updateVenuesDB(List<Item> retrievedList, boolean firstTimeFlag) {

        dataSource.open();
        if (firstTimeFlag) {
            dataSource.deleteVenueTable(); //drop previous results
        }
        dataSource.persistVenues(retrievedList);
        venuesResultList = dataSource.getAllVenues();
        // EventBusVenues.getInstance().post(new UpdateEvent());
        dataSource.close();

    }

}
