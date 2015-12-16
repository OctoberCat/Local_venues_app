package com.forsquare_android_vternovoi.eventBus;

import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

/**
 * Created by valentin on 11.12.15.
 */
public class EventBusVenues {
    private static Bus bus = null;

    private EventBusVenues() {
        // No instances.
    }

    public static Bus getInstance() {
        if (bus == null) {
            return bus = new Bus(ThreadEnforcer.ANY);
        }
        return bus;
    }

/*    private static final Handler mainThread = new Handler(Looper.getMainLooper());

    public static void postOnMain(final Object event) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            getInstance().post(event);
        } else {
            mainThread.post(new Runnable() {
                @Override
                public void run() {
                    getInstance().post(event);

                }
            });
        }
    }*/
}
