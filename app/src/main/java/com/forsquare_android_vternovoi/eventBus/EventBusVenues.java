package com.forsquare_android_vternovoi.eventBus;

import android.app.Application;

import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

/**
 * Created by valentin on 11.12.15.
 */
public class EventBusVenues extends Application {
    private static Bus busInstance = null;

    private EventBusVenues() {
        busInstance = new Bus(ThreadEnforcer.ANY);
    }

    public static Bus getInstance() {
        if (busInstance == null) {
            busInstance = new Bus();
        }
        return busInstance;
    }
}
