package com.forsquare_android_vternovoi.services;

import com.squareup.otto.Bus;

/**
 * Created by valentin on 11.12.15.
 */
public class EventBusVenues {
    private static Bus busInstance = null;

    private EventBusVenues() {
        busInstance = new Bus();
    }

    public static Bus getInstance() {
        if (busInstance == null) {
            busInstance = new Bus();
        }
        return busInstance;
    }
}
