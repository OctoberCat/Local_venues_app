package com.localvenues.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import com.localvenues.database.DBDataSource;
import com.localvenues.eventBus.OttoBus;
import com.localvenues.eventBus.TipsPreparedEvent;
import com.localvenues.eventBus.VenuesPreparedEvent;
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

    public static final String ACTION_EXPLORE_VENUES = "explore_venues";
    public static final String EXTRAS_LL = "ll";
    public static final String EXTRAS_RADIUS = "radius";
    public static final String EXTRAS_LIMIT = "limit";
    public static final String EXTRAS_OFFSET = "offset";
    public static final String ACTION_FETCH_TIPS = "fetch_tips";
    public static final String EXTRAS_VENUEID = "venueId";
    private final String LOG_TAG = WebService.class.getSimpleName();
    HashMap<String, String> parametersMap;
    private ExecutorService executor;
    private TipsResponse tipsResponse;
    private ExploreResponse exploreResponse;

    public WebService() {
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

    public static void fetchTips(Context context, String venueId) {
        Intent intent = new Intent(context, WebService.class);
        intent.setAction(ACTION_FETCH_TIPS);
        intent.putExtra(EXTRAS_VENUEID, venueId);
        context.startService(intent);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        executor = Executors.newCachedThreadPool();
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

        final HashMap<String, String> parameters = new HashMap<>();

        parameters.put("client_secret", "WHAEHHOIJ0A2MARVSA3MEY13EVY1ZRWBJK3FWTAEKVD43FTH");
        parameters.put("client_id", "KZ0IATCNQRWPHXGXPIQQJ3F0QWW3B1CHOGHWH22BQMVTYZDI");
        parameters.put("v", "20160215");
        parameters.put("sort", "recent");
        parameters.put("limit", "10");

        executor.submit(new Runnable() {
            @Override
            public void run() {
                try {
                    ApiRequests client = RESTClient.createRetrofitClient(ApiRequests.class);
                    Call<TipsResponse> call = client.fetchVenueTips(venueId, parameters);
                    tipsResponse = call.execute().body();
                    /////////////////
                    Log.i(LOG_TAG, " Check tips response is null:" + (tipsResponse == null));
                    Log.i(LOG_TAG, "tip count: " + tipsResponse.getResponse().getTips().getCount());
                    if (tipsResponse.getResponse().getTips().getCount() != 0) {
                        Log.i(LOG_TAG, "tip first tip text: " + tipsResponse.getResponse().getTips().getItems().get(0).getText());
                        DBDataSource dataSource = new DBDataSource(getApplicationContext());
                        dataSource.open();
                        dataSource.saveTipsList(tipsResponse.getResponse().getTips().getItems(), venueId);
                        dataSource.close();
                        OttoBus.postOnMain(new TipsPreparedEvent());
                    }
                    ////////////////
                } catch (IOException e) {
                    Log.i(LOG_TAG, "Exception during fetching tips");
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
        //this part should be predefined at API call
        parametersMap.put("client_secret", "WHAEHHOIJ0A2MARVSA3MEY13EVY1ZRWBJK3FWTAEKVD43FTH");
        parametersMap.put("client_id", "KZ0IATCNQRWPHXGXPIQQJ3F0QWW3B1CHOGHWH22BQMVTYZDI");
        parametersMap.put("v", "20160215");
        parametersMap.put("venuePhotos", "1");

        executor.submit(new Runnable() {
            @Override
            public void run() {
                ApiRequests client = RESTClient.createRetrofitClient(ApiRequests.class);
                Call<ExploreResponse> call = client.exploreVenues(parametersMap);
                try {
                    //// TODO: 29.05.16 persist data eventbus
                    exploreResponse = call.execute().body();
                    Log.i(LOG_TAG, "explore response is null: " + (exploreResponse == null));

                    if (exploreResponse != null) {
                        DBDataSource dbDataSource = new DBDataSource(getApplicationContext());
                        dbDataSource.open();
                        dbDataSource.saveVenueList(exploreResponse.getResponse().getGroups().get(0).getItems());
                        dbDataSource.close();
                        OttoBus.postOnMain(new VenuesPreparedEvent());
                    }

                } catch (IOException e) {
                    Log.i(LOG_TAG, "Exception during exploring venues");
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
