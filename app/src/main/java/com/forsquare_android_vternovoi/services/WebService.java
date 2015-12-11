package com.forsquare_android_vternovoi.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.forsquare_android_vternovoi.RevenueListFragment;
import com.forsquare_android_vternovoi.foursquareApi.FoursquareService;
import com.forsquare_android_vternovoi.foursquareApi.WebInterface;
import com.forsquare_android_vternovoi.models.VenueResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit.Call;

/**
 * Created by valentin on 11.12.15.
 */
public class WebService extends Service {
    public static final String ACTION_FETCH_VENUES = "fetch_venues";

    //predefined immutable values
    public static final String CLIENT_ID_VALUE = "KEFRSV01QF2O0R4WANEQKFADJ0DWMDIEPWPQDZWW5GS1YUZH";
    public static final String CLIENT_SECRET_VALUE = "KJQ0PCVM4JK0Y5QCRIBSTVG3Z4B3JKK5PTMCFJJQE2PEUKQB";
    public static final String VERSION_VALUE = "20151210";


    public static final String EXTRAS_CLIENT_ID = "client_id";
    public static final String EXTRAS_CLIENT_SECRET = "client_secret";
    public static final String EXTRAS_VERSION = "v";
    public static final String EXTRAS_LL = "ll";
    public static final String EXTRAS_RADIUS = "radius";
    public static final String EXTRAS_LIMIT = "limit";
    public static final String EXTRAS_OFFSET = "offset";
    public static final String EXTRAS_VENUEPHOTOS = "venuePhotos";


    private VenueResponse resultVenueResponse;
    private ExecutorService executor;
    private Map<String, String> parametersMap;


    public static void fetchVenues(Context context, String ll, String radius, String limit, String offset, String venuesPhotos) {
        Intent intent = new Intent(context, WebService.class);
        intent.setAction(ACTION_FETCH_VENUES);
/*        intent.putExtra(EXTRAS_CLIENT_ID, CLIENT_ID_VALUE);
        intent.putExtra(EXTRAS_CLIENT_SECRET, CLIENT_SECRET_VALUE);
        intent.putExtra(EXTRAS_VERSION, VERSION_VALUE);*/
        intent.putExtra(EXTRAS_LL, ll);
        intent.putExtra(EXTRAS_RADIUS, radius);
        intent.putExtra(EXTRAS_LIMIT, limit);
        intent.putExtra(EXTRAS_OFFSET, offset);
        intent.putExtra(EXTRAS_VENUEPHOTOS, venuesPhotos);
        context.startService(intent);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        executor = Executors.newCachedThreadPool();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String action = intent.getAction();
        if (ACTION_FETCH_VENUES.equals(action)) {
            handleActionFetchVenues(intent);
        }//fetching another data
        return START_REDELIVER_INTENT;
    }


    private void handleActionFetchVenues(Intent intent) {

        String ll = intent.getStringExtra(EXTRAS_LL);
        String radius = intent.getStringExtra(EXTRAS_RADIUS);
        String venuePhotos = intent.getStringExtra(EXTRAS_VENUEPHOTOS);
        String limit = intent.getStringExtra(EXTRAS_LIMIT);
        String offset = intent.getStringExtra(EXTRAS_OFFSET);
        parametersMap = new HashMap<>();
        parametersMap.put(EXTRAS_CLIENT_ID, CLIENT_ID_VALUE);
        parametersMap.put(EXTRAS_CLIENT_SECRET, CLIENT_SECRET_VALUE);
        parametersMap.put(EXTRAS_VERSION, VERSION_VALUE);
        parametersMap.put(EXTRAS_LL, ll);
        parametersMap.put(EXTRAS_LIMIT, limit);
        parametersMap.put(EXTRAS_RADIUS, radius);
        parametersMap.put(EXTRAS_OFFSET, offset);
        parametersMap.put(EXTRAS_VENUEPHOTOS, venuePhotos);

        // web request
        // persisting to db
        // notify UI with EventBus (otto)

        executor.submit(new Runnable() {
            @Override
            public void run() {
                // executing on executor
                try {
                    // make retrofit synchronous web request
                    WebInterface client = FoursquareService.createService(WebInterface.class);
                    Call<VenueResponse> call = client.exploreVenues(parametersMap);
                    resultVenueResponse = call.execute().body();
                    // sync write to db

                    // eventbus.post(event
                    Log.i("executor", "result is null: " + (resultVenueResponse == null));
                    RevenueListFragment.venueResponse = resultVenueResponse;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }
}
