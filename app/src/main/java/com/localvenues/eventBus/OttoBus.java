package com.localvenues.eventBus;

import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;

/**
 * Created by valentyn on 29.05.16.
 */
public class OttoBus {
    private static Bus bus = null;

    private OttoBus() {
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
