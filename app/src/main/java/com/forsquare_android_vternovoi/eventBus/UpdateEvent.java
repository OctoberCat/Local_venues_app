package com.forsquare_android_vternovoi.eventBus;

import com.forsquare_android_vternovoi.models.Venue;

import java.util.ArrayList;

/**
 * Created by valentin on 15.12.15.
 */
public class UpdateEvent {
    ArrayList<Venue> venueArrayList;

    public UpdateEvent(ArrayList<Venue> venueArrayList) {
        this.venueArrayList = venueArrayList;
    }

    public ArrayList<Venue> getVenueArrayList() {
        return venueArrayList;
    }

    public void setVenueArrayList(ArrayList<Venue> venueArrayList) {
        this.venueArrayList = venueArrayList;
    }
}
