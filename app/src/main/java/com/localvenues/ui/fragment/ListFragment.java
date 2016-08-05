package com.localvenues.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.localvenues.R;
import com.localvenues.adapter.VenuesRecyclerAdapter;
import com.localvenues.database.DBDataSource;
import com.localvenues.eventBus.OttoBus;
import com.localvenues.eventBus.VenuesPreparedEvent;
import com.localvenues.model.venueResponse.Venue;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


public class ListFragment extends Fragment {

    private final String LOG_TAG = ListFragment.class.getSimpleName();
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    private VenuesRecyclerAdapter adapter;
    private List<Venue> venues = new ArrayList<>();

    public ListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
/*        if (getArguments() != null) {

        }*/
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.i(LOG_TAG, "onResume");
        OttoBus.getInstance().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.i(LOG_TAG, "onStop");
        OttoBus.getInstance().unregister(this);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list, container, false);
        ButterKnife.bind(this, rootView);
        //setting layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        DBDataSource dataSource = new DBDataSource(getActivity());
        dataSource.open();
        venues = dataSource.getAllVenues();
        dataSource.close();

        adapter = new VenuesRecyclerAdapter(venues);
        recyclerView.setAdapter(adapter);
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Subscribe
    public void displayExploredVenues(VenuesPreparedEvent venuesPreparedEvent) {
        Log.i(LOG_TAG, " event from service received");
        Log.i(LOG_TAG, " event from service received, thread: " + Thread.currentThread().getName());

        DBDataSource dataSource = new DBDataSource(getActivity());
        dataSource.open();
        venues = dataSource.getAllVenues();
        dataSource.close();
        Log.i(LOG_TAG, "check venues from DB - is empty: " + venues.isEmpty());

        Log.i(LOG_TAG, venues.get(0).getName());

        adapter.updateData(venues);
    }

}
