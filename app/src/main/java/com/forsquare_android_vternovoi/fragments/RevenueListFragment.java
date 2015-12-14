package com.forsquare_android_vternovoi.fragments;

//import android.app.LoaderManager;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.forsquare_android_vternovoi.R;
import com.forsquare_android_vternovoi.adapters.RecyclerAdapter;
import com.forsquare_android_vternovoi.models.Venue;
import com.forsquare_android_vternovoi.models.VenueResponse;
import com.forsquare_android_vternovoi.revenueDB.FoursquareDataSource;
import com.forsquare_android_vternovoi.services.WebService;

import java.util.List;

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
    List<Venue> venuesResultList;
    private RecyclerView recyclerView;
    private RecyclerAdapter recyclerAdapter;
    //

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (isOnline()) {
        //Start service
            WebService.fetchVenues(getActivity(), ll, radius, limit, offset, venuePhotos);
        }

        //
        FoursquareDataSource dataSource = new FoursquareDataSource(getActivity()); //todo how to pass context?
        dataSource.open();
        venuesResultList = dataSource.getAllVenues();
        dataSource.close();


        ////////////////////////////////
        //delete this after solving promblem with loaders
        /*StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
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
        }*/
        ////////////////////////////////
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_list, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.my_recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        //recyclerAdapter = new RecyclerAdapter(venueResponse);
        recyclerAdapter = new RecyclerAdapter(venuesResultList);
        recyclerView.setAdapter(recyclerAdapter);
        return rootView;
    }


    boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }

}
