package com.forsquare_android_vternovoi.manager;

import android.content.Context;

import com.forsquare_android_vternovoi.eventBus.EventBusVenues;
import com.forsquare_android_vternovoi.eventBus.UpdateEvent;
import com.forsquare_android_vternovoi.models.Item;
import com.forsquare_android_vternovoi.models.Venue;
import com.forsquare_android_vternovoi.revenueDB.FoursquareDataSource;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by valentin on 15.12.15.
 */
public class DataManager {

    private static DataManager manager = null;
    private Context context;
    private List<Venue> venuesResultList;

    private DataManager(Context context) {
        this.context = context;
    }


    public static DataManager getInstance(Context context) {
        if (manager == null) {
            return manager = new DataManager(context);
        } else {
            return manager;
        }

    }

    List<Venue> getVenuesList() {
        FoursquareDataSource dataSource = new FoursquareDataSource(context);
        dataSource.open();
        venuesResultList = dataSource.getAllVenues();
        dataSource.close();


        return null;
    }

    public void updateVenuesDB(List<Item> retrievedList, boolean firstTimeFlag) {
        FoursquareDataSource dataSource = new FoursquareDataSource(context);
        dataSource.open();
        if (firstTimeFlag) {
            dataSource.dropAndUpgrade();//drop previous results
        }
        dataSource.persistVenues(retrievedList);
        venuesResultList = dataSource.getAllVenues();
        EventBusVenues.getInstance().post(new UpdateEvent((ArrayList<Venue>) venuesResultList));
        dataSource.close();

    }

}
