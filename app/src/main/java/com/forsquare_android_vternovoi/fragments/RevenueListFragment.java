package com.forsquare_android_vternovoi.fragments;

//import android.app.LoaderManager;

import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.forsquare_android_vternovoi.R;
import com.forsquare_android_vternovoi.adapters.OnLoadMoreListener;
import com.forsquare_android_vternovoi.adapters.RecyclerAdapter;
import com.forsquare_android_vternovoi.eventBus.EventBusVenues;
import com.forsquare_android_vternovoi.eventBus.UpdateEvent;
import com.forsquare_android_vternovoi.manager.DataManager;
import com.forsquare_android_vternovoi.models.Venue;
import com.forsquare_android_vternovoi.services.WebService;
import com.squareup.otto.Subscribe;

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
    //check
    protected Handler handler;
    List<Venue> venuesResultList;
    //
    DataManager dataManager;
    private RecyclerView recyclerView;
    private RecyclerAdapter recyclerAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (isOnline()) {
            //Start service
            WebService.fetchVenues(getActivity(), ll, radius, limit, offset, venuePhotos);
        }
        dataManager = DataManager.getInstance(getActivity().getApplicationContext());
        venuesResultList = dataManager.getVenuesFromDB();
        handler = new Handler();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //bus registration
        EventBusVenues.getInstance().register(this);


        View rootView = inflater.inflate(R.layout.fragment_list, container, false);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.my_recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        //attach result from db to adapter
        recyclerAdapter = new RecyclerAdapter(venuesResultList, recyclerView);
        recyclerView.setAdapter(recyclerAdapter);
        //


        recyclerAdapter.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore() {
                venuesResultList.add(null);
                recyclerAdapter.notifyItemInserted(venuesResultList.size() - 1);

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //   remove progress item
                        venuesResultList.remove(venuesResultList.size() - 1);
                        recyclerAdapter.notifyItemRemoved(venuesResultList.size());
                        //add items one by one


                        int currSize = recyclerAdapter.getItemCount();
                        offset = String.valueOf(Integer.valueOf(offset) + currSize);//monstrous, find appropriate approach
                        WebService.fetchVenues(getActivity(), ll, radius, limit, offset, venuePhotos);


                        recyclerAdapter.notifyDataSetChanged();
                        recyclerAdapter.setLoaded();

                    }
                }, 2000);
            }
        });
        return rootView;
    }


    @Subscribe
    public void onDatabaseUpdate(UpdateEvent upd) {
        Log.i("onDatabaseUpdate", "Subscriber received event");
        //
        venuesResultList = dataManager.getVenuesFromDB();
        //bad idea
        recyclerAdapter.updateData(venuesResultList);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBusVenues.getInstance().unregister(this);
    }

    boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }
}
