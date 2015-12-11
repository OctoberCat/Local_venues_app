package com.forsquare_android_vternovoi;

//import android.app.LoaderManager;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.forsquare_android_vternovoi.adapters.RecyclerAdapter;
import com.forsquare_android_vternovoi.foursquareApi.FoursquareService;
import com.forsquare_android_vternovoi.foursquareApi.WebInterface;
import com.forsquare_android_vternovoi.models.Item;
import com.forsquare_android_vternovoi.models.VenueResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit.Call;

/**
 * Created by valentin on 09.12.15.
 */
public class RevenueListFragment extends Fragment {

    //predefined values for service
    public static final String ll = "50.00,36.24";
    public static final String radius = "1500";
    public static final String limit = "50";
    public static final String offset = "50";
    public static final String venuePhotos = "1";
    private final static String TAG = "RevenueListFragment";
    public static VenueResponse venueResponse;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerAdapter recyclerAdapter;
    //

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Start service

        //WebService.fetchVenues(getActivity(),ll,radius,limit,offset,venuePhotos);

        //getLoaderManager().initLoader(0, null, this).forceLoad(); //leave for this moment
        ////////////////////////////////
        //delete this after solving promblem with loaders
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        //

        WebInterface client = FoursquareService.createService(WebInterface.class);
        //until I don't have real parameters I will use predefined
        Map<String, String> parametersMap = new HashMap<>();
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
            venueResponse = call.execute().body();
            List<Item> venueItems = venueResponse.getResponse().getGroups().get(0).getItems();
            for (Item item : venueItems) {
                Log.i("Check result", item.getVenue().getName());
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        ////////////////////////////////
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_list, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.my_recycler_view);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        recyclerAdapter = new RecyclerAdapter(venueResponse);

        recyclerView.setAdapter(recyclerAdapter);
        return rootView;
    }

}
