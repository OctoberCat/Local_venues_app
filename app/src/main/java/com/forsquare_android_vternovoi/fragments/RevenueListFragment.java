package com.forsquare_android_vternovoi.fragments;

//import android.app.LoaderManager;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.forsquare_android_vternovoi.R;
import com.forsquare_android_vternovoi.adapters.RecyclerAdapter;
import com.forsquare_android_vternovoi.adapters.ScrollListener;
import com.forsquare_android_vternovoi.eventBus.EventBusVenues;
import com.forsquare_android_vternovoi.manager.DataManager;
import com.forsquare_android_vternovoi.models.Venue;
import com.forsquare_android_vternovoi.revenueDB.FoursquareDataSource;
import com.forsquare_android_vternovoi.services.WebService;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by valentin on 09.12.15.
 */
public class RevenueListFragment extends Fragment {

    //predefined values for service
    public static final String ll = "50.00,36.24";
    public static final String radius = "1500";
    public static final String limit = "10";
    public static final String venuePhotos = "1";
    private final static String TAG = "RevenueListFragment";
    public static String offset = "0";
    List<Venue> venuesResultList;
    //
    DataManager dataManager;
    private RecyclerView recyclerView;
    private RecyclerAdapter recyclerAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBusVenues.getInstance().register(this);
        if (isOnline()) {
            //Start service
            WebService.fetchVenues(getActivity(), ll, radius, limit, offset, venuePhotos);
        }

        dataManager = DataManager.getInstance(getActivity());


        //move to DataManager
        FoursquareDataSource dataSource = new FoursquareDataSource(getActivity());
        dataSource.open();
        venuesResultList = dataSource.getAllVenues();
        dataSource.close();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_list, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.my_recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);


        recyclerAdapter = new RecyclerAdapter(venuesResultList);
        recyclerView.setAdapter(recyclerAdapter);


        recyclerView.addOnScrollListener(new ScrollListener((LinearLayoutManager) layoutManager) {
            @Override
            public void onLoadMore(int page, int itemCount) {
                // fetch data asynchronously here
                loadMoreDataFromApi(page);
            }
        });
        return rootView;
    }


    boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }


    void loadMoreDataFromApi(int mOffset) {
        Log.i(TAG, "inside loadMoreDataFromApi");
        offset = String.valueOf(Integer.valueOf(offset) + mOffset);//monstrous, find appropriate approach
        int currSize = recyclerAdapter.getItemCount();
        //WebService.fetchVenues(getActivity(), ll, radius, limit, offset, venuePhotos);


        //recyclerAdapter.notifyItemRangeChanged(currSize, );
    }

    @Subscribe
    public void getUpdatedVenues(ArrayList<Venue> venuesUpdateList) {
        for (Venue venue : venuesUpdateList) {
            Log.i("Event received test", "venue names: " + venue.getName());
        }
        //todo use venuesResultList = venuesUpdateList or adapter.update(venuesUpdateList)?

    }

}
