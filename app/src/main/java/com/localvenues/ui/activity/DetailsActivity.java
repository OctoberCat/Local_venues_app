package com.localvenues.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.localvenues.R;
import com.localvenues.adapter.TipsRecyclerAdapter;
import com.localvenues.database.DBDataSource;
import com.localvenues.eventBus.OttoBus;
import com.localvenues.eventBus.TipsPreparedEvent;
import com.localvenues.model.tipResponse.Item;
import com.localvenues.model.venueResponse.Venue;
import com.localvenues.service.WebService;
import com.squareup.otto.Subscribe;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class DetailsActivity extends AppCompatActivity {
    @Bind(R.id.details_toolbar)
    Toolbar toolbar;

    @Bind(R.id.recyclerTips)
    RecyclerView recyclerView;

    private TipsRecyclerAdapter adapter;
    private List<Item> tipsList;
    private String venueID;

    public static final String DETAILS_KEY = "tips";
    private static final String LOG_TAG = DetailsActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);

        //setting layout manager
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        DBDataSource dataSource = new DBDataSource(this);

        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        venueID = intent.getStringExtra(DETAILS_KEY);
        Log.i(LOG_TAG, "onCreate: " + venueID);
        Log.i(LOG_TAG, "Starting tips webservice");
        WebService.fetchTips(this, venueID);
        //
        dataSource.open();
        Venue venue = dataSource.getRelevantVenue(venueID);
        tipsList = dataSource.getAllRelevantTips(venueID);
        dataSource.close();
        //
        getSupportActionBar().setTitle(venue.getName());
        Log.i(LOG_TAG, "onCreate: tipsList is empty: " + tipsList.isEmpty());
        adapter = new TipsRecyclerAdapter(tipsList);
        recyclerView.setAdapter(adapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        OttoBus.getInstance().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        OttoBus.getInstance().unregister(this);
    }

    @Subscribe
    public void displayFetchedTips(TipsPreparedEvent event) {
        Log.i(LOG_TAG, "displayFetchedTips: event from service received ");
        DBDataSource dataSource = new DBDataSource(this);
        dataSource.open();
        tipsList = dataSource.getAllRelevantTips(venueID);
        dataSource.close();
        Log.i(LOG_TAG, "displayFetchedTips: tipsList is empty: " + tipsList.isEmpty());

        adapter.updateData(tipsList);

    }
}
