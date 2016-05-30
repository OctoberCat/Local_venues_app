package com.localvenues.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.localvenues.model.tipResponse.TipsResponse;
import com.localvenues.model.venueResponse.ExploreResponse;
import com.localvenues.restClient.ApiRequests;
import com.localvenues.restClient.RESTClient;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import retrofit.Call;

public class WebService extends Service {

    private final String LOG_TAG = "Service";

    public static final String ACTION_EXPLORE_VENUES = "explore_venues";
    public static final String EXTRAS_LL = "ll";
    public static final String EXTRAS_RADIUS = "radius";
    public static final String EXTRAS_LIMIT = "limit";
    public static final String EXTRAS_OFFSET = "offset";

    public static final String ACTION_FETCH_TIPS = "fetch_tips";
    public static final String EXTRAS_VENUEID = "venueId";


    private ExecutorService executor;
    private TipsResponse tipsResponse;
    private ExploreResponse exploreResponse;
    HashMap<String, String> parametersMap;

    public WebService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        executor = Executors.newCachedThreadPool();
    }

    public static void exploreVenues(Context context, String ll, String radius,
                                     String limit, String offset) {
        Intent intent = new Intent(context, WebService.class);
        intent.setAction(ACTION_EXPLORE_VENUES);
        intent.putExtra(EXTRAS_LL, ll);
        intent.putExtra(EXTRAS_RADIUS, radius);
        intent.putExtra(EXTRAS_LIMIT, limit);
        intent.putExtra(EXTRAS_OFFSET, offset);
        context.startService(intent);
    }

    public static void fetchVenues(Context context, String venueId) {
        Intent intent = new Intent(context, WebService.class);
        intent.setAction(ACTION_FETCH_TIPS);
        intent.putExtra(EXTRAS_VENUEID, venueId);
        context.startService(intent);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String action = intent.getAction();

        switch (action) {
            case ACTION_EXPLORE_VENUES:
                handleExploreVenues(intent);
                break;
            case ACTION_FETCH_TIPS:
                handleFetchTips(intent);
                break;

        }

        return START_REDELIVER_INTENT;
    }

    private void handleFetchTips(Intent intent) {
        final String venueId = intent.getStringExtra(EXTRAS_VENUEID);
        executor.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    ApiRequests client = RESTClient.createRetrofitClient(ApiRequests.class);
                    Call<TipsResponse> call = client.fetchVenueTips(venueId);
                    tipsResponse = call.execute().body();
                    /////////////////
                    Log.i(LOG_TAG, " Check tips response is null:" + (tipsResponse==null));
                    Log.i(LOG_TAG, "tip count: " + tipsResponse.getResponse().getTips().getCount());
                    if (tipsResponse.getResponse().getTips().getCount() != 0) {
                        Log.i(LOG_TAG, "tip first tip text: " + tipsResponse.getResponse().getTips().getItems().get(0).getText());
                    }
                    ////////////////
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private void handleExploreVenues(Intent intent) {
        String ll = intent.getStringExtra(EXTRAS_LL);
        String radius = intent.getStringExtra(EXTRAS_RADIUS);
        String limit = intent.getStringExtra(EXTRAS_LIMIT);
        String offset = intent.getStringExtra(EXTRAS_OFFSET);

        parametersMap = new HashMap<>();
        parametersMap.put(EXTRAS_LL, ll);
        parametersMap.put(EXTRAS_LIMIT, limit);
        parametersMap.put(EXTRAS_RADIUS, radius);
        parametersMap.put(EXTRAS_OFFSET, offset);

        executor.submit(new Runnable() {
            @Override
            public void run() {
                ApiRequests client = RESTClient.createRetrofitClient(ApiRequests.class);
                Call<ExploreResponse> call = client.exploreVenues(parametersMap);
                try {
                    //// TODO: 29.05.16 persist data eventbus
                    exploreResponse = call.execute().body();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
