package com.localvenues.eventBus;

import android.os.Handler;
import android.os.Looper;

import com.squareup.otto.Bus;

/*
 * Created by OctoberCat on 29.05.16.
 */
public class OttoBus {
    private static final Handler mainThread = new Handler(Looper.getMainLooper());
    private static Bus bus = null;

    private OttoBus() {
        // No instances.
    }

    public static Bus getInstance() {
        if (bus == null) {
            return bus = new Bus();
        }
        return bus;
    }

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
    }

/*

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
