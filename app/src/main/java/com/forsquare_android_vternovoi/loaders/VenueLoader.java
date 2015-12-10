package com.forsquare_android_vternovoi.loaders;

import android.content.Context;
import android.util.Log;

import com.forsquare_android_vternovoi.foursquareApi.FoursquareService;
import com.forsquare_android_vternovoi.foursquareApi.WebInterface;
import com.forsquare_android_vternovoi.models.VenueResponse;

import java.util.HashMap;
import java.util.Map;

import retrofit.Call;

/**
 * Created by valentin on 10.12.15.
 */
public class VenueLoader extends android.support.v4.content.AsyncTaskLoader<VenueResponse> {

    private final static String TAG = "VenueLoader";
    VenueResponse result;
    private Map<String, String> parametersMap;

    public VenueLoader(Context context) {//later add check accessibility to location and location itself
        super(context);
        //init and open DS

    }

    @Override
    public VenueResponse loadInBackground() {

        WebInterface client = FoursquareService.createService(WebInterface.class);
        //until I don't have real parameters I will use predefined
        parametersMap = new HashMap<>();
        parametersMap.put("client_id", WebInterface.CLIENT_ID);
        parametersMap.put("client_secret", WebInterface.CLIENT_SECRET);
        parametersMap.put("v", WebInterface.VERSION);
        parametersMap.put(WebInterface.LL_PARAMETER, "50.0,36.2");
        parametersMap.put(WebInterface.LIMIT_PARAMETER, "10");
        parametersMap.put(WebInterface.OFFSET_PARAMETER, "10");
        parametersMap.put(WebInterface.RADIUS_PARAMETER, "2000");
        parametersMap.put(WebInterface.VENUEPHOTOS_PARAMETER, "1");

        Call<VenueResponse> call = client.exploreVenues(parametersMap);
        try {
            result = call.execute().body();
            Log.i(TAG, "loadInBackground");

            //some magic with DB should be here
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        //String testRetrieve =  result.getResponse().getGroups().get(0).getItems().get(0).getVenue().getName();
        Log.i(TAG, "result is null: " + (result == null));
        Log.i(TAG, "result is: " + result.getResponse().getGroups().get(0).getItems());

        return result;
    }
}
