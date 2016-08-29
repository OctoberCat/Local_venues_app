package com.localvenues.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ImageView;

import com.localvenues.R;
import com.localvenues.adapter.DividerItemDecoration;
import com.localvenues.adapter.TipsRecyclerAdapter;
import com.localvenues.database.DBDataSource;
import com.localvenues.eventBus.OttoBus;
import com.localvenues.eventBus.TipsPreparedEvent;
import com.localvenues.model.tipResponse.Item;
import com.localvenues.model.venueResponse.Venue;
import com.localvenues.service.WebService;
import com.squareup.otto.Subscribe;
import com.squareup.picasso.Picasso;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

public class DetailsActivity extends AppCompatActivity {
    public static final String DETAILS_KEY = "tips";
    private static final String LOG_TAG = DetailsActivity.class.getSimpleName();
    @Bind(R.id.details_toolbar)
    Toolbar toolbar;
    @Bind(R.id.collapsing_toolbar)
    CollapsingToolbarLayout toolbarLayout;
    @Bind(R.id.recyclerTips)
    RecyclerView recyclerView;
    @Bind(R.id.venue_photo)
    ImageView venuePhoto;


    private TipsRecyclerAdapter adapter;
    private List<Item> tipsList;
    private String venueId;
    private LinearLayoutManager layoutManager;
    private DBDataSource dataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        ButterKnife.bind(this);
        dataSource = new DBDataSource(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        venueId = intent.getStringExtra(DETAILS_KEY);

        Log.i(LOG_TAG, "onCreate: " + venueId);

        Log.i(LOG_TAG, "Starting tips webservice");
        WebService.fetchTips(this, venueId);
        //
        dataSource.open();
        Venue venue = dataSource.getRelevantVenue(venueId);
        tipsList = dataSource.getAllRelevantTips(venueId);
        dataSource.close();
        //

        String venuePhotoUrl = venue.getFeaturedPhotos().getItems().get(0).getPrefix()
                + "300x300" +
                venue.getFeaturedPhotos().getItems().get(0).getSuffix();
        Uri uri = Uri.parse(venuePhotoUrl);
        Picasso.with(venuePhoto.getContext()).load(uri).into(venuePhoto);

        /*getSupportActionBar().setTitle(venue.getName());
        getSupportActionBar().setSubtitle(venue.getLocation().getAddress());
*/
        toolbarLayout.setTitle(venue.getName());
        //subTitle.setText(venue.getLocation().getAddress());

        Log.i(LOG_TAG, "onCreate: tipsList is empty: " + tipsList.isEmpty());
        //setting layout manager
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new TipsRecyclerAdapter(tipsList);
        RecyclerView.ItemDecoration itemDecoration = new DividerItemDecoration(this, null);
        recyclerView.addItemDecoration(itemDecoration);
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

        dataSource.open();
        tipsList = dataSource.getAllRelevantTips(venueId);
        dataSource.close();
        Log.i(LOG_TAG, "displayFetchedTips: tipsList is empty: " + tipsList.isEmpty());

        adapter.updateData(tipsList);

    }
}
